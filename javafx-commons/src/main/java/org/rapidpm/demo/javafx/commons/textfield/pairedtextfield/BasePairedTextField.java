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

package org.rapidpm.demo.javafx.commons.textfield.pairedtextfield;

import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;

/**
 * User: Sven Ruppert
 * Date: 09.10.13
 * Time: 12:46
 */
public abstract class BasePairedTextField extends Pane {
    protected final TextField leftTextField = new TextField();
    protected final TextField rightTextField = new TextField();

    private final HBox hb = new HBox();
    private int spacing = 10;


    public BasePairedTextField() {
        setAnchors(hb, 0.0);
        hb.getChildren().addAll(leftTextField, rightTextField);
        hb.setSpacing(spacing);
        getChildren().add(hb);
        setPadding(new Insets(0, 10, 0, 10));

        CDIContainerSingleton.getInstance().activateCDI(this);
    }

    public abstract Callable<String> getCallableForLeftToRightTransformation();

    public abstract Callable<String> getCallableForRightToLeftTransformation();

    @PostConstruct
    public void init() {
        final StringBinding leftTextFieldBinding = Bindings.createStringBinding(getCallableForLeftToRightTransformation(), leftTextField.textProperty());

        final StringBinding rigthTextFieldBinding = Bindings.createStringBinding(getCallableForRightToLeftTransformation(), rightTextField.textProperty());


        leftTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                rightTextField.textProperty().bind(leftTextFieldBinding);
            }
        });
        leftTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                rightTextField.textProperty().unbind();
            }
        });

        rightTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                leftTextField.textProperty().bind(rigthTextFieldBinding);
            }
        });
        rightTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                leftTextField.textProperty().unbind();
            }
        });
    }

    private void setAnchors(Node node, Double anchor) {
        AnchorPane.setBottomAnchor(node, anchor);
        AnchorPane.setLeftAnchor(node, anchor);
        AnchorPane.setRightAnchor(node, anchor);
        AnchorPane.setTopAnchor(node, anchor);
    }
}
