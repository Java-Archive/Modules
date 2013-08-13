package org.rapidpm.demo.cdi.commons.tx;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;


/**
 * User: Sven Ruppert
 * Date: 15.07.13
 * Time: 08:18
 */
public class CDITransactionExtension implements Extension {

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager manager) {
        final CDITransactionContext context = new CDITransactionContext();
        context.setBeanManager(manager);
        event.addContext(context);
   }

}
