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

package org.rapidpm.data.table.comparator.zelle.impl.util;

import java.util.Date;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.annotation.CellComparator;
import org.rapidpm.data.table.comparator.zelle.AbstractZellenComparator;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

@CellComparator(Date.class)
public class DateZellenComparator extends AbstractZellenComparator<Date> {
    private static final Logger logger = Logger.getLogger(DateZellenComparator.class);

    @Override
    protected int compareElementValue(final Date date_1, final Date date_2) {
        int result = 0;
        // JIRA MOD-17 DateComparator implementieren
        final boolean after = date_1.after(date_2);
        if (after) {
            result = 1;
        } else if (date_1.before(date_2)) {
            result = -1;
        } else if (date_1.equals(date_2)) {
            result = 0;
        } else {
            logger.error("Vergleich schlug fehl : " + date_1 + " - " + date_2);
            result = -2;
        }

//        if (isSortASC()) {
//            result = -1 * result;
//        }


        return result;

    }

}
