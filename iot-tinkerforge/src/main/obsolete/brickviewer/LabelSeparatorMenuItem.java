package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer;

import javafx.scene.control.SeparatorMenuItem;

public class LabelSeparatorMenuItem extends SeparatorMenuItem {

	public LabelSeparatorMenuItem(String label) {
		this(label, true);
	}

	public LabelSeparatorMenuItem(String label, boolean topPading) {
		super();
		LabelSeparator content = new LabelSeparator(label, topPading);
		content.setPrefHeight(LabelSeparator.USE_COMPUTED_SIZE);
		content.setMinHeight(LabelSeparator.USE_PREF_SIZE);
		setContent(content);
	}
}