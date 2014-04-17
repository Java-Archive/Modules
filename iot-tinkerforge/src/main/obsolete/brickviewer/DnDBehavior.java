package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.scene.Node;

public abstract class DnDBehavior<T extends Node> {

	protected final T node;

	public DnDBehavior(T node) {
		this.node = node;
	}

	public abstract void enableDrag();
}
