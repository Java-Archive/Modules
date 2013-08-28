package org.rapidpm.demo.cdi.commons.registry.property.impl.file.registries;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.locale.CDILocale;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.impl.ClassPropertyRegistry;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 20.06.13
 * Time: 07:36
 */
public class ClassFilePropertyRegistry implements ClassPropertyRegistry {

    private ResourceBundle messages;

    private @Inject @CDILocale Locale defaultLocale;
    private @Inject @CDILogger Logger logger;

    @Override
    public void loadProperties() {
        try {
            messages = ResourceBundle.getBundle("i18n/classes", defaultLocale);
        } catch (MissingResourceException e) {
            logger.warn("ressource not found loading dummy");
            messages = ResourceBundle.getBundle("i18n/classes_dummy", defaultLocale);
        }
    }

    @Override
    public String getProperty(String key) {
        final boolean contains = messages.containsKey(key);
        if (contains) {
            return messages.getString(key);
        } else {
            return "###" + key + "###";  //JIRA MOD-45 write to separat log file that this key is missing
        }
    }

    @Override
    public boolean hasProperty(String key) {
        return messages.containsKey(key);
    }
}
