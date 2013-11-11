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

package org.rapidpm.demo.cdi.commons.registry.property.impl.file;

import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.impl.CompanyPropertyRegistry;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries.ApplicationFilePropertyRegistry;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries.ClassFilePropertyRegistry;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries.ModulFilePropertyRegistry;
import org.rapidpm.module.se.commons.logger.Logger;

import java.io.Serializable;

/**
 * User: Sven Ruppert
 * Date: 20.06.13
 * Time: 09:50
 */
public class FileBasedPropertyRegistryService extends PropertyRegistryService implements Serializable {

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
        if (classFilePropertyRegistry.hasProperty(ressourceKey)) {
            if (logger.isDebugEnabled()) {
                logger.debug("classFilePropertyRegistry found Property " + ressourceKey);
            }
            return classFilePropertyRegistry.getProperty(ressourceKey);
        } else if (modulFilePropertyRegistry.hasProperty(ressourceKey)) {
            if (logger.isDebugEnabled()) {
                logger.debug("modulFilePropertyRegistry found Property " + ressourceKey);
            }
            return modulFilePropertyRegistry.getProperty(ressourceKey);
        } else if (applicationFilePropertyRegistry.hasProperty(ressourceKey)) {
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
