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
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 12.08.13
 * Time: 10:40
 */
@CDICommons
public class DefaultContextResolver implements ContextResolver {
//public class DefaultContextResolver  {

    private @Inject @CDILogger Logger logger;
    @Inject BeanManager beanManager;


    public Set<ContextResolver> gettAllContextResolver(){
        final Set<ContextResolver> resultSet = new HashSet<>();
        final Set<Bean<?>> allBeans = beanManager.getBeans(ContextResolver.class, new AnnotationLiteral<Any>() {});
        for (final Bean<?> bean : allBeans) {
            final Set<Type> types = bean.getTypes();
            for (final Type type : types) {
                if(type.equals(ContextResolver.class)){
                    if (logger.isDebugEnabled()) {
                        logger.debug("type (added) = " + type);
                    }
                    final ContextResolver t = ((Bean<ContextResolver>) bean).create(beanManager.createCreationalContext((Bean<ContextResolver>) bean));
                    resultSet.add(t);
                } else{
                    //
                }
            }
        }
      return resultSet;
    }

    public Set<ContextResolver> gettAllMockedContextResolver() {
        final Set<ContextResolver> resultSet = new HashSet<>();
        final Set<Bean<?>> allBeans = beanManager.getBeans(ContextResolver.class, new AnnotationLiteral<CDICommonsMocked>() {
        });
        for (final Bean<?> bean : allBeans) {
            final Set<Type> types = bean.getTypes();
            for (final Type type : types) {
                if (type.equals(ContextResolver.class)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("type (added) = " + type);
                    }
                    final ContextResolver t = ((Bean<ContextResolver>) bean).create(beanManager.createCreationalContext((Bean<ContextResolver>) bean));
                    resultSet.add(t);
                } else {
                    //
                }
            }
        }
        return resultSet;
    }


    @Override
    public AnnotationLiteral resolveContext(Class<?> targetClass) {

        final Set<ContextResolver> mockedContextResolvers = gettAllMockedContextResolver();
        for (final ContextResolver mockedContextResolver : mockedContextResolvers) {
            final AnnotationLiteral annotationLiteral = mockedContextResolver.resolveContext(targetClass);
            if(annotationLiteral == null){
                //noop
            } else{
                return annotationLiteral;
            }
        }


        final Set<ContextResolver> contextResolvers = gettAllContextResolver();

        for (final ContextResolver contextResolver : contextResolvers) {
            final boolean annotationPresent = contextResolver.getClass().isAnnotationPresent(CDICommonsMocked.class);
            if (annotationPresent) {
                //noop
            } else {
                final AnnotationLiteral annotationLiteral = contextResolver.resolveContext(targetClass);
                if (annotationLiteral == null) {

                } else {
                    return annotationLiteral;
                }
            }
        }


//        final String name = targetClass.getName();
//        if (name.equals(PropertyRegistryService.class.getName())) {
//            return new AnnotationLiteral<CDIPropertyRegistryFileBased>() {
//            };
//        }

//        if (name.equals(Logger.class.getName())) {
//            return new AnnotationLiteral<CDINotMapped>() {
//            };
//        } else

        //return new AnnotationLiteral<CDICommons>() {};  //as Default Implementation
        return null;
    }
}
