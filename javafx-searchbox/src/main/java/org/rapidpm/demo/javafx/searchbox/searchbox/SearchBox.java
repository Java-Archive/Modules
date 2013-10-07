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

package org.rapidpm.demo.javafx.searchbox.searchbox;

/**
 * User: Sven Ruppert
 * Date: 23.05.13
 * Time: 10:35
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.complexPhrase.ComplexPhraseQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.rapidpm.demo.cdi.commons.locale.CDILocale;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.CDIPropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;
import org.rapidpm.module.se.commons.logger.Logger;

import static org.apache.lucene.document.Field.Store.NO;
import static org.apache.lucene.document.Field.Store.YES;

/**
 * A mac style search box with drop down with results
 */
public class SearchBox<T extends SearchBoxDataElement> extends Region {

    //CDI
    private final CDIContainerSingleton cdiContainerSingleton = CDIContainerSingleton.getInstance();
    private final Logger logger = cdiContainerSingleton.getManagedInstance(Logger.class);

    private final CdiHolder cdiHolder = cdiContainerSingleton.getManagedInstance(CdiHolder.class);

    public static class CdiHolder {

        private @Inject @CDILogger Logger logger;

        private @Inject @CDIPropertyRegistryService PropertyRegistryService propertyRegistryService;

        private @Inject @CDILocale Locale defaultLocale;

        public String getRessource(final String relativeKey) {
            final String mappedKey = propertyRegistryService.getClassMappedRessource(SearchBox.class, relativeKey);
            if (logger.isDebugEnabled()) {
                logger.debug("mappedKey - " + mappedKey);
            }
            return mappedKey;
        }

    }


    public static final String IDX_VALUE_FIELD_NAME = "valuefield";
    public static final String ITEM_ID = "itemid";
    private final GermanAnalyzer analyzer = new GermanAnalyzer(Version.LUCENE_43);

    private TextField textBox;
    private Button clearButton;
    private IndexSearcher indexSearcher;
    private ContextMenu contextMenu = new ContextMenu();
    private Popup extraInfoPopup = new Popup();
    private Label infoName;
    private Label infoDescription;
    private VBox infoBox;
    private Tooltip searchErrorTooltip = new Tooltip();
    private Timeline searchErrorTooltipHidder = null;

    private RAMDirectory idx = new RAMDirectory();
    private Map<Long, T> tablevalues = new HashMap<Long, T>();

    private List<T> itemListe = new ArrayList<T>();


    private boolean autoGeneratedIDS = false; //falls man Revisionen per Envers darstellen m�chte

    public boolean isAutoGeneratedIDS() {
        return autoGeneratedIDS;
    }

    public void setAutoGeneratedIDS(boolean autoGeneratedIDS) {
        this.autoGeneratedIDS = autoGeneratedIDS;
    }


    private Semaphore sem = new Semaphore(1);

    //JIRA MOD-51 MappingCode muss extern liegen
    public void refreshIndex(final List<T> itemListe) {
        try {
            sem.acquire();
            this.itemListe = itemListe;
            final IndexWriter writer = new IndexWriter(idx, new IndexWriterConfig(Version.LUCENE_43, analyzer));
            writer.deleteAll();
            tablevalues.clear();
            for (final T item : itemListe) {
                addElementToIndex(writer, item);
            }
            writer.close();
            final DirectoryReader reader = DirectoryReader.open(idx);
            indexSearcher = new IndexSearcher(reader);
        } catch (IOException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            logger.error(e);
        } finally {
            sem.release();
        }
    }

    private void addElementToIndex(final IndexWriter writer, final T item) {
        try {
            final Document d = new Document();
            final long autoID = System.nanoTime();
            if (autoGeneratedIDS) {
                d.add(new LongField(ITEM_ID, autoID, YES));
            } else {
                d.add(new LongField(ITEM_ID, item.getID(), YES));
            }
            addDocument(d, item.getValues());
            writer.addDocument(d, analyzer);
            if (autoGeneratedIDS) {
                tablevalues.put(autoID, item);
            } else {
                tablevalues.put(item.getID(), item);
            }
            writer.commit();
//            writer.close();
        } catch (IOException e) {
            logger.error(e);
        }

    }

    public void addElementToIndex(final T item) {
        try {
            final IndexWriter writer = new IndexWriter(idx, new IndexWriterConfig(Version.LUCENE_43, analyzer));
            final Document d = new Document();
            final long autoID = System.nanoTime();
            if (autoGeneratedIDS) {
                d.add(new LongField(ITEM_ID, autoID, YES));
            } else {
                d.add(new LongField(ITEM_ID, item.getID(), YES));
            }
            addDocument(d, item.getValues());
            writer.addDocument(d, analyzer);
            if (autoGeneratedIDS) {
                tablevalues.put(System.nanoTime(), item);
            } else {
                tablevalues.put(item.getID(), item);
            }
            writer.commit();
            writer.close();
        } catch (IOException e) {
            logger.error(e);
        }

    }


    public void showAllIndexElements() {
        itemListe.clear();
        itemListe.addAll(tablevalues.values());
    }

    private void addStringField(final Document d, final String fieldName, @NotNull final String fieldValue) {
        d.add(new StringField(fieldName, ifNull(fieldValue), NO));
    }

    private void addDocument(final Document d, final List<String> values) {
        for (final String value : values) {
            addStringField(d, IDX_VALUE_FIELD_NAME, value);
        }
    }


    private String ifNull(final String value) {
        if (value == null) {
            return "";
        } else {
            return value.toLowerCase();
        }
    }

    public SearchBox() {
        setId("SearchBox");
        setMinHeight(24);
        setPrefSize(150, 24);
        setMaxHeight(24);
        textBox = new TextField();
        textBox.setPromptText(cdiHolder.getRessource("promt.text"));
        clearButton = new Button();
        clearButton.setVisible(false);
        getChildren().addAll(textBox, clearButton);
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textBox.setText("");
                textBox.requestFocus();
                showAllIndexElements();
            }
        });

        textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    contextMenu.setFocused(true);
                } else if (keyEvent.getCode() == KeyCode.ENTER) {
                    //filtere Tabelle mit den Ergebnisse der Suche
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("keyEvent " + keyEvent);
                    }
                }
            }
        });
        textBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                final String textBoxText = textBox.getText();
                clearButton.setVisible(textBoxText.length() != 0);
                if (textBoxText.length() == 0) {
                    if (contextMenu != null) contextMenu.hide();
                    showError(null);
                    showAllIndexElements();
                } else {
                    boolean haveResults = false;
                    final Map<DocumentType, List<Long>> results = new HashMap<DocumentType, List<Long>>();
                    results.clear();
                    try {
                        String queryString = "";
                        final String[] splitArray = textBoxText.split(" ");
                        for (final String split : splitArray) {
                            final boolean matches = split.matches("\\w+");
                            queryString = queryString + " " + (matches ? "*" : "") + split + (matches ? "*" : "");
                        }
                        if (logger.isDebugEnabled()) {
                            logger.debug("queryString = " + queryString);
                        }

                        final ComplexPhraseQueryParser qp = new ComplexPhraseQueryParser(Version.LUCENE_43, IDX_VALUE_FIELD_NAME, analyzer);
                        qp.setAllowLeadingWildcard(true);
                        qp.setDefaultOperator(QueryParser.Operator.AND);
                        final Query query = qp.parse(queryString);
                        if (logger.isDebugEnabled()) {
                            logger.debug("termQuery.toString() = " + query.toString());
                        }
                        final TopDocs topDocs = indexSearcher.search(query, 100);

                        final int totalHits = topDocs.totalHits;
                        if (logger.isDebugEnabled()) {
                            logger.debug("totalHits = " + totalHits);
                        }
                        if (totalHits > 0) {
                            haveResults = true;
                        } else {
                            haveResults = false;
                        }
                        // check if we have any results
//                        for (List<T> categoryResults: results.values()) {
//                            if (categoryResults.size() > 0) {
//                                haveResults = true;
//                                break;
//                            }
//                        }
                        final ArrayList<Long> documentList = new ArrayList<Long>(totalHits);
                        for (final ScoreDoc scoreDoc : topDocs.scoreDocs) {
                            final int docId = scoreDoc.doc;
                            final Document document = indexSearcher.getIndexReader().document(docId);
                            documentList.add(Long.valueOf(document.get(ITEM_ID)));
                        }
                        results.put(DocumentType.SAMPLE, documentList);

                    } catch (IOException | ParseException e) {
                        logger.error(e);
                    }
                    if (haveResults) {
                        showError(null);
                        //populateMenu(results);

                        filtertable(results);
                        if (!contextMenu.isShowing()) contextMenu.show(SearchBox.this, Side.BOTTOM, 10, -5);
                    } else {
                        if (searchErrorTooltip.getText() == null) showError(cdiHolder.getRessource("nohits.info"));
                        contextMenu.hide();
                    }
                    contextMenu.setFocused(true);
                }
            }
        });
        // create info popup
        infoBox = new VBox();
        infoBox.setId("search-info-box");
        infoBox.setFillWidth(true);
        infoBox.setMinWidth(USE_PREF_SIZE);
        infoBox.setPrefWidth(350);
        infoName = new Label();
        infoName.setId("search-info-name");
        infoName.setMinHeight(USE_PREF_SIZE);
        infoName.setPrefHeight(28);
        infoDescription = new Label();
        infoDescription.setId("search-info-description");
        infoDescription.setWrapText(true);
        infoDescription.setPrefWidth(infoBox.getPrefWidth() - 24);
        infoBox.getChildren().addAll(infoName, infoDescription);
        extraInfoPopup.getContent().add(infoBox);
        // hide info popup when context menu is hidden
        contextMenu.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                extraInfoPopup.hide();
            }
        });
        refreshIndex(Collections.<T>emptyList());
    }

    private void filtertable(Map<DocumentType, List<Long>> results) {
        this.itemListe.clear();
        final List<Long> longList = results.get(DocumentType.SAMPLE);//JIRA MOD-52 verwenden der Gruppen bei der Anzeige
        for (final Long itemID : longList) {
            itemListe.add(tablevalues.get(itemID));
        }
    }

    private void showError(String message) {
        searchErrorTooltip.setText(message);
        if (searchErrorTooltipHidder != null) searchErrorTooltipHidder.stop();
        if (message != null) {
            Point2D toolTipPos = textBox.localToScene(0, textBox.getLayoutBounds().getHeight());
            double x = toolTipPos.getX() + textBox.getScene().getX() + textBox.getScene().getWindow().getX();
            double y = toolTipPos.getY() + textBox.getScene().getY() + textBox.getScene().getWindow().getY();
            searchErrorTooltip.show(textBox.getScene().getWindow(), x, y);
            searchErrorTooltipHidder = new Timeline();
            searchErrorTooltipHidder.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(3),
                            new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent t) {
                                    searchErrorTooltip.hide();
                                    searchErrorTooltip.setText(null);
                                }
                            }
                    )
            );
            searchErrorTooltipHidder.play();
        } else {
            searchErrorTooltip.hide();
        }
    }

    //JIRA MOD-53 per attribut im xml konfiguerieren, oder programatisch
    private void populateMenu(Map<DocumentType, List<Long>> results) {
        contextMenu.getItems().clear();
        for (Map.Entry<DocumentType, List<Long>> entry : results.entrySet()) {
            boolean first = true;
            for (final Long docId : entry.getValue()) {
                final T t = tablevalues.get(docId);
                final String shortInfo = t.shortInfo();
                if (logger.isDebugEnabled()) {
                    logger.debug("shortInfo = " + shortInfo);
                }
                final HBox hBox = new HBox();
                hBox.setFillHeight(true);
                Label itemLabel = new Label(shortInfo);
                itemLabel.getStyleClass().add("item-label");
                if (first) {
                    first = false;
//                    Label groupLabel = new Label(result.getDocumentType().getPluralDisplayName());
                    //JIRA MOD-56 interface f transiente Daten
                    Label groupLabel = new Label("###GroupName###");
                    groupLabel.getStyleClass().add("group-label");
                    groupLabel.setAlignment(Pos.CENTER_RIGHT);
                    groupLabel.setMinWidth(USE_PREF_SIZE);
                    groupLabel.setPrefWidth(70);
                    hBox.getChildren().addAll(groupLabel, itemLabel);
                } else {
                    Region spacer = new Region();
                    spacer.setMinWidth(USE_PREF_SIZE);
                    spacer.setPrefWidth(70);
                    hBox.getChildren().addAll(spacer, itemLabel);
                }
                // create a special node for hiding/showing popup content
                final Region popRegion = new Region();
                popRegion.getStyleClass().add("search-menu-item-popup-region");
                popRegion.setPrefSize(10, 10);
                hBox.getChildren().add(popRegion);

                final String shortDescription = (shortInfo.length() == 160) ? shortInfo + "..." : shortInfo;

                popRegion.opacityProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        Platform.runLater(new Runnable() { //JIRA MOD-54 runLater used here as a workaround for RT-14396
                            @Override
                            public void run() {
                                if (popRegion.getOpacity() == 1) {
                                    infoName.setText("###InfoName###");
                                    infoDescription.setText(shortDescription);
                                    Point2D hBoxPos = hBox.localToScene(0, 0);
                                    extraInfoPopup.show(getScene().getWindow(),
                                            hBoxPos.getX() + contextMenu.getScene().getX() + contextMenu.getX() - infoBox.getPrefWidth() - 10,
                                            hBoxPos.getY() + contextMenu.getScene().getY() + contextMenu.getY() - 27
                                    );
                                }
                            }
                        });
                    }
                });
                // create menu item
                CustomMenuItem menuItem = new CustomMenuItem(hBox, true);
                menuItem.getStyleClass().add("search-menu-item");
                contextMenu.getItems().add(menuItem);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("SearchBox.handle menuItem.setOnAction");
                        }
                        //JIRA MOD-55 Liste externe Actions evtl EventBus
                    }
                });
            }
        }
    }

    @Override
    protected void layoutChildren() {
        textBox.resize(getWidth(), getHeight());
        clearButton.resizeRelocate(getWidth() - 18, 6, 12, 13);
    }
}
