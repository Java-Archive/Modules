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

package org.rapidpm.demo.javafx.tableview.control.cell;

import java.util.List;

import javax.inject.Inject;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Callback;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 05.09.13
 * Time: 19:16
 * <p/>
 * RT RowType
 * VT ValueType
 */
public abstract class GenericComboBoxCell<RT, VT> extends ComboBoxTableCell<RT, VT> {

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
            final RT row = (RT) tableRow.getItem();
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

    public void associateWithCol(final TableView<RT> tableView, final String colName) {
        final ObservableList<TableColumn<RT, ?>> columns = tableView.getColumns();
        for (final TableColumn<RT, ?> column : columns) {
            final String columnText = column.getText();
            if (columnText.equals(colName)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("associating to col " + columnText);
                }
                associate((TableColumn<RT, VT>) column);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("not binding to this col : " + columnText);
                }
            }
        }
    }

    private void associate(TableColumn<RT, VT> column) {
        column.setCellFactory(new Callback<TableColumn<RT, VT>, TableCell<RT, VT>>() {
            @Override
            public TableCell<RT, VT> call(TableColumn<RT, VT> rtTableColumn) {
                final GenericComboBoxCell<RT, VT> mySelf = getComboBoxCellRef();
                mySelf.setComboBoxEditable(false);
                mySelf.setReadOnlyView(readOnlyView);
                return mySelf;
            }
        });
    }

    protected abstract GenericComboBoxCell<RT, VT> getComboBoxCellRef();

    /**
     * logic to disable the combobox, for example if the value ist null or ...
     *
     * @return
     */
    public abstract boolean disableComboBox(final RT row);

    public abstract List<VT> createComboBoxValues(final RT row);

    public abstract void workOnRowItself(final RT row);


    public boolean isReadOnlyView() {
        return readOnlyView;
    }

    public void setReadOnlyView(boolean readOnlyView) {
        this.readOnlyView = readOnlyView;
    }
}
