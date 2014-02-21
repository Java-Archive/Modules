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

package org.rapidpm.data.table.cellvaluefactory;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.Registry;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 14.12.11
 * Time: 11:04
 */
public class FactoryFactory {
    private static final Logger logger = Logger.getLogger(FactoryFactory.class);

    public static <T> T getDefaultValue(final Class<T> valueClass) {
        final Factory<?> factory = Registry.get(FactoryRegistry.class, valueClass);
        T defaultValue = null;
        try {
            @SuppressWarnings("unchecked")
            final T tmp = (T) factory.createInstance();
            defaultValue = tmp;
        } catch (InstantiationException ex) {
            logger.error(ex);
        }
        return defaultValue;
    }

    private FactoryFactory() {
    }
}
