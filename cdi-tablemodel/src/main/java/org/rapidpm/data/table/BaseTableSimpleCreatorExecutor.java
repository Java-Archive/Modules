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

package org.rapidpm.data.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rapidpm.Constants;

/**
 * Sven Ruppert - www.svenruppert.de
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 30.09.2010
 * Time: 14:28:09
 */

public abstract class BaseTableSimpleCreatorExecutor {
    private static final Logger logger = Logger.getLogger(BaseTableSimpleCreatorExecutor.class);

    private SimpleDateFormat format = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);

    protected abstract void initColDef(final TableCreator tableCreator);

    protected abstract void createRows(final TableCreator tableCreator);

    protected abstract String getTableName();


    protected TableCreator createTableCreator() {
        return new TableCreator<>(new BaseCellFactory());
    }

    public Table execute() {
        final TableCreator tableCreator = createTableCreator();
        tableCreator.createNewTableInstance();
        initColDef(tableCreator);
        createRows(tableCreator);

        final Table table = tableCreator.getTable();
        table.reorderCellsInAllRows();
        if (getTableName() == null || getTableName().isEmpty()) {
            table.setTableName("Export_" + format.format(new Date()));
        } else {
            table.setTableName(getTableName());
        }
        return table;
    }


}
