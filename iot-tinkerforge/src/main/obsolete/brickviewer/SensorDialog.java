package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import com.tinkerforge.NotConnectedException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rapidpm.module.iot.tinkerforge.gui.fx.DateAxis;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Sensor;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.service.SensorConnector;
import org.rapidpm.module.iot.tinkerforge.sensor.singlevalue.Barometer;
import org.rapidpm.module.iot.tinkerforge.sensor.singlevalue.Temperature;
import org.rapidpm.module.iot.tinkerforge.sensor.TinkerForgeSensorSingleValue;

import java.util.Date;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class SensorDialog extends Stage {

    private TextField nameText;
    private TextField updateRateText;
    private Sensor sensor;

    private ObservableList seriesData;
    public XYChart.Series seriesTemp;
    private TinkerForgeSensorSingleValue<?> sensorConnection;

    public SensorDialog(StageStyle stageStyle, Sensor sensor) {
        super(stageStyle);
        this.sensor = sensor;

        seriesTemp = new XYChart.Series();
        seriesData = seriesTemp.getData();

        this.setTitle("Sensor-Einstellungen");

        TabPane tabPane = new TabPane();
        BorderPane mainPane = new BorderPane();

        //Create Tabs
        Tab tabA = new Tab();
        tabA.setText("Informationen");

        tabPane.getTabs().add(tabA);

        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        //Defining the Name text field
        nameText = new TextField();
        nameText.setPrefColumnCount(10);
        nameText.getText();
        GridPane.setConstraints(nameText, 0, 0);
        grid.getChildren().add(nameText);

        //Defining the Last Name text field
        updateRateText = new TextField();
        GridPane.setConstraints(updateRateText, 0, 1);
        grid.getChildren().add(updateRateText);

        //Defining the Submit button
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);

        //Defining the Clear button
        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 1);
        grid.getChildren().add(clear);

        tabA.setContent(grid);

        Tab tabB = new Tab();
        tabB.setText("Chart");
        //Add something in Tab
        VBox tabB_stack = new VBox();
        tabB_stack.setAlignment(Pos.CENTER);

        final ToggleButton tb2 = new ToggleButton("Press toConnect");
        tb2.setOnAction(e -> {
            if (tb2.isSelected()) connectSensor();
            else disconnectSensor();
        });

        tabB_stack.getChildren().addAll(tb2, createLineChart(sensor.getConnectionClass().getSimpleName(), seriesTemp));

        tabB.setContent(tabB_stack);
        tabPane.getTabs().add(tabB);

        setScene(new Scene(tabPane));

        StringProperty uidProperty = new SimpleStringProperty();
        uidProperty.set(sensor.getUID());
        Bindings.bindBidirectional(nameText.textProperty(), uidProperty);

       /* StringProperty updateRateProperty = new SimpleStringProperty();
        updateRateProperty.set(sensor.getAbfrageRate());
        Bindings.bindBidirectional(updateRateText.textProperty(), updateRateProperty);*/
    }

    private void connectSensor() {
        System.out.println("connect");
        //FIXME
        sensorConnection = new SensorConnector().connect(sensor);

        if (sensorConnection instanceof Temperature) {
            Temperature temperature = (Temperature) sensorConnection;
            temperature.actionTemperature = new TinkerForgeSensorSingleValue.SensorValueAction() {
                @Override
                public void workOnValue(int sensorvalue) {
                    double correctedSensorValue = sensorvalue / 100.0;
                    final XYChart.Data data = new XYChart.Data(new Date(), correctedSensorValue);
                    Platform.runLater(() -> seriesData.add(data));
                }
            };
        }else if (sensorConnection instanceof Barometer) {
            Barometer barometer = (Barometer) sensorConnection;
            barometer.actionAirPressure = new TinkerForgeSensorSingleValue.SensorValueAction() {
                @Override
                public void workOnValue(int sensorvalue) {
                    double correctedSensorValue = sensorvalue / 1000.0;
                    final XYChart.Data data = new XYChart.Data(new Date(), correctedSensorValue);
                    Platform.runLater(() -> seriesData.add(data));
                }
            };
        }
        new Thread(sensorConnection).start();
    }

    private void disconnectSensor() {
        System.out.println("disconnect");
        if (sensorConnection != null) {
            try {
                sensorConnection.ipcon.disconnect();
            } catch (NotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    private LineChart createLineChart(final String chartName, final XYChart.Series series) {
        final DateAxis dateAxis = new DateAxis();
        dateAxis.setLabel("Time");
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, yAxis);
        lineChart.setTitle(chartName);
        lineChart.getData().add(series);

        return lineChart;
    }
}
