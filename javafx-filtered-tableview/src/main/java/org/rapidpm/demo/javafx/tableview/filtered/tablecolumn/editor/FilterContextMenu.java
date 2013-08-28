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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Window;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableCdiHolder;
import org.rapidpm.demo.javafx.tableview.filtered.control.TitledSeparatorMenuItem;

/**
 * @author Sven Ruppert
 */
public class FilterContextMenu
        extends ContextMenu {

    private static FilterContextMenu currentlyVisibleMenu;

    private final MenuItem titleItem;
    private final MenuItem saveItem;
    private final MenuItem clearItem;
    private final List<MenuItem> menuItems;

    public FilterContextMenu(String title) {
        super();

        setAutoHide(false);

        final CDIContainerSingleton instance = CDIContainerSingleton.getInstance();
        final FilteredTableCdiHolder cdiHolder = instance.getManagedInstance(FilteredTableCdiHolder.class);

        menuItems = new ArrayList<>(30);
        titleItem = new TitledSeparatorMenuItem(title + " Filter");
        saveItem = new MenuItem(cdiHolder.getRessource("default.save"));
        clearItem = new MenuItem(cdiHolder.getRessource("default.clear"));
    }

    public FilterContextMenu(String title, MenuItem... items) {
        this(title);
        for (MenuItem i : items) {
            menuItems.add(i);
        }
    }

    public void addFilterMenuItem(MenuItem item) {
        menuItems.add(item);
        resetMenu();
    }

    public void addFilterMenuItems(Collection<? extends MenuItem> items) {
        menuItems.addAll(items);
        resetMenu();
    }

    public void clearFilterMenuItems() {
        menuItems.clear();
        resetMenu();
    }

    private void resetMenu() {
        getItems().clear();
        getItems().addAll(titleItem);
        getItems().addAll(menuItems);
        getItems().addAll(new SeparatorMenuItem(), saveItem, clearItem);
    }

    public void setSaveEvent(EventHandler<ActionEvent> event) {
        saveItem.setOnAction(event);
    }

    public void setClearEvent(EventHandler<ActionEvent> event) {
        clearItem.setOnAction(event);
    }

    /**
     * There can be only one... visible FilterContextMenu
     */
    private void highlander() {
        if (currentlyVisibleMenu != null && currentlyVisibleMenu != this) {
            currentlyVisibleMenu.hide();
        }
        currentlyVisibleMenu = this;
    }

    @Override
    public void hide() {
        if (currentlyVisibleMenu == this)
            currentlyVisibleMenu = null;
        super.hide();
    }

    @Override
    protected void show() {
        highlander();
        super.show();
    }

    @Override
    public void show(Window window) {
        highlander();
        super.show(window);
    }

    @Override
    public void show(Window window, double d, double d1) {
        highlander();
        super.show(window, d, d1);
    }

    @Override
    public void show(Node node, double d, double d1) {
        highlander();
        super.show(node, d, d1);
    }

    @Override
    public void show(Node node, Side side, double dx, double dy) {
        highlander();
        super.show(node, side, dx, dy);
    }

}
