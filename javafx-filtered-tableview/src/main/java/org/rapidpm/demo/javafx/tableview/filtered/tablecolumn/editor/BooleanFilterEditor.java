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

import javafx.scene.control.ComboBox;
import org.rapidpm.demo.javafx.tableview.filtered.control.ComboBoxMenuItem;
import org.rapidpm.demo.javafx.tableview.filtered.operators.BooleanOperator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;

/**
 * @author Sven Ruppert
 */
public class BooleanFilterEditor
        extends AbstractFilterEditor<BooleanOperator> {

    private BooleanOperator.Type previousType;

    private final ComboBox<BooleanOperator.Type> typeBox;

    private final BooleanOperator.Type DEFAULT_TYPE;

    public BooleanFilterEditor(String title) {
        this(title, BooleanOperator.VALID_TYPES);
    }

    public BooleanFilterEditor(String title, EnumSet<IFilterOperator.Type> types) {
        super(title);

        DEFAULT_TYPE = BooleanOperator.Type.NONE;

        typeBox = new ComboBox<>();

        final ComboBoxMenuItem typeItem = new ComboBoxMenuItem(typeBox);

        addFilterMenuItem(typeItem);

        previousType = DEFAULT_TYPE;

        typeBox.getSelectionModel().select(DEFAULT_TYPE);
        typeBox.getItems().addAll(types);
    }

    @Override
    public BooleanOperator[] getFilters() throws Exception {
        final ArrayList<BooleanOperator> retList = new ArrayList<>();
        final BooleanOperator.Type selectedType = typeBox.getSelectionModel().getSelectedItem();
        if (selectedType == BooleanOperator.Type.NONE) {
            retList.add(new BooleanOperator(selectedType, null));
        } else {
            retList.add(new BooleanOperator(selectedType, selectedType == BooleanOperator.Type.TRUE));
        }
        return retList.toArray(new BooleanOperator[0]);
    }

    @Override
    public void cancel() {
        typeBox.getSelectionModel().select(previousType);
    }

    @Override
    public boolean save() throws Exception {
        boolean changed = false;

        final BooleanOperator.Type selectedType = typeBox.getSelectionModel().getSelectedItem();
        if (selectedType == DEFAULT_TYPE) {
            changed = clear();
        } else {
            changed = previousType != typeBox.getSelectionModel().getSelectedItem();
            previousType = typeBox.getSelectionModel().getSelectedItem();
            setFiltered(true);
        }

        return changed;
    }

    @Override
    public boolean clear() throws Exception {
        boolean changed = false;

        previousType = DEFAULT_TYPE;
        typeBox.getSelectionModel().select(DEFAULT_TYPE);

        if (isFiltered()) {
            setFiltered(false);
            changed = true;
        }

        return changed;
    }

}