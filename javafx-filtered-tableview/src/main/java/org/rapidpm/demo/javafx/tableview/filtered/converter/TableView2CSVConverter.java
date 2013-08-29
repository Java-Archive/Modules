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

package org.rapidpm.demo.javafx.tableview.filtered.converter;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * User: Sven Ruppert
 * Date: 29.08.13
 * Time: 10:01
 */
public abstract class TableView2CSVConverter<T> {


    private TableView<T> tableView;

    public StringBuilder convert() {
        final StringBuilder stringBuilder = new StringBuilder();
        final ObservableList<T> items = tableView.getItems();
        for (final T item : items) {
            convertItem2CSVRow(stringBuilder, item);
        }
        return stringBuilder;
    }

    public abstract void convertItem2CSVRow(StringBuilder stringBuilder, T item);


    public void setTableView(TableView<T> tableView) {
        this.tableView = tableView;
    }
}
