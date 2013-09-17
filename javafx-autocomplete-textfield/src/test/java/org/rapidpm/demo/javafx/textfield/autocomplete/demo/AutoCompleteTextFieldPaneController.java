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

package org.rapidpm.demo.javafx.textfield.autocomplete.demo;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.fxml.FXML;
import org.rapidpm.demo.cdi.commons.fx.CDIJavaFxBaseController;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.javafx.textfield.autocomplete.AutoCompleteElement;
import org.rapidpm.demo.javafx.textfield.autocomplete.AutoCompleteTextField;
import org.rapidpm.demo.javafx.textfield.autocomplete.AutoCompleteTextFieldAction;
import org.rapidpm.demo.javafx.textfield.autocomplete.demo.model.PersistentPojo;
import org.rapidpm.demo.javafx.textfield.autocomplete.demo.model.TransientAutoCompleteElement;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert Date: 17.09.13 Time: 15:32
 */
public class AutoCompleteTextFieldPaneController implements CDIJavaFxBaseController {

    private @Inject @CDILogger Logger logger;

    private @Inject BusinessLogic businessLogic;
    private @Inject DemoDataBuilder demoDataBuilder;


    @FXML AutoCompleteTextField<TransientAutoCompleteElement> autocompleteTextField;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        autocompleteTextField.getCustomActionsList().add(new AutoCompleteTextFieldAction() {
            @Override public void execute(AutoCompleteElement selectedItem) {
                TransientAutoCompleteElement e = (TransientAutoCompleteElement) selectedItem;
                final PersistentPojo pojo = e.getPojo();
                businessLogic.doSomething(pojo);
            }
        });

        autocompleteTextField.getData().clear();
        autocompleteTextField.getData().addAll(demoDataBuilder.create());


    }


    public void onInfoClicked() {
    }


}
