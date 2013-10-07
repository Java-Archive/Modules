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

package org.rapidpm.data.table.formatter;


import java.io.Serializable;


/**
 * NeoScio
 *
 * @param <T>
 * @author svenruppert
 * @since 02.11.2009
 * Time: 17:05:18
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@me.com
 */

public interface CellValueFormatter<T> extends Serializable {


    public String format(final T value);


}
