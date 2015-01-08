
package junit.org.rapidpm.module.iot.tinkerforge.nback;

import com.tinkerforge.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by Alex Bischof on 12.04.14.
 */
public class PushMe extends Application {

  private static final String host = "localhost";
  private static final int port = 4223;
  private static final String UID = "jSS";

  private Map<Integer, Label> numLabelMap;
  private Label currentLabel;
  private Random random = new Random();
  private int randomNum;

  public static void main(String[] args) throws AlreadyConnectedException, IOException, TimeoutException, NotConnectedException {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    // create a grid with some sample data.
    GridPane grid = new GridPane();
    grid.setGridLinesVisible(true);

    //Adds labels to grid 3x4
    numLabelMap = new HashMap<>();
    IntStream.range(0, 12)
        .boxed()
        .forEach(i -> {
          Label label = new Label(String.valueOf(i));
          label.setVisible(false);
          numLabelMap.put(i, label);
          grid.addRow(i % 3, label);
        });

    for (Node n : grid.getChildren()) {
      if (n instanceof Control) {
        Control control = (Control) n;
        control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        control.setStyle("-fx-background-color: cornsilk; -fx-alignment: center;");
      }
      if (n instanceof Pane) {
        Pane pane = (Pane) n;
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.setStyle("-fx-background-color: cornsilk; -fx-alignment: center;");
      }
    }

    grid.setStyle("-fx-background-color: palegreen; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");
    grid.setSnapToPixel(false);

    ColumnConstraints oneFourth = new ColumnConstraints();
    oneFourth.setPercentWidth(100 / 4.0);
    oneFourth.setHalignment(HPos.CENTER);
    grid.getColumnConstraints().addAll(oneFourth, oneFourth, oneFourth, oneFourth);
    RowConstraints oneHalf = new RowConstraints();
    oneHalf.setPercentHeight(100 / 3.0);
    oneHalf.setValignment(VPos.CENTER);
    grid.getRowConstraints().addAll(oneHalf, oneHalf, oneHalf);

    activateNextLabel();

    StackPane layout = new StackPane();
    layout.setStyle("-fx-background-color: whitesmoke; -fx-padding: 10;");
    layout.getChildren().addAll(grid);
    stage.setScene(new Scene(layout, 600, 400));
    stage.show();

    new TouchSensorThread().start();
  }

  private void activateNextLabel() {
    Platform.runLater(() -> {
      if (currentLabel != null) {
        currentLabel.setVisible(false);
      }
      randomNum = random.nextInt(12);
      currentLabel = numLabelMap.get(randomNum);
      currentLabel.setVisible(true);
    });
  }

  public class TouchSensorThread extends Thread {

    private BrickletMultiTouch touch;

    public TouchSensorThread() throws AlreadyConnectedException, IOException {
      IPConnection ipcon = new IPConnection();
      touch = new BrickletMultiTouch(UID, ipcon);
      ipcon.connect(host, port);
    }

    @Override
    public void run() {
      touch.addTouchStateListener(touchState -> {
//        int numPressed = -1;
        String str = "";

        if((touchState & (1 << 12)) == (1 << 12)) {
          str += "In proximity, ";
        }

        if((touchState & 0xfff) == 0) {
          str += "No electrodes touched" + System.getProperty("line.separator");
        } else {
          str += "Electrodes ";
          for(int i = 0; i < 12; i++) {
            if((touchState & (1 << i)) == (1 << i)) {
              if (randomNum == i) {
                activateNextLabel();
              }
              str += i + " ";
            }
          }
          str += "touched" + System.getProperty("line.separator");
        }

        System.out.println(str);


//        if ((touchState & 0xfff) != 0) {
//          //Looks better then naster for loop with bit operations, isn't it? ;)
//          numPressed = (int) (Math.log(Integer.highestOneBit(touchState)) / Math.log(2));
//
//          if (randomNum == numPressed) {
//            activateNextLabel();
//          }
//          System.out.println("numPressed = " + numPressed);
//        }
      });
    }
  }
}
