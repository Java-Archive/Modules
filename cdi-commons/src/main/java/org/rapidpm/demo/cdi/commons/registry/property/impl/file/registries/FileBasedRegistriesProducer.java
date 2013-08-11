package org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;

import org.rapidpm.demo.cdi.commons.registry.property.impl.file.CDIPropertyRegistryFileBased;

/**
 * User: Sven Ruppert
 * Date: 20.06.13
 * Time: 09:06
 */
public class FileBasedRegistriesProducer {


    @Produces
    @CDIPropertyRegistryFileBased
    public CompanyFilePropertyRegistry createCompayPropertyRegistry(@New CompanyFilePropertyRegistry service){
        service.loadProperties();
        return service;
    }

    @Produces
    @CDIPropertyRegistryFileBased
    public ApplicationFilePropertyRegistry createApplicationPropertyRegistry(@New ApplicationFilePropertyRegistry service){
        service.loadProperties();
        return service;
    }

    @Produces
    @CDIPropertyRegistryFileBased
    public ModulFilePropertyRegistry createModulPropertyRegistry(@New ModulFilePropertyRegistry service){
        service.loadProperties();
        return service;
    }

    @Produces
    @CDIPropertyRegistryFileBased
    public ClassFilePropertyRegistry createClassPropertyRegistry(@New ClassFilePropertyRegistry service){
        service.loadProperties();
        return service;
    }
}
