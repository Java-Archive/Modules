package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class BoundLine extends Line {

	BoundLine(IBoundableLine anchor1, IBoundableLine anchor2) {

		startXProperty().bind(anchor1.getxProperty());
		startYProperty().bind(anchor1.getyProperty());

		endXProperty().bind(anchor2.getxProperty());
		endYProperty().bind(anchor2.getyProperty());
		setStrokeWidth(2);
		setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
		setStrokeLineCap(StrokeLineCap.BUTT);
		getStrokeDashArray().setAll(10.0, 5.0);
		setMouseTransparent(true);
	}
}