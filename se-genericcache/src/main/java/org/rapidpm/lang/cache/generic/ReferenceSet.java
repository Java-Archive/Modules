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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;

public class ReferenceSet<T> {
    private final boolean weakRefs;
    private final Set<GcReference<T>> set = new HashSet<>();
    private final ReferenceQueue<T> queue = new ReferenceQueue<>();
    private final ReadWriteLock lock;

    public ReferenceSet(final boolean weakRefs, final ReadWriteLock lock) {
        super();
        this.weakRefs = weakRefs;
        this.lock = lock;
    }

    public ReferenceSet(final boolean weakRefs) {
        this(weakRefs, null);
    }

    public void add(final T object) {
        set.add(createReference(object));
    }

    public void addAll(final ReferenceSet<T> set2Add) {
        set.addAll(set2Add.set);
    }

    public void remove(final T toRemove) {
        final GcReference<T> ref = createReference(toRemove);
        set.remove(ref);
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public Collection<T> toReferents() {
        expungeStaleEntries();
        final Set<T> result = new HashSet<>();
        for (final GcReference<T> ref : set) {
            final T referent = ref.get();
            if (referent != null) {
                result.add(referent);
            }
        }
        return result;
    }

    private void expungeStaleEntries() {
        Reference<? extends T> removed = queue.poll();
        while (removed != null) {
            if (lock != null) {
                try {
                    // upgrade lock manually
                    lock.readLock().unlock();   // must unlock first to obtain writelock
                    lock.writeLock().lock();
                    set.remove(removed);
                    // downgrade lock
                    lock.readLock().lock();  // reacquire read without giving up write lock
                } finally {
                    lock.writeLock().unlock();
                }
            } else {
                set.remove(removed);
            }
            removed = queue.poll();
        }
    }

    private GcReference<T> createReference(final T object) {
//        return weakRefs ? new GcWeakRef<T>(object, queue) : new GcHardRef<T>(object);
        return weakRefs ? new GcSoftRef<>(object, queue) : new GcHardRef<>(object);
    }
}
