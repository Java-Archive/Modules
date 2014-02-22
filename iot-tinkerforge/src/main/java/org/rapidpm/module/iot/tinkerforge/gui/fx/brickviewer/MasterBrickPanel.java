package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Masterbrick;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Sensor;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class MasterBrickPanel extends MyNode {

    public final static DataFormat DATA_FORMAT = new DataFormat("masterbrick");
    private static final int ROW_HEIGHT = 24;

    private Label brickNameLabel;
    Masterbrick masterbrick;
    private ListView<Sensor> sensorListView;

    public MasterBrickPanel(Masterbrick masterbrick, DoubleProperty x, DoubleProperty y) {
        super(x, y);

        this.masterbrick = masterbrick;

        brickNameLabel = new Label(masterbrick.getName(), new ImageView(new Image(MasterBrickPanel.class.getResourceAsStream("masterbrick.jpg"))));

        sensorListView = new ListView<>();
        ObservableList<Sensor> items = FXCollections.observableList(masterbrick.getSensorList());
        sensorListView.setItems(items);
        sensorListView.setPrefHeight(sensorListView.getItems().size() * ROW_HEIGHT + 2);
        sensorListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Sensor selectedSensor = sensorListView.getSelectionModel().getSelectedItem();
                    SensorDialog dialog = new SensorDialog(StageStyle.UTILITY, selectedSensor);
                    dialog.show();
                }
            }
        });

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(brickNameLabel, sensorListView);
        getChildren().add(vbox);

        configureBorder(this);
    }

    public void setMasterbrick(Masterbrick masterbrick) {
        this.masterbrick = masterbrick;
    }

    private static void configureBorder(final Region region) {
        region.setStyle("-fx-background-color: white;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 6;"
                + "-fx-padding: 6;");
    }

    @Override
    public String getName() {
        return masterbrick.getName();
    }

    @Override
    public Object getDraggable() {
        return masterbrick;
    }

    @Override
    public DataFormat getDataFormat() {
        return DATA_FORMAT;
    }
}
