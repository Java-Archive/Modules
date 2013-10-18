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

package org.rapidpm.demo.javafx.pairedtextfield;

import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

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
import org.rapidpm.demo.javafx.commons.textfield.pairedtextfield.BasePairedTextField;
import org.rapidpm.demo.javafx.pairedtextfield.demologic.DemoContext;

/**
 * User: Sven Ruppert
 * Date: 08.10.13
 * Time: 14:44
 */
public class PairedTextField extends BasePairedTextField {

    @Inject Instance<LeftTextFieldBindingCallable> leftTextFieldBindingCallableInstance;
    @Inject Instance<RightTextFieldBindingCallable> rightTextFieldBindingCallableInstance;

    @Inject DemoContext demoContext;

    public PairedTextField() {
        super();
    }

    @Override public Callable<String> getCallableForLeftToRightTransformation() {
        final LeftTextFieldBindingCallable callable = leftTextFieldBindingCallableInstance.get();
        return callable;
    }

    @Override public Callable<String> getCallableForRightToLeftTransformation() {
        final RightTextFieldBindingCallable callable = rightTextFieldBindingCallableInstance.get();
        return callable;
    }

}
