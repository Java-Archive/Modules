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

package org.rapidpm.data.table.corrector;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 29.11.11
 * Time: 11:59
 */
public abstract class AutoCorrector<T> implements Corrector<T> {

    private String name;
    private boolean enabled = true;

    protected AutoCorrector(final String name, final boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    protected AutoCorrector(final String name) {
        this.name = name;
    }

    protected AutoCorrector() {
        name = AutoCorrector.class.getSimpleName();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean canCorrect(final T value) {
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AutoCorrector");
        sb.append("{name='").append(name).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }
}
