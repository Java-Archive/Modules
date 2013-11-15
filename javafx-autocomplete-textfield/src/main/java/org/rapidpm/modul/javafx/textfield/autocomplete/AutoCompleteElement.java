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

package org.rapidpm.modul.javafx.textfield.autocomplete;

import java.util.Objects;

/**
 * User: Sven Ruppert Date: 13.05.13 Time: 08:42
 */
public class AutoCompleteElement {


    private String key; //das was der Anwender eingetippt hat
    private String shortinfo; //das was der Anwender sehen wird
    private Long id; //ID der referenzierten Einheit

    public AutoCompleteElement key(final String key) {
        this.key = key;
        return this;
    }

    public AutoCompleteElement shortinfo(final String shortinfo) {
        this.shortinfo = shortinfo;
        return this;
    }

    public AutoCompleteElement id(final Long id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShortinfo() {
        return shortinfo;
    }

    public void setShortinfo(String shortinfo) {
        this.shortinfo = shortinfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AutoCompleteElement{");
        sb.append("key='").append(key).append('\'');
        sb.append(", shortinfo='").append(shortinfo).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    @Override public int hashCode() {
        return Objects.hash(key, shortinfo, id);
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AutoCompleteElement other = (AutoCompleteElement) obj;
        return Objects.equals(this.key, other.key) && Objects.equals(this.shortinfo, other.shortinfo) && Objects.equals(this.id, other.id);
    }


}