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

import java.util.Date;

import org.rapidpm.demo.javafx.tableview.filtered.operators.DateOperator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.StringOperator;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 06.05.13
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public class DefaultDateOperation implements Operation<DateOperator, Date> {


    @Override
    public boolean check(DateOperator filter, Date value2Test) {
        //IFilterOperator.Type.AFTER, IFilterOperator.Type.AFTERON,
        // IFilterOperator.Type.BEFORE, IFilterOperator.Type.BEFOREON

        final IFilterOperator.Type type = filter.getType();
        final Date filterValue = filter.getValue();


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
        } else if (type.equals(IFilterOperator.Type.AFTER)) {
            if (value2Test.after(filterValue)) {
                //alles ok
            } else {
                return true;
            }
        } else if (type.equals(IFilterOperator.Type.AFTERON)) {
            if (value2Test.after(filterValue)) {
                //alles ok
            } else {
                if (value2Test.equals(filterValue)) {
                    //alles ok
                } else {
                    return true;
                }
            }
        } else if (type.equals(IFilterOperator.Type.BEFORE)) {
            if (value2Test.before(filterValue)) {
                //alles ok
            } else {
                return true;
            }

        } else if (type.equals(IFilterOperator.Type.BEFOREON)) {
            if (value2Test.before(filterValue)) {
                //alles ok
            } else {
                if (value2Test.equals(filterValue)) {
                    //alles ok
                } else {
                    return true;
                }
            }

        }
        return false;


    }
}
