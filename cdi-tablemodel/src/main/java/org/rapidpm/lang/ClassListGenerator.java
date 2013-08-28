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

package org.rapidpm.lang; /**
 * Sven Ruppert
 * User: svenruppert
 * Date: 15.11.11
 * Time: 09:57
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.rapidpm.Constants;

public class ClassListGenerator {
    private static final Logger logger = Logger.getLogger(ClassListGenerator.class);

    public static void main(String[] args) throws IOException {
        final PackageClassLoader pcl = new PackageClassLoader();
        final List<Class> classes = pcl.getClasses("org.rapidpm");
        final FileWriter fw = new FileWriter("classlist.txt");
        for (final Class aClass : classes) {
            if (logger.isDebugEnabled()) {
                logger.debug("aClass.getName() = " + aClass.getName());
            }
            fw.write(aClass.getName() + Constants.LINE_BREAK);
        }
        fw.flush();
        fw.close();
    }


}
