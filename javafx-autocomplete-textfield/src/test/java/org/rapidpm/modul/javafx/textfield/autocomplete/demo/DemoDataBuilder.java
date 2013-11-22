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

package org.rapidpm.modul.javafx.textfield.autocomplete.demo;

import java.util.ArrayList;
import java.util.List;

import org.rapidpm.modul.javafx.textfield.autocomplete.demo.model.PersistentPojo;
import org.rapidpm.modul.javafx.textfield.autocomplete.demo.model.TransientAutoCompleteElement;

/**
 * User: Sven Ruppert Date: 17.09.13 Time: 16:15
 */
public class DemoDataBuilder {

    public List<TransientAutoCompleteElement> create() {
        final List<TransientAutoCompleteElement> result = new ArrayList<>();

        result.add(next(1L, "a"));
        result.add(next(2L, "aa"));
        result.add(next(3L, "aaa"));
        result.add(next(4L, "aaab"));
        result.add(next(5L, "aaabb"));
        result.add(next(6L, "aaabbb"));
        result.add(next(7L, "aaabbbb"));


        return result;
    }


    private TransientAutoCompleteElement next(Long id, String key) {
        final TransientAutoCompleteElement e = new TransientAutoCompleteElement();
        e.setId(id);
        e.setKey(key);
        e.setShortinfo(" ShortInfo - " + key);

        final PersistentPojo pojo = new PersistentPojo();
        pojo.setId(100L + id);
        pojo.setText("Pojo " + key);
        e.setPojo(pojo);

        return e;
    }


}
