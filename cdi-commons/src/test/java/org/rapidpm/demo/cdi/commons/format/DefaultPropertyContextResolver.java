package org.rapidpm.demo.cdi.commons.format;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.CDINotMapped;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.ContextResolver;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.CDIPropertyRegistryFileBased;

/**
 * User: Sven Ruppert
 * Date: 26.06.13
 * Time: 16:21
 *
 */


public class DefaultPropertyContextResolver implements ContextResolver {

    //private static final Logger logger = Logger.getLogger(DefaultPropertyContextResolver.class);
    private
    @Inject
    @CDILogger
    org.rapidpm.demo.cdi.commons.logger.Logger logger;

    public AnnotationLiteral resolveContext(final Class<?> targetClass){
        if(targetClass.getName().equals(PropertyRegistryService.class.getName())){
            return new AnnotationLiteral<CDIPropertyRegistryFileBased>() {};
        } else{
            if (logger.isDebugEnabled()) {
                logger.debug("class not mapped " + targetClass);
            }
            return new AnnotationLiteral<CDINotMapped>() {};
        }
    }
}
