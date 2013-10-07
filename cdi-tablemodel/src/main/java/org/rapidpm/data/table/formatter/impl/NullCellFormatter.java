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

package org.rapidpm.data.table.formatter.impl;
/**
 * NeoScio
 * User: svenruppert
 * Date: 12.04.2010
 * Time: 09:13:33
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.table.formatter.CellValueFormatter;

//@CellFormatter();
public class NullCellFormatter implements CellValueFormatter {
    private static final Logger logger = Logger.getLogger(NullCellFormatter.class);

    @Override
    public String format(final Object value) {
        if (value != null) {
            logger.warn("Value ist nicht null.. NullValueFormatter fehlerhaft aufgerufen. " + value);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("NullCellFormatter aufgerufen..");
            }
        }
        return "";
    }
}
