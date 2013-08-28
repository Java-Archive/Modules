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

package org.rapidpm.data.table.importer;
/**
 * Sven Ruppert
 * User: svenruppert / Sven Ruppert
 * Date: 06.05.12
 * Time: 15:59
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.BaseTableSimpleCreatorExecutor;
import org.rapidpm.data.table.CellTypeEnum;
import org.rapidpm.data.table.ColumnInformation;
import org.rapidpm.data.table.ColumnProperty;
import org.rapidpm.data.table.Table;
import org.rapidpm.data.table.TableCreator;

public class CSV2TableImporter {
    private static final Logger logger = Logger.getLogger(CSV2TableImporter.class);


    public Table convert(final File datafile) {

        final BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(datafile));
            final String name = datafile.getName();

            return convert(br, name);

        } catch (IOException e) {
            logger.error(e);
        }
        return null;

    }

    public Table convert(final BufferedReader br, final String name) {
        final BaseTableSimpleCreatorExecutor creatorExecutor = new BaseTableSimpleCreatorExecutor() {
            @Override
            protected void initColDef(TableCreator tableCreator) {
                //erste Zeile
                try {
                    final String line = br.readLine();
                    final String[] cols = line.split(";");
                    for (String col : cols) {
                        if (col.startsWith("\"") && col.endsWith("\"")) {
                            col = col.replaceFirst("\"", "").subSequence(0, col.length() - 2) + "";
                        } else {

                        }
                        tableCreator.addNextColInfo(col, String.class, ColumnProperty.Sortable, ColumnProperty.Editable);
                    }
                } catch (IOException e) {
                    logger.error(e);
                }
            }

            @Override
            protected void createRows(TableCreator tableCreator) {
                try {
                    String line = br.readLine();
                    final Table table = tableCreator.getTable();
                    final int colInfoSize = table.getColumnInfoList().size();
                    while (line != null) {
                        tableCreator.addNewRow();
                        final String[] cols = line.split(";");
                        for (int i = 0; i < cols.length && i < colInfoSize; i++) {
                            String col = cols[i];
                            if (col.startsWith("\"") && col.endsWith("\"")) {
                                col = col.replaceFirst("\"", "").subSequence(0, col.length() - 2) + "";
                            } else {

                            }
                            final ColumnInformation colInfo = table.getColumnInformationFor(i);
                            tableCreator.addNextCell(colInfo.getSpaltenName(), CellTypeEnum.RawData, col, "", null);
                        }
                        line = br.readLine();
                    }
                } catch (IOException e) {
                    logger.error(e);
                }

            }

            @Override
            protected String getTableName() {
                return name;
            }
        };
        final Table table = creatorExecutor.execute();
        return table;
    }

}
