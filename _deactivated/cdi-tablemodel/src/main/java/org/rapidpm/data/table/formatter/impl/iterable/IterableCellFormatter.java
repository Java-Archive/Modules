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

package org.rapidpm.data.table.formatter.impl.iterable;

import org.rapidpm.data.table.Registry;
import org.rapidpm.data.table.annotation.CellFormatter;
import org.rapidpm.data.table.formatter.CellValueFormatter;
import org.rapidpm.data.table.formatter.FormatterRegistry;

/**
 * Sven Ruppert
 * User: MLI
 * Date: 23.04.2010
 * Time: 00:54:06
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */
@CellFormatter(Iterable.class)
public class IterableCellFormatter implements CellValueFormatter<Iterable> {

    @Override
    public String format(final Iterable value) {
        final StringBuilder builder = new StringBuilder();
        for (final Object o : value) {
            final CellValueFormatter formatter = Registry.get(FormatterRegistry.class, o.getClass());
            final String formattedValue = formatter.format(o);
            builder.append(formattedValue).append(", ");

        }
        return builder.toString();
    }
}
