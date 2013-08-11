package org.rapidpm.demo.javafx.tableview.filtered.operators.operation;

import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 03.05.13
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public interface Operation<O extends IFilterOperator<T>, T> {


    public boolean check(O filter, T value2Test);
}
