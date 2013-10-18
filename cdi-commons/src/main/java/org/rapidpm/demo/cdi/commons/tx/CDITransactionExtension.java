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

package org.rapidpm.demo.cdi.commons.tx;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;


/**
 * User: Sven Ruppert
 * Date: 15.07.13
 * Time: 08:18
 */
public class CDITransactionExtension implements Extension {


    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager manager) {
        System.out.println("CDITransactionExtension->event = " + event);

        final AnnotatedType annotationType = manager.createAnnotatedType(CDITransactionContext.class);
        final InjectionTarget injectionTarget = manager.createInjectionTarget(annotationType);
        final CreationalContext creationalContext = manager.createCreationalContext(null);
        final CDITransactionContext context = new CDITransactionContext();
        injectionTarget.inject(context, creationalContext);
        injectionTarget.postConstruct(CDITransactionContext.class);

        System.out.println("context = " + context);
        context.setBeanManager(manager);
        event.addContext(context);
    }


}
