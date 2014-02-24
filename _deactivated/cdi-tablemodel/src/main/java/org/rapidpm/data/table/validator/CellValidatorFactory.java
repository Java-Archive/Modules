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

package org.rapidpm.data.table.validator;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.cellvaluefactory.FactoryFactory;
import org.rapidpm.data.table.corrector.AutoCorrector;

/**
 * Factory zur Erstellung von gebräuchlichen Zellenvalidatoren.
 *
 * @author Alexander Vos
 * @see CellValidator
 */
public class CellValidatorFactory {
    private static final Logger logger = Logger.getLogger(CellValidatorFactory.class);

    public static <T> CellValidator<T> createNullValidator(final Class<T> cellType, final boolean autoFix) {
        final CellValidator<T> validator = new CellValidator<>();
        final CellValidator<T>.Condition condition = validator.new Condition() {
            @Override
            public boolean check(final T value) {
                return value != null;
            }

            @Override
            public String getErrorCause() {
                return "Der Wert darf nicht NULL sein.";
            }
        };
        validator.addCondition(condition);
        if (autoFix) {
            final T defaultValue = FactoryFactory.getDefaultValue(cellType);
            condition.addAutoCorrector(new AutoCorrector<T>() {
                @Override
                public T correct(final T value) {
                    return defaultValue;
                }
            });
        }
        return validator;
    }


    /**
     * Erstellt einen Integer-Zellenvalidator.
     *
     * @return Integer-Zellenvalidator.
     */
    public static CellValidator<Integer> createIntegerValidator() {
        return new CellValidator<>();
    }

    /**
     * Erstellt einen String-Zellenvalidator mit dem angegebenen Regulären Ausdruck als Bedingung.
     *
     * @param name     Name.
     * @param regex    Der Reguläre Ausdruck.
     * @param optional <code>true</code>, wenn die Bedingung optional sein soll.
     * @return Regex-Zellenvalidator.
     */
    public static CellValidator<String> createStringValidator(final String name, final String regex, final boolean optional) {
        final CellValidator<String> validator = new CellValidator<>(name);
        validator.addCondition(validator.new Condition(optional) {
            final Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean check(final String value) {
                final boolean matches = pattern.matcher(value).matches();
                if (!matches) {
                    setErrorCause("\"" + value + "\" entspricht nicht dem Pattern: " + pattern);
                }
                return matches;
            }
        });
        return validator;
    }

    /**
     * Erstellt einen String-Zellenvalidator mit dem angegebenen Regulären Ausdruck als Bedingung.
     *
     * @param regex    Der Reguläre Ausdruck.
     * @param optional <code>true</code>, wenn die Bedingung optional sein soll.
     * @return Regex-Zellenvalidator.
     */
    public static CellValidator<String> createStringValidator(final String regex, final boolean optional) {
        return createStringValidator(null, regex, optional);
    }
}
