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

package org.rapidpm.data.table.converter.fromtable.checks;

import java.util.ArrayList;
import java.util.List;

import org.rapidpm.data.table.Row;

/**
 * Sven Ruppert
 * User: svenruppert
 * Date: 12/6/10
 * Time: 12:25 PM
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */
public abstract class Check<T> {

    /**
     * Eine Row entspricht mindestens einer Entität.
     *
     * @param row
     * @return true wenn alles OK ist, sonst false; @getErrorDescription
     */
    public abstract boolean check(final Row row);

    private final List<String> errorList = new ArrayList<>();
    private final List<String> warnList = new ArrayList<>();

    public List<String> getWarnDescriptions() {
        return warnList;
    }

    public List<String> getErrorDescriptions() {
        return errorList;
    }

}
