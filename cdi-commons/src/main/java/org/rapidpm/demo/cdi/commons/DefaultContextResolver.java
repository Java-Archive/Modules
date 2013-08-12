package org.rapidpm.demo.cdi.commons;

import javax.enterprise.util.AnnotationLiteral;

import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.cdi.commons.registry.ContextResolver;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.impl.file.CDIPropertyRegistryFileBased;

/**
 * User: Sven Ruppert
 * Date: 12.08.13
 * Time: 10:40
 */
@CDICommons
public class DefaultContextResolver implements ContextResolver {
//public class DefaultContextResolver  {

    @Override
    public AnnotationLiteral resolveContext(Class<?> targetClass) {

        final String name = targetClass.getName();
        if(name.equals(Logger.class.getName())){
            return new AnnotationLiteral<CDINotMapped>() {};
        } else if(name.equals(PropertyRegistryService.class.getName())){
            return new AnnotationLiteral<CDIPropertyRegistryFileBased>() {};
        }


        return null;
    }
}
