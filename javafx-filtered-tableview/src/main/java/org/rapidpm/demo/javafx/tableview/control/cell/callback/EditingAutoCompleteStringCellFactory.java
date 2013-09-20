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

package org.rapidpm.demo.javafx.tableview.control.cell.callback;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableDataRow;
import org.rapidpm.demo.javafx.textfield.autocomplete.AutoCompleteElement;
import org.rapidpm.demo.javafx.textfield.autocomplete.AutoCompleteTextField;
import org.rapidpm.demo.javafx.textfield.autocomplete.AutoCompleteTextFieldAction;

/**
 * User: Sven Ruppert Date: 17.09.13 Time: 15:24
 */
public class EditingAutoCompleteStringCellFactory implements Callback<TableColumn<FilteredTableDataRow, ?>, TableCell<FilteredTableDataRow, ?>> {

    public EditingAutoCompleteStringCellFactory() {
        CDIContainerSingleton.getInstance().activateCDI(this);
    }


    @Override public TableCell<FilteredTableDataRow, ?> call(TableColumn<FilteredTableDataRow, ?> tableColumn) {
        return editingCellInstance.get();
    }

    private @Inject Instance<EditingCell> editingCellInstance;

    public static class EditingCell extends AbstractEditingCell<String> {
        private @Inject @CDILogger org.rapidpm.module.se.commons.logger.Logger logger;
        @Inject AutoCompleteTextField<AutoCompleteElement> autoCompleteTextField;

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            final String item = getItem();
            if (logger.isDebugEnabled()) {
                logger.debug("cancelEdit->item = " + item);
            }
            setText(item);
            setGraphic(null);
        }

        @Override public void updateItemIsEditing() {
            if (autoCompleteTextField != null) {
                autoCompleteTextField.getTextbox().setText(getString());
            }
        }

        @Override public String getStringIfItemNotNull() {
            final String text = autoCompleteTextField.getText();
            return text;
        }

        @Override public void startEditIsNotEmptyLastActions() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override public Node getGraphicNode() {
            return autoCompleteTextField;
        }

        @Override public void createValueField() {
            autoCompleteTextField.getTextbox().setText(getItem());
            autoCompleteTextField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            //TODO inject on runtime
            autoCompleteTextField.getCustomActionsList().add(new AutoCompleteTextFieldAction() {
                @Override public void execute(AutoCompleteElement selectedItem) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("AutoCompleteTextFieldAction ... update other fields ??");
                    }
                }
            });
            autoCompleteTextField.getData().clear();
            //TODO inject on runtime
            final AutoCompleteElement e = new AutoCompleteElement();
            e.setId(1L);
            e.setKey("aaa");
            e.setShortinfo("ShortInfo - aaa");
            autoCompleteTextField.getData().add(e);

            autoCompleteTextField.getTextbox().focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(autoCompleteTextField.getTextbox().getText());
                    }
                }
            });

        }
    }


}
