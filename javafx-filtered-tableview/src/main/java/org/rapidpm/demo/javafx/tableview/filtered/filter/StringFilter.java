package org.rapidpm.demo.javafx.tableview.filtered.filter;

import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableView;
import org.rapidpm.demo.javafx.tableview.filtered.filter.FilteredTableViewIterator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.operation.DefaultStringOperation;
import org.rapidpm.demo.javafx.tableview.filtered.operators.operation.Operation;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 03.05.13
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public  abstract class StringFilter<T> extends FilteredTableViewIterator<T, String> {

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
