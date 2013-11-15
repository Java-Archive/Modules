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

package org.rapidpm.commons.cdi.format;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.rapidpm.commons.cdi.locale.CDILocale;
import org.rapidpm.commons.cdi.registry.property.CDIPropertyRegistryService;
import org.rapidpm.commons.cdi.registry.property.PropertyRegistryService;

/**
 * User: Sven Ruppert
 * Date: 03.06.13
 * Time: 08:28
 */

public class SimpleDateFormatterProducer {

    public SimpleDateFormat createDefault(InjectionPoint injectionPoint) {
        //default res key -> company wide yyyyMMdd
        //return new SimpleDateFormat("dd.MM.yyyy");
        final String ressource = propertyRegistryService.getRessourceForKey("date.yyyyMMdd");
        return new SimpleDateFormat(ressource, defaultLocale);
    }

    private @Inject @CDIPropertyRegistryService
    PropertyRegistryService propertyRegistryService;

    private @Inject @CDILocale Locale defaultLocale;


    /**
     * es fehlt noch die auflösung des Locale
     *
     * @param injectionPoint
     * @return
     */
    @Produces
    @CDISimpleDateFormatter
    public SimpleDateFormat produceSimpleDateFormatter(InjectionPoint injectionPoint) {
        final Annotated annotated = injectionPoint.getAnnotated();
        if (annotated.isAnnotationPresent(CDISimpleDateFormatter.class)) {
            final CDISimpleDateFormatter annotation = annotated.getAnnotation(CDISimpleDateFormatter.class);
            final String ressourceKey = annotation.value();
            final String ressource = propertyRegistryService.getRessourceForKey(ressourceKey);
            if (ressource.equals("###" + ressourceKey + "###")) {
                return createDefault(injectionPoint);
            } else {
                return new SimpleDateFormat(ressource, defaultLocale);
            }
        } else {
            return createDefault(injectionPoint);
        }
    }
}
