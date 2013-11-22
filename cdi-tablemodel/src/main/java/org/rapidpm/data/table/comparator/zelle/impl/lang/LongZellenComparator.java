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

package org.rapidpm.data.table.comparator.zelle.impl.lang;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.annotation.CellComparator;
import org.rapidpm.data.table.comparator.zelle.AbstractZellenComparator;

/**
 * Sven Ruppert - www.svenruppert.de
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p></p>
 *          This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 23.06.2010
 * Time: 01:14:12
 */
@CellComparator(Long.class)
public class LongZellenComparator extends AbstractZellenComparator<Long> {
    private static final Logger logger = Logger.getLogger(LongZellenComparator.class);

    @Override
    protected int compareElementValue(final Long i1, final Long i2) {
        int result = 0;
        if (i1 > i2) {
            result = 1;
        } else if (i1 < i2) {
            result = -1;
        } else if (i1 == i2) {
            result = 0;
        } else {
            logger.error("Vergleich schlug fehl: i1=" + i1 + " i2=" + i2);
            result = -2;
        }

//        if (isSortASC()) {
//            result = -1 * result;
//        }

        return result;
    }
}
