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

package org.rapidpm.data.table.comparator.zelle.impl.iterable;

import org.rapidpm.data.table.annotation.CellComparator;
import org.rapidpm.data.table.comparator.zelle.AbstractZellenComparator;

/**
 * Sven Ruppert
 * User: MLI
 * Date: 23.04.2010
 * Time: 00:33:08
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */
@CellComparator(Iterable.class)
public class IterableZellenComparator extends AbstractZellenComparator<Iterable> {

    /**
     * ineffizient.. ueberschreiben..
     *
     * @param val
     * @return Anzahl der Elemente
     */
    protected int setSize(final Iterable val) {
        int size = 0;
        for (final Object o : val) {
            size++;
        }
        return size;
    }

    @Override
    protected final int compareElementValue(final Iterable valI, final Iterable valII) {
        int result = 0;
        result = internalCompare(setSize(valI), setSize(valII));
        return result;
    }

    private int internalCompare(final int size1, final int size2) {
        final int result;
        if (size1 < size2) {
            result = -1;
        } else if (size1 > size2) {
            result = 1;
        } else if (size1 == size2) {
            result = 0;
        } else {
            result = Integer.MAX_VALUE;
        }
        return result;
    }
}
