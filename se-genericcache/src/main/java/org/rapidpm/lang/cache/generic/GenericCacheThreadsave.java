/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
 */


package org.rapidpm.lang.cache.generic;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

public class GenericCacheThreadsave<T> implements Cache<T> {
    private static final Logger logger = Logger.getLogger(GenericCacheThreadsave.class);

    // maps the attribute names of the cacheable to a map, that maps the attribute
    // values to a collection of cacheables, to build up the index.
    private final Map<String, Index<T>> attribName2Index = new HashMap<>();
    // maps attribute names to read methods
    private final Map<String, Method> attribName2Method = new HashMap<>();
    // attribute name for primary (unique) key attribute of cacheable
    private String primaryKeyAttributeName;
    private final boolean weakRefs;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();


    public GenericCacheThreadsave(final Class<T> clazz, final boolean weakRefs) {
        super();
        this.weakRefs = weakRefs;
        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                final Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod != null) {
                    final String name = propertyDescriptor.getName();
                    // put name/readMethod pair to attribute map
                    attribName2Method.put(name, readMethod);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new GenericCache for the specified type
     *
     * @param clazz class to be cached
     */
    public GenericCacheThreadsave(final Class<T> clazz) {
        this(clazz, true);
    }

    /**
     * Fills the cache with the listed cacheables
     *
     * @param cacheables list of cacheables
     */
    @Override
    public void fillCache(final List<T> cacheables) throws IllegalAccessException, InvocationTargetException {
        for (final T cacheable : cacheables) {
            fillCache(cacheable);
        }
    }

    /**
     * Adds the speciafied cacheable to the cache
     *
     * @param cacheable cacheable to be added
     */
    @Override
    public void fillCache(final T cacheable) throws IllegalAccessException, InvocationTargetException {

        final boolean annotationPresent = cacheable.getClass().isAnnotationPresent(Cacheable.class);
        if(annotationPresent){
            final Cacheable annotation = cacheable.getClass().getAnnotation(Cacheable.class);
            primaryKeyAttributeName =  annotation.primaryKeyAttributeName();
            for (final Map.Entry<String, Method> attribute : attribName2Method.entrySet()) {
                final String attribName = attribute.getKey();
                Index<T> index = attribName2Index.get(attribName);
                if (index == null) {
                    index = new Index<>();
                    attribName2Index.put(attribName, index);
                }
                final Method readMethod = attribute.getValue();
                final Object key = readMethod.invoke(cacheable);
                index.putSingleOrMultiple(key, cacheable);
            }
        } else{
            if (logger.isDebugEnabled()) {
                logger.debug("Class has no Annotation Cacheable");
            }
        }
    }

    @Override
    public void removeFromCache(final Collection<T> cacheables) throws IllegalAccessException, InvocationTargetException {
        for (final T cacheable : cacheables) {
            removeFromCache(cacheable);
        }
    }

    @Override
    public void removeFromCache(final T cacheable2Remove) throws IllegalAccessException, InvocationTargetException {
        for (final Map.Entry<String, Method> entry : attribName2Method.entrySet()) {
            final String attrName = entry.getKey();
            final Method readMethod = entry.getValue();
            final Object key = readMethod.invoke(cacheable2Remove);
            final Index<T> index = attribName2Index.get(attrName);
            index.remove(key, cacheable2Remove);
        }
    }

    @Override
    public Collection<T> findForKey(final String attributeName, final Object key) {
        final Index<T> index = attribName2Index.get(attributeName);
        return index == null ?
                new ArrayList<T>() :
                index.get(key);
    }

    @Override
    public Collection<T> findForKeys(final String attributeName, final Collection<? extends Object> keys) {
        final Index<T> index = attribName2Index.get(attributeName);
        if (index == null) {
            return Collections.emptyList();
        }
        final Set<T> result = new HashSet<>();
        for (final Object key : keys) {
            result.addAll(index.get(key));
        }
        return result;
    }

    @Override
    public Collection<T> getAllFromCache() {
        if (primaryKeyAttributeName == null) {
            return Collections.emptyList();
        }
        final Index<T> index = attribName2Index.get(primaryKeyAttributeName);
        return index.getAllValues();
    }

    @Override
    public <C> CacheFinder<T, C> createCacheFinder(final String attributeName) {
        assert attributeName != null : "attributeName must not be null";
        return new CacheFinder<>(this, attributeName);
    }

    //----------------------------------------------------------------------------
    //---------------------- Index -----------------------------------------------
    //----------------------------------------------------------------------------

    /**
     *
     */
    private class Index<T> {
        // non static => private final GenericCache super_this;
        private final Map<Object, ReferenceSet<T>> indexMap = new HashMap<>();

        public void put(final Object key, final T value) {
            ReferenceSet<T> referenceSet = indexMap.get(key);
            if (referenceSet == null) {
                referenceSet = new ReferenceSet<>(weakRefs, lock);
                indexMap.put(key, referenceSet);
            }
            referenceSet.add(value);
        }

        /**
         * Puts the cacheable object to the index-map.
         *
         * @param key   key
         * @param value value
         */
        public void putSingleOrMultiple(final Object key, final T value) {
            try {
                lock.writeLock().lock();
                if (key instanceof Iterable) {
                    final Collection<Object> keys = (Collection<Object>) key;
                    for (final Object singleKey : keys) {
                        put(singleKey, value);
                    }
                } else {
                    put(key, value);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        public void remove(final Object key, final T value) {
            try {
                lock.writeLock().lock();
                if (key instanceof Iterable) {
                    final Collection<Object> keys = (Collection<Object>) key;
                    for (final Object singleKey : keys) {
                        final ReferenceSet<T> referenceSet = indexMap.get(singleKey);
                        referenceSet.remove(value);
                        if (referenceSet.isEmpty()) {
                            indexMap.remove(singleKey);
                        }
                    }
                } else {
                    final ReferenceSet<T> referenceSet = indexMap.get(key);
                    referenceSet.remove(value);
                    if (referenceSet.isEmpty()) {
                        indexMap.remove(key);
                    }
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        public Collection<T> get(final Object key) {
            try {
                lock.readLock().lock();
                final ReferenceSet<T> result = indexMap.get(key);
                if (result == null) {
                    return Collections.emptyList();
                }
                if (result.isEmpty()) {
                    try {
                        // upgrade lock manually
                        lock.readLock().unlock();   // must unlock first to obtain writelock
                        lock.writeLock().lock();
                        //do write operation
                        indexMap.remove(key);
                        // downgrade lock
                        lock.readLock().lock();  // reacquire read without giving up write lock
                    } finally {
                        lock.writeLock().unlock(); // unlock write, still hold read
                    }
                }
                return result.toReferents();
            } finally {
                lock.readLock().unlock();
            }
        }

        public Collection<T> getAllValues() {
            try {
                lock.readLock().lock();
                final Set<T> result = new HashSet<>();
                for (final ReferenceSet<T> values : indexMap.values()) {
                    result.addAll(values.toReferents());
                }
                return result;
            } finally {
                lock.readLock().unlock();
            }
        }
    }
}
