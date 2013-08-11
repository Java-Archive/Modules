package org.rapidpm.demo.cdi.commons.cache;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;

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
    public Cache createCache(final BeanManager beanManager, final InjectionPoint injectionPoint){
        final Annotated annotated = injectionPoint.getAnnotated();
        final boolean annotationPresent = annotated.isAnnotationPresent(CDIGenericCache.class);
        if(annotationPresent){
            final Class<?> aClass = annotated.getAnnotation(CDIGenericCache.class).clazz2Cache();
            return new GenericCacheThreadsave(aClass, false);
        } else{
            if (logger.isDebugEnabled()) {
                logger.debug("Annotation CDIGenericCache not present...");
            }
        }
       return null;
    }
}
