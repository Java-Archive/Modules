package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.beans.property.DoubleProperty;

public interface IBoundableLine {
	public DoubleProperty getxProperty();

	public DoubleProperty getyProperty();
	
	public String getName();
}
