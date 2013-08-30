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

package org.rapidpm.data.table.export;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.data.table.Cell;
import org.rapidpm.data.table.ColumnInformation;
import org.rapidpm.data.table.Row;
import org.rapidpm.data.table.Table;

/**
 * Sven Ruppert - www.svenruppert.de
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 05.05.2010
 * Time: 14:32:33
 */

public class Table2CSV implements TableExport<String> {
    private static final Logger logger = Logger.getLogger(Table2CSV.class);


    private Table table;


    @Override
    public String export(final Table table) {
        final Collection<ColumnInformation> infoList = table.getColumnInfoList();

        String result = "";

        String header = "";
        for (final ColumnInformation columnInformation : infoList) {
            if (columnInformation.isExportable()) {
                final String spaltenName = columnInformation.getSpaltenName();
                header = header + spaltenName + " ; ";
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Col nicht exportable " + columnInformation.getSpaltenName());
                }
            }
        }
        header = header + Constants.LINE_BREAK;

        result = result + header;

        final List<Row> rowList = table.getRowList();
        for (final Row row : rowList) {
            String rowData = "";
            final List<Cell> cellList = row.getCells();
            for (final Cell cell : cellList) {
                final ColumnInformation information = cell.getColInfo();
                if (information.isExportable()) {
                    rowData = rowData + cell.getFormattedValue() + " ; ";
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Zelle not exportable ");
                    }
                }

            }
            rowData = rowData + Constants.LINE_BREAK;
            result = result + rowData;
        }
        return result;
    }

}
