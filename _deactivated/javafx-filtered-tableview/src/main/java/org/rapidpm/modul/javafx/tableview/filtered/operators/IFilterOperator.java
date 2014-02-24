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

package org.rapidpm.modul.javafx.tableview.filtered.operators;


import org.rapidpm.commons.cdi.se.CDIContainerSingleton;
import org.rapidpm.modul.javafx.tableview.filtered.FilteredTableCdiHolder;

/**
 * @author Sven Ruppert
 */
public interface IFilterOperator<T> {

    /**
     * Probably should turn this into a normal class, so I can create true subsets of these type in IFilterOperator subclasses
     */
    public static enum Type {
        NONE("filteroperator.none"), NOTSET("filteroperator.notset"), EQUALS("filteroperator.equals"),
        NOTEQUALS("filteroperator.notequals"), GREATERTHAN("filteroperator.greaterthan"),
        GREATERTHANEQUALS("greaterthanequals"), LESSTHAN("filteroperator.lessthan"),
        LESSTHANEQUALS("filteroperator.lessthanequals"), CONTAINS("filteroperator.contains"),
        STARTSWITH("filteroperator.startswith"), ENDSWITH("filteroperator.endswith"), BEFORE("filteroperator.before"),
        BEFOREON("filteroperator.beforeon"), AFTER("filteroperator.after"), AFTERON("filteroperator.afteron"),
        TRUE("filteroperator.true"), FALSE("filteroperator.false");

        private final String display;

        Type(String display) {
            this.display = display;
        }

        @Override
        public String toString() {
            final CDIContainerSingleton instance = CDIContainerSingleton.getInstance();
            final FilteredTableCdiHolder managedInstance = instance.getManagedInstance(FilteredTableCdiHolder.class);
            return managedInstance.getRessource(display);
        }
    }

    public T getValue();

    public Type getType();

}
