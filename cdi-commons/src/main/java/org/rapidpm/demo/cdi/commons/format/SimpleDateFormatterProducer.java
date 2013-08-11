package org.rapidpm.demo.cdi.commons.format;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.locale.CDILocale;
import org.rapidpm.demo.cdi.commons.registry.property.CDIPropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;

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
        if(annotated.isAnnotationPresent(CDISimpleDateFormatter.class)){
            final CDISimpleDateFormatter annotation = annotated.getAnnotation(CDISimpleDateFormatter.class);
            final String ressourceKey = annotation.value();
            final String ressource = propertyRegistryService.getRessourceForKey(ressourceKey);
            if(ressource.isEmpty()){
                return createDefault(injectionPoint);
            } else{
                return new SimpleDateFormat(ressource, defaultLocale);
            }
        } else{
            return createDefault(injectionPoint);
        }
    }
}
