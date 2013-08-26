package org.rapidpm.demo.cdi.commons.registry.property;

import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.CDICommons;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.ContextResolver;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.CDIPropertyRegistryFileBased;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 17.07.13
 * Time: 09:12
 */
public class PropertyRegistryServiceProducer {

    private @Inject @CDILogger Logger logger;
    private @Inject @CDIPropertyRegistryFileBased PropertyRegistryService defaultRegistry;


    @Produces
    @CDIPropertyRegistryService
    public PropertyRegistryService create(BeanManager beanManager, InjectionPoint injectionPoint,
                                          @CDICommons ContextResolver contextResolver) {
        if (logger.isDebugEnabled()) {
            logger.debug("used ContextResolver - " + contextResolver.getClass().getName());
        }
        final Set<Bean<?>> beanSet = beanManager.getBeans(PropertyRegistryService.class, contextResolver.resolveContext(PropertyRegistryService.class));
        for (final Bean<?> bean : beanSet) {
            final Set<Type> types = bean.getTypes();
            for (final Type type : types) {
                if (type.equals(PropertyRegistryService.class)) {
                    final Bean<PropertyRegistryService> registry = (Bean<PropertyRegistryService>) bean;
                    return registry.create(beanManager.createCreationalContext(registry));
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("! type.equals(PropertyRegistryService.class) " + type);
                    }
                }
            }
        }
        //return Default
        if (logger.isInfoEnabled()) {
            logger.info("PropertyRegistryService - Using default filebased implementation ");
        }
        return defaultRegistry;
    }
}
