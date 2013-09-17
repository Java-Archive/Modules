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

import java.util.List;

import org.junit.Test;

/**
 * Created by Sven Ruppert on 16.09.13.
 */
public class TableImplTest {

    @Test
    public void testTable001() throws Exception {

        final BaseTableSimpleCreatorExecutor executor = new BaseTableSimpleCreatorExecutor() {
            @Override
            protected void initColDef(TableCreator tableCreator) {
                tableCreator.addNextColInfo("Spalte A", String.class, ColumnProperty.Sortable);
                tableCreator.addNextColInfo("Spalte B", String.class, ColumnProperty.Sortable);
            }

            @Override
            protected void createRows(TableCreator tableCreator) {
                tableCreator.addNewRow();
                tableCreator.addNextCell("Spalte A", CellTypeEnum.RawData, "val a1", null, "val a1");
                tableCreator.addNextCell("Spalte B", CellTypeEnum.RawData, "val b1", null, "val b1");
            }

            @Override
            protected String getTableName() {
                return "TabelName-ABC";
            }
        };

        final Table table = executor.execute();
        final List<Row> rowList = table.getRowList();
        for (final Row row : rowList) {
            System.out.println("row = " + row);
        }


    }
}
