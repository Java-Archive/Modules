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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

public interface Cache<T> {
    void fillCache(List<T> cacheables) throws IllegalAccessException, InvocationTargetException;

    void fillCache(T cacheable) throws IllegalAccessException, InvocationTargetException;

    void removeFromCache(Collection<T> cacheables) throws IllegalAccessException, InvocationTargetException;

    void removeFromCache(T cacheable2Remove) throws IllegalAccessException, InvocationTargetException;

    Collection<T> getAllFromCache();

    <C> CacheFinder<T, C> createCacheFinder(String attributeName);

    Collection<T> findForKey(String attributeName, Object key);

    Collection<T> findForKeys(String attributeName, Collection<? extends Object> keys);
}
