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

package org.rapidpm.demo.javafx.tableview.filtered.contextmenue.menuitems.csv;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 29.08.13
 * Time: 09:55
 */
public class OpenCSV {

    private @Inject @CDILogger Logger logger;

    private MenuItem menuItem = new MenuItem("contextmenu.opencsv");

    @PostConstruct
    public void init() {
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {

                    @Override public void run() {
//                        try {
//                            final StringBuilder stringBuilder = convertTable2CSV();
//                            final File temp = File.createTempFile("xls-temp-" + System.nanoTime(), ".csv");
//                            final FileOutputStream fos = new FileOutputStream(temp);
//                            fos.write(stringBuilder.toString().getBytes());
//                            fos.flush();
//                            fos.close();
//                            Desktop desktop = Desktop.getDesktop();
//                            desktop.open(temp);
//                        } catch (IOException e1) {
//                            logger.error(e1);
//                        }
                    }
                });
            }
        });
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }
}
