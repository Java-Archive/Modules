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

package junit.org.rapidpm.module.iot.tinkerforge.wjaxdemo;

import com.tinkerforge.*;
import junit.org.rapidpm.module.iot.tinkerforge.TinkerChase;
import org.rapidpm.module.iot.tinkerforge.actor.LCD20x4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Sven Ruppert on 04.11.2014.
 */
public class Main {

  private static final String host = "localhost";
  private static final int port = 4223;
  private static final IPConnection ipcon = new IPConnection();


  private static final BrickletNFCRFID nfc = new BrickletNFCRFID("oCT", ipcon);
  private static short currentTagType = 0;

  private static LCD20x4 lcd;

  private static final Thread thread = new Thread(() -> {
    // Select NFC Forum Type 2 tag
    while (true) {
      try {
        System.out.println("request TAG ID");
        nfc.requestTagID(BrickletNFCRFID.TAG_TYPE_TYPE2);
        Thread.sleep(1000);
      } catch (TimeoutException | InterruptedException | NotConnectedException e) {
        e.printStackTrace();
      }
    }
  });

  public static void main(String[] args) {

    try {
      ipcon.connect(host, port);
      lcd = new LCD20x4("og5", ipcon);
      lcd.setIpcon(ipcon);

    } catch (IOException | AlreadyConnectedException e) {
      e.printStackTrace();
    }

    startNFCDemo();
    thread.run();


//    start2048Demo();
    startServoDemo();

    startTinkerGame();


    WaitForQ.waitForQ();


    try {
      ipcon.disconnect();
    } catch (NotConnectedException e) {
      e.printStackTrace();
    }

  }

  private static TinkerChase tinkerChase;

  private static void startTinkerGame() {
    try {
      tinkerChase = new TinkerChase(ipcon);
    } catch (Exception e) {
      System.out.println("e = " + e);
    }
  }

  private static void startServoDemo() {

  }

  private static void start2048Demo() {

  }

  private static void startNFCDemo() {
//    final BrickletNFCRFID nfc = new BrickletNFCRFID("oCT", ipcon);
//    writeNFC(nfc);
    nfc.addStateChangedListener((state, idle) -> {
      try {

        if (idle) {
          currentTagType = (short) ((currentTagType + 1) % 3);
          nfc.requestTagID(currentTagType);
        }

        if (state == BrickletNFCRFID.STATE_REQUEST_TAG_ID_READY) {
          BrickletNFCRFID.TagID tagID = nfc.getTagID();
          String s = "Found tag of type " + tagID.tagType +
              " with ID [" + Integer.toHexString(tagID.tid[0]);

          for (int i = 1; i < tagID.tidLength; i++) {
//            s += " " + Integer.toHexString(tagID.tid[i]);
            s += " " + String.valueOf((byte) tagID.tid[i]);
          }

          s += "]";
          System.out.println(s);

//          short[] data = nfc.getPage();
//          String sTXT = "" ;
//          for (final short i1 : data) { sTXT += String.valueOf((char) i1); }
//          sTXT = sTXT.trim();
//          sTXT += "";
//          System.out.println(sTXT);
//          lcd.printLine1( sTXT);
        }

        if (state == BrickletNFCRFID.STATE_WRITE_PAGE_READY) {
          // Request pages 5-8
          nfc.requestPage(5);
          System.out.println("Requesting data...");
        }


        if (state == BrickletNFCRFID.STATE_REQUEST_PAGE_READY) {
          // Get and print pages
          short[] data = nfc.getPage();
          String s = "Read data: [";
          for (final short i1 : data) {
            s += String.valueOf((char) i1);
          }
          s = s.trim();
          s += "]";
          System.out.println(s);
          lcd.printLine1(s);
        }


      } catch (Exception e) {
        System.out.println(e);
      }
    });

//    nfc.requestTagID(BrickletNFCRFID.TAG_TYPE_MIFARE_CLASSIC);


  }

  private static void writeNFC(BrickletNFCRFID nfc) {
    nfc.addStateChangedListener((state, idle) -> {
      try {
        if (state == BrickletNFCRFID.STATE_REQUEST_TAG_ID_READY) {
          System.out.println("Tag found");
          // Write 16 byte to pages 5-8
          short[] fourPagesArray = new short[16];  //immer 4x4
          Arrays.fill(fourPagesArray, (byte) 0);
          final byte[] data2Write = "Hallo".getBytes();
          System.arraycopy(data2Write, 0, fourPagesArray, 0, data2Write.length);
          nfc.writePage(5, fourPagesArray);
          System.out.println("Writing data...");
        } else if (state == BrickletNFCRFID.STATE_WRITE_PAGE_READY) {
          // Request pages 5-8
          nfc.requestPage(5);
          System.out.println("Requesting data...");
        } else if (state == BrickletNFCRFID.STATE_REQUEST_PAGE_READY) {
          // Get and print pages
          short[] data = nfc.getPage();
          String s = "Read data: [";
          for (final short i1 : data) {
            s += String.valueOf((char) i1);
          }
          s = s.trim();
          s += "]";
          System.out.println(s);
        } else if ((state & (1 << 6)) == (1 << 6)) {
          // All errors have bit 6 set
          System.out.println("Error: " + state);
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    });
  }


  public static class WaitForQ {
    public static void waitForQ() {
      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

      final Thread t = new Thread(() -> {
        System.out.println("press Q THEN ENTER to terminate");
        int quit = 0;
        while (true) {
          try {
            Thread.sleep(10000);
            String msg = null;
            while (true) {
              try {
                msg = in.readLine();
              } catch (Exception e) {
              }
              if (msg != null && msg.equals("Q")) {
                quit = 1;
              }
              if (quit == 1) break;
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          break;
        }
      });
      t.start();
    }
  }
}
