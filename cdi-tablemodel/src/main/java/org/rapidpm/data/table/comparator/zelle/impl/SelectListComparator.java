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

package org.rapidpm.data.table.comparator.zelle.impl;

import org.rapidpm.data.SelectList;
import org.rapidpm.data.table.annotation.CellComparator;
import org.rapidpm.data.table.comparator.zelle.AbstractZellenComparator;

/**
 * Komparator für die Selektierende-Liste.
 *
 * @author Christian Ernst
 */
@CellComparator(SelectList.class)
public class SelectListComparator extends AbstractZellenComparator<SelectList> {
    @Override
    protected int compareElementValue(final SelectList val1, final SelectList val2) {
        return 0; // JIRA MOD-18 Komparator implementieren für SelectListComparator
    }
}
