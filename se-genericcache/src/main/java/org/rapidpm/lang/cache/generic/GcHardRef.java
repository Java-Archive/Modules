/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
 */


package org.rapidpm.lang.cache.generic;

public class GcHardRef<T> implements GcReference<T> {
    private final T referent;

    public GcHardRef(final T referent) {
        super();
        this.referent = referent;
    }

    @Override
    public T get() {
        return referent;
    }

    @Override
    public int hashCode() {
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
        final GcHardRef<T> ref = (GcHardRef<T>) o;
        final T otherReferent = ref.get();
        return (referent == null && otherReferent == null) || referent.equals(otherReferent);
    }
}
