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

package org.rapidpm.data.table;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Die globale Registry verwaltet alle Typ-Registry-Instanzen als Singleton-Objekte.
 *
 * @author Alexander Vos
 */
public final class Registry {
    private static final Logger logger = Logger.getLogger(Registry.class);

    // verwaltet alle konkreten Registries als Singleton-Objekte
    private static final Map<Class<? extends AbstractRegistry<?, ?>>, AbstractRegistry<?, ?>> registryMap =
            new HashMap<>();
//    private static final ClassToInstanceMap<AbstractRegistry<?, ?>> registryMap = MutableClassToInstanceMap.create();

    /**
     * Gibt die Typ-Registry-Instanz zurück oder legt eine neue an.
     *
     * @param registryClass Registry-Klasse.
     * @param <A>           Annototationstyp.
     * @param <T>           Rückgabetyp der Registry.
     * @return Instanz der Registry-Klasse oder <code>null</code>, wenn ein Fehler auftritt.
     * @see #get(Class, Class)
     */
    public static <A extends Annotation, T> AbstractRegistry<A, T> get(final Class<? extends AbstractRegistry<A, T>> registryClass) {
        @SuppressWarnings("unchecked")
        AbstractRegistry<A, T> registry = (AbstractRegistry<A, T>) registryMap.get(registryClass); // erfolgreicher Cast ist garantiert!
        if (registry == null) {
            try {
                registry = registryClass.newInstance();
                registryMap.put(registryClass, registry);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error(e);
            }
        }
        return registry;
    }

    /**
     * Hilfsmethode für direkten Zugriff auf eine Klasse innerhalb einer konkreten Registry-Inbstanz.
     *
     * @param registryClass Registry-Klasse.
     * @param clazz         Klasse innerhalb der Registry.
     * @param <A>           Annototationstyp.
     * @param <T>           Rückgabetyp der Registry.
     * @return Instanz der Klasse innerhalb der Registry oder <code>null</code>, wenn ein Fehler auftritt.
     * @see #get(Class)
     */
    public static <A extends Annotation, T> T get(final Class<? extends AbstractRegistry<A, T>> registryClass, final Class<?> clazz) {
        final AbstractRegistry<A, T> registry = get(registryClass);
        return registry != null ? registry.getClassFor(clazz) : null;
    }

    private Registry() {
    }
}
