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

package org.rapidpm.demo.javafx.tableview.filtered.demo.controll.cell;

import java.util.List;

import javax.inject.Inject;

import javafx.scene.control.TableRow;
import javafx.scene.control.cell.ComboBoxTableCell;
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

                getItems().addAll(createComboBoxValues(row));

                this.setDisable(false);
            }
            workOnRowItself(row);
        }
    }


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
