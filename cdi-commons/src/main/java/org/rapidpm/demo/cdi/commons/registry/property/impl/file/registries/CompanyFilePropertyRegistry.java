package org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.cdi.commons.locale.CDILocale;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.impl.CompanyPropertyRegistry;

/**
 * User: Sven Ruppert
 * Date: 20.06.13
 * Time: 07:35
 */
public class CompanyFilePropertyRegistry implements CompanyPropertyRegistry {


    private ResourceBundle messages;
    private @Inject @CDILocale Locale defaultLocale;
    private @Inject @CDILogger Logger logger;

    @Override
    public void loadProperties() {
        try {
            messages = ResourceBundle.getBundle("i18n/company", defaultLocale);
        } catch (MissingResourceException e) {
            logger.warn("ressource not found loading dummy");
            messages = ResourceBundle.getBundle("i18n/company_dummy", defaultLocale);
        }
    }

    @Override
    public String getProperty(String key) {
        final boolean contains = messages.containsKey(key);
        if(contains){
            return messages.getString(key);
        } else{
            return "";  //TODO not good, replace with def null object
        }
    }

    @Override
    public boolean hasProperty(String key) {
        return messages.containsKey(key);
    }
}
