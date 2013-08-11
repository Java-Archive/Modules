package org.rapidpm.demo.cdi.commons.registry;

import javax.enterprise.util.AnnotationLiteral;

/**
 * User: Sven Ruppert
 * Date: 26.06.13
 * Time: 16:20
 */
public interface ContextResolver {

    public AnnotationLiteral resolveContext();



}
