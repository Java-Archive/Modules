package org.rapidpm.demo.cdi.commons.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * User: Sven Ruppert
 * Date: 19.07.13
 * Time: 10:13
 */
@Qualifier
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface CDISimpleDateFormatter {
    @Nonbinding String value() default "";
}
