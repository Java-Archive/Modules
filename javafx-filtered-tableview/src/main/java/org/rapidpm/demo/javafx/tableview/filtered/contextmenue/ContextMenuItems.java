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

package org.rapidpm.demo.javafx.tableview.filtered.contextmenue;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableView;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert Date: 14.08.13 Time: 16:30
 */
public class ContextMenuItems<T> {

    private @Inject @CDILogger Logger logger;

    private boolean openCSV = false;
    private boolean saveCSV = false;
    private boolean openXLSX = false;
    private boolean saveXLSX = false;
    private boolean copyClippboardCSV = false;
    private boolean copyClippboardImage = false;


    public ContextMenuItems() {

    }

    private FilteredTableView<T> filteredTableView;

    //    @PostConstruct
    public void init() {
        MenuItem openCSV = new MenuItem("contextmenu.opencsv");
        openCSV.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {

                    @Override public void run() {
                        try {
                            final StringBuilder stringBuilder = convertTable2CSV();
                            final File temp = File.createTempFile("xls-temp-" + System.nanoTime(), ".csv");
                            final FileOutputStream fos = new FileOutputStream(temp);
                            fos.write(stringBuilder.toString().getBytes());
                            fos.flush();
                            fos.close();
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(temp);
                        } catch (IOException e1) {
                            logger.error(e1);
                        }
                    }
                });
            }
        });
        MenuItem saveCSV = new MenuItem("contextmenu.savecsv");
        saveCSV.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        try {
                            final StringBuilder stringBuilder = convertTable2CSV();
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("contextmenu.savecsv");
                            fileChooser.setInitialDirectory(
                                    new File(System.getProperty("contextmenu.defaultdir"))
                            );
                            final File targetFile = fileChooser.showSaveDialog(filteredTableView.getScene().getWindow());
                            if (targetFile != null) {
                                final FileOutputStream fos = new FileOutputStream(targetFile);
                                fos.write(stringBuilder.toString().getBytes());
                                fos.flush();
                                fos.close();
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("keine Ziel ausgewaehlt..");
                                }
                            }
                        } catch (IOException e1) {
                            logger.error(e1);
                        }
                    }
                });
            }
        });
        final MenuItem openXlS = new MenuItem("contextmenu.openxls");
        openXlS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (logger.isDebugEnabled()) {
                    logger.debug(openXlS.toString());
                }
                Platform.runLater(new Runnable() {

                    @Override public void run() {
                        try {
                            final byte[] bytes = convertTable2Xls();
                            final File temp = File.createTempFile("xls-temp-" + System.nanoTime(), ".xls");
                            final FileOutputStream fos = new FileOutputStream(temp);
                            fos.write(bytes);
                            fos.flush();
                            fos.close();
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(temp);
                        } catch (IOException e1) {
                            logger.error(e1);
                        }
                    }
                });
            }
        });


        final MenuItem saveXlS = new MenuItem("contextmenu.savexls");
        saveXlS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        try {
                            final byte[] bytes = convertTable2Xls();
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("contextmenu.savexls");
                            fileChooser.setInitialDirectory(
                                    new File(System.getProperty("contextmenu.defaultdir"))
                            );
                            final File targetFile = fileChooser.showSaveDialog(filteredTableView.getScene().getWindow());
                            if (targetFile != null) {
                                final FileOutputStream fos = new FileOutputStream(targetFile);
                                fos.write(bytes);
                                fos.flush();
                                fos.close();
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("keine Ziel ausgew�hlt..");
                                }
                            }
                        } catch (IOException e1) {
                            logger.error(e1);
                        }
                    }
                });
            }
        });
        MenuItem copyTableCSV2Clipboard = new MenuItem("contextmenu.copyclipboard");
        copyTableCSV2Clipboard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        final StringBuilder clipboardString = convertTable2CSV();
                        final ClipboardContent content = new ClipboardContent();
                        content.putString(clipboardString.toString());
                        Clipboard.getSystemClipboard().setContent(content);
                    }
                });
            }
        });

        //send csv as mail
        //send xls as mail
        //save as pdf
        //zoom view

        MenuItem copyTableImage2Clipboard = new MenuItem("contextmenu.copyimage");
        copyTableImage2Clipboard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        final WritableImage snapImage = filteredTableView.snapshot(new SnapshotParameters(), null);
                        final ClipboardContent content = new ClipboardContent();
                        content.putImage(snapImage);
                        Clipboard.getSystemClipboard().setContent(content);
                    }
                });
            }
        });


//        getItems().addAll(openCSV, saveCSV, openXlS, saveXlS, copyTableCSV2Clipboard, copyTableImage2Clipboard);
    }


    private StringBuilder convertTable2CSV() {
        final StringBuilder clipboardString = new StringBuilder();
        //konvertiere..
        return clipboardString;
    }

    private byte[] convertTable2Xls() throws IOException {
        //konvertiere


        return null;
    }

    public void setFilteredTableView(FilteredTableView<T> filteredTableView) {
        this.filteredTableView = filteredTableView;
    }
}
