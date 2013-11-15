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

package org.rapidpm.modul.javafx.dialog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.rapidpm.commons.javafx.tableview.control.ColumnWidthOptimizer;

/**
 * User: Sven Ruppert Date: 24.06.13 Time: 12:58
 */
public class Dialog extends Stage {

    protected String stacktrace;
    protected double originalWidth, originalHeight;

    protected Scene scene;
    protected BorderPane borderPanel;
    protected ImageView icon;

    protected VBox messageBox;
    protected Label messageLabel;

    protected boolean scrollPaneVisible;
    protected HBox stacktraceButtonsPanel;
    protected ToggleButton viewStacktraceButton;
    protected Button copyStacktraceButton;
    //    protected Button mailStacktraceButton;
    protected ScrollPane scrollPane;
    protected Label stackTraceLabel;

    protected HBox buttonsPanel;
    protected Button okButton;
    protected Button showAllButton;

    /**
     * Extracts stack trace from Throwable
     */
    protected static class StacktraceExtractor {

        public String extract(Throwable t) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * Dialog builder
     */
    public static class Builder {
        protected static final int STACKTRACE_LABEL_MAXHEIGHT = 240;
        protected static final int MESSAGE_MIN_WIDTH = 180;
        protected static final int MESSAGE_MAX_WIDTH = 800;
        protected static final int BUTTON_WIDTH = 60;
        protected static final double MARGIN = 10;
//        protected static final String ICON_PATH = "/images/confirm/";

        protected Dialog stage;

        public Builder create() {
            stage = new Dialog();
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setIconified(false);
            stage.centerOnScreen();
            stage.borderPanel = new BorderPane();

            // icon
            stage.icon = new ImageView();
            stage.borderPanel.setLeft(stage.icon);
            BorderPane.setMargin(stage.icon, new Insets(MARGIN));

            // message
            stage.messageBox = new VBox();
            stage.messageBox.setAlignment(Pos.CENTER_LEFT);

            stage.messageLabel = new Label();
            stage.messageLabel.setWrapText(true);
            stage.messageLabel.setMinWidth(MESSAGE_MIN_WIDTH);
            stage.messageLabel.setMaxWidth(MESSAGE_MAX_WIDTH);

            stage.messageBox.getChildren().add(stage.messageLabel);


            stage.borderPanel.setCenter(stage.messageBox);
            BorderPane.setAlignment(stage.messageBox, Pos.CENTER);
            BorderPane.setMargin(stage.messageBox, new Insets(MARGIN, MARGIN, MARGIN, 2 * MARGIN));

            // buttons
            stage.buttonsPanel = new HBox();
            stage.buttonsPanel.setSpacing(MARGIN);
            stage.buttonsPanel.setAlignment(Pos.BOTTOM_CENTER);
            BorderPane.setMargin(stage.buttonsPanel, new Insets(0, 0, 1.5 * MARGIN, 0));
            stage.borderPanel.setBottom(stage.buttonsPanel);
            stage.borderPanel.widthProperty().addListener(new ChangeListener<Number>() {

                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                    stage.buttonsPanel.layout();
                }

            });

            stage.scene = new Scene(stage.borderPanel);
            stage.setScene(stage.scene);
            return this;
        }

        public Builder setOwner(Window owner) {
            if (owner != null) {
                stage.initOwner(owner);
                stage.borderPanel.setMaxWidth(owner.getWidth());
                stage.borderPanel.setMaxHeight(owner.getHeight());
            }
            return this;
        }

        public Builder setTitle(String title) {
            stage.setTitle(title);
            return this;
        }

        public Builder setMessage(String message) {
            stage.messageLabel.setText(message);
            return this;
        }

        private void alignScrollPaneForStackTrace() {
            stage.setWidth(
                    stage.icon.getImage().getWidth()
                            + Math.max(
                            stage.messageLabel.getWidth(),
                            (stage.scrollPaneVisible
                                    ? Math.max(
                                    stage.stacktraceButtonsPanel.getWidth(),
                                    stage.stackTraceLabel.getWidth())
                                    : stage.stacktraceButtonsPanel.getWidth()))
                            + 5 * MARGIN);

            stage.setHeight(
                    Math.max(
                            stage.icon.getImage().getHeight(),
                            stage.messageLabel.getHeight()
                                    + stage.stacktraceButtonsPanel.getHeight()
                                    + (stage.scrollPaneVisible
                                    ? Math.min(
                                    stage.stackTraceLabel.getHeight(),
                                    STACKTRACE_LABEL_MAXHEIGHT)
                                    : 0))

                            + stage.buttonsPanel.getHeight()
                            + 3 * MARGIN);
            if (stage.scrollPaneVisible) {
                stage.scrollPane.setPrefHeight(
                        stage.getHeight()
                                - stage.messageLabel.getHeight()
                                - stage.stacktraceButtonsPanel.getHeight()
                                - 2 * MARGIN);
            }
            stage.centerOnScreen();
        }

        private void alignScrollPaneForInfoTable() {
//            stage.setWidth(stage.icon.getImage().getWidth() + stage.scrollPane.getPrefWidth() + 20);
//            stage.setHeight(stage.icon.getImage().getHeight() + STACKTRACE_LABEL_MAXHEIGHT
//                            + stage.buttonsPanel.getHeight()
//                            + 3 * MARGIN);

            stage.setWidth(500);
            stage.scrollPane.setPrefWidth(500 - stage.icon.getImage().getWidth() - 5);
            ((TableView) stage.scrollPane.getContent()).setPrefWidth(stage.scrollPane.getPrefWidth() - 20);

            stage.setHeight(400);
            stage.scrollPane.setPrefHeight(400 - stage.icon.getImage().getHeight() - stage.okButton.getHeight() - 5);
            ((TableView) stage.scrollPane.getContent()).setPrefHeight(stage.scrollPane.getPrefHeight() - 20);

            stage.centerOnScreen();
        }

        // NOTE: invoke once during Dialog creating
        private Builder setStackTrace(Throwable t) {
            // view button
            stage.viewStacktraceButton = new ToggleButton("View stacktrace"); //JIRA MOD-36 CDI i18n

            // copy button
            stage.copyStacktraceButton = new Button("Copy to clipboard"); //JIRA MOD-37 CDI i18n
            HBox.setMargin(stage.copyStacktraceButton, new Insets(0, 0, 0, MARGIN));

            //maol button
//            stage.mailStacktraceButton = new Button("mail it us"); //JIRA MOD-37 CDI i18n
//            HBox.setMargin(stage.mailStacktraceButton, new Insets(0, 0, 0, MARGIN));

            stage.stacktraceButtonsPanel = new HBox();
//            stage.stacktraceButtonsPanel.getChildren().addAll(stage.viewStacktraceButton, stage.copyStacktraceButton, stage.mailStacktraceButton);
            stage.stacktraceButtonsPanel.getChildren().addAll(stage.viewStacktraceButton, stage.copyStacktraceButton);
            VBox.setMargin(stage.stacktraceButtonsPanel, new Insets(MARGIN, MARGIN, MARGIN, 0));
            stage.messageBox.getChildren().add(stage.stacktraceButtonsPanel);

            // stacktrace text
            stage.stackTraceLabel = new Label();
            stage.stackTraceLabel.widthProperty().addListener(new ChangeListener<Number>() {

                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    alignScrollPaneForStackTrace();
                }
            });

            stage.stackTraceLabel.heightProperty().addListener(new ChangeListener<Number>() {

                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    alignScrollPaneForStackTrace();
                }
            });

            StacktraceExtractor extractor = new StacktraceExtractor();
            stage.stacktrace = extractor.extract(t);

            stage.scrollPane = new ScrollPane();
            stage.scrollPane.setContent(stage.stackTraceLabel);

            stage.viewStacktraceButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    stage.scrollPaneVisible = !stage.scrollPaneVisible;
                    if (stage.scrollPaneVisible) {
                        stage.messageBox.getChildren().add(stage.scrollPane);
                        stage.stackTraceLabel.setText(stage.stacktrace);

                        alignScrollPaneForStackTrace();
                    } else {
                        stage.messageBox.getChildren().remove(stage.scrollPane);

                        //alignScrollPaneForStackTrace();
                        stage.setWidth(stage.originalWidth);
                        stage.setHeight(stage.originalHeight);
                        stage.stackTraceLabel.setText(null);
                        stage.centerOnScreen();
                    }
                    stage.messageBox.layout();
                }
            });

            stage.copyStacktraceButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    Map<DataFormat, Object> map = new HashMap<DataFormat, Object>();
                    map.put(DataFormat.PLAIN_TEXT, stage.stacktrace);
                    clipboard.setContent(map);
                }
            });

//            stage.mailStacktraceButton.setOnAction(new EventHandler<ActionEvent>() {
//
//                public void handle(ActionEvent t) {
//                    Clipboard clipboard = Clipboard.getSystemClipboard();
//                    Map<DataFormat, Object> map = new HashMap<DataFormat, Object>();
//                    map.put(DataFormat.PLAIN_TEXT, stage.stacktrace);
//                    clipboard.setContent(map);
//                    try {
//                        final String encoded = java.net.URLEncoder.encode(clipboard.getString(), "ISO-8859-1");
//                        final String s = encoded.replaceAll("\\+", "%20");
//
//                        final WritableImage snapImage = stage.getScene().snapshot(null);
//                        final ClipboardContent content = new ClipboardContent();
//                        content.putImage(snapImage);
//                        Clipboard.getSystemClipboard().setContent(content);
//
//                        final BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapImage, null);
//                        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
//
//
//                        BASE64Encoder base64Encoder = new BASE64Encoder();
//                        final String encodedImage = base64Encoder.encode(byteArrayOutputStream.toByteArray());
//
//                        final String encodedImageISO = URLEncoder.encode(encodedImage, "ISO-8859-1");
//
//                        final URL url = new URL("mailto:sven.ruppert@rapidpm.org?subject=ErrorMessage&body="+s+"&attachment="+ encodedImageISO.replaceAll("\\+", "%20"));
////                        final URL url = new URL("mailto:sven.ruppert@rapidpm.org?subject=ErrorMessage&body="+s);
//                        Desktop.getDesktop().mail(url.toURI());
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//            });


            stage.showingProperty().addListener(new ChangeListener<Boolean>() {

                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        stage.originalWidth = stage.getWidth();
                        stage.originalHeight = stage.getHeight();
                    }
                }
            });

            return this;
        }

        public static interface InfoTableRow {
            public List<String> allCols();
        }

        protected <T extends InfoTableRow> Builder setInfoTableView(List<T> messages) {
            stage.scrollPane = new ScrollPane();
            final TableView<T> messageTableView = new TableView<>();
            final ObservableList<T> observableList = FXCollections.observableArrayList();
            observableList.addAll(messages);

            final T t = messages.get(0);
            final List<String> strings = t.allCols();
            for (final String string : strings) {
                final TableColumn<T, String> col = new TableColumn<>(string);
                col.setCellValueFactory(new PropertyValueFactory(string));
                messageTableView.getColumns().add(col);
            }

            messageTableView.setItems(observableList);

            //  final ContextMenu adden

            final ColumnWidthOptimizer optimizer = new ColumnWidthOptimizer();
            optimizer.optimize(messageTableView);
            stage.scrollPane.setContent(messageTableView);
            return this;
        }


        protected void setIconFromResource(String resourceName) {
            final Image image = new Image(getClass().getResourceAsStream(resourceName));
//            final Image image = new Image(resourceName);
            stage.icon.setImage(image);
        }

        protected Builder setWarningIcon() {
//            setIconFromResource(ICON_PATH + "warningIcon.png");
            setIconFromResource("warningIcon.png");
            return this;
        }

        protected Builder setErrorIcon() {
            setIconFromResource("errorIcon.png");
            return this;
        }

        protected Builder setThrowableIcon() {
            setIconFromResource("bugIcon.png");
            return this;
        }

        protected Builder setInfoIcon() {
            setIconFromResource("infoIcon.png");
            return this;
        }

        protected Builder setConfirmationIcon() {
            setIconFromResource("confirmationIcon.png");
            return this;
        }

        protected Builder addOkButton() {
            stage.okButton = new Button("OK"); //JIRA MOD-38 CDI i18n
            stage.okButton.setPrefWidth(BUTTON_WIDTH);
            stage.okButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    stage.close();
                }

            });
            stage.buttonsPanel.getChildren().add(stage.okButton);
            return this;
        }

        protected Builder addShowAllButton() {
            stage.showAllButton = new Button("Details"); //JIRA MOD-38 CDI i18n
            stage.showAllButton.setPrefWidth(BUTTON_WIDTH);
            stage.showAllButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    stage.scrollPaneVisible = !stage.scrollPaneVisible;
                    if (stage.scrollPaneVisible) {
                        stage.messageBox.getChildren().add(stage.scrollPane);
                        stage.showAllButton.setVisible(false);
                        alignScrollPaneForInfoTable();
                    } else {
                        stage.messageBox.getChildren().remove(stage.scrollPane);
                        stage.setWidth(stage.originalWidth);
                        stage.setHeight(stage.originalHeight);
                        stage.centerOnScreen();
                    }
                    stage.messageBox.layout();
                }
            });
            stage.buttonsPanel.getChildren().add(stage.showAllButton);

            return this;
        }

        protected Builder addConfirmationButton(String buttonCaption, final EventHandler actionHandler) {
            Button confirmationButton = new Button(buttonCaption);
            confirmationButton.setMinWidth(BUTTON_WIDTH);
            confirmationButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    stage.close();
                    if (actionHandler != null)
                        actionHandler.handle(t);
                }
            });

            stage.buttonsPanel.getChildren().add(confirmationButton);
            return this;
        }

        /**
         * Add Yes button to confirmation dialog
         *
         * @param actionHandler action handler
         *
         * @return
         */
        public Builder addYesButton(EventHandler actionHandler) {
            return addConfirmationButton("Ja", actionHandler);  //JIRA MOD-39 CDI i18n
        }

        /**
         * Add No button to confirmation dialog
         *
         * @param actionHandler action handler
         *
         * @return
         */
        public Builder addNoButton(EventHandler actionHandler) {
            return addConfirmationButton("Nein", actionHandler); //JIRA MOD-40 CDI i18n
        }

        /**
         * Add Cancel button to confirmation dialog
         *
         * @param actionHandler action handler
         *
         * @return
         */
        public Builder addCancelButton(EventHandler actionHandler) {
            return addConfirmationButton("Abbrechen", actionHandler);  //JIRA MOD-41 CDI i18n
        }

        /**
         * Build dialog
         *
         * @return dialog instance
         */
        public Dialog build() {
            if (stage.buttonsPanel.getChildren().size() == 0)
                throw new RuntimeException("Add one dialog button at least");

            stage.buttonsPanel.getChildren().get(0).requestFocus();
            return stage;
        }

    }

    /**
     * Show information dialog box as parentWindow child
     *
     * @param title   dialog title
     * @param message dialog message
     * @param owner   parent window
     */
    public static void showInfo(String title, String message, Window owner) {
        new Builder()
                .create()
                .setOwner(owner)
                .setTitle(title)
                .setInfoIcon()
                .setMessage(message)
                .addOkButton()
                .build()
                .show();
    }

    /**
     * Show information dialog box as parentStage child
     *
     * @param title   dialog title
     * @param message dialog message
     */
    public static void showInfo(String title, String message) {
        showInfo(title, message, null);
    }

    /**
     * Show warning dialog box as parentStage child
     *
     * @param title   dialog title
     * @param message dialog message
     * @param owner   parent window
     */
    public static void showWarning(String title, String message, Window owner) {
        new Builder()
                .create()
                .setOwner(owner)
                .setTitle(title)
                .setWarningIcon()
                .setMessage(message)
                .addOkButton()
                .build()
                .show();
    }

    public static <T extends Builder.InfoTableRow> void showInfoTable(String title, String shortMessage, List<T> messages) {
        showInfoTable(title, shortMessage, messages, null);
    }

    public static <T extends Builder.InfoTableRow> void showInfoTable(String title, String shortMessage, List<T> messages, Window owner) {
        new Builder()
                .create()
                .setOwner(owner)
                .setTitle(title)
                .setInfoIcon()
                .setMessage(shortMessage)
                .setInfoTableView(messages)
                .addShowAllButton()
                .addOkButton()
                .build()
                .show();
    }

    /**
     * Show warning dialog box
     *
     * @param title   dialog title
     * @param message dialog message
     */
    public static void showWarning(String title, String message) {
        showWarning(title, message, null);
    }

    /**
     * Show error dialog box
     *
     * @param title   dialog title
     * @param message dialog message
     * @param owner   parent window
     */
    public static void showError(String title, String message, Window owner) {
        new Builder()
                .create()
                .setOwner(owner)
                .setTitle(title)
                .setErrorIcon()
                .setMessage(message)
                .addOkButton()
                .build()
                .show();
    }

    /**
     * Show error dialog box
     *
     * @param title   dialog title
     * @param message dialog message
     */
    public static void showError(String title, String message) {
        showError(title, message, null);
    }

    /**
     * Show error dialog box with stacktrace
     *
     * @param title   dialog title
     * @param message dialog message
     * @param t       throwable
     * @param owner   parent window
     */
    public static void showThrowable(String title, String message, Throwable t, Window owner) {
        new Builder()
                .create()
                .setOwner(owner)
                .setTitle(title)
                .setThrowableIcon()
                .setMessage(message)
                .setStackTrace(t)
                .addOkButton()
                .build()
                .show();
    }

    /**
     * Show error dialog box with stacktrace
     *
     * @param title   dialog title
     * @param message dialog message
     * @param t       throwable
     */
    public static void showThrowable(String title, String message, Throwable t) {
        showThrowable(title, message, t, null);
    }

    /**
     * Build confirmation dialog builder
     *
     * @param title   dialog title
     * @param message dialog message
     * @param owner   parent window
     *
     * @return
     */
    public static Builder buildConfirmation(String title, String message, Window owner) {
        return new Builder()
                .create()
                .setOwner(owner)
                .setTitle(title)
                .setConfirmationIcon()
                .setMessage(message);
    }

    /**
     * Build confirmation dialog builder
     *
     * @param title   dialog title
     * @param message dialog message
     *
     * @return
     */
    public static Builder buildConfirmation(String title, String message) {
        return buildConfirmation(title, message, null);
    }
}