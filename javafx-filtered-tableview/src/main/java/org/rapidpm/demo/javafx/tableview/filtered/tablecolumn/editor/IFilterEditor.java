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

package org.rapidpm.demo.javafx.tableview.filtered.tablecolumn.editor;

import javafx.beans.property.BooleanProperty;
import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;

/**
 * A graphical interface used to change filters
 *
 * @author Sven Ruppert
 */
public interface IFilterEditor<R extends IFilterOperator> {
    /**
     * @return The user entered filters
     * @throws Exception
     */
    abstract public R[] getFilters() throws Exception;

    /**
     * Cancel filter editing
     */
    abstract public void cancel();

    /**
     * Set the filter to the saved
     *
     * @return If the filter was successfully saved
     * @throws Exception
     */
    abstract public boolean save() throws Exception;

    /**
     * Clears the filter back to its default state.
     * If successful, the menu should hide.
     *
     * @return If the filter was successfully cleared
     * @throws Exception
     */
    abstract public boolean clear() throws Exception;

    /**
     * @return The menu used to change the filter
     */
    abstract public FilterContextMenu getFilterMenu();

    /**
     * @return Property identifying if there is a filter set
     */
    abstract public BooleanProperty filteredProperty();

    /**
     * @return If there is currently a filter set
     */
    abstract public boolean isFiltered();
}
