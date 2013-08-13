package org.rapidpm.demo.cdi.commons.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 10:17
 */


@Qualifier
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface CDIGenericCache {
    @Nonbinding Class<?> clazz2Cache() default Object.class;
}
