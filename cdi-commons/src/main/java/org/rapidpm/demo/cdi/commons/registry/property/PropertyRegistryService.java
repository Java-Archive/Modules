package org.rapidpm.demo.cdi.commons.registry.property;

import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 10.06.13
 * Time: 07:34
 * <p/>
 * The PropertyRegistryService will decide
 * what kind of registry-implementations will be used here.
 * <p/>
 * For example the file based implementations.
 */
public abstract class PropertyRegistryService {

    private @Inject @CDILogger Logger logger;

    public abstract String getRessourceForKey(String ressourceKey);

    private String mappClassRessourceKey(final Class clazz, final String relativeKey) {
        return clazz.getName() + "." + relativeKey;
    }

    public String getClassMappedRessource(final Class clazz, final String relativeKey) {
        final String mappedKey = mappClassRessourceKey(clazz, relativeKey);
        if (logger.isDebugEnabled()) {
            logger.debug("mappedKey - " + mappedKey);
        }
        return getRessourceForKey(mappedKey);
    }
}
