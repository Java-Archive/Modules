package org.rapidpm.demo.cdi.commons.tx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.NormalScope;
import javax.inject.Qualifier;

/**
 * User: Sven Ruppert
 * Date: 12.07.13
 * Time: 14:32
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@NormalScope
public @interface CDITransactionScope {
}
