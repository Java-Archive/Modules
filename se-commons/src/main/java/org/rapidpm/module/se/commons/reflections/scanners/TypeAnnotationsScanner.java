/*
 * Copyright [2014] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
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

package org.rapidpm.module.se.commons.reflections.scanners;

import java.lang.annotation.Inherited;
import java.util.List;

/**
 * scans for class's annotations, where @Retention(RetentionPolicy.RUNTIME)
 */
@SuppressWarnings({"unchecked"})
public class TypeAnnotationsScanner extends AbstractScanner {
  public void scan(final Object cls) {
    final String className = getMetadataAdapter().getClassName(cls);

    for (String annotationType : (List<String>) getMetadataAdapter().getClassAnnotationNames(cls)) {

      if (acceptResult(annotationType) ||
          annotationType.equals(Inherited.class.getName())) { //as an exception, accept Inherited as well
        getStore().put(annotationType, className);
      }
    }
  }

}
