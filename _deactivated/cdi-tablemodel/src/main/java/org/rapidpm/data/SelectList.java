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

package org.rapidpm.data;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Zur Erstellung einer Liste die den selektierten Index speichern kann und zur Auswahl in Tabellen genutzt werden kann.
 *
 * @param <T> Typisierung des Tabelleninhaltes.
 * @author Christian Ernst
 */
public class SelectList<T> {
    private final ImmutableList<T> list;
    private int selectedIndex = -1;

    /**
     * Konstruktor mit Übergabe des Listeninhalts.
     *
     * @param list Listeninhalt
     */
    public SelectList(final ImmutableList<T> list) {
        this.list = list;
    }

    /**
     * Prüft ob ein gültiger Index gewählt wurde um zu bestimmen ob etwas ausgewählt wurde.
     *
     * @return {@code true} wenn eine Auswahl getroffen wurde
     * {@code false} wenn keine Auswahl getroffen wurde
     */
    public boolean isSelected() {
        return selectedIndex >= 0;
    }

    /**
     * Gibt das selektierte Objekt zurück.
     *
     * @return selektiertes Objekt
     */
    public T getSelected() {
        return isSelected() ? list.get(selectedIndex) : null;
    }

    /**
     * Setzt das Objekt, das selektiert werden soll.
     *
     * @param selected Zu selektierendes Objekt.
     */
    public void setSelected(final T selected) {
        selectedIndex = list.indexOf(selected);
    }

    /**
     * Gibt den Index des Selektierten Objekts zurück.
     *
     * @return Index des Selektierten Objekts.
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Setzt den Index des zu selektierenden Objekts.
     *
     * @param selectedIndex Index des zu selektierenden Objekts
     */
    public void setSelectedIndex(final int selectedIndex) {
        if (selectedIndex < 0 || selectedIndex >= list.size()) {
            this.selectedIndex = -1;
        } else {
            this.selectedIndex = selectedIndex;
        }
    }

    /**
     * Gibt die Liste der selektierbaren Objekte zurück.
     *
     * @return Liste der selektierbaren Objekte
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Gibt die Länge der Liste zurück.
     *
     * @return Länge der Liste
     */
    public int size() {
        return list.size();
    }

    /**
     * Gibt das Objekt anhand eines bestimmten Indexes zurück.
     *
     * @param index Index
     * @return Objekt anhand eines bestimmten Indexes.
     */
    public T get(final int index) {
        return list.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (list.isEmpty())
            return "[]";

        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < list.size(); i++) {
            final T e = list.get(i);
            if (i == selectedIndex) {
                sb.append('>');
                sb.append(e == this ? "(this List)" : e);
                sb.append('<');
            } else {
                sb.append(e == this ? "(this List)" : e);
            }
            if (i == list.size() - 1) {
                return sb.append(']').toString();
            }
            sb.append(", ");
        }
        return "[]";
    }
}
