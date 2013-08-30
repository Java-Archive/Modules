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
/**
 * NeoScio
 * User: svenruppert
 * Date: 23.10.2009
 * Time: 13:31:31
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import java.util.List;

public interface Row extends Iterable<Cell> {

    public List<Cell> getCells();

    public int getCellCount();

    public void addCell(Cell cell);

    public void reorderCells();

    public int getRowNr();

    public void setRowNr(int i);

    public Cell getCellForName(final String columnName);

    public boolean hasColumn(final String colName);

    /**
     * Liefert zurueck ob die Rownr gerade oder ungerade ist.
     *
     * @return
     */
    public boolean isRowNrHigh();

    public <T> Cell<T> getCell(int index);

}
