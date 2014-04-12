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

package org.rapidpm.data.table.converter.fromtable;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.Row;
import org.rapidpm.data.table.Table;
import org.rapidpm.data.table.converter.fromtable.checks.Check;
import org.rapidpm.data.table.converter.fromtable.checks.CheckRegistry;

/**
 * Sven Ruppert
 * User: svenruppert
 * Date: 12/6/10
 * Time: 12:17 PM
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */
public abstract class Table2EntitiesConverter<T> {
    private static final Logger logger = Logger.getLogger(Table2EntitiesConverter.class);

    protected Class<T> entityClass;

    private List<String> errorList = new ArrayList<>();
    private List<String> warnList = new ArrayList<>();


    private List<Check> requiredChecks = CheckRegistry.getRequiredChecksFor(entityClass);
    private List<Check> optionalChecks = CheckRegistry.getOptionalChecksFor(entityClass);

    private final Table table;

    protected Table2EntitiesConverter(final Table table, final Class<T> entityClass) {
        this.entityClass = entityClass;
        this.table = table;
    }


    public List<T> convert() {
        final List<T> result = new ArrayList<>();
        if (table == null || table.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Null oder leere Tabelle wurde übergeben..");
            }
        } else {
            final List<Row> rows = table.getRowList();
            for (final Row row : rows) {
                if (check(row)) {
                    if (checkOptionals(row)) {
                        if (logger.isInfoEnabled()) {
                            logger.info("Optional Checks schlugen fehl..");
                        }
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Optional Checks alle ok..");
                        }
                    }
                    final T t = convertRow(row);
                    result.add(t);

                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Row hat Fehler...");
                    }
                }
            }


        }

        return result;
    }

    private boolean check(final Row row) {
        boolean result = true;
        for (final Check<T> requiredCheck : requiredChecks) {
            if (requiredCheck.check(row)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Check OK .. " + requiredCheck.getClass().getName());
                }
            } else {
                result = false;
                errorList.addAll(requiredCheck.getErrorDescriptions());
            }
        }
        return result;
    }

    private boolean checkOptionals(final Row row) {
        boolean result = true;
        for (final Check<T> optionalCheck : optionalChecks) {
            if (optionalCheck.check(row)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Check OK .. " + optionalCheck.getClass().getName());
                }
            } else {
                result = false;
                warnList.addAll(optionalCheck.getWarnDescriptions());
            }
        }
        return result;
    }


    protected abstract T convertRow(final Row row);


    public List<String> getErrorList() {
        return errorList;
    }

    public List<String> getWarnList() {
        return warnList;
    }
}
