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
