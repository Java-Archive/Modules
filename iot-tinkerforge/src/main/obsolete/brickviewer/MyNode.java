package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

import java.util.Set;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public abstract class MyNode extends Pane implements IBoundableLine, IDndBehaviorable {

    private DnDBehavior DnDBehavior;
    private DoubleProperty xProperty;
    private DoubleProperty yProperty;

    protected MyNode(DoubleProperty x, DoubleProperty y) {
        this.xProperty = x;
        this.yProperty = y;

        setLayoutX(x.get());
        setLayoutY(y.get());
        x.bind(layoutXProperty());
        y.bind(layoutYProperty());
    }

    public DnDBehavior getDnDBehavior() {
        return DnDBehavior;
    }

    public void setDnDBehavior(DnDBehavior anchorDnDBehavior) {
        this.DnDBehavior = anchorDnDBehavior;
        if (this.DnDBehavior != null) {
            anchorDnDBehavior.enableDrag();
        }
    }

    @Override
    public DoubleProperty getxProperty() {
        return xProperty;
    }

    @Override
    public DoubleProperty getyProperty() {
        return yProperty;
    }

    public void setContextMenu(Set<MyNode> boundableLineSet, final Group centerGroup) {
        LabelSeparatorMenuItem item1 = new LabelSeparatorMenuItem(
                "Verbindbare Knoten");

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setMinWidth(100);
        contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {

                ObservableList<MenuItem> items = contextMenu.getItems();
                items.clear();

                items.add(item1);
                for (final IBoundableLine boundableLine : boundableLineSet) {
                    MenuItem e = new MenuItem(boundableLine
                            .getName());
                    e.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            BoundLine labelline = new BoundLine(MyNode.this,
                                    boundableLine);
                            centerGroup.getChildren().addAll(labelline);
                        }
                    });
                    items.add(e);
                }
            }
        });
        contextMenu.getItems().add(item1);

        //Show Context on Pane
        EventHandler<MouseEvent> eh = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(MyNode.this, event.getScreenX(), event.getScreenY());
                    event.consume();
                }
            }
        };
        this.addEventFilter(MouseEvent.ANY, eh);
    }
    public abstract Object getDraggable();

    public abstract DataFormat getDataFormat();
}
