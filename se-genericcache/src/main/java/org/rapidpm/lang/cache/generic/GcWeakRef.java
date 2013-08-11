/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
 */


package org.rapidpm.lang.cache.generic;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class GcWeakRef<T> extends WeakReference<T> implements GcReference<T> {

    public GcWeakRef(final T referent, final ReferenceQueue<? super T> queue) {
        super(referent, queue);
    }

    public GcWeakRef(final T referent) {
        super(referent);
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
        final GcWeakRef<T> ref = (GcWeakRef<T>) o;
        final T referent = get();
        final T otherReferent = ref.get();
        return (referent == null && otherReferent == null) || referent.equals(otherReferent);
    }
}
