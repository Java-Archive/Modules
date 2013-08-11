package org.rapidpm.demo.javafx.tableview.filtered;

import java.util.Locale;

import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.cdi.commons.locale.CDILocale;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.CDIPropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;

/**
 * User: Sven Ruppert
 * Date: 21.06.13
 * Time: 13:26
 */
public class FilteredTableCdiHolder {

    private static final String COMPONENT_NAME = "javafx.filtered.tableview";

    private @Inject
    @CDILogger
    Logger logger;

    @Inject @CDIPropertyRegistryService
    private PropertyRegistryService propertyRegistryService;

    private @Inject @CDILocale
    Locale defaultLocale;

    public String getRessource(final String relativeKey){
        final String mappedKey = propertyRegistryService.getRessourceForKey(COMPONENT_NAME +"." + relativeKey);
        if (logger.isDebugEnabled()) {
            logger.debug("mappedKey - " + mappedKey);
        }
        return mappedKey;
    }

}
