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

package org.rapidpm.demo.cdi.commons.registry.property;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.CDICommons;
import org.rapidpm.demo.cdi.commons.ContextResolver;
import org.rapidpm.demo.cdi.commons.ManagedInstanceCreator;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
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
    private @Inject BeanManager beanManager;
    private @Inject ManagedInstanceCreator creator;
    @Produces
    @CDIPropertyRegistryService
    public PropertyRegistryService create(InjectionPoint injectionPoint,
                                          @CDICommons ContextResolver contextResolver) {
        if (logger.isDebugEnabled()) {
            logger.debug("used ContextResolver - " + contextResolver.getClass().getName());
        }
        final Class<PropertyRegistryService> beanType = PropertyRegistryService.class;
        final AnnotationLiteral annotationLiteral = contextResolver.resolveContext(beanType);
        final PropertyRegistryService propertyRegistryService = creator.getManagedInstance(beanType, annotationLiteral);
//        final Set<Bean<?>> beanSet = beanManager.getBeans(beanType, annotationLiteral);
//        for (final Bean<?> bean : beanSet) {
//            final Set<Type> types = bean.getTypes();
//            for (final Type type : types) {
//                if (type.equals(beanType)) {
//                    final Bean<PropertyRegistryService> registry = (Bean<PropertyRegistryService>) bean;
//                    propertyRegistryService = registry.create(beanManager.createCreationalContext(registry));
//                } else {
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("! type.equals(beanType) " + type);
//                    }
//                }
//            }
//        }
        //return Default
        if (logger.isInfoEnabled()) {
            logger.info("PropertyRegistryService - Using default filebased implementation ");
        }

        if(propertyRegistryService == null){
            return defaultRegistry;
        } else{
            return propertyRegistryService;
        }
    }








}
