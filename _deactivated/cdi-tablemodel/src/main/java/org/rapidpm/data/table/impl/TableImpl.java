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

package org.rapidpm.data.table.impl;
/**
 * NeoScio
 * User: svenruppert
 * Date: 28.10.2009
 * Time: 01:13:44
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rapidpm.data.soaptable.SoapTable;
import org.rapidpm.data.soaptable.SoapTableCreator;
import org.rapidpm.data.table.BaseCellFactory;
import org.rapidpm.data.table.Cell;
import org.rapidpm.data.table.ColumnInformation;
import org.rapidpm.data.table.ColumnProperty;
import org.rapidpm.data.table.Row;
import org.rapidpm.data.table.Table;
import org.rapidpm.data.table.comparator.zelle.ZellenComparator;
import org.rapidpm.data.table.formatter.CellValueFormatter;
import org.rapidpm.data.table.validator.RowValidator;
import org.rapidpm.data.table.validator.TableError;

/**
 * Diese Klasse sollte immer über den TableCreator erzeugt und befüllt werden.
 */
public class TableImpl implements Table {
    private static final Logger logger = Logger.getLogger(TableImpl.class);

    private String tableName = "";
    private String fileName = null;

    private int sortColNr = 0;
    private boolean sortColDirection = true;

    //private Map<String, ColumnInformation> colInfoMap = new HashMap<String, ColumnInformation>();
    private final List<ColumnInformation> columnInfoList = new ArrayList<>();
    private transient final Map<String, Integer> columnInfoIndex = new HashMap<>();

    private final List<Row> rowList = new ArrayList<>();
    private final List<RowValidator> rowValidatorList = new ArrayList<>();


    @Override
    public Row getRow(final int index) {
        return rowList.get(index);
    }

    @Override
    public void removeRow(final int index) {
        rowList.remove(index);
        for (final ColumnInformation ci : columnInfoList) {
            //JIRA MOD-33 zellen direkt löschen
            final List zellen = ci.getZellen();
            if (zellen.size() > index) {
                zellen.remove(index);
            }
        }
    }

    @Override
    public ColumnInformation getColumnInformationFor(final int index) {
        return columnInfoList.get(index);
    }

    @Override
    public SoapTable exportAsSoapTable() {
        final SoapTableCreator creator = new SoapTableCreator() {
            @Override
            public void addAllColInfoEntries() {
                final Collection<ColumnInformation> columnInfoList = getColumnInfoList();
                for (final ColumnInformation columnInformation : columnInfoList) {
                    //JIRA MOD-34 Col Typeinfo fehlt noch
                    // final Cell cell = (Cell) columnInformation.getZellen().get(0);
                    // cell.is

//                    addColInfo(columnInformation.getSpaltenName(), SoapTable.ColInfoType.STRING); //CellValueMapping
                    addColInfo(columnInformation.getSpaltenName()); //CellValueMapping
                }
            }

            @Override
            public void addAllColEntries() {
                final List<Row> rows = getRowList();
                for (final Row row : rows) {
                    startNewRow();
                    final List<Cell> cells = row.getCells();
                    for (final Cell cell : cells) {
                        addColEntry(cell.getFormattedValue());
                    }
                    stopNewRow();
                }
            }
        };
        return creator.execute().getTable();
    }

    @Override
    public boolean isEmpty() {
        return rowList.isEmpty();
    }

    @Override
    public boolean isNotEmpty() {
        return !rowList.isEmpty();
    }

    @Override
    public Iterator<Row> iterator() {
        return rowList.iterator();
    }

    @Override
    public void setColInfo(final String colName, final ColumnInformation columnInformation) {
        columnInfoList.add(columnInformation);
        columnInfoIndex.put(colName, columnInfoList.size() - 1);
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, ColumnInformation> getColInfoMap() {
        final Map<String, ColumnInformation> colInfoMap = new HashMap<>();
        for (final ColumnInformation columnInformation : columnInfoList) {
            colInfoMap.put(columnInformation.getSpaltenName(), columnInformation);
        }
        return colInfoMap;
    }

    @Override
    public int getRowCount() {
        return rowList.size();
    }

    @Override
    public void setSortCol(final int colNr, final boolean asc) {
        this.sortColNr = colNr;
        this.sortColDirection = asc;
    }

    /**
     * rekursiv alle Spalten neu ordnen
     */
    @Override
    public void reorderCols() {

    }

    /**
     * rekursiv alle Zeilen sortieren
     */
    @Override
    public void reorderRows(final int colNr, final boolean asc) {
        setSortCol(colNr, asc);
        reorderRowsImpl();
    }

    private void reorderRowsImpl() {
        if (isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Tabelle ist leer...");
            }
        } else {
            final List<Cell> cellListe = new ArrayList<>();
            for (final Row row : rowList) {
                final List<Cell> cellList = row.getCells();
                if (cellList.isEmpty()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Cell List der Row ist leer.");
                    }
                } else {
                    final int index = sortColNr - 1;
                    final Cell cell = cellList.get(index);
                    cellListe.add(cell);
                }
            }

            //finde den Comparator
            if (!cellListe.isEmpty()) {
                final Cell cell = cellListe.get(0);

                final ColumnInformation information = cell.getColInfo();
                final ZellenComparator zellenComparator = information.getZellenComparator();

                zellenComparator.setSortASC(sortColDirection);
                final boolean sortValue = information.isSortValue();
                zellenComparator.setSortValue(sortValue);
                Collections.sort(cellListe, zellenComparator); //JIRA MOD-35 Vererbung nicht sauber , ZellenComparator


                final List<Integer> idListe = new ArrayList<>();
                for (final Cell cellTmp : cellListe) {
                    idListe.add(cellTmp.getRow().getRowNr());
                }

                final List<Row> rowListNew = new ArrayList<>();
                for (final Integer rowId : idListe) {
                    rowListNew.add(rowList.get(rowId));
                }
                rowList.clear();
                rowList.addAll(rowListNew);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("CellList ist leer.");
                }
            }

            //ID s neu setzten ?????
            int i = 0;
            for (final Row row : rowList) {
                row.setRowNr(i);
                i++;
            }
        }

    }

    /**
     * rekursiv alle Zellen in allen Zeilen sortieren
     */
    @Override
    public void reorderCellsInAllRows() {
        for (final Row row : rowList) {
            row.reorderCells();
        }
    }

    /**
     * Fuerg eine neue Zeile hinzu. Es wird nicht auf doppelten Datensaetze ueberpruft.
     * Nr der zeile ist immer, Aktuelle Anzahl plus eins.
     *
     * @param zeile
     */
    @Override
    public void addRow(final Row zeile) {
        zeile.setRowNr(rowList.size());
        rowList.add(zeile);
    }

    @Override
    public void insertRow(final int index, final Row row) {
        row.setRowNr(index);
        rowList.add(index, row);
        // nachfolgende Zeilennummern nach unten schieben
        for (int i = index + 1; i < rowList.size(); i++) {
            rowList.get(i).setRowNr(i);
        }
    }

    @Override
    public void insertRow(final int index) {
        final Row row = createEmptyRow();
        row.setRowNr(index);
        insertRow(index, row);
    }

    @Override
    public void addNextColInfo(final String colName, final Class clazz, final ColumnProperty... properties) {
        final EnumSet<ColumnProperty> columnFlags = EnumSet.noneOf(ColumnProperty.class);
        columnFlags.addAll(Arrays.asList(properties));
        addNextColInfo(colName, clazz, columnFlags);
    }

    @Override
    public void addNextColInfo(String colName, Class clazz, CellValueFormatter cellValueFormatter, ColumnProperty... properties) {
        final EnumSet<ColumnProperty> columnFlags = EnumSet.noneOf(ColumnProperty.class);
        columnFlags.addAll(Arrays.asList(properties));
        addNextColInfo(colName, clazz, cellValueFormatter, columnFlags);
    }

    public void addNextColInfo(final String colName, final Class clazz, final EnumSet<ColumnProperty> flags) {
        final ColumnInformation columnInformation = createColumnInformation(colName, clazz, flags);
        setColInfo(colName, columnInformation);
    }

    public void addNextColInfo(final String colName, final Class clazz, final CellValueFormatter cellValueFormatter, final EnumSet<ColumnProperty> flags) {
        final ColumnInformation columnInformation = createColumnInformation(colName, clazz, flags);
        columnInformation.setFormatter(cellValueFormatter);
        setColInfo(colName, columnInformation);
    }

    private ColumnInformation createColumnInformation(String colName, Class clazz, EnumSet<ColumnProperty> flags) {
        final int colNr = getColInfoMap().size() + 1;
        final ColumnInformation columnInformation = ColumnInformation.newInstance(colName, colNr, clazz);
        columnInformation.setSortable(flags.contains(ColumnProperty.Sortable));
        columnInformation.setEditable(flags.contains(ColumnProperty.Editable));
        columnInformation.setExportable(!flags.contains(ColumnProperty.NotExportable));
        columnInformation.setSortValue(!flags.contains(ColumnProperty.SortLabel));
        columnInformation.setVisible(!flags.contains(ColumnProperty.Invisible));
        return columnInformation;
    }

    // REFAC methode entfernen ?
    @Override
    public void addNextCell(final String colName, final Cell newCell, final Row activeRow) {
        if (newCell != null) {
            connectColInfo(colName, newCell, activeRow);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("addNextCell (2) Value == null ");
            }
        }
    }

    private void connectColInfo(final String colName, final Cell newCell, final Row activeRow) {
        final Map<String, ColumnInformation> colInfoMap = this.getColInfoMap();
        if (colInfoMap.containsKey(colName)) {
            final ColumnInformation information = colInfoMap.get(colName);
            newCell.setColInfo(information);
            information.getZellen().add(newCell);
            activeRow.addCell(newCell);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Angegebene Col nicht definiert " + colName);
            }
        }
    }

    @Override
    public List<ColumnInformation> getColumnInfoList() {
        return Collections.unmodifiableList(columnInfoList);

        //        final Row row = rowList.get(0);
        //        final List<Cell> cellList = row.getCells();
        //        final List<ColumnInformation> colInfoList = new ArrayList<ColumnInformation>();
        //        for (Cell cell : cellList) {
        //            colInfoList.add(cell.getColInfo());
        //        }
//        final Collection<ColumnInformation> informationCollection = colInfoMap.values();
//        final List<ColumnInformation> colInfoList = new ArrayList<ColumnInformation>();
//        colInfoList.addAll(informationCollection);
//        Collections.sort(colInfoList, new ColumnInformation.SpaltenInformationenComparator());
//        return colInfoList;
    }

    @Override
    public boolean hasColumn(final String colName) {
        return columnInfoIndex.containsKey(colName);
    }

    @Override
    public ColumnInformation getColumnInformationFor(final String colName) {
        return columnInfoList.get(columnInfoIndex.get(colName));
    }

    @Override
    public Set<String> getColumnNames() {
        return columnInfoIndex.keySet();
    }

    @Override
    public boolean changeColumnName(final String oldName, final String newName) {
        if (oldName.equals(newName)) {
            return true;
        }
        if (hasColumn(oldName) && !hasColumn(newName)) {
            final Integer colIndex = columnInfoIndex.remove(oldName);
            columnInfoList.get(colIndex).setSpaltenName(newName);
            columnInfoIndex.put(newName, colIndex);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getColumnCount() {
        return columnInfoList.size();
    }

    @Override
    public int getVisibleColumnCount() {
        int count = 0;
        for (final ColumnInformation columnInformation : columnInfoList) {
            if (columnInformation.isVisible()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int[] getVisibleColumnIndices() {
        final int[] indices = new int[getVisibleColumnCount()];
        for (int i = 0, vi = 0; i < getColumnCount(); i++) {
            final ColumnInformation colInfo = columnInfoList.get(i);
            if (colInfo.isVisible()) {
                indices[vi] = i;
                vi++;
            }
        }
        return indices;
    }

    @Override
    public List<ColumnInformation> getVisibleColumns() {
        final ArrayList<ColumnInformation> columnList = new ArrayList<>();
        for (final ColumnInformation columnInformation : columnInfoList) {
            if (columnInformation.isVisible()) {
                columnList.add(columnInformation);
            }
        }
        return columnList;
    }

    @Override
    public List getUniqueValuesForCol(final String colName) {
        final List uniqueValues = new ArrayList();
        if (hasColumn(colName)) {
            final ColumnInformation columnInformation = getColumnInformationFor(colName);

            final HashSet valueSet = new HashSet();
            final List<Cell> zellen = columnInformation.getZellen();
            for (final Cell cell : zellen) {
                valueSet.add(cell.getValue());
            }
            uniqueValues.addAll(valueSet);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Die Tabelle enthält nicht die Spalte " + colName);
            }
        }
        return uniqueValues;
    }


    @Override
    public List<Row> getRowList() {
        return rowList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TableImpl");
        sb.append("{tableName='").append(tableName).append('\'');
        sb.append(", sortColNr=").append(sortColNr);
        sb.append(", sortColDirection=").append(sortColDirection);
        sb.append(", columnInfoList=").append(columnInfoList);
        sb.append(", columnInfoIndex=").append(columnInfoIndex);
//        sb.append(", rowList=").append(rowList);
        sb.append(", rowValidatorList=").append(rowValidatorList);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Row createEmptyRow() {
        final Row row = new RowImpl();

        for (final ColumnInformation columnInformation : columnInfoList) {
            final Cell newCell = new BaseCellFactory().createNewEmptyCell(columnInformation.getCellValueClass());
            connectColInfo(columnInformation.getSpaltenName(), newCell, row);
            row.addCell(newCell);
        }

        return row;
    }

    @Override
    public TableError validate() {
        final TableError error = new TableError();
        for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
            final Row row = rowList.get(rowIndex);
            if (row.getCellCount() != getColumnCount()) {
                error.addError(new TableError.RowError(rowIndex, "the cell count (" + row.getCellCount() +
                        ") does not match the column count (" + getColumnCount() + ") of this table"));
            }
            for (int cellIndex = 0; cellIndex < row.getCellCount(); cellIndex++) {
                final Cell cell = row.getCell(cellIndex);
                final ColumnInformation cellColInfo = cell.getColInfo();
                if (cell.getRow() != row) {
                    error.addError(new TableError.CellError(cellIndex, rowIndex,
                            "the parent row of the cell does not match this row"));
                }
                if (cellIndex >= getColumnCount()) {
                    error.addError(new TableError.CellError(cellIndex, rowIndex,
                            "the cell index is greater than the column count of this table (" + getColumnCount() + ")"));
                } else if (!cellColInfo.equals(columnInfoList.get(cellIndex))) {
                    error.addError(new TableError.CellError(cellIndex, rowIndex,
                            "the column information of this cell do not match the column information of this table column"));
                } else if (cell.getCellValueClass() != cellColInfo.getCellValueClass()) {
                    error.addError(new TableError.CellError(cellIndex, rowIndex,
                            "the cell value type (" + cell.getCellValueClass() + ") does not match ne column type (" +
                                    cellColInfo.getCellValueClass() + ")"));
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(getTableName() + " : " + error);
        }
        return error;
    }

    @Override
    public List<RowValidator> getRowValidatorList() {
        return rowValidatorList;
    }

    @Override
    public void addRowValidator(final RowValidator rowValidator) {
        rowValidatorList.add(rowValidator);
    }
}
