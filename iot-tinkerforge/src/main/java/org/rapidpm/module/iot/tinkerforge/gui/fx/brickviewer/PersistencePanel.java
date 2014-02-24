package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.Region;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Persistence;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class PersistencePanel extends MyNode {

    public final static DataFormat DATA_FORMAT = new DataFormat("persistence");

    private Label databaseLabel;
    Persistence persistence;

    public PersistencePanel(Persistence persistence, DoubleProperty x, DoubleProperty y) {
        super(x, y);

        this.persistence = persistence;

        databaseLabel = new Label(persistence.getBezeichnung(), new ImageView(new Image(PersistencePanel.class.getResourceAsStream("database.jpg"))));

        getChildren().add(databaseLabel);

        configureBorder(this);
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
        return persistence.getBezeichnung();
    }

    @Override
    public Object getDraggable() {
        return persistence;
    }

    @Override
    public DataFormat getDataFormat() {
        return DATA_FORMAT;
    }
}
