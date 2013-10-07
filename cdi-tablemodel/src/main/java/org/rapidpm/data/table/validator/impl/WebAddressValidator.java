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

import org.apache.commons.validator.UrlValidator;
import org.rapidpm.Constants;
import org.rapidpm.data.table.corrector.AutoCorrector;
import org.rapidpm.data.table.validator.CellValidator;

import static org.rapidpm.Constants.PROTOCOL_IDENTIFIER;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 14.12.11
 * Time: 12:09
 */
public class WebAddressValidator extends CellValidator<String> {

    public static final UrlValidator URL_VALIDATOR = new UrlValidator(Constants.DEFAULT_PROTOCOLS);

    public WebAddressValidator() {
        super("Webadresse-Validator");
        addCondition(createProtocolRemoverCondition());
        addCondition(createUrlCondition());
    }

    private Condition createProtocolRemoverCondition() {
        final Condition condition = new Condition() {
            @Override
            public boolean check(final String value) {
                return !value.contains(PROTOCOL_IDENTIFIER);
            }

            @Override
            public String getErrorCause() {
                return "Das Protokoll muss entfernt werden.";
            }
        };
        condition.addAutoCorrector(new AutoCorrector<String>("Protokoll-Korrektor") {
            @Override
            public String correct(final String value) {
                // alles bis "://" entfernen
                return value.substring(value.indexOf(PROTOCOL_IDENTIFIER) + PROTOCOL_IDENTIFIER.length());
            }
        });
        return condition;
    }

    private Condition createUrlCondition() {
        return new Condition() {
            @Override
            public boolean check(final String value) {
                return URL_VALIDATOR.isValid(Constants.HTTP + value);
            }

            @Override
            public String getErrorCause() {
                return "Die URL ist ungültig.";
            }
        };
    }
}
