/*
 * Copyright [2013] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.rapidpm.demo.javafx.tableview.filtered.control;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * @author Sven Ruppert
 */
public class TitledSeparatorMenuItem extends SeparatorMenuItem {
    final Label label;

    public TitledSeparatorMenuItem(String title) {
        this.label = new Label(title);

        final HBox line = HBoxBuilder.create()
                .styleClass("line")
                .minHeight(2)
                .prefHeight(2)
                .maxHeight(Region.USE_PREF_SIZE)
                .build();

        final StackPane pane = new StackPane();
        pane.getChildren().setAll(line, label);
        pane.setPadding(new Insets(10, 6, 6, 6)); // setting padding in css doesn't work great; do it here
        pane.getStyleClass().add("container");

        setContent(pane);
        getStyleClass().add("titled-separator-menu-item");
    }

    public Label getLabel() {
        return label;
    }
}