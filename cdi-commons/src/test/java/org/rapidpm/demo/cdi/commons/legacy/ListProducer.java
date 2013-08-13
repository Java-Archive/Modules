package org.rapidpm.demo.cdi.commons.legacy;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;

import org.rapidpm.demo.cdi.commons.registry.ContextResolver;

/**
 * User: Sven Ruppert
 * Date: 02.08.13
 * Time: 07:15
 */
public class ListProducer {

    @Produces @CDILegacyTest
    public List createList(InjectionPoint injectionPoint, BeanManager beanManager, ContextResolver contextResolver){
        //treffen der Entscheidungen...

        return new ArrayList();
    }
}
