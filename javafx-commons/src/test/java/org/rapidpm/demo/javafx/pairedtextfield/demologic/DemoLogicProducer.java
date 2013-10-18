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

package org.rapidpm.demo.javafx.pairedtextfield.demologic;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.CDICommons;
import org.rapidpm.demo.cdi.commons.ContextResolver;
import org.rapidpm.demo.cdi.commons.ManagedInstanceCreator;

/**
 * User: Sven Ruppert
 * Date: 16.10.13
 * Time: 17:21
 */
public class DemoLogicProducer {
    private @Inject ManagedInstanceCreator creator;

    @Inject @CDICommons ContextResolver contextResolver;


    @Produces @DemoLogicContext
    public DemoLogic create(){   //@New DemoContextResolver contextResolver
        final Class<DemoLogic> beanType = DemoLogic.class;
        final AnnotationLiteral annotationLiteral = contextResolver.resolveContext(beanType);
        final DemoLogic demoLogic = creator.getManagedInstance(beanType, annotationLiteral);
        return demoLogic;
    }
}
