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

package gui.org.rapidpm.demo.javafx.dialog.demo;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import org.rapidpm.demo.cdi.commons.fx.CDIJavaFxBaseController;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.javafx.dialog.confirm.Dialog;

/**
 * User: Sven Ruppert
 * Date: 10.09.13
 * Time: 14:39
 */
public class DialogDemoPaneController implements CDIJavaFxBaseController {

    private @Inject @CDILogger org.rapidpm.module.se.commons.logger.Logger logger;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onInfoClicked(ActionEvent event) {
        Dialog.showInfo("Information", "einmal lesen bitte");
    }

    public void onConfirmationClicked(ActionEvent event) {
        Dialog.buildConfirmation("Bestätigung", "Möchten Sie diesen Datensatz löschen")
                .addYesButton(new ConfirmDialogHandler("Ja"))
                .addNoButton(new ConfirmDialogHandler("Nein"))
                .build()
                .show();
    }

    public void onErrorClicked(ActionEvent event) {
        Dialog.showError("Error", "Messages des Dialogs ");
    }

    public void onWarningClicked(ActionEvent event) {
        Dialog.showWarning("Warning", "Messages des Dialogs ");
    }

    public void onThrowableClicked(ActionEvent event) {
        Dialog.showThrowable("Throwable", "Messages des Dialogs ", new Throwable("skrhwpretbnaoerb naorezu nüaz"));
    }

    class ConfirmDialogHandler implements EventHandler<Event> {

        private String button = "";

        public ConfirmDialogHandler(String s) {
            this.button = s;
        }

        @Override
        public void handle(Event t) {
            if (button.equals("Ja")) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Löschen wurde bestätigt.");
                }
                //do Something();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Abbruch des Löschvorgangs durch den User");
                }
            }
        }

    }

}
