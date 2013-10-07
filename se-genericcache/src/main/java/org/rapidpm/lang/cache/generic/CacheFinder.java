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


package org.rapidpm.lang.cache.generic;

import java.util.Collection;

public class CacheFinder<C, T> {
    private final Cache<C> cache;
    private final String attributeName;

    public CacheFinder(final Cache<C> cache, final String attributeName) {
        super();
        this.cache = cache;
        this.attributeName = attributeName;
    }

    public Collection<C> findForKey(final T value) {
        return cache.findForKey(attributeName, value);
    }

    public Collection<C> findForKeys(final Collection<T> values) {
        return cache.findForKeys(attributeName, values);
    }
}
