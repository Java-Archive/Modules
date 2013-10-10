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

import javax.enterprise.util.AnnotationLiteral;

import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.CDIPropertyRegistryFileBased;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 12.08.13
 * Time: 10:40
 */
@CDICommons
public class DefaultContextResolver implements ContextResolver {
//public class DefaultContextResolver  {

    @Override
    public AnnotationLiteral resolveContext(Class<?> targetClass) {

        final String name = targetClass.getName();
        if (name.equals(Logger.class.getName())) {
            return new AnnotationLiteral<CDINotMapped>() {
            };
        } else if (name.equals(PropertyRegistryService.class.getName())) {
            return new AnnotationLiteral<CDIPropertyRegistryFileBased>() {
            };
        }

        return new AnnotationLiteral<CDICommons>() {
        };  //as Default Implementation
    }
}
