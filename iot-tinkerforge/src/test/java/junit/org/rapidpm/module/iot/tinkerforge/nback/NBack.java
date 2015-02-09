package junit.org.rapidpm.module.iot.tinkerforge.nback;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lenaernst on 12.04.14.
 */
public class NBack extends Application {

  private static final String host = "localhost";
  private static final int port = 4223;
  private static final String UID = "j5K";

  private List<Integer> leftNumberList;
  private Label right;
  private Label left;

  public static void main(String[] args) throws AlreadyConnectedException, IOException, TimeoutException, NotConnectedException {
    launch(args);
  }

  private List<Integer> generateRandomList() {
    final Random random = new Random();
    return Stream
        .generate(() -> random.nextInt(5))
        .limit(5)
        .collect(Collectors.toList());
  }

  @Override
  public void start(Stage stage) throws Exception {

    leftNumberList = generateRandomList();

    GridPane grid = new GridPane();
    left = new Label("");
    grid.add(left, 0, 1);

    right = new Label("");
    grid.add(right, 1, 1);

    grid.setHgap(100);

    Button start = new Button("Start");
    start.setOnAction(e -> {
      new DisplayThread().start();
        start.setDisable(true);
    });
    grid.add(start, 1, 2);

    StackPane layout = new StackPane();
    layout.setStyle("-fx-background-color: whitesmoke; -fx-padding: 10;");
    layout.getChildren().addAll(grid);
    stage.setScene(new Scene(layout, 600, 400));
    stage.show();
  }


  public class DisplayThread extends Thread {
    @Override
    public void run() {
      for (Integer integer : leftNumberList) {
        try {
          Platform.runLater(()->left.setText(integer.toString()));
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
