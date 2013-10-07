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
import java.util.List;

import javafx.scene.control.CheckBox;
import org.rapidpm.demo.javafx.tableview.filtered.control.CheckBoxMenuItem;
import org.rapidpm.demo.javafx.tableview.filtered.operators.EnumOperator;

/**
 * @author Sven Ruppert
 */
public class EnumFilterEditor<T>
        extends AbstractFilterEditor<EnumOperator<T>> {
    private boolean[] previousSelections;

    private List<CheckBox> enumCombos;


    public EnumFilterEditor(String title, T[] values) {
        super(title);
        populateMenuItems(values);
    }

    final public void populateMenuItems(T[] values) {
        final int len = values == null ? 0 : values.length;
        this.previousSelections = new boolean[len];
        this.enumCombos = new ArrayList<>(len);

        clearFilterMenuItems();
        final List<CheckBoxMenuItem> menuItems = new ArrayList<>(len);
        if (values != null) {
            for (T value : values) {
                final CheckBox ecb = new CheckBox(value.toString());
                ecb.setUserData(value);
                enumCombos.add(ecb);
                menuItems.add(new CheckBoxMenuItem(ecb));
            }
        }
        addFilterMenuItems(menuItems);
    }

    @Override
    public EnumOperator<T>[] getFilters() throws Exception {
        final ArrayList<EnumOperator> retList = new ArrayList<>();

        for (CheckBox emt : enumCombos) {
            if (emt.isSelected()) {
                retList.add(new EnumOperator<>(EnumOperator.Type.EQUALS, emt.getUserData()));
            }
        }

        if (retList.isEmpty()) {
            retList.add(new EnumOperator<>(EnumOperator.Type.NONE, null));
        }

        return retList.toArray(new EnumOperator[0]);
    }

    @Override
    public void cancel() {
        int i = 0;
        for (CheckBox emu : enumCombos) {
            emu.setSelected(previousSelections[i++]);
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean changed = false;

        // Determine if there are any changes
        boolean noSelections = true;
        selectionCheck:
        for (int i = 0; i < enumCombos.size(); i++) {
            final CheckBox emu = enumCombos.get(i);
            if (emu.isSelected()) {
                noSelections = false;
                break selectionCheck;
            }
        }

        if (noSelections) {
            changed = clear();
        } else {
            setFiltered(true);

            // Determine if anything's changed
            changedCheck:
            for (int i = 0; i < enumCombos.size(); i++) {
                final CheckBox emu = enumCombos.get(i);
                if (previousSelections[i] != emu.isSelected()) {
                    changed = true;
                    break changedCheck;
                }
            }

            // Save to previousSelection
            for (int i = 0; i < enumCombos.size(); i++) {
                final CheckBox emu = enumCombos.get(i);
                previousSelections[i] = emu.isSelected();
            }
        }

        return changed;
    }

    @Override
    public boolean clear() throws Exception {
        boolean changed = false;

        for (int i = 0; i < previousSelections.length; i++) {
            previousSelections[i] = false;
        }
        for (CheckBox emu : enumCombos) {
            emu.setSelected(false);
        }

        if (isFiltered()) {
            setFiltered(false);
            changed = true;
        }

        return changed;
    }

}