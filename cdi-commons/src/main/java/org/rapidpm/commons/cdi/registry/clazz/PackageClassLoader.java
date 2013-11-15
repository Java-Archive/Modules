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

package org.rapidpm.commons.cdi.registry.clazz;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 10.06.13
 * Time: 07:39
 */
@Dependent

public class PackageClassLoader {

    private @Inject @CDILogger Logger logger;

    public Class[] getClasses(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        final String path = packageName.replace('.', '/');
        final List<Class> classes;
        try {
            final Enumeration<URL> resources = classLoader.getResources(path);
            final List<File> dirs = new ArrayList<>();
            while (resources.hasMoreElements()) {
                final URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            classes = new ArrayList<>();
            for (final File directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
        } catch (IOException e) {
            logger.error(e);
            return new Class[0];
        } catch (ClassNotFoundException e) {
            logger.error(e);
            return new Class[0];
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        final List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        final File[] files = directory.listFiles();
        for (final File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
