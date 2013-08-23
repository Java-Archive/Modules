package org.rapidpm.demo.cdi.commons.registry.property.impl.file;

import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.impl.CompanyPropertyRegistry;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries.ApplicationFilePropertyRegistry;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries.ClassFilePropertyRegistry;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries.ModulFilePropertyRegistry;

/**
 * User: Sven Ruppert
 * Date: 20.06.13
 * Time: 09:50
 */
public class FileBasedPropertyRegistryService extends PropertyRegistryService {

    private @Inject @CDILogger
    Logger logger;

    private @Inject @CDIPropertyRegistryFileBased
    CompanyPropertyRegistry companyPropertyRegistry;

    private @Inject @CDIPropertyRegistryFileBased
    ApplicationFilePropertyRegistry applicationFilePropertyRegistry;

    private @Inject @CDIPropertyRegistryFileBased
    ModulFilePropertyRegistry modulFilePropertyRegistry;

    private @Inject @CDIPropertyRegistryFileBased
    ClassFilePropertyRegistry classFilePropertyRegistry;

    @Override
    public String getRessourceForKey(String ressourceKey) {
        if(classFilePropertyRegistry.hasProperty(ressourceKey)){
            if (logger.isDebugEnabled()) {
                logger.debug("classFilePropertyRegistry found Property " + ressourceKey);
            }
            return classFilePropertyRegistry.getProperty(ressourceKey);
        } else if(modulFilePropertyRegistry.hasProperty(ressourceKey)){
            if (logger.isDebugEnabled()) {
                logger.debug("modulFilePropertyRegistry found Property " + ressourceKey);
            }
            return modulFilePropertyRegistry.getProperty(ressourceKey);
        } else if(applicationFilePropertyRegistry.hasProperty(ressourceKey)) {
            if (logger.isDebugEnabled()) {
                logger.debug("applicationFilePropertyRegistry found Property " + ressourceKey);
            }
            return applicationFilePropertyRegistry.getProperty(ressourceKey);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("companyPropertyRegistry must have Property " + ressourceKey);
            }
            return companyPropertyRegistry.getProperty(ressourceKey);
        }
    }
}
