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

package org.rapidpm.modul.javafx.tableview.filtered.tablecolumn.editor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.rapidpm.modul.javafx.tableview.filtered.control.ComboBoxMenuItem;
import org.rapidpm.modul.javafx.tableview.filtered.control.TextFieldMenuItem;
import org.rapidpm.modul.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.modul.javafx.tableview.filtered.operators.NumberOperator;

/**
 * @author Sven Ruppert
 */
public class NumberFilterEditor<T extends Number>
        extends AbstractFilterEditor<NumberOperator<T>> {
    private final Class<T> klass;
    private final NumberFilterEditor.Picker picker1;
    private final NumberFilterEditor.Picker picker2;

    public NumberFilterEditor(String title, Class<T> klass) {
        this(title, klass, NumberOperator.VALID_TYPES);
    }

    public NumberFilterEditor(String title, Class<T> klass, EnumSet<IFilterOperator.Type> types) {
        super(title);
        this.klass = klass;

        final List<NumberOperator.Type> set1 = new ArrayList<>(20);
        final List<NumberOperator.Type> set2 = new ArrayList<>(20);
        parseTypes(types, set1, set2);

        picker1 = new Picker(set1.toArray(new NumberOperator.Type[0]));
        picker2 = new Picker(set2.toArray(new NumberOperator.Type[0]));

        // Disable the 2nd picker if the 1st picker isn't the start of a range
        picker2.setEnabled(false);
        picker1.typeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NumberOperator.Type>() {
            @Override
            public void changed(ObservableValue<? extends NumberOperator.Type> ov, NumberOperator.Type old, NumberOperator.Type newVal) {
                picker2.setEnabled(newVal.equals(NumberOperator.Type.GREATERTHAN) || newVal.equals(NumberOperator.Type.GREATERTHANEQUALS));
            }
        });
    }

    private void parseTypes(EnumSet<IFilterOperator.Type> types, List<NumberOperator.Type> set1, List<NumberOperator.Type> set2) {
        set1.add(NumberOperator.Type.NONE);
        set2.add(NumberOperator.Type.NONE);
        for (NumberOperator.Type type : types) {
            // Only these range types should show up in 2nd picker
            if (type.equals(NumberOperator.Type.LESSTHAN) || type.equals(NumberOperator.Type.LESSTHANEQUALS)) {
                if (!set2.contains(type)) set2.add(type);
            }
            // Everything else but above types should show up in 1st picker
            else {
                if (!set1.contains(type)) set1.add(type);
            }
        }
    }

    @Override
    public NumberOperator<T>[] getFilters() throws Exception {
        final NumberOperator<T> val1 = picker1.getFilter();
        final NumberOperator<T> val2 = picker2.getFilter();

        //JIRA MOD-21 if the Types are ranges, we should probably check that they're within the proper bounds.  Need a separate check for each <T> though

        return new NumberOperator[]{val1, val2};
    }

    @Override
    public void cancel() {
        picker1.cancel();
        picker2.cancel();
    }

    @Override
    public boolean save() throws Exception {
        boolean changed = false;

        final NumberOperator<T> do1 = picker1.getFilter();
        final NumberOperator<T> do2 = picker2.getFilter();

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
        private final String DEFAULT_TEXT = "";
        private final NumberOperator.Type DEFAULT_TYPE = NumberOperator.Type.NONE;

        private String previousText = DEFAULT_TEXT;
        private NumberOperator.Type previousType = DEFAULT_TYPE;

        private final TextField textField;
        private final ComboBox<NumberOperator.Type> typeBox;

        private Picker(NumberOperator.Type[] choices) {
            textField = new TextField(DEFAULT_TEXT);
            final TextFieldMenuItem textItem = new TextFieldMenuItem(textField);

            typeBox = new ComboBox<>();
            typeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NumberOperator.Type>() {
                @Override
                public void changed(ObservableValue<? extends NumberOperator.Type> ov, NumberOperator.Type old, NumberOperator.Type newVal) {
                    textField.setDisable(newVal == NumberOperator.Type.NONE);
                }
            });
            typeBox.getSelectionModel().select(DEFAULT_TYPE);
            typeBox.getItems().addAll(choices);
            final ComboBoxMenuItem typeItem = new ComboBoxMenuItem(typeBox);

            NumberFilterEditor.this.addFilterMenuItem(typeItem);
            NumberFilterEditor.this.addFilterMenuItem(textItem);
        }

        public void setEnabled(boolean enable) {
            typeBox.setDisable(!enable);
            textField.setDisable(!enable || typeBox.getSelectionModel().getSelectedItem() == NumberOperator.Type.NONE);
        }

        public void cancel() {
            textField.setText(previousText);
            typeBox.getSelectionModel().select(previousType);
        }

        public void clear() {
            previousText = DEFAULT_TEXT;
            previousType = DEFAULT_TYPE;

            textField.setText(DEFAULT_TEXT);
            typeBox.getSelectionModel().select(DEFAULT_TYPE);
        }

        public boolean save() {
            final boolean changed = previousType != typeBox.getSelectionModel().getSelectedItem()
                    || (typeBox.getSelectionModel().getSelectedItem() != NumberOperator.Type.NONE
                    && previousText.equals(textField.getText()) == false);

            previousText = textField.getText();
            previousType = typeBox.getSelectionModel().getSelectedItem();

            return changed;
        }

        public NumberOperator<T> getFilter() throws Exception {
            final String text = textField.getText();
            final NumberOperator.Type selectedType = typeBox.getSelectionModel().getSelectedItem();

            if (typeBox.isDisable() || selectedType == NumberOperator.Type.NONE) {
                return new NumberOperator(NumberOperator.Type.NONE, 0);
            } else {
                if (text.isEmpty()) {
                    throw new Exception("Filter text cannot be empty");
                } else {
                    Number number;
                    if (klass == BigInteger.class) {
                        number = new BigInteger(text);
                    } else if (klass == BigDecimal.class) {
                        number = new BigDecimal(text);
                    } else if (klass == Byte.class) {
                        number = Byte.parseByte(text);
                    } else if (klass == Short.class) {
                        number = Short.parseShort(text);
                    } else if (klass == Integer.class) {
                        number = Integer.parseInt(text);
                    } else if (klass == Long.class) {
                        number = Long.parseLong(text);
                    } else if (klass == Float.class) {
                        number = Float.parseFloat(text);
                    } else // Double
                    {
                        number = Double.parseDouble(text);
                    }

                    return new NumberOperator(selectedType, number);
                }
            }
        }
    }

    ;

}
