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

import org.rapidpm.modul.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.modul.javafx.tableview.filtered.operators.StringOperator;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 03.05.13
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class DefaultStringOperation implements Operation<StringOperator, String> {

    public boolean check(StringOperator filter, String value2Test) {
        final IFilterOperator.Type type = filter.getType();
        final String filterValue = filter.getValue();

        if (type.equals(StringOperator.Type.NONE)) {
            //nüscht
        } else if (value2Test == null) {
            return true;
        } else if (type.equals(IFilterOperator.Type.EQUALS)) {
            if (filterValue.equals(value2Test)) {
                //alles ok
            } else {
                return true;
            }
        } else if (type.equals(IFilterOperator.Type.NOTEQUALS)) {
            if (filterValue.equals(value2Test)) {
                return true;
            } else {
                //alles ok
            }
        } else if (type.equals(IFilterOperator.Type.CONTAINS)) {
            if (value2Test.contains(filterValue)) {
                //alles ok
            } else {
                return true;
            }
        } else if (type.equals(IFilterOperator.Type.STARTSWITH)) {
            if (value2Test.startsWith(filterValue)) {
                //alles ok
            } else {
                return true;
            }
        } else if (type.equals(IFilterOperator.Type.ENDSWITH)) {
            if (value2Test.endsWith(filterValue)) {
                //alles ok
            } else {
                return true;
            }

        }
        return false;
    }
}
