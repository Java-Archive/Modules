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

package org.rapidpm.commons.cdi.tx;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.Cacheable;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 15.07.13
 * Time: 07:15
 */
public class CDITransactionContext implements Context {

    private final long contextNr = System.nanoTime();

    //Context kann nicht gemaneged werden -> CDI Extension -> CDITransactionExtension
    private static final Logger logger = new Logger(CDITransactionContext.class);


    //    TxKey-> Map<Class, Object>
//    private Map<String, Map<Class, Object>> classCacheMap = new HashMap<>(); // per init immer neu setzen..
    private Map<String, Cache> classCacheMap = new HashMap<>(); // per init immer neu setzen..

    //set by the Extension
    private BeanManager beanManager;

    public void begin(final String txNumber) {
        classCacheMap.remove(txNumber);
//        classCacheMap.put(txNumber, new HashMap<Class, Object>());

    }

    public void end(final String txNumber) {
        classCacheMap.remove(txNumber);
    }

    public void destroy() {
        classCacheMap = new HashMap<>();
    }


    /**
     * Get the scope type of the context object.
     *
     * @return the scope
     */
    @Override
    public Class<? extends Annotation> getScope() {
        return CDITransactionScope.class;
    }

    /**
     * Return an existing instance of certain contextual type or create a new instance by calling
     * {@link javax.enterprise.context.spi.Contextual#create(javax.enterprise.context.spi.CreationalContext)} and return the new instance.
     *
     * @param <T>               the type of contextual type
     * @param contextual        the contextual type
     * @param creationalContext the context in which the new instance will be created
     * @return the contextual instance
     * @throws javax.enterprise.context.ContextNotActiveException if the context is not active
     */
    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        System.out.println("long " + this);
        if (creationalContext == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("erzeuge creationalContext");
            }
            return null;
        } else {
            final Bean<T> bean = (Bean<T>) contextual;
            final Set<Type> types = bean.getTypes();
            for (final Type type : types) {
//                final Bean<?> resolve = beanManager.resolve(beanManager.getBeans(type, new AnnotationLiteral<CDITransactionScoped>() {}));
                final Set<Annotation> qualifiers = bean.getQualifiers();
                final Annotation[] annotations = qualifiers.toArray(new Annotation[qualifiers.size()]);
                if (logger.isDebugEnabled()) {
                    final Class<?> beanClass = bean.getBeanClass();
                    logger.debug("bean Class " + beanClass);
                    for (final Annotation annotation : annotations) {
                        logger.debug("annotation " + annotation);
                    }
                }

                final Bean<?> resolve = beanManager.resolve(beanManager.getBeans(type, annotations));
                if (logger.isDebugEnabled()) {
                    logger.debug("resolve = " + resolve);
                }
                if (resolve != null) {
                    final T t = bean.create(creationalContext);
                    final boolean annotationPresent = t.getClass().isAnnotationPresent(Cacheable.class);
                    if (logger.isDebugEnabled()) {
                        logger.debug("annotationPresent = " + annotationPresent);
                    }
                    if (annotationPresent) {
                        final String className = t.getClass().getAnnotation(Cacheable.class).className().getName();
                        if (logger.isDebugEnabled()) {
                            logger.debug("classname " + className);
                        }

                        //pruefe die txNumber

                        //cache..
                        if (classCacheMap.containsKey(className)) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("contains Key " + className);
                            }
//                            final Map<Class, Object> classObjectMap = classCacheMap.get(className);
                            final Cache cache = classCacheMap.get(className);
                            final Collection cacheForKey = cache.getAllFromCache();
                            final int size = cacheForKey.size();
                            if (logger.isDebugEnabled()) {
                                logger.debug("size : " + size);
                            }
                            if (size > 1) {
                                logger.warn("mehr als eine Instanz im Cache:... nehme erste Element");
                            } else { /*All ok */ }
                            return (T) cacheForKey.toArray()[0];
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Key not found " + className + " class cacheable -> will create a new cached instance " + t);
                            }
                            final GenericCacheThreadsave cache = new GenericCacheThreadsave(t.getClass(), false);
                            try {
                                cache.fillCache(t);
                                classCacheMap.put(className, cache);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                logger.error(e);
                            }
                            return t;
                        }
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("t not cacheable " + t);

                        }
                    }
                    return t;
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("resolve == null");
                    }
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Return an existing instance of a certain contextual type or a null value.
     *
     * @param <T>        the type of the contextual type
     * @param contextual the contextual type
     * @return the contextual instance, or a null value
     * @throws javax.enterprise.context.ContextNotActiveException if the context is not active
     */
    @Override
    public <T> T get(Contextual<T> contextual) {
        System.out.println("short " + this);
//        System.out.println("get () contextual = " + contextual);
        Bean<T> bean = (Bean<T>) contextual;
//        final Class<?> beanClass = bean.getBeanClass();
//        System.out.println("beanClass = " + beanClass);
        final Set<Type> types = bean.getTypes();
        for (final Type type : types) {
//            System.out.println("type = " + type);
//            final String typeName = type.getTypeName();
//            System.out.println("typeName = " + typeName);
            final Bean<?> resolve = beanManager.resolve(beanManager.getBeans(type.getClass().getName()));
            if (resolve != null) {
                final CreationalContext<T> creationalContext = (CreationalContext<T>) beanManager.createCreationalContext(resolve);
                if (creationalContext != null) {
                    final T t = bean.create(creationalContext);
                    if (logger.isDebugEnabled()) {
                        logger.debug("get t " + t);
                    }
                    return t;
//                    return createInstance(t.getClass());
                } else {
                }
            } else {
            }
        }
        return null;
    }

    /**
     * Determines if the context object is active.
     *
     * @return <tt>true</tt> if the context is active, or <tt>false</tt> otherwise.
     */
    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CDITransactionContext{ nr=" + contextNr + " ");
        sb.append("classCacheMap=").append(classCacheMap);
        sb.append('}');
        return sb.toString();
    }

    public void setBeanManager(BeanManager beanManager) {
        this.beanManager = beanManager;
    }

    public BeanManager getBeanManager() {
        return beanManager;
    }
}