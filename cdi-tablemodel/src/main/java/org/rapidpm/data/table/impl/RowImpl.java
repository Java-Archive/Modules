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
 * Time: 21:48:46
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.Cell;
import org.rapidpm.data.table.ColumnInformation;
import org.rapidpm.data.table.Row;

public class RowImpl implements Row {
    private static final Logger logger = Logger.getLogger(RowImpl.class);


    private int rowNr;

    private List<Cell> cellListe = new ArrayList<>();

    @Override
    public int getCellCount() {
        return cellListe.size();
    }

    @Override
    public boolean hasColumn(final String colName) {
        for (final Cell cell : cellListe) {
            final ColumnInformation colInfo = cell.getColInfo();
            final String spaltenName = colInfo.getSpaltenName();
            final boolean b = spaltenName.equals(colName);
            if (b) {
                return b;
            } else {
                //
            }
        }
        return false;
    }

    @Override
    public Cell getCellForName(final String columnName) {
        Cell result = null;
        for (final Cell cell : cellListe) {
            final ColumnInformation information = cell.getColInfo();
            final String spaltenName = information.getSpaltenName();
            if (columnName.equals(spaltenName)) {
                result = cell;
                break;
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Spaltennamen passen nicht zusammen");
                }
            }

        }
        return result;
    }


    @Override
    public List<Cell> getCells() {
        return cellListe;
    }

    @Override
    public void addCell(final Cell cell) {
        final ColumnInformation informationCellNew = cell.getColInfo();
        final String colNameNew = informationCellNew.getSpaltenName();
        int indexTemp = cellListe.size();
        for (int i = 0; i < cellListe.size(); i++) {
            final Cell cellTemp = cellListe.get(i);
            final ColumnInformation information = cellTemp.getColInfo();
            final String colName = information.getSpaltenName();
            if (colName.equals(colNameNew)) {
                indexTemp = i;
                cellListe.remove(i);
//                if (logger.isDebugEnabled()) {
//                    logger.debug("Column have the same name: " + colName);
//                }
                break;
            } else {
                //
            }
        }

        cell.setRow(this);
        cellListe.add(indexTemp, cell);

    }

    @Override
    public void reorderCells() {
        final List<ColumnInformation> infoList = new ArrayList<>();
        for (final Cell cell : cellListe) {
            final ColumnInformation information = cell.getColInfo();
            infoList.add(information);
        }

        Collections.sort(infoList, new ColumnInformation.SpaltenInformationenComparator());

        final List<Cell> newCellList = new ArrayList<>();
        if (!infoList.isEmpty()) {
            ColumnInformation columnInformation = infoList.get(0);
            while (!infoList.isEmpty()) {
                for (final Cell cell : cellListe) {
                    if (columnInformation.equals(cell.getColInfo())) {
                        newCellList.add(cell);
                    } else {
                        //if(logger.isDebugEnabled()) logger.debug("");
                    }
                }
                infoList.remove(0);
                if (!infoList.isEmpty()) {
                    columnInformation = infoList.get(0);
                }
            }
            if (cellListe.size() == newCellList.size()) {
                cellListe.clear();
                cellListe.addAll(newCellList);
            } else {
                logger.warn(" CELL - Nach dem sortieren sind nicht gleich viele Elemente in den Listen");
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("TabellenSpalte ist leer");
            }
        }

    }


    @Override
    public int getRowNr() {
        return rowNr;
    }

    @Override
    public void setRowNr(final int rowNr) {
        this.rowNr = rowNr;
    }

    /**
     * Liefert zurueck ob die Rownr gerade oder ungerade ist.
     *
     * @return
     */
    @Override
    public boolean isRowNrHigh() {
        return rowNr % 2 == 0;
    }


    @Override
    public <T> Cell<T> getCell(final int index) {
        return cellListe.get(index);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RowImpl");
        sb.append("{rowNr=").append(rowNr);
        sb.append(", cellListe=").append(cellListe);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Iterator<Cell> iterator() {
        return cellListe.iterator();
    }
}
