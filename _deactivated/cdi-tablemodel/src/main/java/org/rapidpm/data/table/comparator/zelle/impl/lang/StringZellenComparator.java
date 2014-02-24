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
 * Time: 07:16:16
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.table.annotation.CellComparator;
import org.rapidpm.data.table.comparator.zelle.AbstractZellenComparator;

//import org.rapidpm.lang.comparator.StringComparator;

@CellComparator(String.class)
public class StringZellenComparator extends AbstractZellenComparator<String> {
    private static final Logger logger = Logger.getLogger(StringZellenComparator.class);


//    private final StringComparator stringComparator = new StringComparator();


    @Override
    public int compareElementValue(final String val1, final String val2) {
//        return stringComparator.compare(val1, val2);
        return val1.compareTo(val2);
    }
}
