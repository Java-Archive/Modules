/*
 * Copyright [2014] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package junit.org.rapidpm.module.iot.tinkerforge;

import com.tinkerforge.*;
import com.tinkerforge.BrickletDualButton.StateChangedListener;
import com.tinkerforge.BrickletJoystick.PositionReachedListener;

/**
 * A small multiplayer game in which one player must chase the opponent via two joysticks on
 * an lcd. The currently chasing player is signalled with the DualButton lights.
 */
public class TinkerChase {
  private static final int WINNING_SCORE = 3;
  private static final byte[] DIGITS = {0x3f, 0x06, 0x5b, 0x4f,
      0x66, 0x6d, 0x7d, 0x07,
      0x7f, 0x6f, 0x77, 0x7c,
      0x39, 0x5e, 0x79, 0x71}; // 0~9,A,b,C,d,E,F
  private Player player1;
  private Player player2;
  private GameStatus gameStatus;
  private Hardware hardware;

  public TinkerChase(IPConnection ipConnection) throws Exception {
    try {
      hardware = new Hardware(ipConnection);
      gameStatus = new GameStatus();
      gameStatus.lastCatchedSystemMillis = System.currentTimeMillis();

      player1 = new Player();
      player1.setRandomPosition();
      player1.symbol = "x";
      player1.isCatcher = true;
      player1.isLeftPlayer = true;

      player2 = new Player();
      player2.setRandomPosition();
      player2.symbol = "o";

      MoveCursorListener moveCursorListener1 = new MoveCursorListener(hardware, gameStatus, player1, player2);
      MoveCursorListener moveCursorListener2 = new MoveCursorListener(hardware, gameStatus, player2, player1);
      hardware.addPositionReachedListeners(moveCursorListener1, moveCursorListener2);
      hardware.addResetButtonListener((buttonL, buttonR, ledL, ledR) -> {
        if (buttonL == BrickletDualButton.BUTTON_STATE_RELEASED || buttonR == BrickletDualButton.BUTTON_STATE_RELEASED) {
          performReset();
        }
      });

      hardware.movePlayer(player1, player1.currentX, player1.currentY, player2);

      new SwitchCatchingTimer(hardware, gameStatus, player1, player2).start();

//			System.out.println("Press key to exit");
//			System.in.read();
    } finally {
//			hardware.disconnect();
    }
  }

  protected void performReset() {
    player1.reset();
    player2.reset();
    player1.isCatcher = true;
    gameStatus.lastCatchedSystemMillis = System.currentTimeMillis();
    hardware.updateButtonLights(player1);
    hardware.updateScore(player1, player2);
    hardware.updateLcd(player1, player2);
  }

  public static void main(String args[]) throws Exception {
    final IPConnection ipConnection = new IPConnection();

    new TinkerChase(ipConnection);
  }

  private class GameStatus {
    long lastCatchedSystemMillis = 0;
  }

  private class Player {
    short currentX = 0, currentY = 0;
    int score = 0;
    boolean isCatcher = false;
    boolean isLeftPlayer = false;
    String symbol;

    public void setRandomPosition() {
      currentX = (short) (20 * Math.random());
      currentY = (short) (20 * Math.random());
    }

    public void reset() {
      score = 0;
      setRandomPosition();
      isCatcher = false;
    }
  }

  private class MoveCursorListener implements PositionReachedListener {
    private Player player;
    private Player opponent;
    private Hardware hardware;
    private GameStatus gameStatus;

    private MoveCursorListener(Hardware hardware, GameStatus gameStatus, Player player, Player opponent) {
      this.hardware = hardware;
      this.gameStatus = gameStatus;
      this.player = player;
      this.opponent = opponent;
      hardware.updateButtonLights(player);
      hardware.updateScore(player, opponent);
    }

    @Override
    public void positionReached(short x, short y) {
      try {
        move(x, y);
        catched();
        finished();
      } catch (TimeoutException | NotConnectedException e) {
        e.printStackTrace();
      }
    }

    private void catched() {
      if (player.isCatcher) {
        if (player.currentX == opponent.currentX && player.currentY == opponent.currentY) {
          // catched
          player.isCatcher = !player.isCatcher;
          opponent.isCatcher = !opponent.isCatcher;
          player.score++;
          gameStatus.lastCatchedSystemMillis = System.currentTimeMillis();
          hardware.updateScore(player, opponent);
          hardware.updateButtonLights(player);
        }
      }
    }

    private void finished() {
      if (player.score >= WINNING_SCORE || opponent.score >= WINNING_SCORE) {
        String winnerSymbol = player.score >= WINNING_SCORE ? player.symbol
            : opponent.symbol;
        hardware.winningAnimation(winnerSymbol);
      }
    }

    private void move(short x, short y) throws TimeoutException,
        NotConnectedException {
      short newX = player.currentX;
      short newY = player.currentY;
      if (x == 100) // right
        newX++;
      else if (x == -100) // left
        newX--;
      if (y == 100) // right
        newY--;
      else if (y == -100) // left
        newY++;
      newX = (short) ((20 + newX) % 20);
      newY = (short) ((4 + newY) % 4);
      hardware.movePlayer(player, newX, newY, opponent);
      player.currentX = newX;
      player.currentY = newY;
      System.out.println(player.symbol + " moved to " + newX + "," + newY);
    }
  }

  private class SwitchCatchingTimer extends Thread {

    private Player player1;
    private Player player2;
    private Hardware hardware;
    private GameStatus gameStatus;

    public SwitchCatchingTimer(Hardware hardware, GameStatus gameStatus, Player player1, Player player2) {
      this.hardware = hardware;
      this.gameStatus = gameStatus;
      this.player1 = player1;
      this.player2 = player2;
    }

    @Override
    public void run() {
      while (player1.score < WINNING_SCORE && player2.score < WINNING_SCORE) {
        try {
          Thread.sleep(1000);
          if (gameStatus.lastCatchedSystemMillis + 10 * 1000 <= System.currentTimeMillis()
              && player1.score < WINNING_SCORE && player2.score < WINNING_SCORE) {
            gameStatus.lastCatchedSystemMillis = System.currentTimeMillis();
            player1.isCatcher = !player1.isCatcher;
            player2.isCatcher = !player2.isCatcher;
            hardware.updateButtonLights(player1);
          }
        } catch (InterruptedException e) {
          break;
        }
      }
    }
  }

  private class Hardware {
    private static final String HOST = "localhost";
    private static final int PORT = 4223;
    private static final String DUAL_BUTTON_UID = "j3V";
    private static final String JOYSTICK1_UID = "gSq";
    private static final String JOYSTICK2_UID = "hDp";
    private static final String LCD_UID = "jvX";
    private static final String SEGMENT_DISPLAY_UID = "kTU";

    private BrickletLCD20x4 lcd;
    private BrickletJoystick joy1;
    private BrickletJoystick joy2;
    private BrickletSegmentDisplay4x7 score;
    private BrickletDualButton buttonLights;
    private IPConnection ipcon;

    public Hardware(IPConnection ipcon) throws Exception {
      this.ipcon = ipcon;
      lcd = new BrickletLCD20x4(LCD_UID, ipcon);
      joy1 = new BrickletJoystick(JOYSTICK1_UID, ipcon);
      joy2 = new BrickletJoystick(JOYSTICK2_UID, ipcon);
      score = new BrickletSegmentDisplay4x7(SEGMENT_DISPLAY_UID, ipcon);
      buttonLights = new BrickletDualButton(DUAL_BUTTON_UID, ipcon);

//			ipcon.connect(HOST, PORT); // Connect to brickd

      lcd.clearDisplay();
      lcd.backlightOn();

      joy1.setDebouncePeriod(200);
      joy2.setDebouncePeriod(200);
      // Configure threshold for "x or y value outside of [-99..99]"
      joy1.setPositionCallbackThreshold('o', (short) -99, (short) 99, (short) -99, (short) 99);
      joy2.setPositionCallbackThreshold('o', (short) -99, (short) 99, (short) -99, (short) 99);
    }

    public void updateLcd(Player player1, Player player2) {
      try {
        lcd.clearDisplay();
        movePlayer(player1, player1.currentX, player1.currentY, player2);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    public void movePlayer(Player player, short newX, short newY,
                           Player opponent) throws TimeoutException, NotConnectedException {
      lcd.writeLine(player.currentY, player.currentX, " ");
      lcd.writeLine(newY, newX, player.symbol);
      lcd.writeLine(opponent.currentY, opponent.currentX, opponent.symbol);

    }

    public void disconnect() throws NotConnectedException {
      ipcon.disconnect();
    }

    private void updateButtonLights(Player player) {
      try {
        if (player.isLeftPlayer && player.isCatcher
            || !player.isLeftPlayer && !player.isCatcher)
          buttonLights.setLEDState((short) 0, (short) 1);
        else
          buttonLights.setLEDState((short) 1, (short) 0);
      } catch (TimeoutException e) {
        e.printStackTrace();
      } catch (NotConnectedException e) {
        e.printStackTrace();
      }

    }

    private void updateScore(Player player, Player opponent) {
      short[] segments;
      if (player.isLeftPlayer)
        segments = new short[]{DIGITS[player.score / 10], DIGITS[player.score % 10], DIGITS[opponent.score / 10], DIGITS[opponent.score % 10]};
      else
        segments = new short[]{DIGITS[opponent.score / 10], DIGITS[opponent.score % 10], DIGITS[player.score / 10], DIGITS[player.score % 10]};
      try {
        score.setSegments(segments, (short) 7, false);
      } catch (TimeoutException e) {
        e.printStackTrace();
      } catch (NotConnectedException e) {
        e.printStackTrace();
      }
    }

    public void winningAnimation(final String winnerSymbol) {
      new Thread() {
        @Override
        public void run() {
          try {
            for (int i = 0; i < 10; i++) {
              lcd.clearDisplay();
              Thread.sleep(500);
              lcd.writeLine((short) 0, (short) 0, "####################");
              lcd.writeLine((short) 1, (short) 0, "###  Gewinner: " + winnerSymbol + "  ###");
              lcd.writeLine((short) 2, (short) 0, "####################");
              lcd.writeLine((short) 3, (short) 0, "####################");
              Thread.sleep(500);
            }
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
      }.start();
    }

    public void addPositionReachedListeners(
        PositionReachedListener moveCursorListener1,
        PositionReachedListener moveCursorListener2) {
      joy1.addPositionReachedListener(moveCursorListener1);
      joy2.addPositionReachedListener(moveCursorListener2);
    }

    public void addResetButtonListener(StateChangedListener listener) {
      buttonLights.addStateChangedListener(listener);
    }
  }

}
