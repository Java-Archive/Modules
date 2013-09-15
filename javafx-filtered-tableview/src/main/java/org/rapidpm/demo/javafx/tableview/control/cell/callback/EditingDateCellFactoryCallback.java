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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.rapidpm.demo.cdi.commons.format.CDISimpleDateFormatter;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableDataRow;
import org.rapidpm.module.se.commons.logger.Logger;
import thirdparty.eu.schudt.javafx.controls.calendar.DatePicker;

/**
 * User: Sven Ruppert Date: 13.09.13 Time: 07:44
 */
public class EditingDateCellFactoryCallback implements Callback<TableColumn<FilteredTableDataRow, ? extends Date>, TableCell<FilteredTableDataRow, ? extends Date>> {
    @Override
    public TableCell<FilteredTableDataRow, ? extends Date> call(TableColumn<FilteredTableDataRow, ? extends Date> tTableColumn) {
        final EditingCell editingCell = CDIContainerSingleton.getInstance().getManagedInstance(EditingCell.class);
        return editingCell;
//        return editingCellInstance.get();
    }

    //@Inject Instance<EditingCell> editingCellInstance;

    public static class EditingCell extends TableCell<FilteredTableDataRow, Date> {

        private
        @Inject
        @CDILogger
        Logger logger;
        @Inject
        @CDISimpleDateFormatter(value = "default.yyyyMMdd")
        SimpleDateFormat sdf;   //TODO von aussen setzen

        //        @Inject Instance<DatePicker> datePickerInstance;
        private
        @Inject
        DatePicker datePicker;

        public EditingCell() {
        }

        @PostConstruct
        public void init() {
            if (getItem() == null) {

            } else {
                setText(sdf.format(getItem()));
            }
        }

        @Override
        public void startEdit() {
            if (logger.isDebugEnabled()) {
                logger.debug("startEdit");
            }
            if (!isEmpty()) {
                super.startEdit();
                createValueField();
                setText(null);
                setGraphic(datePicker);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("startEdit-> isEmpty() == true");
                }
            }
        }


        @Override
        public void commitEdit(Date date) {
            super.commitEdit(date);
            System.out.println("commitEdit-> date = " + date);
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            final Date item = getItem();
            final String format = sdf.format(item);
            if (logger.isDebugEnabled()) {
                logger.debug("cancelEdit->format = " + format);
            }
            setText(format);
            setGraphic(null);
        }

        @Override
        public void updateItem(Date item, boolean empty) {
            if (logger.isDebugEnabled()) {
                logger.debug("updateItem " + item);
            }
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("isEditing == true");
                    }
                    if (datePicker != null) {
                        datePicker.getTextField().setText(getString());
                    }
                    setText(null);
                    setGraphic(datePicker);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }


        private void createValueField() {
            if (logger.isDebugEnabled()) {
                logger.debug("createValueField");
            }
            //datePicker = datePickerInstance.get();
            datePicker.setDateFormat(sdf);
            datePicker.getTextField().setText(getString());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.getTextField().setEditable(false);
            final ObjectProperty<Date> dateObjectProperty = datePicker.selectedDateProperty();
            dateObjectProperty.addListener(new ChangeListener<Date>() {
                @Override
                public void changed(ObservableValue<? extends Date> observableValue, Date date, Date date2) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("changed");
                    }
                    commitEdit(datePicker.getSelectedDate());
                    setItem(datePicker.getSelectedDate());
                }
            });
        }

        private String getString() {
            if (logger.isDebugEnabled()) {
                logger.debug("getString called ");
            }
            if (getItem() == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("format -> = '' ");
                }
                return "";
            } else {
                final String format = sdf.format(getItem());
                if (logger.isDebugEnabled()) {
                    logger.debug("format -> " + format);
                }
                return format;
            }
        }
    }
}

