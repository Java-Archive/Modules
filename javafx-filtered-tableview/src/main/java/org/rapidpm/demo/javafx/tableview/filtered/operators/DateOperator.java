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

package org.rapidpm.demo.javafx.tableview.filtered.operators;

import java.util.Date;
import java.util.EnumSet;

/**
 * @author Sven Ruppert
 */
public class DateOperator implements IFilterOperator<Date> {
    private final IFilterOperator.Type type;
    private final Date value;

    public static final EnumSet VALID_TYPES = EnumSet.of(Type.NONE, Type.EQUALS, Type.NOTEQUALS
            , Type.AFTER, Type.AFTERON, Type.BEFORE, Type.BEFOREON);

    public DateOperator(IFilterOperator.Type type, Date value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public IFilterOperator.Type getType() {
        return type;
    }

    @Override
    public Date getValue() {
        return value;
    }


}
