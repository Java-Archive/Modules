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

package org.rapidpm.modul.javafx.tableview.filtered.tablecolumn;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import org.rapidpm.modul.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.modul.javafx.tableview.filtered.tablecolumn.editor.IFilterEditor;

/**
 * @author Sven Ruppertheets@gmail.com
 */
public interface IFilterableTableColumn<R extends IFilterOperator, M extends IFilterEditor<R>> {
    /**
     * Note: this method can return { IFilterOperator.Type.NONE}'s.
     * Use {@link #isFiltered()} to determine if there is actually a filter
     * applied to this column
     *
     * @return All applied filters.
     */
    public ObservableList<R> getFilters();

    /**
     * @return Property indicating if this column has filters applied
     */
    public BooleanProperty filteredProperty();

    /**
     * @return If this column has filters applied
     */
    public boolean isFiltered();
}
