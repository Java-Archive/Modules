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

import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class GcWeakRef<T> extends WeakReference<T> implements GcReference<T>,Serializable {

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
