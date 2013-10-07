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
 * Time: 13:32:23
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import java.util.List;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.formatter.CellValueFormatter;
import org.rapidpm.data.table.validator.CellValidator;

public class Cell<T> {
    private static final Logger logger = Logger.getLogger(Cell.class);


    private Row row;
    private ColumnInformation colInfo;


    private T value;
    private String valueContext;

    private String label;

    private CellTypeEnum cellType;

    public boolean isNull() {
        return value == null;
    }


    public Class getCellValueClass() {
        return value.getClass();
    }

    public boolean isRawLink() {
        return cellType.equals(CellTypeEnum.RawLink);
    }

    public boolean isPageLink() {
        return cellType.equals(CellTypeEnum.PageLink);
    }

    public boolean isEventLink() {
        return cellType.equals(CellTypeEnum.EventLink);
    }

    public boolean isRawData() {
        return cellType.equals(CellTypeEnum.RawData);
    }


    public String getValueContext() {
        return valueContext;
    }

    public void setValueContext(final String valueContext) {
        this.valueContext = valueContext;
    }

    public CellTypeEnum getCellType() {
        return cellType;
    }

    public void setCellType(final CellTypeEnum cellType) {
        this.cellType = cellType;
    }


    public String getFormattedValue() {
        final CellValueFormatter valueFormatter = colInfo.getFormatter();
        if (value == null) {
            return "";
        } else {
            return valueFormatter.format(value);
        }
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(final Row row) {
        this.row = row;
    }

    public ColumnInformation getColInfo() {
        return colInfo;
    }

    public void setColInfo(final ColumnInformation colInfo) {
        this.colInfo = colInfo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Cell");
//        sb.append("{row=").append(row);
//        sb.append(", colInfo=").append(colInfo);
        sb.append(", value=").append(value);
        sb.append(", valueContext='").append(valueContext).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", cellType=").append(cellType);
        sb.append('}');
        return sb.toString();
    }

    public List<CellValidator<T>> getValidatorList() {
        return colInfo.getCellValidatorList();
    }
}
