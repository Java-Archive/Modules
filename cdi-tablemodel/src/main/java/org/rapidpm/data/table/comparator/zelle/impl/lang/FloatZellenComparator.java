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

@CellComparator(Float.class)
public class FloatZellenComparator extends AbstractZellenComparator<Float> {
    private static final Logger logger = Logger.getLogger(FloatZellenComparator.class);


    @Override
    public int compareElementValue(final Float f1, final Float f2) {
        int result = 0;
        if (f1 > f2) {
            result = 1;
        } else if (f1 < f2) {
            result = -1;
        } else if (f1 == f2) {
            result = 0;
        } else {
            logger.error("Vergleich schlug fehl: f1=" + f1 + " f2=" + f2);
            result = -2;
        }

//        if (isSortASC()) {
//            result = -1 * result;
//        }

        return result;

    }
}
