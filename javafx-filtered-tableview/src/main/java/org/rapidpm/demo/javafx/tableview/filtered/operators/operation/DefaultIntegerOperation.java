package org.rapidpm.demo.javafx.tableview.filtered.operators.operation;

import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.NumberOperator;

/**
 * User: Sven Ruppert
 * Date: 22.08.13
 * Time: 10:20
 */
public class DefaultIntegerOperation implements Operation<NumberOperator<Integer>, Integer> {


    @Override public boolean check(NumberOperator<Integer> filter, Integer value2Test) {
        final IFilterOperator.Type type = filter.getType();
        final Integer filterValue = filter.getValue();

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
        }  else if (type.equals(IFilterOperator.Type.GREATERTHAN)) {
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
        }else if (type.equals(IFilterOperator.Type.LESSTHAN)) {
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
