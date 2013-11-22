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

package org.rapidpm.modul.javafx.tableview.control.cell.callback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.rapidpm.commons.cdi.se.CDIContainerSingleton;
import org.rapidpm.modul.javafx.tableview.filtered.FilteredTableDataRow;

/**
 * User: Sven Ruppert Date: 13.09.13 Time: 07:16
 */
public class EditingStringCellFactoryCallback implements Callback<TableColumn<FilteredTableDataRow, ?>, TableCell<FilteredTableDataRow, ?>> {

    public EditingStringCellFactoryCallback() {
        CDIContainerSingleton.getInstance().activateCDI(this);
    }

    @Override public TableCell<FilteredTableDataRow, ?> call(TableColumn<FilteredTableDataRow, ?> tableColumn) {
        return new EditingCell();
    }

    public static class EditingCell extends AbstractEditingCell<String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setGraphic(null);
        }

        public void createValueField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
                }
            });
        }

        @Override public void updateItemIsEditing() {
            if (textField != null) {
                textField.setText(getString());
            }
        }

        @Override public String getStringIfItemNotNull() {
            return getItem();
        }

        @Override public void startEditIsNotEmptyLastActions() {
            textField.selectAll();
        }

        @Override public Node getGraphicNode() {
            return textField;
        }
    }


}
