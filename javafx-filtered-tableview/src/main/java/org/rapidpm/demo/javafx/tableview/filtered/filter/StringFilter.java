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
import org.rapidpm.demo.javafx.tableview.filtered.operators.operation.DefaultStringOperation;
import org.rapidpm.demo.javafx.tableview.filtered.operators.operation.Operation;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 03.05.13
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class StringFilter<T> extends FilteredTableViewIterator<T, String> {

    public StringFilter(FilteredTableView tableView) {
        super(tableView);
    }

    /**
     * z.B. return new DefaultStringOperation();
     */
    @Override
    public Operation getDefaultOperation() {
        return new DefaultStringOperation();
    }
}
