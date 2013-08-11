/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
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
