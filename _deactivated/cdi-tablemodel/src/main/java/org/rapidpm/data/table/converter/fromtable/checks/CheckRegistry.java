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

package org.rapidpm.data.table.converter.fromtable.checks;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.converter.fromtable.annotation.OptionalRule;
import org.rapidpm.data.table.converter.fromtable.annotation.RequiredRule;
import org.rapidpm.lang.PackageClassLoader;

/**
 * Sven Ruppert
 * User: svenruppert
 * Date: 12/6/10
 * Time: 1:14 PM
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */
public class CheckRegistry {
    private static final Logger logger = Logger.getLogger(CheckRegistry.class);

    private static final List<String> pkgNameList = new ArrayList<>();
    private static final Map<Class, List<Class<? extends Check>>> checkRequiredMap = new HashMap<>();
    private static final Map<Class, List<Class<? extends Check>>> checkOptionalMap = new HashMap<>();


    //JIRA MOD-19 Registry generisch implementieren. 1to1 Registry und 1toN Registry
    static {
        pkgNameList.add("org.rapidpm.data.table.converter.fromtable.checks");   //JIRA MOD-20 pkgNameList refactoring gestallten
        final PackageClassLoader packageClassLoader = new PackageClassLoader();
        for (final String packageName : pkgNameList) {

            final List<Class> classes = packageClassLoader.getClasses(packageName);
            for (final Class aClass : classes) {
                final Annotation annotationReq = aClass.getAnnotation(RequiredRule.class);
                if (annotationReq != null) {
                    final RequiredRule responsibleFor = (RequiredRule) annotationReq;
                    final Class classResponsibleFor = responsibleFor.value();
                    if (checkRequiredMap.containsKey(classResponsibleFor)) {
                        final List<Class<? extends Check>> classList = checkRequiredMap.get(classResponsibleFor);
                        classList.add(aClass);
                        checkRequiredMap.put(classResponsibleFor, classList);
                    } else {
                        final List<Class<? extends Check>> classList = new ArrayList<>();
                        classList.add(aClass);
                        checkRequiredMap.put(classResponsibleFor, classList);
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Class ohne Req-Annotation " + aClass.getSimpleName());
                    }
                }

                final Annotation annotationOpt = aClass.getAnnotation(OptionalRule.class);
                if (annotationReq != null) {
                    final OptionalRule responsibleFor = (OptionalRule) annotationReq;
                    final Class classResponsibleFor = responsibleFor.value();
                    if (checkOptionalMap.containsKey(classResponsibleFor)) {
                        final List<Class<? extends Check>> classList = checkOptionalMap.get(classResponsibleFor);
                        classList.add(aClass);
                        checkOptionalMap.put(classResponsibleFor, classList);
                    } else {
                        final List<Class<? extends Check>> classList = new ArrayList<>();
                        classList.add(aClass);
                        checkOptionalMap.put(classResponsibleFor, classList);
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Class ohne Opt-Annotation " + aClass.getSimpleName());
                    }
                }
            }
        }


    }

    public static List<Check> getRequiredChecksFor(final Class clazz) {
        final List<Check> result = new ArrayList<>();
        if (clazz != null) {
            final boolean b = checkRequiredMap.containsKey(clazz);
            if (b) {
                try {
                    final List<Class<? extends Check>> classList = checkRequiredMap.get(clazz);
                    for (final Class<? extends Check> aClass : classList) {
                        final Check check = aClass.newInstance();
                        result.add(check);
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error(e);
                }
            } else {
                logger.error("Kein Checks für diese Klasse registriert.." + clazz.getSimpleName());
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("keine Checks registriert f " + clazz.getName());
            }
        }
        return result;
    }

    public static List<Check> getOptionalChecksFor(final Class clazz) {
        final List<Check> result = new ArrayList<>();
        if (clazz != null) {
            final boolean b = checkOptionalMap.containsKey(clazz);
            if (b) {
                try {
                    final List<Class<? extends Check>> classList = checkOptionalMap.get(clazz);
                    for (final Class<? extends Check> aClass : classList) {
                        final Check check = aClass.newInstance();
                        result.add(check);
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error(e);
                }
            } else {
                logger.error("Kein Checks für diese Klasse registriert.." + clazz.getSimpleName());
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("keine Checks registriert f " + clazz.getName());
            }
        }
        return result;
    }


}
