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

import org.rapidpm.demo.javafx.tableview.control.cell.callback.AbstractEditingComboBoxCellFactoryCallBack;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableDataRow;
import org.rapidpm.demo.javafx.tableview.filtered.demo.logic.ContextLogic;
import org.rapidpm.demo.javafx.tableview.filtered.demo.model.TransientDemoDataRow;

import static org.rapidpm.demo.javafx.tableview.filtered.demo.controll.cell.BetragEditingComboBoxCellFactoryCallBack.BetragComboBoxCell;

/**
 * User: Sven Ruppert
 * Date: 05.09.13
 * Time: 19:23
 */
public class BetragEditingComboBoxCellFactoryCallBack extends AbstractEditingComboBoxCellFactoryCallBack<BetragComboBoxCell> {


    @Inject Instance<BetragComboBoxCell> betragComboBoxCellInstance;

    @Override public BetragComboBoxCell getComboBoxCellInstance() {
        return betragComboBoxCellInstance.get();
    }


    public static class BetragComboBoxCell extends AbstractEditingComboBoxCellFactoryCallBack.GenericComboBoxCell<Double> {

        @Inject Instance<ContextLogic> contextLogicInstance;


        @Override protected GenericComboBoxCell<Double> getComboBoxCellRef() {
            return this;
        }

        /**
         * logic to disable the combobox, for example if the value ist null or ...
         *
         * @return
         */
        @Override public boolean disableComboBox(FilteredTableDataRow row) {
            final TransientDemoDataRow rowImpl = (TransientDemoDataRow) row;
            return rowImpl.getBetrag() == null || rowImpl.getBetrag().isInfinite() || rowImpl.getBetrag().isNaN();
        }

        @Override public List<Double> createComboBoxValues(FilteredTableDataRow row) {
            final ContextLogic contextLogic = contextLogicInstance.get();
            final TransientDemoDataRow rowImpl = (TransientDemoDataRow) row;
            final List<Double> doubleList = contextLogic.workOnContext(rowImpl.getBetrag());
            return doubleList;
        }

        @Override public void workOnRowItself(FilteredTableDataRow row) {

        }


    }


}
