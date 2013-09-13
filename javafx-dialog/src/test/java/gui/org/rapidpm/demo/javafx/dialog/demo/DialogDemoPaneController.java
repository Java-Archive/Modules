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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import org.rapidpm.demo.cdi.commons.fx.CDIJavaFxBaseController;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.javafx.dialog.Dialog;

/**
 * User: Sven Ruppert Date: 10.09.13 Time: 14:39
 */
public class DialogDemoPaneController implements CDIJavaFxBaseController {

    private @Inject @CDILogger org.rapidpm.module.se.commons.logger.Logger logger;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public static class TransientDemoElement implements Dialog.Builder.InfoTableRow {

        private StringProperty spalteA = new SimpleStringProperty();
        private StringProperty spalteB = new SimpleStringProperty();

        public TransientDemoElement() {
        }

        public TransientDemoElement(String spalteA, String spalteB) {
            this.spalteA.setValue(spalteA);
            this.spalteB.setValue(spalteB);
        }

        public String getSpalteA() {
            return spalteA.get();
        }

        public StringProperty spalteAProperty() {
            return spalteA;
        }

        public void setSpalteA(String spalteA) {
            this.spalteA.set(spalteA);
        }

        public String getSpalteB() {
            return spalteB.get();
        }

        public StringProperty spalteBProperty() {
            return spalteB;
        }

        public void setSpalteB(String spalteB) {
            this.spalteB.set(spalteB);
        }

        @Override public List<String> allCols() {
            final List<String> result = new ArrayList<>();
            result.add("spalteA");
            result.add("spalteB");
            return result;
        }
    }

    public void onInfoTableViewClicked(ActionEvent event) {
        final List<TransientDemoElement> elements = new ArrayList<>();
        elements.add(new TransientDemoElement("A1", "B1"));
        elements.add(new TransientDemoElement("A1", "B2"));
        elements.add(new TransientDemoElement("A1", "B3"));
        elements.add(new TransientDemoElement("A1", "B4"));
        elements.add(new TransientDemoElement("A1", "B5"));
        elements.add(new TransientDemoElement("A1", "B6"));


        Dialog.<TransientDemoElement>showInfoTable("InfoTableView", "Hier die KurzInfo mit gaaaaaaaannnnnzz langem Kramt", elements);

    }

    public void onInfoClicked(ActionEvent event) {
        Dialog.showInfo("Information", "einmal lesen bitte \n einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +

                " einmal lesen bitte \n" +
                " einmal lesen bitte \n" +
                " ");
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
