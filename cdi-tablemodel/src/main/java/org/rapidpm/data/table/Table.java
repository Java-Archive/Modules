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
import java.util.Map;
import java.util.Set;

import org.rapidpm.data.soaptable.SoapTable;
import org.rapidpm.data.table.formatter.CellValueFormatter;
import org.rapidpm.data.table.validator.RowValidator;
import org.rapidpm.data.table.validator.TableError;

/**
 * NeoScio
 * User: svenruppert
 * Date: 23.10.2009
 * Time: 13:30:19
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 */

public interface Table extends Iterable<Row> {

    public SoapTable exportAsSoapTable();


    public String getTableName();

    public void setTableName(String tableName);


    public String getFileName();

    public void setFileName(String fileName);


    public void setSortCol(int colNr, boolean asc);

    /**
     * rekursiv alle Spalten neu ordnen
     */
    public void reorderCols();


    /**
     * rekursiv alle Zeilen sortieren
     *
     * @param rowNr die Row nach der sortiert werden soll.
     * @param asc   aufsteigend oder absteigend sortieren.
     */
    public void reorderRows(int rowNr, boolean asc);


    /**
     * rekursiv alle Zellen in allen Zeilen sortieren
     */
    public void reorderCellsInAllRows();

    /**
     * Fuerg eine neue Zeile hinzu. Es wird nicht auf doppelten Datensaetze ueberpruft.
     * Nr der zeile ist immer, Aktuelle Anzahl plus eins.
     *
     * @param zeile
     */
    public void addRow(Row zeile);

    /**
     * Fügt eine Zeile hinzu.
     *
     * @param index Zeilenindex.
     * @param row   Zeile.
     */
    public void insertRow(int index, Row row);

    /**
     * Fügt eine leere Zeile hinzu.
     *
     * @param index Zeilenindex.
     */
    public void insertRow(int index);

    public Row getRow(int index);

    public void removeRow(int index);

    public void addNextColInfo(String colName, Class clazz, ColumnProperty... properties);

    public void addNextColInfo(String colName, Class clazz, CellValueFormatter cellValueFormatter, ColumnProperty... properties);

    public void addNextCell(String colName, Cell newCell, Row activeRow);

    public List<ColumnInformation> getColumnInfoList();

    public boolean hasColumn(String colName);

    public ColumnInformation getColumnInformationFor(String colName);

    public ColumnInformation getColumnInformationFor(int index);

    public Set<String> getColumnNames();

    public boolean changeColumnName(String oldName, String newName);

    public int getColumnCount();

    public int getVisibleColumnCount();

    public int[] getVisibleColumnIndices();

    public List<ColumnInformation> getVisibleColumns();

    public List getUniqueValuesForCol(String colName);

    public Map<String, ColumnInformation> getColInfoMap();

//    public void setColInfoMap(Map<String, ColumnInformation> colInfoMap);

    public List<Row> getRowList();

    public int getRowCount();

    public boolean isEmpty();

    public boolean isNotEmpty();

    public void setColInfo(String colName, ColumnInformation columnInformation);

    public Row createEmptyRow();

    public TableError validate();

    public void addRowValidator(RowValidator rowValidator);

    public List<RowValidator> getRowValidatorList();

}
