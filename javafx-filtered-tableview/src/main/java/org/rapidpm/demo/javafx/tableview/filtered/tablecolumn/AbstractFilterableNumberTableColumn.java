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

package org.rapidpm.demo.javafx.tableview.filtered.tablecolumn;

import org.rapidpm.demo.javafx.tableview.filtered.operators.NumberOperator;
import org.rapidpm.demo.javafx.tableview.filtered.tablecolumn.editor.NumberFilterEditor;

/**
 * You probably shouldn't ever extend this class.
 * It's only meant to be used by certain Java built-in classes which extend {@link Number},
 * almost all of which I've created sub-classes for.
 *
 * @author Sven Ruppert
 */
public class AbstractFilterableNumberTableColumn<S, T extends Number>
        extends AbstractFilterableTableColumn<S, T, NumberOperator<T>, NumberFilterEditor<T>> {
    public AbstractFilterableNumberTableColumn(Class<T> klass) {
        this("", klass);
    }

    public AbstractFilterableNumberTableColumn(String text, Class<T> klass) {
        super(text, new NumberFilterEditor<>(text, klass));
    }
}
