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

package org.rapidpm.data.table.comparator.zelle;
/**
 * NeoScio
 * User: svenruppert
 * Date: 26.10.2009
 * Time: 07:24:48
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.Cell;


public abstract class AbstractZellenComparator<T> implements ZellenComparator<Cell<T>>, Serializable {
    private static final Logger logger = Logger.getLogger(AbstractZellenComparator.class);

    private boolean sortASC = false;
    private boolean sortValue = true;

    protected AbstractZellenComparator() {
    }

    @Override
    public int compare(final Cell<T> zelle1, final Cell<T> zelle2) {
        int result;

        if (sortValue) {
            result = compareValue(zelle1, zelle2);
        } else {
            result = compareLabel(zelle1, zelle2);
        }

        if (!isSortASC()) {
            result = -1 * result;
        }

        return result;

    }

    private int compareValue(final Cell<T> zelle1, final Cell<T> zelle2) {
        int result = 0;
        if (zelle1 != null && zelle2 != null) {
            final T val1 = zelle1.getValue();
            final T val2 = zelle2.getValue();

            if (val1 != null && val2 != null) {
                result = compareElementValue(val1, val2);
            } else {
                throw new IllegalArgumentException(
                        "mind. ein Parameter.value ist null.. zelle1=" + zelle1 + " , zelle2=" + zelle2);
            }
        } else {
            throw new IllegalArgumentException(
                    "mind. ein Parameter ist null.. zelle1=" + zelle1 + " , zelle2=" + zelle2);
        }


        return result;
    }


    private int compareLabel(final Cell<T> zelle1, final Cell<T> zelle2) {
        int result = 0;
        if (zelle1 != null && zelle2 != null) {
            final String val1 = zelle1.getLabel();
            final String val2 = zelle2.getLabel();

            if (val1 != null && val2 != null) {
                result = val1.compareTo(val2);
            } else {
                throw new IllegalArgumentException(
                        "mind. ein Parameter.value ist null.. zelle1=" + zelle1 + " , zelle2=" + zelle2);
            }
        } else {
            throw new IllegalArgumentException(
                    "mind. ein Parameter ist null.. zelle1=" + zelle1 + " , zelle2=" + zelle2);
        }
        return result;
    }


    protected abstract int compareElementValue(T valI, T valII);


    protected AbstractZellenComparator(final boolean sortASC) {
        this.sortASC = sortASC;
    }

    @Override
    public boolean isSortASC() {
        return sortASC;
    }

    @Override
    public void setSortASC(final boolean sortASC) {
        this.sortASC = sortASC;
    }

    @Override
    public boolean isSortValue() {
        return sortValue;
    }

    @Override
    public void setSortValue(final boolean sortValue) {
        this.sortValue = sortValue;
    }
}
