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

package org.rapidpm.data.table.formatter.impl.lang;

import org.rapidpm.data.table.annotation.CellFormatter;
import org.rapidpm.data.table.formatter.CellValueFormatter;

/**
 * Formatiert einen Double.
 *
 * @author Christian Ernst
 * @author Alexander Vos
 */
@CellFormatter(Double.class)
public class DoubleCellFormatter implements CellValueFormatter<Double> {
    @Override
    public String format(final Double value) {
        return value.toString();
    }
}
