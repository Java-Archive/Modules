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

package org.rapidpm.data.table.formatter.impl.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.rapidpm.Constants;
import org.rapidpm.data.table.annotation.CellFormatter;
import org.rapidpm.data.table.formatter.CellValueFormatter;

/**
 * Sven Ruppert
 *
 * @author svenruppert
 * @version This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 02.11.2009
 * Time: 17:57:49
 */

//import org.apache.log4j.Logger;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

@CellFormatter(Date.class)
public class DateCellFormatter implements CellValueFormatter<Date> {
    // private static final Logger logger = Logger.getLogger(DateCellFormatter.class);
    // public static final Log logger = LogFactory.getLog(DateCellFormatter.class);


    private final SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.GERMANY);

    @Override
    public String format(final Date value) {
        return formatter.format(value);
    }
}
