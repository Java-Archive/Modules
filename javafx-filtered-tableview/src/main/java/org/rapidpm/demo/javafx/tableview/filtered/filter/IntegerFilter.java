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

package org.rapidpm.demo.javafx.tableview.filtered.filter;

import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableView;
import org.rapidpm.demo.javafx.tableview.filtered.operators.operation.DefaultIntegerOperation;
import org.rapidpm.demo.javafx.tableview.filtered.operators.operation.Operation;

/**
 * User: Sven Ruppert
 * Date: 22.08.13
 * Time: 10:36
 */
public abstract class IntegerFilter<T> extends FilteredTableViewIterator<T, Integer> {

    public IntegerFilter(FilteredTableView tableView) {
        super(tableView);
    }

    /**
     * z.B. return new DefaultStringOperation();
     */
    @Override public Operation getDefaultOperation() {
        return new DefaultIntegerOperation();
    }
}
