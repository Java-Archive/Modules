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

package org.rapidpm.demo.javafx.tableview.filtered.tablecolumn.editor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableCdiHolder;
import org.rapidpm.demo.javafx.tableview.filtered.control.ComboBoxMenuItem;
import org.rapidpm.demo.javafx.tableview.filtered.control.DateMenuItem;
import org.rapidpm.demo.javafx.tableview.filtered.operators.DateOperator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.module.se.commons.logger.Logger;
import thirdparty.eu.schudt.javafx.controls.calendar.DatePicker;

/**
 * @author Sven Ruppert
 */
public class DateFilterEditor
        extends AbstractFilterEditor<DateOperator> {
    private final Picker picker1;
    private final Picker picker2;

    //CDI
    private final CDIContainerSingleton cdiContainerSingleton = CDIContainerSingleton.getInstance();
    private final FilteredTableCdiHolder cdiHolder = cdiContainerSingleton.getManagedInstance(FilteredTableCdiHolder.class);
    private final Logger logger = cdiContainerSingleton.getManagedInstance(Logger.class);

    public DateFilterEditor(String title) {
        this(title, "", DateOperator.VALID_TYPES);
    }

    public DateFilterEditor(String title, String dateFormat) {
        this(title, dateFormat, DateOperator.VALID_TYPES);
    }

    public DateFilterEditor(String title, EnumSet<IFilterOperator.Type> types) {
        this(title, "", types);
    }

    public DateFilterEditor(String title, String dateFormat, EnumSet<IFilterOperator.Type> types) {
        super(title);

        final List<DateOperator.Type> set1 = new ArrayList<>(20);
        final List<DateOperator.Type> set2 = new ArrayList<>(20);
        parseTypes(types, set1, set2);

        if (dateFormat.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("setze default dateformat");
            }
            dateFormat = cdiHolder.getRessource("default.dateformat");

        } else {
            //nichts
        }

        picker1 = new Picker(dateFormat, set1.toArray(new DateOperator.Type[0]));
        picker2 = new Picker(dateFormat, set2.toArray(new DateOperator.Type[0]));

        // Disable the 2nd picker if the 1st picker isn't the start of a range
        picker2.setEnabled(false);
        picker1.typeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DateOperator.Type>() {
            @Override
            public void changed(ObservableValue<? extends DateOperator.Type> ov, DateOperator.Type old, DateOperator.Type newVal) {
                picker2.setEnabled(newVal.equals(DateOperator.Type.AFTER) || newVal.equals(DateOperator.Type.AFTERON));
            }
        });
    }

    private void parseTypes(EnumSet<IFilterOperator.Type> types, List<DateOperator.Type> set1, List<DateOperator.Type> set2) {
        set1.add(DateOperator.Type.NONE);
        set2.add(DateOperator.Type.NONE);
        for (DateOperator.Type type : types) {
            // Only these range types should show up in 2nd picker
            if (type.equals(DateOperator.Type.BEFORE) || type.equals(DateOperator.Type.BEFOREON)) {
                if (!set2.contains(type)) set2.add(type);
            }
            // Everything else but above types should show up in 1st picker
            else {
                if (!set1.contains(type)) set1.add(type);
            }
        }
    }

    @Override
    public DateOperator[] getFilters() throws Exception {
        final DateOperator val1 = picker1.getFilter();
        final DateOperator val2 = picker2.getFilter();
        final Date d1 = val1.getValue();
        final Date d2 = val2.getValue();

        // Bounds check the dates
        if (d1 != null && d2 != null && (d1.after(d2) || d1.equals(d2))
                && ((DateOperator.Type.AFTER == val1.getType() && DateOperator.Type.BEFORE == val2.getType())
                || (DateOperator.Type.AFTER == val1.getType() && DateOperator.Type.BEFOREON == val2.getType())
                || (DateOperator.Type.AFTERON == val1.getType() && DateOperator.Type.BEFORE == val2.getType())
                || (DateOperator.Type.AFTERON == val1.getType() && DateOperator.Type.BEFOREON == val2.getType())))
            throw new Exception("Second date cannot be before or the same as the first date");

        return new DateOperator[]{val1, val2};
    }

    @Override
    public void cancel() {
        picker1.cancel();
        picker2.cancel();
    }

    @Override
    public boolean save() throws Exception {
        boolean changed = false;

        final DateOperator do1 = picker1.getFilter();
        final DateOperator do2 = picker2.getFilter();

        if (do1.getType() == picker1.DEFAULT_TYPE && do2.getType() == picker2.DEFAULT_TYPE) {
            changed = clear();
        } else {
            final boolean changed1 = picker1.save();
            final boolean changed2 = picker2.save();
            setFiltered(true);
            changed = changed1 || changed2;
        }

        return changed;
    }

    @Override
    public boolean clear() throws Exception {
        boolean changed = false;

        picker1.clear();
        picker2.clear();

        if (isFiltered()) {
            setFiltered(false);
            changed = true;
        }

        return changed;
    }


    /**
     * Separate code out so we can reuse it for multiple Date picker groups
     */
    private class Picker {
        private final Date DEFAULT_DATE = null;
        private final DateOperator.Type DEFAULT_TYPE = DateOperator.Type.NONE;

        private Date previousDate = DEFAULT_DATE;
        private DateOperator.Type previousType = DEFAULT_TYPE;

        private final DatePicker datePicker;
        private final ComboBox<DateOperator.Type> typeBox;

        private Picker(String dateFormat, DateOperator.Type[] choices) {
            datePicker = new DatePicker();
            datePicker.setSelectedDate(DEFAULT_DATE);
            datePicker.setDateFormat(new SimpleDateFormat(dateFormat));
            final DateMenuItem textItem = new DateMenuItem(datePicker);

            typeBox = new ComboBox<>();
            typeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DateOperator.Type>() {
                @Override
                public void changed(ObservableValue<? extends DateOperator.Type> ov, DateOperator.Type old, DateOperator.Type newVal) {
                    datePicker.setDisable(newVal == DateOperator.Type.NONE);
                }
            });
            typeBox.getSelectionModel().select(DEFAULT_TYPE);
            typeBox.getItems().addAll(choices);
            final ComboBoxMenuItem typeItem = new ComboBoxMenuItem(typeBox);

            DateFilterEditor.this.addFilterMenuItem(typeItem);
            DateFilterEditor.this.addFilterMenuItem(textItem);
        }

        public void setEnabled(boolean enable) {
            typeBox.setDisable(!enable);
            datePicker.setDisable(!enable || typeBox.getSelectionModel().getSelectedItem() == DateOperator.Type.NONE);
        }

        public void cancel() {
            datePicker.setSelectedDate(previousDate);
            typeBox.getSelectionModel().select(previousType);
        }

        public void clear() {
            previousDate = DEFAULT_DATE;
            previousType = DEFAULT_TYPE;

            datePicker.setSelectedDate(DEFAULT_DATE);
            typeBox.getSelectionModel().select(DEFAULT_TYPE);
        }

        public boolean save() {
            final boolean changed = previousType != typeBox.getSelectionModel().getSelectedItem()
                    || (typeBox.getSelectionModel().getSelectedItem() != DateOperator.Type.NONE
                    && previousDate.equals(datePicker.getSelectedDate()) == false);

            previousDate = datePicker.getSelectedDate();
            previousType = typeBox.getSelectionModel().getSelectedItem();
            return changed;
        }

        public DateOperator getFilter() throws Exception {
            final Date date = datePicker.getSelectedDate();
            final DateOperator.Type selectedType = typeBox.getSelectionModel().getSelectedItem();
            if (typeBox.isDisable() || selectedType == DateOperator.Type.NONE) {
                return new DateOperator(DateOperator.Type.NONE, DEFAULT_DATE);
            } else {
                if (date == null) {
                    throw new Exception("Filter text cannot be empty");
                } else {
                    return new DateOperator(selectedType, date);
                }
            }
        }
    }

    ;

}
