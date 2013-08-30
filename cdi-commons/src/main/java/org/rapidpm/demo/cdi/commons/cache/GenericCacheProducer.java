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

package org.rapidpm.demo.cdi.commons.cache;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 10:29
 */
public class GenericCacheProducer {

    private
    @Inject
    @CDILogger
    Logger logger;

    @Produces @CDIGenericCache
    public Cache createCache(final BeanManager beanManager, final InjectionPoint injectionPoint) {
        final Annotated annotated = injectionPoint.getAnnotated();
        final boolean annotationPresent = annotated.isAnnotationPresent(CDIGenericCache.class);
        if (annotationPresent) {
            final Class<?> aClass = annotated.getAnnotation(CDIGenericCache.class).clazz2Cache();
            return new GenericCacheThreadsave(aClass, false);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Annotation CDIGenericCache not present...");
            }
        }
        return null;
    }
}
