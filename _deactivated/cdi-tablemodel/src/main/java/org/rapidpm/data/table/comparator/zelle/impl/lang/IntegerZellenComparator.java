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
/**
 * NeoScio
 * User: svenruppert
 * Date: 26.10.2009
 * Time: 07:16:28
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.table.annotation.CellComparator;
import org.rapidpm.data.table.comparator.zelle.AbstractZellenComparator;

@CellComparator(Integer.class)
public class IntegerZellenComparator extends AbstractZellenComparator<Integer> {
    private static final Logger logger = Logger.getLogger(IntegerZellenComparator.class);


    @Override
    public int compareElementValue(final Integer i1, final Integer i2) {
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
