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

import java.util.Objects;

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

    @Override public int hashCode() {
        return Objects.hash(referent);
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final GcHardRef other = (GcHardRef) obj;
        return Objects.equals(this.referent, other.referent);
    }

    //    @Override
//    public int hashCode() {
//        return referent == null ? 0 : referent.hashCode();
//    }
//
//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        final GcHardRef<T> ref = (GcHardRef<T>) o;
//        final T otherReferent = ref.get();
//        return (referent == null && otherReferent == null) || referent.equals(otherReferent);
//    }
}
