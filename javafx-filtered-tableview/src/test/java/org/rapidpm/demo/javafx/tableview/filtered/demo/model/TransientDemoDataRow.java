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

package org.rapidpm.demo.javafx.tableview.filtered.demo.model;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.rapidpm.demo.cdi.commons.registry.property.CDIPropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.javafx.tableview.filtered.demo.DemoKeyMapper;

/**
 * User: Sven Ruppert
 * Date: 30.08.13
 * Time: 07:28
 */
public class TransientDemoDataRow implements Serializable {

    @Inject @CDIPropertyRegistryService PropertyRegistryService propertyRegistryService;
    @Inject DemoKeyMapper keyMapper;

    private StringProperty vorname;
    private StringProperty nachname;
    private StringProperty datum;
    private StringProperty betrag;

    @PostConstruct
    public void init() {
        vorname = new SimpleStringProperty(this, map("vorname"));
        nachname = new SimpleStringProperty(this, map("nachname"));
        datum = new SimpleStringProperty(this, map("datum"));
        betrag = new SimpleStringProperty(this, map("betrag"));
    }

    private String map(final String key) {
        return propertyRegistryService.getRessourceForKey(keyMapper.map(key));
    }

    public String getVorname() {
        return vorname.get();
    }

    public StringProperty vornameProperty() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname.set(vorname);
    }

    public String getNachname() {
        return nachname.get();
    }

    public StringProperty nachnameProperty() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname.set(nachname);
    }

    public String getDatum() {
        return datum.get();
    }

    public StringProperty datumProperty() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum.set(datum);
    }

    public String getBetrag() {
        return betrag.get();
    }

    public StringProperty betragProperty() {
        return betrag;
    }

    public void setBetrag(String betrag) {
        this.betrag.set(betrag);
    }
}
