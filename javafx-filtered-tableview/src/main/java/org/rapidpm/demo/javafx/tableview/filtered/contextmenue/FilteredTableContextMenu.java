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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.CDIPropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableDataRow;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableKeyMapper;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableView;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert Date: 14.08.13 Time: 16:30
 */
public class FilteredTableContextMenu extends ContextMenu {

    private @Inject @CDILogger Logger logger;
    @Inject @CDIPropertyRegistryService PropertyRegistryService propertyRegistryService;
    @Inject FilteredTableKeyMapper keyMapper;

    private boolean openCSV = true;
    private boolean saveCSV = true;
    private boolean openXLSX = true;
    private boolean saveXLSX = true;
    private boolean copyClippboardCSV = true;
    private boolean copyClippboardImage = true;


    public FilteredTableContextMenu() {

    }

    private FilteredTableView filteredTableView;

    public void seti18n() {

        final ObservableList<MenuItem> items = getItems();
        for (final MenuItem item : items) {
            item.setText(map(item.getText()));
        }

    }

    private String map(final String key) {
        return propertyRegistryService.getRessourceForKey(keyMapper.map(key));
    }

    //    @PostConstruct
    public void init() {
        if (openCSV) {
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
            getItems().add(openCSV);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("open csv not active ");
            }
        }

        if (saveCSV) {
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
            getItems().add(saveCSV);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("save csv not active ");
            }
        }

        if (openXLSX) {
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
            getItems().add(openXlS);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("openXLSXv not active ");
            }
        }

        if (saveXLSX) {
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
            getItems().add(saveXlS);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("saveXLSX not active ");
            }
        }
        if (copyClippboardCSV) {
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
            getItems().add(copyTableCSV2Clipboard);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("copyClippboardCSV not active ");
            }
        }

        if (copyClippboardImage) {
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
            getItems().add(copyTableImage2Clipboard);

        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("copyClippboardImage not active ");
            }
        }


        //send csv as mail
        //send xls as mail
        //save as pdf
        //zoom view
        seti18n();
    }


    private StringBuilder convertTable2CSV() {
        final StringBuilder clipboardString = new StringBuilder();
        final ObservableList<FilteredTableDataRow> items = filteredTableView.getItems();
        for (final FilteredTableDataRow item : items) {
            final String csvRow = item.convertToCSV();
            clipboardString.append(csvRow);
            clipboardString.append("\n");
        }
        return clipboardString;
    }

    private byte[] convertTable2Xls() throws IOException {
        //konvertiere


        return null;
    }

    public void setFilteredTableView(FilteredTableView filteredTableView) {
        this.filteredTableView = filteredTableView;
    }

    public FilteredTableContextMenu openCSV(final boolean openCSV) {
        this.openCSV = openCSV;
        return this;
    }

    public FilteredTableContextMenu saveCSV(final boolean saveCSV) {
        this.saveCSV = saveCSV;
        return this;
    }

    public FilteredTableContextMenu openXLSX(final boolean openXLSX) {
        this.openXLSX = openXLSX;
        return this;
    }

    public FilteredTableContextMenu saveXLSX(final boolean saveXLSX) {
        this.saveXLSX = saveXLSX;
        return this;
    }

    public FilteredTableContextMenu copyClippboardCSV(final boolean copyClippboardCSV) {
        this.copyClippboardCSV = copyClippboardCSV;
        return this;
    }

    public FilteredTableContextMenu copyClippboardImage(final boolean copyClippboardImage) {
        this.copyClippboardImage = copyClippboardImage;
        return this;
    }

    public FilteredTableContextMenu filteredTableView(final FilteredTableView filteredTableView) {
        this.filteredTableView = filteredTableView;
        return this;
    }


}
