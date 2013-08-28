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
