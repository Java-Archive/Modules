package org.rapidpm.demo.cdi.commons.format;

import javax.enterprise.util.AnnotationLiteral;

import org.rapidpm.demo.cdi.commons.registry.ContextResolver;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.CDIPropertyRegistryFileBased;

/**
 * User: Sven Ruppert
 * Date: 26.06.13
 * Time: 16:21
 *
 */


public class DefaultPropertyContextResolver implements ContextResolver {

    public AnnotationLiteral resolveContext(){
        return new AnnotationLiteral<CDIPropertyRegistryFileBased>() {};
    }
}
