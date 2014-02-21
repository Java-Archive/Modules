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

package org.rapidpm.modul.javafx.tableview.filtered.filter;

/**

 */

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.rapidpm.modul.javafx.tableview.filtered.FilteredTableView;
import org.rapidpm.modul.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.modul.javafx.tableview.filtered.operators.operation.Operation;
import org.rapidpm.modul.javafx.tableview.filtered.tablecolumn.IFilterableTableColumn;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 03.05.13
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 *
 * @param <T>yp   der Typ der eine Zeile/Row beschreibt, z.B. TransientRechnung
 * @param <V>alue der den Zelleninhalt beschreibt der im vergleich verwendet werden soll.. z.B. String
 */
//JIRA MOD-57 die DefaultTypOperationen können per Annotation aus der Registry geholt werden.
public abstract class FilteredTableViewIterator<T, V> {
    private static final Logger logger = Logger.getLogger(FilteredTableViewIterator.class);

    private final List<T> remove = new ArrayList<T>();

    private final FilteredTableView filteredTableView;

    public FilteredTableViewIterator(final FilteredTableView tableView) {
        this.filteredTableView = tableView;
    }

    public void filter(final String columnName) {
        final ObservableList<T> data2Filter = filteredTableView.getItems();
        final ObservableList<TableColumn<T, ?>> tableColumns = filteredTableView.getColumns();
        for (final TableColumn<T, ?> tableColumn : tableColumns) {
            final String columnId = tableColumn.getText();
            if (columnId.equals(columnName)) {
                final IFilterableTableColumn filteredColumn = (IFilterableTableColumn) tableColumn;
                final ObservableList<IFilterOperator> filters = filteredColumn.getFilters();
                for (final IFilterOperator filter : filters) {

                    for (final T rowElement : data2Filter) {
                        final V value2Test = getValue(rowElement);
                        if (value2Test == null) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("value2Test == null -> wird behalten");
                            }
                            //remove.add(rowElement);
                        } else {
                            decide(filter, value2Test, rowElement);
                        }
                    }
                }
            }
        }
        data2Filter.removeAll(remove);
    }

    private void decide(final IFilterOperator filter, final V value2Test, final T rowElement) {
        final Operation defaultOp = getDefaultOperation();
        if (defaultOp.check(filter, value2Test)) {
            remove.add(rowElement);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("defaultOp.check(filter,value2Test) == false " + value2Test);
            }
        }
    }

    /**
     * hole das Value aus der Row das getestet werden soll
     * z.B. final String barcode = transientRechnung.getBarcode() + "";
     */
    public abstract V getValue(final T rowElement);

    /**
     * z.B. return new DefaultStringOperation();
     */
    public abstract Operation getDefaultOperation();

}