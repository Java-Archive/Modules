package org.rapidpm.demo.javafx.tableview.filtered.contextmenue;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableView;

/**
 * User: Sven Ruppert Date: 14.08.13 Time: 16:30
 */
public class ContextMenu<T> extends javafx.scene.control.ContextMenu {

    private static final Logger logger = Logger.getLogger(ContextMenu.class);
//    private @Inject @CDILogger Logger logger;

    public ContextMenu() {

    }

    private FilteredTableView<T> filteredTableView;

//    @PostConstruct
    public void init(){
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


        getItems().addAll(openCSV, saveCSV, openXlS, saveXlS, copyTableCSV2Clipboard,copyTableImage2Clipboard);
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
