/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
 */

package org.rapidpm.lang.cache.generic;

import org.apache.log4j.Logger;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class GcSoftRef<T> extends SoftReference<T> implements GcReference<T> {
    private static final Logger logger = Logger.getLogger(GcSoftRef.class);

    public GcSoftRef(final T referent) {
        super(referent);
    }

    public GcSoftRef(final T referent, final ReferenceQueue<? super T> q) {
        super(referent, q);
    }


    @Override
    public int hashCode() {
        final T referent = get();
        return referent == null ? 0 : referent.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GcSoftRef<T> ref = (GcSoftRef<T>) o;
        final T referent = get();
        final T otherReferent = ref.get();
        return (referent == null && otherReferent == null) || referent.equals(otherReferent);
    }
}
