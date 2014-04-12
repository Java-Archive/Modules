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

package org.rapidpm.data.table.validator;

/**
 * Generischer Tabellenvalidator.
 *
 * @param <T> Typ des zu validierenden Objektes.
 * @author Alexander Vos
 */
public interface Validator<T> {
    /**
     * Validiert ein Objekt.
     *
     * @param value Objekt.
     * @return <code>true</code>, wenn die Validierung erfolgreich war; sonst <code>false</code>.
     * @see #getErrorCause()
     */
    public boolean validate(T value);

    /**
     * Fehlergeschreibung, wenn die Validierung fehlgeschlagen ist.
     *
     * @return Fehlergeschreibung oder <code>null</code>, wenn kein Fehler aufgetreten ist.
     * @see #validate(Object)
     */
    public String getErrorCause();
}
