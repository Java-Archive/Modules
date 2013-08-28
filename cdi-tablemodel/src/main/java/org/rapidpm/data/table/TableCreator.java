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
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.data.table.formatter.CellValueFormatter;
import org.rapidpm.data.table.impl.RowImpl;
import org.rapidpm.data.table.impl.TableImpl;
import org.rapidpm.data.table.validator.CellValidator;
import org.rapidpm.data.table.validator.RowValidator;

/**
 * Sven Ruppert - www.svenruppert.de
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 28.03.2010
 * Time: 20:03:24
 * <p/>
 * <p/>
 * BeispielCode:
 * final TableCreator creator = new TableCreator(); //refac verwende TableCreatorExecuter
 * creator.createNewTableInstance();
 * creator.addNextColInfo(TEST_COL01, String.class);
 * creator.addNextColInfo(TEST_COL02, String.class);
 * <p/>
 * creator.addNewRow();
 * <p/>
 * creator.addNextCell(TEST_COL01, CellTypeEnum.RawData, "TestString f TestCol1 row 1", "testLabel");
 * creator.addNextCell(TEST_COL02, CellTypeEnum.RawData, "TestString f TestCol2 row 1", "testLabel");
 * <p/>
 * creator.addNewRow();
 * creator.addNextCell(TEST_COL01, CellTypeEnum.RawData, "TestString f TestCol1 row 2", "testLabel");
 * creator.addNextCell(TEST_COL02, CellTypeEnum.RawData, "TestString f TestCol2 row 2", "testLabel");
 * <p/>
 * final Table table = creator.getTable();
 * table.reorderCellsInAllRows();
 */

public class TableCreator<T extends BaseCellFactory> {
    private static final Logger logger = Logger.getLogger(TableCreator.class);

    //    private BaseCellFactory cellFactory = new BaseCellFactory();
    private T cellFactory;

    public TableCreator(T cellFactory) {
        this.cellFactory = cellFactory;
    }


    private int colNr = 0;
    private Table table;

    private Row activeRow = null;
    private boolean rowValid = true;

    private SimpleDateFormat format = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM);


    public void createNewTableInstance() {
        table = new TableImpl();
        table.setTableName("NeoScioTable_" + format.format(new Date()));
        colNr = 0;
    }

//    private void connectColInfo(final String colName, final Cell newCell){
//        final Map<String, ColumnInformation> colInfoMap = table.getColInfoMap();
//        if(colInfoMap.containsKey(colName)){
//            final ColumnInformation information = colInfoMap.get(colName);
//            newCell.setColInfo(information);
//            information.getZellen().add(newCell);
//            activeRow.addCell(newCell);
//        }else{
//            if(logger.isDebugEnabled()){
//                logger.debug("Angegebene Col nicht definiert " + colName);
//            }
//        }
//    }

    private void connectColInfo(final String colName, final Cell newCell) {
        if (table.hasColumn(colName)) {
            final ColumnInformation columnInformation = table.getColumnInformationFor(colName);
            final List<CellValidator<?>> cellValidatorList = columnInformation.getCellValidatorList();
            for (final CellValidator<?> cellValidator : cellValidatorList) {
                if (!cellValidator.validate(newCell)) {
                    logger.error("Zelle ist nicht gültig: " + cellValidator.getErrorCause());
                    rowValid = false;
                    return;
                }
            }
            newCell.setColInfo(columnInformation);
            columnInformation.getZellen().add(newCell);
            activeRow.addCell(newCell);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Angegebene Col nicht definiert " + colName);
            }
        }
    }

    public <C> void addNextCell(final String colName, final CellTypeEnum cellType, final C value, final String valueContext, final String cellLabel) {
        if (value != null) {
            final Cell<C> newCell = cellFactory.createNewCell(value, valueContext);
            newCell.setCellType(cellType);
            newCell.setLabel(cellLabel);
            connectColInfo(colName, newCell);
        } else {
            logger.warn("addNextCell (1) Value == null ");
        }
    }

    public <C> void addNextCellRawData(final String colName, final C value) {
        if (value != null) {
            final Cell<C> newCell = cellFactory.createNewCell(value);
            newCell.setCellType(CellTypeEnum.RawData);
            connectColInfo(colName, newCell);
        } else {
            logger.warn("addNextCell (2) Value == null ");
        }
    }

    public <C> void addNextCellRawLink(final String colName, final C value, final String cellLabel) {
        if (value != null) {
            final Cell<C> newCell = cellFactory.createNewCell(value);
            newCell.setCellType(CellTypeEnum.RawLink);
            newCell.setLabel(cellLabel);
            connectColInfo(colName, newCell);
        } else {
            logger.warn("addNextCell (3) Value == null ");
        }
    }

    public <C> void addNextCellEventLink(final String colName, final C value, final String valueContext, final String cellLabel) {
        if (value != null) {
            final Cell<C> newCell = cellFactory.createNewCell(value, valueContext);
            newCell.setCellType(CellTypeEnum.EventLink);
            newCell.setLabel(cellLabel);
            connectColInfo(colName, newCell);
        } else {
            logger.warn("addNextCell (4) Value == null ");
        }
    }

    public <C> void addNextCellPageLink(final String colName, final C ctxPath, final String valueContext, final String cellLabel) {
        if (ctxPath != null) {
            final Cell<C> newCell = cellFactory.createNewCell(ctxPath, valueContext);
            newCell.setCellType(CellTypeEnum.PageLink);
            newCell.setLabel(cellLabel);
            connectColInfo(colName, newCell);
        } else {
            logger.warn("addNextCell (5) Value == null ");
        }
    }

    public <C> void addNextCellPageLink(final String colName, final Class pageClass, final String valueContext, final String cellLabel) {
        if (pageClass != null) {
            final String standardBasePagePkg = pageClass.getPackage().getName().replace(".", "/") + "/";
            final String pageClassPkg = pageClass.getClass().getPackage().getName().replace(".", "/") + "/";
            final String pageCtxPath = pageClassPkg.replace(standardBasePagePkg, "");
            final Cell<String> newCell = cellFactory.createNewCell(pageCtxPath, valueContext);
            newCell.setCellType(CellTypeEnum.PageLink);
            newCell.setLabel(cellLabel);
            connectColInfo(colName, newCell);
        } else {
            logger.warn("addNextCell (6) Value == null ");
        }

    }


    //    private void addNullValue(final String colName){
    //        addNextCellRawData(colName, "");
    //
    //    }

    public void addNewRow() {
        final Row newRow = new RowImpl();
        activeRow = newRow;
        rowValid = true;
        table.addRow(newRow);

//        final Collection<ColumnInformation> infoList = table.getColumnInfoList();
//        for(final ColumnInformation information : infoList){
//            final Class aClass = information.getCellValueClass();
//            try{
//                addNextCell(information.getSpaltenName(), CellTypeEnum.RawData, aClass.newInstance(), "", "");
//            }catch(InstantiationException e){
//                logger.error(e);
//            }catch(IllegalAccessException e){
//                logger.error(e);
//            }
//        }

    }

    public boolean removeActiveRow() {
        if (activeRow == null) {
            return false;
        }
        table.removeRow(activeRow.getRowNr());
        activeRow = null;
        return true;
    }

    /**
     * Erzeugt eine neue ColumnInformation und legt sie in die interne Registry
     * unter dem symbolischen Namen.
     *
     * @param colName Spaltenname
     * @param clazz   Typ der Spalte
     * @param flags   Spalteneigenschaften
     */
    public void addNextColInfo(final String colName, final Class clazz, final EnumSet<ColumnProperty> flags) {
        colNr++;
        final ColumnInformation columnInformation = createColumnInformation(colName, clazz, flags);
        table.setColInfo(colName, columnInformation);
    }

    public void addNextColInfo(final String colName, final Class clazz, final CellValueFormatter cellValueFormatter, final EnumSet<ColumnProperty> flags) {
        colNr++;
        final ColumnInformation columnInformation = createColumnInformation(colName, clazz, flags);
        columnInformation.setFormatter(cellValueFormatter);
        table.setColInfo(colName, columnInformation);
    }

    private ColumnInformation createColumnInformation(String colName, Class clazz, EnumSet<ColumnProperty> flags) {
        final ColumnInformation columnInformation = ColumnInformation.newInstance(colName, colNr, clazz);
        columnInformation.setSortable(flags.contains(ColumnProperty.Sortable));
        columnInformation.setEditable(flags.contains(ColumnProperty.Editable));
        columnInformation.setExportable(!flags.contains(ColumnProperty.NotExportable));
        columnInformation.setSortValue(!flags.contains(ColumnProperty.SortLabel));
        columnInformation.setVisible(!flags.contains(ColumnProperty.Invisible));
        return columnInformation;
    }

    /**
     * Erzeugt eine neue ColumnInformation und legt sie in die interne Registry
     * unter dem symbolischen Namen.
     *
     * @param colName Spaltenname
     * @param clazz   Typ der Spalte
     * @param flags   Spalteneigenschaften
     */
    public void addNextColInfo(final String colName, final Class clazz, final ColumnProperty... flags) {
        final EnumSet<ColumnProperty> columnFlags = EnumSet.noneOf(ColumnProperty.class);
        columnFlags.addAll(Arrays.asList(flags));
        addNextColInfo(colName, clazz, columnFlags);
    }

    public void addNextColInfo(final String colName, final Class clazz, final CellValueFormatter cellValueFormatter, final ColumnProperty... flags) {
        final EnumSet<ColumnProperty> columnFlags = EnumSet.noneOf(ColumnProperty.class);
        columnFlags.addAll(Arrays.asList(flags));
        addNextColInfo(colName, clazz, columnFlags);
    }

    /**
     * Erzeugt eine neue ColumnInformation und legt sie in die interne Registry
     * unter dem symbolischen Namen.
     *
     * @param colName
     * @param clazz
     * @deprecated
     */
    public void addNextColInfo(final String colName, final Class clazz, final boolean sortable, final boolean exportable) {
        colNr++;
        final ColumnInformation columnInformation = ColumnInformation.newInstance(colName, colNr, clazz);
        columnInformation.setSortable(sortable);
        columnInformation.setExportable(exportable);
        table.setColInfo(colName, columnInformation);
    }

    /**
     * Erzeugt eine neue ColumnInformation und legt sie in die interne Registry
     * unter dem symbolischen Namen.
     *
     * @param colName
     * @param clazz
     * @deprecated
     */
    public void addNextColInfo(final String colName, final Class clazz, final boolean sortable, final boolean exportable, final boolean sortValue) {
        colNr++;
        final ColumnInformation columnInformation = ColumnInformation.newInstance(colName, colNr, clazz);
        columnInformation.setSortable(sortable);
        columnInformation.setExportable(exportable);
        columnInformation.setSortValue(sortValue);
        table.setColInfo(colName, columnInformation);
    }

    public void addCellValidator(final String colName, final CellValidator<?> cellValidator) {
        if (table.hasColumn(colName)) {
            final ColumnInformation ci = table.getColumnInformationFor(colName);
            ci.addCellValidator(cellValidator);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Angegebene Col nicht definiert " + colName);
            }
        }
    }

    public void addRowValidator(final RowValidator rowValidator) {
        table.addRowValidator(rowValidator);
    }

    public boolean validateRow() {
        if (rowValid) {
            for (final RowValidator rowValidator : table.getRowValidatorList()) {
                if (rowValidator.validate(activeRow)) {
                    //
                } else {
                    rowValid = false;
                    if (logger.isDebugEnabled()) {
                        logger.debug("Row validation failed: " + rowValidator.getErrorCause());
                    }
                    break;
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Row already invalid!");
            }
        }
        return rowValid;
    }

    public boolean isRowValid() {
        return rowValid;
    }

    public Table getTable() {
        return table;
    }
}
