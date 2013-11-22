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

package org.rapidpm.commons.javafx.textfield;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * User: Sven Ruppert
 * Date: 02.10.13
 * Time: 10:38
 */
public class LabledTextField extends Pane {

    private final Label label = new Label();
    private final TextField textField = new TextField();
    private final HBox hb = new HBox();
    private int spacing = 10;

    public LabledTextField() {
        setAnchors(hb, 0.0);
        hb.getChildren().addAll(label, textField);
        hb.setSpacing(spacing);
        getChildren().add(hb);
        setPadding(new Insets(0, 10, 0, 10));
    }

    public void setLabelWidth(final double width) {
        label.setPrefWidth(width);
    }

    public void setTextFieldWidth(final double width) {
        textField.setPrefWidth(width);
    }

    public double getLabelWidth() {
        return label.getPrefWidth();
    }

    public double getTextFieldWidth() {
        return textField.getPrefWidth();
    }

    private void setAnchors(Node node, Double anchor) {
        AnchorPane.setBottomAnchor(node, anchor);
        AnchorPane.setLeftAnchor(node, anchor);
        AnchorPane.setRightAnchor(node, anchor);
        AnchorPane.setTopAnchor(node, anchor);
    }

//    public void setLabel


    public void setText(final String text) {
        textField.setText(text);
    }

    public String getText() {
        return textField.getText();
    }

    public String getLabelText() {
        return label.getText();
    }

    public void setLabelText(final String text) {
        label.setText(text);
    }

    public Label getLabel() {
        return label;
    }

    public TextField getTextField() {
        return textField;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }
}
