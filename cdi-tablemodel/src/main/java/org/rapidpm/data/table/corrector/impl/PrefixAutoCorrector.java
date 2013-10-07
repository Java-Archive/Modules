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

package org.rapidpm.data.table.corrector.impl;

import org.rapidpm.data.table.corrector.AutoCorrector;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 29.11.11
 * Time: 12:07
 */
public class PrefixAutoCorrector extends AutoCorrector<String> {

    private String prefix;

    public PrefixAutoCorrector(final String prefix) {
        super(prefix + "-AutoCorrector");
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String correct(final String value) {
        if (value == null) {
            return prefix;
        }
        return value.startsWith(prefix) ? value : prefix.concat(value);
    }
}
