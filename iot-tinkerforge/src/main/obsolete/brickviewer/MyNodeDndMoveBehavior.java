package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class MyNodeDndMoveBehavior extends DnDBehavior<MyNode> {

    public MyNodeDndMoveBehavior(MyNode anchor) {
        super(anchor);
    }

    @Override
    public void enableDrag() {
        final Delta dragDelta = new Delta();

        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // remember initial mouse cursor coordinates
                // and node position
                dragDelta.x = node.getLayoutX() - mouseEvent.getSceneX();
                dragDelta.y = node.getLayoutY() - mouseEvent.getSceneY();
                node.getScene().setCursor(Cursor.MOVE);
                mouseEvent.consume();
            }
        });
        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.getScene().setCursor(Cursor.HAND);
                mouseEvent.consume();
            }
        });
        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // shift node from its initial position by delta
                // calculated from mouse cursor movement
                node.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                node.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);

                mouseEvent.consume();
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    node.getScene().setCursor(Cursor.DEFAULT);
                    mouseEvent.consume();
                }
            }
        });
    }
}
