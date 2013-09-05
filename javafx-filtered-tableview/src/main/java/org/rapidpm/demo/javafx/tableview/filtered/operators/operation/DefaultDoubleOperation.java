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

package org.rapidpm.demo.javafx.tableview.filtered.operators.operation;

import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.NumberOperator;

/**
 * User: Sven Ruppert
 * Date: 05.09.13
 * Time: 18:20
 */
public class DefaultDoubleOperation implements Operation<NumberOperator<Double>, Double> {


    @Override public boolean check(NumberOperator<Double> filter, Double value2Test) {
        final IFilterOperator.Type type = filter.getType();
        final Double filterValue = filter.getValue();

        if (type.equals(NumberOperator.Type.NONE)) {
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
        } else if (type.equals(IFilterOperator.Type.GREATERTHAN)) {
            if (value2Test > filterValue) {
                //alles ok
            } else {
                return true;
            }
        } else if (type.equals(IFilterOperator.Type.GREATERTHANEQUALS)) {
            if (value2Test >= filterValue) {
                //alles ok
            } else {
                return true;
            }
        } else if (type.equals(IFilterOperator.Type.LESSTHAN)) {
            if (value2Test < filterValue) {
                //alles ok
            } else {
                return true;
            }
        } else if (type.equals(IFilterOperator.Type.LESSTHANEQUALS)) {
            if (value2Test <= filterValue) {
                //alles ok
            } else {
                return true;
            }
        }
        return false;
    }
}
