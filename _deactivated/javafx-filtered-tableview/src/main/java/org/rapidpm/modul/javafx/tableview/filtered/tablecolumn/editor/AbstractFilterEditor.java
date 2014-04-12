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

package org.rapidpm.modul.javafx.tableview.filtered.tablecolumn.editor;

import java.util.Collection;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;
import org.rapidpm.modul.javafx.tableview.filtered.operators.IFilterOperator;

/**
 * @author Sven Ruppert
 */
public abstract class AbstractFilterEditor<R extends IFilterOperator>
        implements IFilterEditor<R> {
    private FilterContextMenu menu;
    private SimpleBooleanProperty filtered;

    public AbstractFilterEditor(String title) {
        menu = new FilterContextMenu(title);
        filtered = new SimpleBooleanProperty(false);

        menu.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                AbstractFilterEditor.this.cancel();
            }
        });
    }

    @Override
    public FilterContextMenu getFilterMenu() {
        return menu;
    }

    /**
     * Adds an item to the filter editor
     *
     * @param item
     */
    public void addFilterMenuItem(MenuItem item) {
        menu.addFilterMenuItem(item);
    }

    /**
     * Adds all items to the filter editor, in the supplied order
     *
     * @param items
     */
    public void addFilterMenuItems(Collection<? extends MenuItem> items) {
        menu.addFilterMenuItems(items);
    }

    public void clearFilterMenuItems() {
        menu.clearFilterMenuItems();
    }

    @Override
    public BooleanProperty filteredProperty() {
        return filtered;
    }

    @Override
    public boolean isFiltered() {
        return filtered.get();
    }

    /**
     * @param isFiltered If there are any non-default filters applied
     */
    protected void setFiltered(boolean isFiltered) {
        filtered.set(isFiltered);
    }
}
