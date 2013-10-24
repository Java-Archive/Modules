package org.rapidpm.demo.cdi.commons.contextresolver;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.CDICommons;

/**
 * User: Sven Ruppert
 * Date: 24.10.13
 * Time: 13:56
 */

public class TestContextResolver implements ContextResolver {

    @Inject CDIContext context;

    @CheckMockedContext
    @Override public AnnotationLiteral resolveContext(Class<?> targetClass) {
        return new AnnotationLiteral<CDICommons>() {};
    }
}
