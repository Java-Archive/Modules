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

package org.rapidpm.demo.javafx.tableview.filtered;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;
import org.rapidpm.demo.javafx.tableview.filtered.tablecolumn.AbstractFilterableTableColumn;
import org.rapidpm.demo.javafx.tableview.filtered.tablecolumn.ColumnFilterEvent;
import org.rapidpm.module.se.commons.logger.Logger;


/**
 * A {@link TableView} that identifies any {@link AbstractFilterableTableColumn}'s added to it,
 * and fires a single event when any of them have their filters changed.
 * <p/>
 * To listen for changes the table's filters, register a {@link ColumnFilterEvent#FILTER_CHANGED_EVENT}
 * with {@link #addEventFilter(javafx.event.EventType, javafx.event.EventHandler) }
 * or {@link #addEventHandler(javafx.event.EventType, javafx.event.EventHandler) }
 *
 * @author Sven Ruppert
 */
public class FilteredTableView<T extends FilteredTableDataRow> extends TableView<T> {
    //private static final Logger logger = Logger.getLogger(FilteredTableView.class);

    final private Logger logger = CDIContainerSingleton.getInstance().getManagedInstance(Logger.class);

    /**
     * List of filterable columns with a filter applied
     */
    private ObservableList<AbstractFilterableTableColumn> filteredColumns;

    private List<MouseClickedRowAction> mouseDoubleClickedRowActions = new ArrayList<>();
    private List<MouseClickedRowAction> mouseSingleClickedRowActions = new ArrayList<>();
    private ObservableList<T> backupItems = FXCollections.observableArrayList();

    public void setTableViewData(final ObservableList<T> items) {
        super.setItems(items);
        backupItems.clear();
        backupItems.addAll(items);
    }

    public ObservableList<T> getBackupItems() {
        final ObservableList<T> copy = FXCollections.observableArrayList();
        copy.addAll(backupItems);
        return copy;
    }

    public void resetTableViewDataFromBackup() {
        super.setItems(getBackupItems());
    }


    public FilteredTableView(ObservableList<T> items) {
        this();
        setTableViewData(items);
    }

    public FilteredTableView() {
        super();

        filteredColumns = FXCollections.observableArrayList();

        // Execute the filteringChanged runnable
        // And, if a column has a filter on it, make sure that column is in our filteredColumns list
        final EventHandler<ColumnFilterEvent> columnFilteredEventHandler = new EventHandler<ColumnFilterEvent>() {
            @Override
            public void handle(ColumnFilterEvent event) {
                // Keep track of which TableColumn's are currently filtered
                final AbstractFilterableTableColumn col = event.sourceColumn();

                if (col.isFiltered() == true && filteredColumns.contains(col) == false) {
                    filteredColumns.add(col);
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("Filter added on column: %s", col.getText()));
                    }
                } else if (col.isFiltered() == false && filteredColumns.contains(col) == true) {
                    filteredColumns.remove(col);
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("Filter removed on column: %s", col.getText()));
                    }
                }

                // Forward event
                fireEvent(event);
            }
        };

        // Make sure any filterable columns on this table have the columnFilterEventHandler
        getColumns().addListener(new ListChangeListener<TableColumn>() {
            @Override
            public void onChanged(Change<? extends TableColumn> change) {
                change.next();// must advance to next change, for whatever reason...
                if (change.wasAdded()) {
                    for (final TableColumn<T, ?> col : change.getAddedSubList()) {
                        if (col instanceof AbstractFilterableTableColumn) {
                            logger.debug(String.format("Now listening for filter changes on column: %s", col.getText()));
                            final AbstractFilterableTableColumn fcol = (AbstractFilterableTableColumn) col;
                            fcol.addEventHandler(ColumnFilterEvent.FILTER_CHANGED_EVENT, columnFilteredEventHandler);
                        }
                    }
                }
                if (change.wasRemoved()) {
                    for (final TableColumn<T, ?> col : change.getAddedSubList()) {
                        if (col instanceof AbstractFilterableTableColumn) {
                            logger.debug(String.format("No longer listening for filter changes on column: %s", col.getText()));
                            final AbstractFilterableTableColumn fcol = (AbstractFilterableTableColumn) col;
                            fcol.removeEventHandler(ColumnFilterEvent.FILTER_CHANGED_EVENT, columnFilteredEventHandler);
                        }
                    }
                }
            }
        });

        this.setEditable(true);
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    final T selectedItem = getSelectedItem(mouseEvent);
                    for (final MouseClickedRowAction clickedRowAction : mouseDoubleClickedRowActions) {
                        clickedRowAction.workOnSelecteditem(selectedItem);
                    }
                }
                if (mouseEvent.getClickCount() == 1) {
                    final T selectedItem = getSelectedItem(mouseEvent);
                    for (final MouseClickedRowAction clickedRowAction : mouseSingleClickedRowActions) {
                        clickedRowAction.workOnSelecteditem(selectedItem);
                    }

                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("ClickCount <= 1 " + mouseEvent);
                    }
                }
            }
        });
    }

    private T getSelectedItem(MouseEvent mouseEvent) {
        final FilteredTableView<T> filteredTableView = (FilteredTableView<T>) mouseEvent.getSource();
        final TablePosition<T, ?> editingCell = filteredTableView.getEditingCell();
        if (logger.isDebugEnabled()) {
            logger.debug("edditing Cell " + editingCell);
        }
        final TableViewSelectionModel<T> selectionModel = filteredTableView.getSelectionModel();
        final T selectedItem = selectionModel.getSelectedItem();
        if (logger.isDebugEnabled()) {
            logger.debug("selectedItem = " + selectedItem);
        }
        return selectedItem;
    }


    /*
     * Load our custom CSS file to apply styling
     */
    @Override
    protected String getUserAgentStylesheet() {
        return FilteredTableView.class.getResource("filterableTableColumns.css").toExternalForm();
    }

    /**
     * @return Observable list containing any {@link AbstractFilterableTableColumn}'s that have a filter applied
     */
    public ObservableList<AbstractFilterableTableColumn> getFilteredColumns() {
        return filteredColumns;
    }

    public List<MouseClickedRowAction> getMouseDoubleClickedRowActions() {
        return mouseDoubleClickedRowActions;
    }

    public List<MouseClickedRowAction> getMouseSingleClickedRowActions() {
        return mouseSingleClickedRowActions;
    }

    public abstract class MouseClickedRowAction {
        public abstract void workOnSelecteditem(final T selectedItem);
    }
}
