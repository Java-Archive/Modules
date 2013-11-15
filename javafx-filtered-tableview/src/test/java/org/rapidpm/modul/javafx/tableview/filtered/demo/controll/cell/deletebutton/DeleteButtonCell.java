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


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.event.ActionEvent;
import javafx.scene.control.TableRow;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.commons.cdi.registry.property.CDIPropertyRegistryService;
import org.rapidpm.commons.cdi.registry.property.PropertyRegistryService;
import org.rapidpm.modul.javafx.tableview.control.cell.ButtonCell;
import org.rapidpm.modul.javafx.tableview.filtered.demo.model.TransientDemoDataRow;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 02.10.13
 * Time: 14:06
 */
public class DeleteButtonCell extends ButtonCell<TransientDemoDataRow> {

    private @Inject @CDILogger Logger logger;
    private @Inject @CDIPropertyRegistryService PropertyRegistryService propertyRegistryService;
    private @Inject DeleteButtonLogic logic;

    @Override public String getButtonLabelText() {
        return propertyRegistryService.getRessourceForKey("delete");
    }

    public DeleteButtonCell() {

    }

    @PostConstruct
    public void init() {
        super.init();
        if (logger.isDebugEnabled()) {
            logger.debug("DeleteButtonCell->init");
        }
        getActionList().add(new ButtonCellAction<TransientDemoDataRow>() {
            @Override public void execute(ButtonCell<TransientDemoDataRow> buttonCell, ActionEvent t) {
                if (logger.isDebugEnabled()) {
                    final Object source = t.getSource();
                    logger.debug("ButtonCellAction -> " + source);
                }
                final TableRow tableRow = buttonCell.getTableRow();
            }
        });
    }
}
