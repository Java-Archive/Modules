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

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import javafx.scene.control.TableRow;
import javafx.scene.control.cell.ComboBoxTableCell;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.javafx.tableview.filtered.demo.logic.ContextLogic;
import org.rapidpm.demo.javafx.tableview.filtered.demo.model.TransientDemoDataRow;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 05.09.13
 * Time: 18:37
 */
public class LegacyBetragComboBoxCell extends ComboBoxTableCell<TransientDemoDataRow, Double> {

    private @Inject @CDILogger Logger logger;
    private boolean readOnlyView = false;

    @Inject Instance<ContextLogic> contextLogicInstance;

    @Override
    public void updateItem(Double o, boolean b) {
        if (logger.isDebugEnabled()) {
            logger.debug("ComboBoxTableCell->updateItem " + o);
        }
        super.updateItem(o, b);
        if (o == null) {
            //
        } else {
            final TableRow tableRow = getTableRow();
            final TransientDemoDataRow row = (TransientDemoDataRow) tableRow.getItem();
            getItems().clear();
            if (row.getBetrag() == null || row.getBetrag().isInfinite() || row.getBetrag().isNaN()) {
                this.setDisable(true);
            } else if (readOnlyView) {
                this.setDisable(true);
            } else {
                final ContextLogic contextLogic = contextLogicInstance.get();
                final List<Double> doubleList = contextLogic.workOnContext(row.getBetrag());

                getItems().addAll(doubleList);

                this.setDisable(false);
            }
            //application logic on row itself
            //application logic external
        }
    }


    public boolean isReadOnlyView() {
        return readOnlyView;
    }

    public void setReadOnlyView(boolean readOnlyView) {
        this.readOnlyView = readOnlyView;
    }
}