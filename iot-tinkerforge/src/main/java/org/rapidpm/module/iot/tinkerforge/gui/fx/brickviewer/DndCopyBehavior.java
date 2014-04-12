package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DndCopyBehavior<T extends MyNode> extends DnDBehavior<T> {

    public DndCopyBehavior(T node) {
        super(node);
    }

    @Override
    public void enableDrag() {
        node.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.getScene().setCursor(Cursor.MOVE);

                Dragboard db = node.startDragAndDrop(TransferMode.COPY);

				/* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();

                //TODO String
                content.putString(node.getClass().getCanonicalName());
                content.put(node.getDataFormat(), node.getDraggable());

                db.setContent(content);

                mouseEvent.consume();
            }
        });
    }
}
