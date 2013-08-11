/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
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
