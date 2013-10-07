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

import java.util.List;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.TableView;
import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.demo.javafx.tableview.filtered.tablecolumn.editor.IFilterEditor;

/**
 * An event that is fired when an {@link AbstractFilterableTableColumn} has its filter changed
 *
 * @author Sven Ruppert
 */
public class ColumnFilterEvent<S, T, R extends IFilterOperator, M extends IFilterEditor<R>>
        extends Event {
    /**
     * An event indicating that the filter has changed
     */
    public static final EventType<ColumnFilterEvent> FILTER_CHANGED_EVENT = new EventType<>(Event.ANY, "FILTER_CHANGED");

    private List<R> filter;

    private AbstractFilterableTableColumn<S, T, R, M> sourceColumn;


    public ColumnFilterEvent(TableView table, AbstractFilterableTableColumn<S, T, R, M> sourceColumn, List<R> filter) {
        super(table, Event.NULL_SOURCE_TARGET, ColumnFilterEvent.FILTER_CHANGED_EVENT);

        if (table == null) {
            throw new NullPointerException("TableView can not be null");
        }

        this.filter = filter;
        this.sourceColumn = sourceColumn;
    }

    /**
     * @return Any and all filters applied to the column
     */
    public List<R> getFilters() {
        return filter;
    }

    /**
     * @return The {@link AbstractFilterableTableColumn} which had its filter changed
     */
    public AbstractFilterableTableColumn<S, T, R, M> sourceColumn() {
        return sourceColumn;
    }
}
