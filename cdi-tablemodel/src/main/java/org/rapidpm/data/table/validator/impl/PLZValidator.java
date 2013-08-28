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

package org.rapidpm.data.table.validator.impl;

import org.rapidpm.data.table.corrector.AutoCorrector;
import org.rapidpm.data.table.validator.CellValidator;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 14.12.11
 * Time: 10:03
 */
public class PLZValidator extends CellValidator<String> {

    public PLZValidator() {
        super("PLZ-Validator");
        addCondition(createFloatingPointCondition());
        addCondition(createDigitCondition());
        addCondition(createLengthCondition());
    }

    private Condition createFloatingPointCondition() {
        final Condition condition = new Condition() {
            @Override
            public boolean check(final String value) {
                return value.lastIndexOf('.') == -1; // QUEST auch auf "," prüfen?
            }

            @Override
            public String getErrorCause() {
                return "Die PLZ muss eine Ganzzahl sein.";
            }
        };
        condition.addAutoCorrector(new AutoCorrector<String>("Gleitkommazahl-Korrektor") {
            @Override
            public String correct(final String value) {
                return value.substring(0, value.lastIndexOf('.'));
            }
        });
        return condition;
    }

    private Condition createDigitCondition() {
        final Condition condition = new Condition() {
            @Override
            public boolean check(final String value) {
                for (final char c : value.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String getErrorCause() {
                return "Die PLZ darf nur aus Zahlen bestehen.";
            }
        };
        condition.addAutoCorrector(new AutoCorrector<String>("Zahlen-Korrektor") {
            @Override
            public String correct(final String value) {
                // remove all non digit chars
                final StringBuilder sb = new StringBuilder(value);
                for (int i = sb.length() - 1; i >= 0; i--) {
                    final char c = sb.charAt(i);
                    if (!Character.isDigit(c)) {
                        sb.deleteCharAt(i);
                    }
                }
                return sb.toString();
            }
        });
        return condition;
    }

    private Condition createLengthCondition() {
        final Condition condition = new Condition() {
            @Override
            public boolean check(final String value) {
                return value.length() == 5;
            }

            @Override
            public String getErrorCause() {
                return "Die PLZ muss 5 Zeichen lang sein.";
            }
        };
        condition.addAutoCorrector(new AutoCorrector<String>("Länge-Korrektor") {
            @Override
            public boolean canCorrect(final String value) {
                return value.length() == 4;
            }

            @Override
            public String correct(final String value) {
                return "0".concat(value);
            }
        });
        return condition;
    }
}
