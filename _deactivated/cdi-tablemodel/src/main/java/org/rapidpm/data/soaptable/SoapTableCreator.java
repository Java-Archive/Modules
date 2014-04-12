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

package org.rapidpm.data.soaptable;

/**
 * Sven Ruppert
 * User: svenruppert
 * Date: 11.02.11
 * Time: 16:45
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */

import org.apache.log4j.Logger;

public abstract class SoapTableCreator {
    private static final Logger logger = Logger.getLogger(SoapTableCreator.class);

    private SoapTable table;

    protected SoapTableCreator() {
        table = new SoapTable();
    }

    public SoapTableCreator execute() {
        addAllColInfoEntries();
        addAllColEntries();
        return this;
    }


    protected abstract void addAllColInfoEntries();

    protected abstract void addAllColEntries();

    //    protected void addColInfo(final String colname, final SoapTable.ColInfoType type ){
    protected void addColInfo(final String colname) {
        final SoapTable.ColInfo colInfo = new SoapTable.ColInfo();
        colInfo.setColname(colname);
        colInfo.setColNr(table.getColinfolist().size());
        table.getColinfolist().add(colInfo);
    }


    private SoapTable.Row row;

    protected void startNewRow() {
        row = new SoapTable.Row();
    }

    protected void stopNewRow() {
        table.getRowlist().add(row);
    }


    protected void addColEntry(final String value) {
        final SoapTable.Row.ColEntry colEntry = new SoapTable.Row.ColEntry(row.getColEntryList().size(), value);
        row.getColEntryList().add(colEntry);

    }

    protected void addColEntry(final Number value) {
        final SoapTable.Row.ColEntry colEntry = new SoapTable.Row.ColEntry(row.getColEntryList().size(), value + "");
        row.getColEntryList().add(colEntry);
    }

    public SoapTable getTable() {
        return table;
    }
}
