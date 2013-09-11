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

package org.rapidpm.demo.javafx.tableview.filtered.tablecolumn;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.demo.javafx.tableview.filtered.tablecolumn.editor.IFilterEditor;
import org.rapidpm.module.se.commons.logger.Logger;


/**
 * Note: we hijack the ContextMenu to display the filter selection dialog.
 * Do not set the ContextMenu anywhere else.
 *
 * @author Sven Ruppert
 */
public class AbstractFilterableTableColumn<S, T, R extends IFilterOperator, M extends IFilterEditor<R>>
        extends TableColumn<S, T>
        implements IFilterableTableColumn<R, M> {
    private static final Logger logger = Logger.getLogger(AbstractFilterableTableColumn.class);

    private final M filterEditor;
    private final ObservableList<R> filterResults;


    public AbstractFilterableTableColumn(String name, final M filterEditor) {
        super(name);

        this.filterEditor = filterEditor;
        this.filterResults = FXCollections.observableArrayList();

        // Display a button on the column to show the menu
        final Button filterTrigger = new Button();

        filterTrigger.visibleProperty().bind(this.visibleProperty());

        filterTrigger.getStyleClass().add("filter-button-node");
        filterTrigger.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (getContextMenu().isShowing()) {
                    getContextMenu().hide();
                } else {
                    getContextMenu().show(filterTrigger, Side.BOTTOM, 0, 0);
                }

            }
        });


        // Change the filter button icon based on filtered status
        filteredProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal == Boolean.TRUE) {
                    filterTrigger.getStyleClass().add("active");
                } else {
                    filterTrigger.getStyleClass().remove("active");
                }
            }
        });

        // I'd love to do this, but you have to set the content to GRAPHIC_ONLY, but there's
        // no way to do that as the header skin is part of the table, not the column
        //final Label lbl = new Label();
        //lbl.textProperty().bind(this.textProperty());
        //final BorderPane pane = new BorderPane();
        //pane.setLeft(filterTrigger);
        //pane.setCenter(lbl);
        //setGraphic(pane);

        setGraphic(filterTrigger);
        setContextMenu(filterEditor.getFilterMenu());

        filterEditor.getFilterMenu().setClearEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    if (filterEditor.clear()) {
                        filterResults.setAll(filterEditor.getFilters());

                        final ColumnFilterEvent e = new ColumnFilterEvent(
                                AbstractFilterableTableColumn.this.getTableView()
                                , AbstractFilterableTableColumn.this
                                , getFilters());

                        Event.fireEvent(AbstractFilterableTableColumn.this, e);
                    }
                    getContextMenu().hide();
                } catch (Exception ex) {
                    logger.error(String.format("Error clearing filter on column: %s"
                            , AbstractFilterableTableColumn.this.getText()), ex);
                }
            }
        });

        filterEditor.getFilterMenu().setSaveEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    if (filterEditor.save()) {
                        filterResults.setAll(filterEditor.getFilters());

                        final ColumnFilterEvent e = new ColumnFilterEvent(
                                AbstractFilterableTableColumn.this.getTableView()
                                , AbstractFilterableTableColumn.this
                                , getFilters());

                        Event.fireEvent(AbstractFilterableTableColumn.this, e);
                    }
                    getContextMenu().hide();
                } catch (Exception ex) {
                    logger.error(String.format("Error saving filter on column: %s"
                            , AbstractFilterableTableColumn.this.getText()), ex);
                }
            }
        });
    }


    protected M getFilterEditor() {
        return filterEditor;
    }

    @Override
    public ObservableList<R> getFilters() {
        return filterResults;
    }

    @Override
    public final BooleanProperty filteredProperty() {
        return filterEditor.filteredProperty();
    }

    @Override
    public boolean isFiltered() {
        return filterEditor.isFiltered();
    }


}
