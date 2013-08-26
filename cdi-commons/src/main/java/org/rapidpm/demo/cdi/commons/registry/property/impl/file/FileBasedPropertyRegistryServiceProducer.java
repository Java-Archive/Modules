package org.rapidpm.demo.cdi.commons.registry.property.impl.file;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 10.06.13
 * Time: 09:09
 */
public class FileBasedPropertyRegistryServiceProducer {

    private @Inject @CDILogger
    Logger logger;

    @Produces
    @CDIPropertyRegistryFileBased
    private PropertyRegistryService createFileBased(@New FileBasedPropertyRegistryService service) {
        return service;
    }
}
