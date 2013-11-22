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

package org.rapidpm.modul.javafx.tableview.filtered.control;

import javafx.geometry.Insets;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * @author Sven Ruppert
 */
public class TextFieldMenuItem extends CustomMenuItem {
    final TextField textField;

    public TextFieldMenuItem(TextField textField) {
        this.textField = textField;

        final BorderPane pane = new BorderPane();
        pane.setCenter(textField);
        pane.setPadding(new Insets(0, 2, 0, 2)); // setting padding in css doesn't work great; do it here
        pane.getStyleClass().add("container");

        setContent(pane);
        setHideOnClick(false);
        getStyleClass().add("textfield-menu-item");
    }

    public TextField getTextField() {
        return textField;
    }

}