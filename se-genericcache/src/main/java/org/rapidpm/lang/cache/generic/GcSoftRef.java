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


import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import org.rapidpm.module.se.commons.logger.Logger;

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
