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

package org.rapidpm.commons.cdi.fx.components.tableview.cell;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 02.10.13
 * Time: 13:58
 */
public abstract class CDIButtonCell<T> extends TableCell<T, Boolean> {

    public Button cellButton;

    public abstract String getButtonLabelText();

    private List<ButtonCellAction<T>> actionList = new ArrayList<>();

    private @Inject @CDILogger
    Logger logger;

    public CDIButtonCell() {

    }

    @PostConstruct
    public void init() {
        if (logger.isDebugEnabled()) {
            logger.debug("CDIButtonCell->init");
        }
        cellButton = new Button(getButtonLabelText());
        cellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                for (final ButtonCellAction<T> buttonCellAction : actionList) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("execute buttonCellAction-> " + buttonCellAction);
                    }
                    buttonCellAction.execute(CDIButtonCell.this, t);
                }
            }
        });
    }

    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setGraphic(cellButton);
        }
    }

    public static abstract class ButtonCellAction<T> {
        public abstract void execute(CDIButtonCell<T> CDIButtonCell, ActionEvent t);
    }


    public List<ButtonCellAction<T>> getActionList() {
        return actionList;
    }
}
