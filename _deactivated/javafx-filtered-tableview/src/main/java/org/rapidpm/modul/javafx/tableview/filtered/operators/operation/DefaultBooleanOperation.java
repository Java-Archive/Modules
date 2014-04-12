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

package org.rapidpm.modul.javafx.tableview.filtered.operators.operation;

import org.rapidpm.modul.javafx.tableview.filtered.operators.BooleanOperator;
import org.rapidpm.modul.javafx.tableview.filtered.operators.IFilterOperator;

/**
 * User: Sven Ruppert
 * Date: 20.09.13
 * Time: 16:40
 */
public class DefaultBooleanOperation implements Operation<BooleanOperator, Boolean> {
    @Override public boolean check(BooleanOperator filter, Boolean value2Test) {
        final IFilterOperator.Type type = filter.getType();
        final Boolean filterValue = filter.getValue();

        if (type.equals(BooleanOperator.Type.NONE)) {
            //nüscht
        } else if (value2Test == null) {
            return true;
        } else if (type.equals(IFilterOperator.Type.EQUALS)) {
            if (filterValue == value2Test) {
                //alles ok
            } else {
                return true;
            }
        } else if (type.equals(IFilterOperator.Type.NOTEQUALS)) {
            if (filterValue == value2Test) {
                return true;
            } else {
                //alles ok
            }
        }
        return false;
    }
}
