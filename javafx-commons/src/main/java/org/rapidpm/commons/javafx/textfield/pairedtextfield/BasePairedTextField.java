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

package org.rapidpm.commons.javafx.textfield.pairedtextfield;

import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.commons.cdi.se.CDIContainerSingleton;
import org.rapidpm.commons.javafx.textfield.LabledTextField;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 09.10.13
 * Time: 12:46
 */
public abstract class BasePairedTextField extends Pane {
//    protected final TextField leftTextField = new TextField();
//    protected final TextField leftTextField = new TextField();
    protected @Inject LabledTextField leftTextField ;
    protected @Inject LabledTextField rightTextField ;

    private @Inject @CDILogger Logger logger;

    private final HBox hb = new HBox();
    private int spacing = 10;


    public BasePairedTextField() {
        CDIContainerSingleton.getInstance().activateCDI(this);
    }

    public abstract Callable<String> getCallableForLeftToRightTransformation();

    public abstract Callable<String> getCallableForRightToLeftTransformation();

    @PostConstruct
    public void init() {
        setAnchors(hb, 0.0);
        hb.getChildren().addAll(leftTextField, rightTextField);
        hb.setSpacing(spacing);
        getChildren().add(hb);
        setPadding(new Insets(0, 10, 0, 10));

//        final StringBinding leftTextFieldBinding = Bindings.createStringBinding(getCallableForLeftToRightTransformation(), leftTextField.textProperty());
//        final StringBinding rigthTextFieldBinding = Bindings.createStringBinding(getCallableForRightToLeftTransformation(), rightTextField.textProperty());
        final StringBinding leftTextFieldBinding = Bindings.createStringBinding(getCallableForLeftToRightTransformation(), leftTextField.getTextField().textProperty());
        final StringBinding rigthTextFieldBinding = Bindings.createStringBinding(getCallableForRightToLeftTransformation(), rightTextField.getTextField().textProperty());


        leftTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (logger.isDebugEnabled()) {
                    logger.debug("handle-> binding right to left ");
                }
                rightTextField.getTextField().textProperty().bind(leftTextFieldBinding);
            }
        });
        leftTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (logger.isDebugEnabled()) {
                    logger.debug("handle-> unbinding right to left ");
                }
                rightTextField.getTextField().textProperty().unbind();
            }
        });

        rightTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (logger.isDebugEnabled()) {
                    logger.debug("handle-> binding left to right ");
                }
                leftTextField.getTextField().textProperty().bind(rigthTextFieldBinding);
            }
        });
        rightTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (logger.isDebugEnabled()) {
                    logger.debug("handle-> unbinding left to right ");
                }
                leftTextField.getTextField().textProperty().unbind();
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
