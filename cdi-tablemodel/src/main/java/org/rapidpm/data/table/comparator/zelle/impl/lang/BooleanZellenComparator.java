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
 *          <p/>
 *          This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 29.03.2010
 * Time: 01:57:27
 */

@CellComparator(Boolean.class)
public class BooleanZellenComparator extends AbstractZellenComparator<Boolean> {
    private static final Logger logger = Logger.getLogger(BooleanZellenComparator.class);


    @Override
    protected int compareElementValue(final Boolean valI, final Boolean valII) {
        return valI.compareTo(valII);
    }
}
