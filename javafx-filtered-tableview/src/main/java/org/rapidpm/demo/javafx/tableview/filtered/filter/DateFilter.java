package org.rapidpm.demo.javafx.tableview.filtered.filter;

import java.util.Date;

import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableView;
import org.rapidpm.demo.javafx.tableview.filtered.operators.operation.DefaultDateOperation;
import org.rapidpm.demo.javafx.tableview.filtered.operators.operation.Operation;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 06.05.13
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public abstract class DateFilter <T> extends FilteredTableViewIterator<T, Date> {

    public DateFilter(FilteredTableView tableView) {
        super(tableView);
    }

    /**
     * z.B. return new DefaultStringOperation();
     */
    @Override
    public Operation getDefaultOperation() {
        return new DefaultDateOperation();
    }
}
