/*
 * Copyright (c) 2013, jhsheets@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.rapidpm.demo.javafx.tableview.filtered.operators;


import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableCdiHolder;

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
