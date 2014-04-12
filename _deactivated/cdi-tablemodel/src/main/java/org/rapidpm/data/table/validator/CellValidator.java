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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.Cell;
import org.rapidpm.data.table.corrector.AutoCorrector;

/**
 * Validator für eine Tabellen-Zelle.
 *
 * @param <T> Datentyp der Zelle.
 * @author Alexander Vos
 * @see RowValidator
 * @see CellValidatorFactory
 */
public class CellValidator<T> implements Validator<Cell<T>> {
    private static final Logger logger = Logger.getLogger(CellValidator.class);

    private final List<Condition> conditionList = new ArrayList<>();
    private final Collection<String> errorCauses = new ArrayList<>();

    private String name;
    private boolean enabled = true;

    public CellValidator() {
        name = toString();
    }

    public CellValidator(final String name) {
        this.name = name == null ? toString() : name;
    }

    public CellValidator(final String name, final boolean enabled) {
        this(name);
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    /**
     * Fügt eine weitere Bedingung zu diesem Validator hinzu.
     *
     * @param condition Bedingung.
     */
    public void addCondition(final Condition condition) {
        conditionList.add(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(final Cell<T> cell) {
        T value = cell.getValue();
        boolean valid = true;
        errorCauses.clear();
        for (final Condition condition : conditionList) {
            if (!condition.check(value)) {
                errorCauses.add(condition.getErrorCause());
                if (condition.isOptional()) {
                    logger.warn("optional cell condition failed: " + condition.getErrorCause());
                    // valid = false;
                } else {
                    logger.error("required cell condition failed (" + condition.getErrorCause() + ")");
                    valid = false;
                }
                // Automatische Korrektur
                if (condition.hasAutoCorrectors()) {
                    for (final AutoCorrector<T> autoCorrector : condition.getAutoCorrectorList()) {
                        if (autoCorrector.isEnabled()) {
                            if (autoCorrector.canCorrect(value)) {
                                final T correctedValue = autoCorrector.correct(value);
                                // nach der Korrektur erneut auf Gültigkeit prüfen
                                if (condition.check(correctedValue)) {
                                    valid = true; // Wert ist wieder gültig
                                    if (logger.isDebugEnabled()) {
                                        logger.debug("auto correction successful (" + value + " => " + correctedValue + ")");
                                    }
                                    value = correctedValue;
                                    break; // Korrektur erfolgreich, die folgenden Auto-Korrektoren überspringen
                                } else {
                                    logger.warn("auto correction failed, value is still invalid (" + value + " => " + correctedValue + ")");
                                }
                                value = correctedValue;
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Wert kann nicht korrigiert werden " + value);
                                }
                            }
                        } else {
                            // automatische Korrektur ist deaktiviert
                        }
                    }
                    // Zelle aktualisieren
                    cell.setValue(value);
                } else {
                    // keine Auto-Korrektoren
                }
                if (!valid) {
                    // vorzeitig abbrechen, wenn der Wert bereits als ungültig markiert wurde
                    break;
                }
            } else {
                // Wert gültig
            }
        }
        return valid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorCause() {
        return errorCauses.toString();
    }

    /**
     * Bedingung eines Zellenvalidators.
     */
    public abstract class Condition {
        private final boolean optional;
        private String errorCause = "condition failed";

        private List<AutoCorrector<T>> autoCorrectorList = null;

        /**
         * Erstellt eine Zellenbedingung.
         */
        protected Condition() {
            optional = false;
        }

        /**
         * Erstellt eine Zellenbedingung.
         *
         * @param optional <code>true</code> , wenn die Bedingung optional sein soll.
         */
        protected Condition(final boolean optional) {
            this.optional = optional;
        }

        public boolean hasAutoCorrectors() {
            return autoCorrectorList != null && !autoCorrectorList.isEmpty();
        }

        public List<AutoCorrector<T>> getAutoCorrectorList() {
            return autoCorrectorList;
        }

        public void setAutoCorrectorList(final List<AutoCorrector<T>> autoCorrectorList) {
            this.autoCorrectorList = autoCorrectorList;
        }

        public void addAutoCorrector(final AutoCorrector<T> autoCorrector) {
            if (autoCorrectorList == null) {
                autoCorrectorList = new ArrayList<>();
            }
            autoCorrectorList.add(autoCorrector);
        }

        /**
         * Führt die Prüfung dieser Bedingung durch.
         *
         * @param value Das zu prüfende Objekt.
         * @return <code>true</code>, wenn die Prüfung erfolgreich war (Objekt gültig); sonst <code>false</code>.
         */
        public abstract boolean check(T value);

        /**
         * Gibt an, ob diese Bedning optional ist.
         *
         * @return <code>true</code> , wenn die Bedingung optional ist; sonst <code>false</code>.
         */
        public boolean isOptional() {
            return optional;
        }

        /**
         * Gibt die Fehlerbeschreibung dieser Bedingung zurück.
         *
         * @return Fehlerbeschreibung.
         * @see #setErrorCause(String)
         */
        public String getErrorCause() {
            return errorCause;
        }

        /**
         * Setzt die Fehlerbeschreibung dieser Bedingung.
         *
         * @param cause Fehlerbeschreibung.
         * @see #getErrorCause()
         */
        protected void setErrorCause(final String cause) {
            errorCause = cause;
        }
    }

}
