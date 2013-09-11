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

package org.rapidpm.demo.javafx.tableview.control;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * User: Sven Ruppert
 * Date: 11.09.13
 * Time: 12:22
 */
public class ColumnWidthOptimizer {
    public void optimize(final TableView tableView) {
        //set optimal size
        final ObservableList<TableColumn<?, ?>> columns = tableView.getColumns();
        final ObservableList<TableColumn<?, ?>> visibleLeafColumns = tableView.getVisibleLeafColumns();
        final int size = visibleLeafColumns.size();
        final SimpleDoubleProperty newSizeProperty = new SimpleDoubleProperty(size);
        visibleLeafColumns.addListener(new ListChangeListener<TableColumn<?, ?>>() {
            @Override public void onChanged(Change<? extends TableColumn<?, ?>> change) {
                final int newSize = change.getList().size();
                newSizeProperty.set(newSize);
            }
        });
        for (final TableColumn<?, ?> column : columns) {
            column.prefWidthProperty().bind(tableView.widthProperty().divide(newSizeProperty));
        }
    }
}
