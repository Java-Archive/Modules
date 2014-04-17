package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Masterbrick;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Persistence;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.service.MasterBrickService;

import java.util.HashSet;
import java.util.Set;

public class TinkerforgeApplication extends Application {
    private MasterBrickService masterBrickService;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private Set<MyNode> boundableLineSet = new HashSet<>();
    private VBox selectableComponentsVBox;
    private Pane centerPane;
    private Group centerGroup;

    @Override
    public void start(final Stage stage) throws Exception {

        masterBrickService = new MasterBrickService();

        final BorderPane sceneRoot = new BorderPane();

        // Left
        final Pane leftPane = new Pane();
        leftPane.setStyle("-fx-background-color: orange;");

        VBox selectableComponentsVBox1 = getSelectableComponentsVBox();

        //Auswählbare Objekte
        masterBrickService.findMasterbricks().stream().map(e -> new MasterBrickPanel(e,
                new SimpleDoubleProperty(100), new SimpleDoubleProperty(100))).forEach((e) -> {
            e.setDnDBehavior(new DndCopyBehavior(e));
            selectableComponentsVBox1.getChildren().add(e);
        });


        PersistencePanel dbPanel = new PersistencePanel(new Persistence("ArangoDB"), new SimpleDoubleProperty(100), new SimpleDoubleProperty(100));
        dbPanel.setDnDBehavior(new DndCopyBehavior(dbPanel));

        selectableComponentsVBox1.getChildren().add(dbPanel);

        leftPane.getChildren().add(selectableComponentsVBox1);
        sceneRoot.setLeft(leftPane);

        // Center
        centerPane = new Pane();
        centerPane.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != centerPane
                        && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY);
                }

                event.consume();
            }
        });
        centerPaneDropBehavior(centerPane);
        centerPane.setStyle("-fx-background-color: white;");
        centerGroup = new Group();//new MasterBrickPanel("hkajshkjah"));

        centerPane.getChildren().add(centerGroup);

        BorderPane.setAlignment(centerPane, Pos.TOP_LEFT);
        sceneRoot.setCenter(centerPane);

        final Scene scene = new Scene(sceneRoot, 400, 300);
        //scene.getStylesheets().add(
        //      TinkerforgeApplication.class.getResource("my.css").toExternalForm());

        stage.setTitle("Tinkerforge Masterbricks");
        stage.setScene(scene);
        stage.show();
    }

    private void centerPaneDropBehavior(Pane panelsPane) {
        panelsPane.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasContent(MasterBrickPanel.DATA_FORMAT)) {
                    dropMasterbrick(event, db);
                } else if (db.hasContent(PersistencePanel.DATA_FORMAT)) {
                    dropDatabase(event, db);
                }
                /*
                 * let the source know whether the string was successfully
                 * transferred and used
                 */
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }

    private void dropMasterbrick(DragEvent event, Dragboard db) {
        Masterbrick copy = (Masterbrick) db.getContent(MasterBrickPanel.DATA_FORMAT);

        double sceneX = event.getX();
        double sceneY = event.getY();

        DoubleProperty startX = new SimpleDoubleProperty(sceneX);
        DoubleProperty startY = new SimpleDoubleProperty(sceneY);
        MasterBrickPanel mbp = new MasterBrickPanel(copy, startX, startY);

        mbp.setDnDBehavior(new MyNodeDndMoveBehavior(mbp));

        // ContextMenu
        mbp.setContextMenu(boundableLineSet, centerGroup);

        boundableLineSet.add(mbp);
        centerPane.getChildren().add(new Group(mbp));
    }

    private void dropDatabase(DragEvent event, Dragboard db) {
        Persistence copy = (Persistence) db.getContent(PersistencePanel.DATA_FORMAT);

        double sceneX = event.getX();
        double sceneY = event.getY();

        DoubleProperty startX = new SimpleDoubleProperty(sceneX);
        DoubleProperty startY = new SimpleDoubleProperty(sceneY);
        PersistencePanel mbp = new PersistencePanel(copy, startX, startY);

        mbp.setDnDBehavior(new MyNodeDndMoveBehavior(mbp));

        // ContextMenu
        mbp.setContextMenu(boundableLineSet, centerGroup);

        boundableLineSet.add(mbp);
        centerPane.getChildren().add(new Group(mbp));
    }

    public VBox getSelectableComponentsVBox() {
        if (selectableComponentsVBox == null) {
            selectableComponentsVBox = new VBox(2);
        }
        return selectableComponentsVBox;
    }
}
