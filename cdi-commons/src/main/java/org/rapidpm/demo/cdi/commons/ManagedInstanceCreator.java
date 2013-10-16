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

package org.rapidpm.demo.cdi.commons;

import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 15.10.13
 * Time: 15:06
 */
public class ManagedInstanceCreator {

    private @Inject @CDILogger Logger logger;

    @Inject BeanManager beanManager;

    public <T> T getManagedInstance(final Class<T> beanType, final AnnotationLiteral annotationLiteral ){

        T result = null;

        final Set<Bean<?>> beanSet = beanManager.getBeans(beanType, annotationLiteral);
        for (final Bean<?> bean : beanSet) {
            final Set<Type> types = bean.getTypes();
            for (final Type type : types) {
                if (type.equals(beanType)) {
                    final Bean<T> registry = (Bean<T>) bean;
                    result = registry.create(beanManager.createCreationalContext(registry));
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("! type.equals(beanType) " + type);
                    }
                }
            }
        }
        return result;
    }




}
