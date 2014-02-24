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

package org.rapidpm.modul.javafx.tableview.filtered.demo.controll.cell.deletebutton;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.rapidpm.commons.cdi.se.CDIContainerSingleton;
import org.rapidpm.modul.javafx.tableview.filtered.demo.model.TransientDemoDataRow;

/**
 * User: Sven Ruppert
 * Date: 02.10.13
 * Time: 14:15
 */
public class DeleteButtonCellFactory
        implements Callback<TableColumn<TransientDemoDataRow, ?>, TableCell<TransientDemoDataRow, ?>> {

    @Inject Instance<DeleteButtonCell> deleteButtonCellInstance;

    public DeleteButtonCellFactory() {
        CDIContainerSingleton.getInstance().activateCDI(this);
    }

    @Override public TableCell<TransientDemoDataRow, ?> call(TableColumn<TransientDemoDataRow, ?> column) {
        return deleteButtonCellInstance.get();
    }
}
