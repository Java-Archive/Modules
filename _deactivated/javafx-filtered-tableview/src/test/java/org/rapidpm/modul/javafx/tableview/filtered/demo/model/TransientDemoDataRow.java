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

package org.rapidpm.modul.javafx.tableview.filtered.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.commons.cdi.registry.property.CDIPropertyRegistryService;
import org.rapidpm.commons.cdi.registry.property.PropertyRegistryService;
import org.rapidpm.modul.javafx.tableview.filtered.FilteredTableDataRow;
import org.rapidpm.modul.javafx.tableview.filtered.demo.DemoKeyMapper;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert Date: 30.08.13 Time: 07:28
 */
public class TransientDemoDataRow implements FilteredTableDataRow, Serializable {

    private @Inject @CDILogger Logger logger;
    @Inject @CDIPropertyRegistryService PropertyRegistryService propertyRegistryService;
    @Inject
    DemoKeyMapper keyMapper;

    private StringProperty vorname;
    private StringProperty nachname;
    private JavaBeanObjectProperty<Date> datumProperty;
    private Date datum;
    private SimpleDoubleProperty betrag;

    @PostConstruct
    public void init() {
        vorname = new SimpleStringProperty(this, map("vorname"));
        nachname = new SimpleStringProperty(this, map("nachname"));
//        datum = new SimpleStringProperty(this, map("datum"));
        betrag = new SimpleDoubleProperty(this, map("betrag"));
        try {
            datumProperty = JavaBeanObjectPropertyBuilder.create().bean(this).name(map("datum")).build();
        } catch (NoSuchMethodException e) {
            logger.error(e);
        }
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

    public Date getDatum() {
        return datum;
    }

    public JavaBeanObjectProperty<Date> datumProperty() {
        return datumProperty;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Double getBetrag() {
        return betrag.get();
    }

    public DoubleProperty betragProperty() {
        return betrag;
    }

    public void setBetrag(Double betrag) {
        this.betrag.set(betrag);
    }

    @Override public String convertToCSV() {
        return "" + getVorname() + ";" + getNachname() + ";" + getNachname() + ";" + getBetrag() + "";
    }
}
