package org.rapidpm.commons.cdi.fx.components.tableview.cell;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Created by ts40 on 19.03.2014.
 * RT == RowType
 */
public class CDIBaseButtonCellValueFactory<RT> implements Callback<TableColumn.CellDataFeatures<RT, Boolean>,ObservableValue<Boolean>> {

    @Override
    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<RT, Boolean> p) {
        final Object value = p.getValue();
        return new SimpleBooleanProperty(value != null);
    }
}
