package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class LabelSeparator extends StackPane {

	private Label lblText;

	public LabelSeparator(String label) {
		this(label, true);
	}

	public LabelSeparator(String label, boolean topPading) {
		// HBox line = HBoxBuilder.create().styleClass("line").minHeight(2)
		// .prefHeight(2).prefWidth(USE_PREF_SIZE)
		// .maxHeight(USE_PREF_SIZE).build();
		HBox line = new HBox();
		line.setPrefHeight(2);
		line.setMinHeight(2);
		line.setMaxHeight(USE_PREF_SIZE);
		line.setPrefWidth(USE_PREF_SIZE);
		line.getStyleClass().add("line");
		// line.setStyle("-fx-border-color: derive(-fx-background, -25%) -fx-background derive(-fx-background, 40%) -fx-background;");

		if (topPading) {
			setPadding(new Insets(10, 0, 0, 0));
		}
		lblText = new Label(label);
		this.getChildren().addAll(line, lblText);
		this.getStyleClass().add("label-separator");
		// this.setStyle("-fx-background-color: #090a0c,linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), linear-gradient(#20262b, #191d22),        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));    -fx-background-radius: 5,4,3,5;    -fx-background-insets: 0,1,2,0;    -fx-text-fill: white;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );    -fx-font-family: 'Arial';    -fx-text-fill: linear-gradient(white, #d0d0d0);    -fx-font-size: 12px;    -fx-padding: 2 10;");

	}

	public void setText(String label) {
		textProperty().set(label);
	}

	public String getText() {
		return textProperty().get();
	}

	public StringProperty textProperty() {
		return lblText.textProperty();
	}
}
