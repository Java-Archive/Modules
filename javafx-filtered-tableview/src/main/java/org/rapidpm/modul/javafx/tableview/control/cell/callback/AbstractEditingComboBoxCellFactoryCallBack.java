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

package org.rapidpm.modul.javafx.tableview.control.cell.callback;

import java.util.List;

import javax.inject.Inject;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Callback;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.commons.cdi.se.CDIContainerSingleton;
import org.rapidpm.modul.javafx.tableview.filtered.FilteredTableDataRow;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 20.09.13
 * Time: 13:55
 */
public abstract class AbstractEditingComboBoxCellFactoryCallBack<T extends AbstractEditingComboBoxCellFactoryCallBack.GenericComboBoxCell>
        implements Callback<TableColumn<FilteredTableDataRow, ?>, TableCell<FilteredTableDataRow, ?>> {


    protected AbstractEditingComboBoxCellFactoryCallBack() {
        CDIContainerSingleton.getInstance().activateCDI(this);
    }

    @Override public TableCell<FilteredTableDataRow, ?> call(TableColumn<FilteredTableDataRow, ?> filteredTableDataRowTableColumn) {
        return getComboBoxCellInstance();
    }

    public abstract T getComboBoxCellInstance();

    public static abstract class GenericComboBoxCell<VT> extends ComboBoxTableCell<FilteredTableDataRow, VT> {

        private @Inject @CDILogger Logger logger;
        private boolean readOnlyView = false;

        @Override
        public void updateItem(VT o, boolean b) {
            if (logger.isDebugEnabled()) {
                logger.debug("ComboBoxTableCell->updateItem " + o);
            }
            super.updateItem(o, b);
            if (o == null) {
                //
            } else {
                final TableRow tableRow = getTableRow();
                if (tableRow == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("getTableRow(); == null");
                    }
                } else {
                    final FilteredTableDataRow row = (FilteredTableDataRow) tableRow.getItem();
                    getItems().clear();
                    if (disableComboBox(row)) {
                        this.setDisable(true);
                    } else if (readOnlyView) {
                        this.setDisable(true);
                    } else {

                        final List<VT> comboBoxValues = createComboBoxValues(row);
                        getItems().addAll(comboBoxValues);

                        this.setDisable(false);
                    }
                    workOnRowItself(row);
                }
            }
        }

        protected abstract GenericComboBoxCell<VT> getComboBoxCellRef();

        /**
         * logic to disable the combobox, for example if the value ist null or ...
         *
         * @return
         */
        public abstract boolean disableComboBox(final FilteredTableDataRow row);

        public abstract List<VT> createComboBoxValues(final FilteredTableDataRow row);

        public abstract void workOnRowItself(final FilteredTableDataRow row);


        public boolean isReadOnlyView() {
            return readOnlyView;
        }

        public void setReadOnlyView(boolean readOnlyView) {
            this.readOnlyView = readOnlyView;
        }
    }

}
