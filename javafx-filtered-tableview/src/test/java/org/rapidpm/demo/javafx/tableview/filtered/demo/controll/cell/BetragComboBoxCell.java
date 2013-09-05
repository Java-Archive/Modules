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

import org.rapidpm.demo.javafx.tableview.filtered.demo.logic.ContextLogic;
import org.rapidpm.demo.javafx.tableview.filtered.demo.model.TransientDemoDataRow;

/**
 * User: Sven Ruppert
 * Date: 05.09.13
 * Time: 19:23
 */
public class BetragComboBoxCell extends GenericComboBoxCell<TransientDemoDataRow, Double> {

    @Inject Instance<ContextLogic> contextLogicInstance;


    @Override public boolean disableComboBox(TransientDemoDataRow row) {
        return row.getBetrag() == null || row.getBetrag().isInfinite() || row.getBetrag().isNaN();
    }

    @Override public List<Double> createComboBoxValues(TransientDemoDataRow row) {
        final ContextLogic contextLogic = contextLogicInstance.get();
        final List<Double> doubleList = contextLogic.workOnContext(row.getBetrag());
        return doubleList;
    }

    @Override public void workOnRowItself(TransientDemoDataRow row) {
        //nothing for this example
    }
}
