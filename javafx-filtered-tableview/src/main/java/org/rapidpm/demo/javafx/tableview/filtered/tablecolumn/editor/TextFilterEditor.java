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

import java.util.ArrayList;
import java.util.EnumSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.rapidpm.demo.javafx.tableview.filtered.control.ComboBoxMenuItem;
import org.rapidpm.demo.javafx.tableview.filtered.control.TextFieldMenuItem;
import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.StringOperator;

/**
 * @author Sven Ruppert
 */
public class TextFilterEditor
        extends AbstractFilterEditor<StringOperator> {
    private String previousText;
    private StringOperator.Type previousType;

    private final TextField textField;
    private final ComboBox<StringOperator.Type> typeBox;

    private final String DEFAULT_TEXT;
    private final StringOperator.Type DEFAULT_TYPE;

    public TextFilterEditor(String title) {
        this(title, StringOperator.VALID_TYPES);
    }

    public TextFilterEditor(String title, EnumSet<IFilterOperator.Type> types) {
        super(title);

        DEFAULT_TEXT = "";
        DEFAULT_TYPE = StringOperator.Type.NONE;

        textField = new TextField();
        typeBox = new ComboBox<>();

        final ComboBoxMenuItem typeItem = new ComboBoxMenuItem(typeBox);
        final TextFieldMenuItem textItem = new TextFieldMenuItem(textField);

        addFilterMenuItem(typeItem);
        addFilterMenuItem(textItem);

        previousText = DEFAULT_TEXT;
        previousType = DEFAULT_TYPE;

        typeBox.getSelectionModel().select(DEFAULT_TYPE);
        typeBox.getItems().addAll(types);
        typeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StringOperator.Type>() {
            @Override
            public void changed(ObservableValue<? extends StringOperator.Type> ov, StringOperator.Type old, StringOperator.Type newVal) {
                textField.setDisable(newVal == StringOperator.Type.NONE);
            }
        });

        textField.setDisable(true);
    }

    @Override
    public StringOperator[] getFilters() throws Exception {
        final ArrayList<StringOperator> retList = new ArrayList<>();

        final String text = textField.getText();
        final StringOperator.Type selectedType = typeBox.getSelectionModel().getSelectedItem();
        if (selectedType == StringOperator.Type.NONE) {
            retList.add(new StringOperator(selectedType, ""));
        } else {
            if (text.isEmpty()) {
                throw new Exception("Filter text cannot be empty");
            } else {
                retList.add(new StringOperator(selectedType, text));
            }
        }
        return retList.toArray(new StringOperator[0]);
    }

    @Override
    public void cancel() {
        textField.setText(previousText);
        typeBox.getSelectionModel().select(previousType);
    }

    @Override
    public boolean save() throws Exception {
        boolean changed = false;

        final StringOperator.Type selectedType = typeBox.getSelectionModel().getSelectedItem();
        if (selectedType == DEFAULT_TYPE) {
            changed = clear();
        } else {
            changed = previousType != typeBox.getSelectionModel().getSelectedItem()
                    || (typeBox.getSelectionModel().getSelectedItem() != StringOperator.Type.NONE
                    && previousText.equals(textField.getText()) == false);

            previousText = textField.getText();
            previousType = typeBox.getSelectionModel().getSelectedItem();
            setFiltered(true);
            //changed = true;
        }

        return changed;
    }

    @Override
    public boolean clear() throws Exception {
        boolean changed = false;

        previousText = DEFAULT_TEXT;
        previousType = DEFAULT_TYPE;

        textField.setText(DEFAULT_TEXT);
        typeBox.getSelectionModel().select(DEFAULT_TYPE);

        if (isFiltered()) {
            setFiltered(false);
            changed = true;
        }

        return changed;
    }

}
