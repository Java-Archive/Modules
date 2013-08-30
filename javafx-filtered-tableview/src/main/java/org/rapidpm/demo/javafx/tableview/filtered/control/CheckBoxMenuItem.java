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
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.layout.BorderPane;

/**
 * @author Sven Ruppert
 */
public class CheckBoxMenuItem extends CustomMenuItem {
    private final CheckBox checkBox;

    public CheckBoxMenuItem(CheckBox checkBox) {
        this.checkBox = checkBox;
        checkBox.setPrefWidth(Math.max(144, checkBox.getPrefWidth())); // 144px is approx the size of our TextFieldMenuItem
        checkBox.setMaxWidth(Double.MAX_VALUE);
        checkBox.setFocusTraversable(false);

        final BorderPane pane = new BorderPane();
        pane.setCenter(checkBox);
        pane.setPadding(new Insets(0, 2, 0, 2)); // setting padding in css doesn't work great; do it here
        pane.getStyleClass().add("container");
        pane.setMaxWidth(Double.MIN_VALUE);

        setContent(pane);
        setHideOnClick(false);
        getStyleClass().add("checkbox-menu-item");
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

}
