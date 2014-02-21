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

package org.rapidpm.lang;
/**
 * NeoScio
 * User: svenruppert
 * Date: 07.04.2010
 * Time: 23:20:02
 * This Source Code is part of the www.svenruppert.de project.
 * please contact sven.ruppert@web.de
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

public class PackageClassLoader {
    private static final Logger logger = Logger.getLogger(PackageClassLoader.class);

    public static final String CLASSLIST_FILE = "classlist.txt";

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages
     * and are annotated by the given annotation.
     *
     * @param packageName The base package
     * @param annotation  The annotation
     * @return The classes
     */
    public List<Class> getClassesWithAnnotation(final String packageName, final Class<? extends Annotation> annotation) {
        final List<Class> classList = new ArrayList<>();
        final List<Class> classes = getClasses(packageName);
        for (final Class aClass : classes) {
            if (aClass.isAnnotationPresent(annotation)) {
                classList.add(aClass);
            } else {
                //
            }
        }
        return classList;
    }


    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     *
     */
    public List<Class> getClasses(final String packageName) {
        final ClassLoader startClassLoader = Thread.currentThread().getContextClassLoader();
        //        final ClassLoader startClassLoader = ClassLoader..getContextClassLoader();
        if (logger.isDebugEnabled()) {
            logger.debug("ClassLoader current Thread : " + startClassLoader);
        }
        final List<Class> classes = new ArrayList<>();
        final String path = packageName.replace('.', '/');

        assert startClassLoader != null;
        final Set<ClassLoader> classLoaderSet = new HashSet<>();
        classLoaderSet.add(startClassLoader);

        ClassLoader childClassLoader = startClassLoader;

        while (childClassLoader.getParent() != null) {
            final ClassLoader parent = childClassLoader.getParent();
            if (parent != null) {
                classLoaderSet.add(parent);
                childClassLoader = parent;
            } else {
            }
        }

        final List<File> dirs = new ArrayList<>();
        final List<File> jars = new ArrayList<>();

        for (final ClassLoader loader : classLoaderSet) {
            try {
                final Enumeration<URL> resources = loader.getResources(path);
                while (resources.hasMoreElements()) {
                    final URL resource = resources.nextElement();
                    //                final String fileName = resource.getFile();
                    final String fileName = resource.getFile().replace("%20", " "); // REFAC Leerzeichen im Pfad (%20)!?
                    if (logger.isDebugEnabled()) {
                        logger.debug("ClassLoader fileName = " + fileName);
                    }
                    if (fileName.contains(".jar")) {
                        final String jarPath = resource.getPath();
                        final String jarFileName = jarPath.split(".jar")[0] + ".jar";
                        if (logger.isDebugEnabled()) {
                            logger.debug("Füge JAR hinzu : " + jarFileName);
                        }
                        jars.add(new File(jarFileName.replace("file:", ".")));
                    } else {
                        logger.debug("Füge Dir hinzu " + fileName);
                        dirs.add(new File(fileName));
                    }
                }
            } catch (IOException e) {
                logger.error(e);
            }


            for (final File directory : dirs) {
                final List<Class> list = findClassesInDirectories(directory, packageName);
                if (logger.isDebugEnabled()) {
                    logger.debug("ClassLoader list = " + list);
                }
                classes.addAll(list);
            }

            for (final File jar : jars) {
                findClassesInJars(classLoaderSet, classes, path, jar);
            }

            try {
                final InputStream resourceAsStream = startClassLoader.getResourceAsStream(CLASSLIST_FILE);
                if (resourceAsStream == null) {
                    logger.error("Classlist '" + CLASSLIST_FILE + "' existiert nicht");
                } else {
                    final BufferedReader bis = new BufferedReader(new InputStreamReader(resourceAsStream));

                    String line = bis.readLine();
                    while (line != null) {
                        try {
                            final Class<?> aClass = startClassLoader.loadClass(line);
                            classes.add(aClass);
                        } catch (ClassNotFoundException e) {
                            logger.error("Fehler beim Versuch diese Klasse zu laden : " + line);
                            logger.error(e);
                        }
                        line = bis.readLine();
                    }

                }
            } catch (IOException e) {
                logger.error(e);
            }
        }


        return classes;
    }

    private void findClassesInJars(final Set<ClassLoader> classLoaderSet, final List<Class> classes, final String path, final File jar) {
        try {
            final ZipFile zipFile = new ZipFile(jar);
            //        final Enumeration<ZipEntry> entries = zipFile.entries();
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry zipEntry = entries.nextElement();
                if (logger.isDebugEnabled()) {
                    logger.debug("ZipEntry : " + zipEntry);
                }
                final String name = zipEntry.getName();
                if (name.contains(path) && name.endsWith(".class")) {
                    try {
                        for (final ClassLoader classLoader : classLoaderSet) {
                            final String nameForLoading = name.replace("/", ".").replace(".class", "");
                            final Class<?> aClass = classLoader.loadClass(nameForLoading);
                            classes.add(aClass);
                        }
                    } catch (ClassNotFoundException e) {
                        logger.error(e);
                    }
                }
            }
        } catch (ZipException e) {
            logger.error("findClassesInJars (ZipException) - Versuchte folgende Datei zu öffnen : " + jar.getAbsolutePath());
            logger.error(e);
        } catch (IOException e) {
            logger.error("findClassesInJars (IOException) - Versuchte folgende Datei zu öffnen : " + jar.getAbsolutePath());
            logger.error(e);
        }
    }


    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     *
     */
    private List<Class> findClassesInDirectories(final File directory, final String packageName) {
        final List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            logger.warn("findClassesInDirectories : directory existiert nicht: " + directory.getAbsolutePath());

        } else {
            final File[] files = directory.listFiles();
            for (final File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClassesInDirectories(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    try {
                        final String classNameForLoading = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        final ClassLoader startClassLoader = Thread.currentThread().getContextClassLoader();
                        //                        final Class<?> aClass = Class.forName(classNameForLoading);
                        final Class<?> aClass = startClassLoader.loadClass(classNameForLoading);
                        classes.add(aClass);
                    } catch (ClassNotFoundException e) {
                        logger.error(e);
                    }
                }
            }
        }
        return classes;
    }

}
