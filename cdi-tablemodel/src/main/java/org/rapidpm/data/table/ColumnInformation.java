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
 * Time: 13:37:16
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.comparator.ComparatorRegistry;
import org.rapidpm.data.table.comparator.zelle.ZellenComparator;
import org.rapidpm.data.table.formatter.CellValueFormatter;
import org.rapidpm.data.table.formatter.FormatterRegistry;
import org.rapidpm.data.table.validator.CellValidator;

/**
 * @author
 */
public class ColumnInformation<T> {
    private static final Logger logger = Logger.getLogger(ColumnInformation.class);

    private Class<T> entityClass;
    private String spaltenName;
    private int spaltenNr;

    private boolean visible = true;
    private boolean sortable = false;
    private boolean sortValue = true;
    private boolean editable = false;
    private boolean exportable = true;

    private ZellenComparator zellenComparator;

    private CellValueFormatter formatter;

    private final List<CellValidator<T>> cellValidatorList = new ArrayList<>();

    private List<Cell<T>> zellen = new ArrayList<>();
    private List<T> predefinedValues = null;

    public void reorderZellen() {
        Collections.sort(zellen, zellenComparator);  //JIRA MOD-29 design refactoring ZellenComparator ?
    }

    private ColumnInformation() {
    }

    public static <T> ColumnInformation<T> newInstance(final String colName, final int colNr, final Class clazz) {
        final ColumnInformation columnInformation = new ColumnInformation();
        columnInformation.spaltenName = colName;
        columnInformation.spaltenNr = colNr;
        columnInformation.visible = true;
        columnInformation.zellenComparator = Registry.get(ComparatorRegistry.class, clazz);
        columnInformation.formatter = Registry.get(FormatterRegistry.class, clazz);
        columnInformation.entityClass = clazz;
        return columnInformation;
    }

    public Class<T> getCellValueClass() {
        return entityClass;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(final boolean editable) {
        this.editable = editable;
    }

    public boolean isExportable() {
        return exportable;
    }

    public void setExportable(final boolean exportable) {
        this.exportable = exportable;
    }

    public CellValueFormatter getFormatter() {
        return formatter;
    }

    public String getSpaltenName() {
        return spaltenName;
    }

    public void setSpaltenName(final String spaltenName) {
        this.spaltenName = spaltenName;
    }

    public int getSpaltenNr() {
        return spaltenNr;
    }

    public void setSpaltenNr(final int spaltenNr) {
        this.spaltenNr = spaltenNr;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public ZellenComparator getZellenComparator() {
        return zellenComparator;
    }

    public List<CellValidator<T>> getCellValidatorList() {
        return cellValidatorList;
    }

    public void addCellValidator(final CellValidator<T> cellValidator) {
        cellValidatorList.add(cellValidator);
    }

    public void setZellenComparator(final ZellenComparator zellenComparator) {
        this.zellenComparator = zellenComparator;
    }

    public List<Cell<T>> getZellen() {
        return zellen;
    }

    public void setZellen(final List<Cell<T>> zellen) {
        this.zellen = zellen;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(final boolean sortable) {
        this.sortable = sortable;
    }


    public boolean isSortValue() {
        return sortValue;
    }

    public void setSortValue(final boolean sortValue) {
        this.sortValue = sortValue;
    }

    public boolean hasPredefinedValues() {
        return predefinedValues != null && !predefinedValues.isEmpty();
    }

    public List<T> getPredefinedValues() {
        return predefinedValues;
    }

    public void setPredefinedValues(final List<T> predefinedValues) {
        this.predefinedValues = predefinedValues;
    }

    public List<T> createDefaultPredefinedValues() {
        predefinedValues = new ArrayList<>(getUniqueValues());
        return predefinedValues;
    }

    public void setFormatter(CellValueFormatter formatter) {
        this.formatter = formatter;
    }

    public List<T> createDefaultPredefinedValues(final Comparator<? super T> comparator) {
        final List<T> values = createDefaultPredefinedValues();
        Collections.sort(values, comparator);
        return values;
    }

    /**
     * @return Eindeutige Werte dieser Spalte.
     * @see Table#getUniqueValuesForCol(String)
     */
    public Set<T> getUniqueValues() {
        final Set<T> uniqueValues = new HashSet<>();
        for (final Cell<T> cell : zellen) {
            uniqueValues.add(cell.getValue());
        }
        return uniqueValues;
    }

    public static class SpaltenInformationenComparator implements Comparator<ColumnInformation>, Serializable {

        @Override
        public int compare(final ColumnInformation info1, final ColumnInformation info2) {
            int result = 0;
            final int nr1 = info1.getSpaltenNr();
            final int nr2 = info2.getSpaltenNr();
            result = nr1 - nr2;
            //JIRA MOD-30 IntegerComaparator verwenden - SpaltenInformationenComparator
            /*if (nr1 > nr2) {
                result = 1;
            } else if (nr1 < nr2) {
                result = -1;
            } else {
                result = 0;
            }*/
            return result;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ColumnInformation");
        sb.append("{spaltenName='").append(spaltenName).append('\'');
        sb.append(", spaltenNr=").append(spaltenNr);
        sb.append(", visible=").append(visible);
        sb.append(", sortable=").append(sortable);
        sb.append(", sortValue=").append(sortValue);
        sb.append(", editable=").append(editable);
        sb.append(", exportable=").append(exportable);
        sb.append(", zellenComparator=").append(zellenComparator);
        sb.append(", formatter=").append(formatter);
        sb.append('}');
        return sb.toString();
    }
}
