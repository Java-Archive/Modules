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
import java.util.Collections;
import java.util.List;

/**
 * Tabellenfehlerkontainer.
 *
 * @author Christian Ernst
 * @author Alexander Vos
 */
public class TableError {

    /**
     * Fehlerobjekt.
     */
    public static class ErrorObject {
        /**
         * Fehlertext
         */
        protected final String cause;

        /**
         * Konstruktor mit Fehlertext.
         *
         * @param cause Fehlertext
         */
        protected ErrorObject(final String cause) {
            this.cause = cause;
        }

        /**
         * Gibt den Fehlertext zurück.
         *
         * @return Fehlertext
         */
        public String getCause() {
            return cause;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "ErrorObject{" +
                    "cause='" + cause + '\'' +
                    '}';
        }
    }

    /**
     * Fehlerobjekt für Zeilen
     */
    public static class RowError extends ErrorObject {
        private final int rowIndex;

        /**
         * Konstruktor mit Fehlertext und Zeilennummer.
         *
         * @param rowIndex Zeilennummer
         * @param cause    Fehlertext
         */
        public RowError(final int rowIndex, final String cause) {
            super(cause);
            this.rowIndex = rowIndex;
        }

        /**
         * Gibt den Zeilenindex zurück.
         *
         * @return Zeilenindex
         */
        public int getRowIndex() {
            return rowIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "RowError{" +
                    ", rowIndex=" + rowIndex +
                    ", cause=" + cause +
                    '}';
        }
    }

    /**
     * Fehlerobjekt für Spalten
     */
    public static class CellError extends ErrorObject {
        private final int columnIndex;
        private final int rowIndex;

        /**
         * Konstruktor mit Zellenposition und Fehlertext.
         *
         * @param columnIndex Spaltennummer
         * @param rowIndex    Zeilennummer
         * @param cause       Fehlertext
         */
        public CellError(final int columnIndex, final int rowIndex, final String cause) {
            super(cause);
            this.columnIndex = columnIndex;
            this.rowIndex = rowIndex;
        }

        /**
         * Gibt den Spaltenindex zurück.
         *
         * @return Spaltenindex
         */
        public int getColumnIndex() {
            return columnIndex;
        }

        /**
         * Gibt den Zeilenindex zurück.
         *
         * @return Zeilenindex
         */
        public int getRowIndex() {
            return rowIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "CellError{" +
                    "columnIndex=" + columnIndex +
                    ", rowIndex=" + rowIndex +
                    ", cause=" + cause +
                    '}';
        }
    }

    private final List<ErrorObject> errorObjects = new ArrayList<>();

    /**
     * Prüft ob ein Fehler aufgetreten ist.
     *
     * @return <code>true</code>, wenn ein Fehler aufgetreten ist; sonst <code>false</code>
     */
    public boolean hasErrors() {
        return !errorObjects.isEmpty();
    }

    /**
     * Gibt die Anzahl der Fehler zurück.
     *
     * @return Anzahl der Fehler
     */
    public int getErrorCount() {
        return errorObjects.size();
    }

    /**
     * Fügt Fehlermeldungen zum Objekt hinzu.
     *
     * @param error Fehlermeldungen
     */
    public void addError(final ErrorObject error) {
        errorObjects.add(error);
    }

    /**
     * Gibt alle Fehlerobjekte zurück.
     *
     * @return Fehlerobjekte
     */
    public Collection<ErrorObject> getErrorObjects() {
        return Collections.unmodifiableCollection(errorObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TableError{" +
                "errorCount=" + getErrorCount() +
                ", errorObjects=" + errorObjects +
                '}';
    }
}
