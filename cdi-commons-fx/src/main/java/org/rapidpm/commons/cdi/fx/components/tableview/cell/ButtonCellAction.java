package org.rapidpm.commons.cdi.fx.components.tableview.cell;

import javafx.event.ActionEvent;
import javafx.scene.control.TableRow;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

import javax.inject.Inject;

/**
* Created by ts40 on 19.03.2014.
*/
public abstract class ButtonCellAction<T> {
    public @Inject @CDILogger Logger logger;

    public void execute(CDIButtonCell<T> buttonCell, ActionEvent t) {
        if (logger.isDebugEnabled()) {
            final Object source = t.getSource();
            logger.debug("ButtonCellAction -> " + source);
        }
        final TableRow<T> tableRow = buttonCell.getTableRow();
        workOnRow(tableRow);
        final T item = tableRow.getItem();
        workOnRowItem(item);
    }

    public abstract void workOnRow(TableRow<T> row);
    public abstract void workOnRowItem(T row);
}
