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

package org.rapidpm.modul.javafx.tableview.filtered.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.rapidpm.commons.cdi.format.CDISimpleDateFormatter;
import org.rapidpm.modul.javafx.tableview.filtered.demo.model.TransientDemoDataRow;

/**
 * User: Sven Ruppert Date: 30.08.13 Time: 09:36
 */
public class DemoDataBuilder {

    private @Inject @CDISimpleDateFormatter SimpleDateFormat sdf;
    private @Inject Instance<TransientDemoDataRow> rowInstance;

    public ObservableList<TransientDemoDataRow> create() {
        final ObservableList<TransientDemoDataRow> observableList = FXCollections.observableArrayList();

        try {
            createRow(observableList, "Holger", "Mueller", "2013.10.02", 120.1);
            createRow(observableList, "Holger", "Mueller", "2013.10.03", 121.1);

            createRow(observableList, "Holger", "Hoppel", "2013.10.02", 120.1);
            createRow(observableList, "Holger", "Hoppel", "2013.10.01", 121.1);
            createRow(observableList, "Holger", "Hoppel", "2013.10.01", 122.1);
            createRow(observableList, "Holger", "Hoppel", "2013.10.01", 123.1);

            createRow(observableList, "Willi", "Hampel", "2013.10.04", 10.1);
            createRow(observableList, "Willi", "Hampel", "2013.10.05", 10.1);

            createRow(observableList, "Willi", "Pampel", "2013.10.04", 11.1);
            createRow(observableList, "Willi", "Pampel", "2013.10.05", 11.1);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return observableList;
    }

    private void createRow(ObservableList<TransientDemoDataRow> observableList, String vorname, String nachname, String datum, Double betrag) throws ParseException {
        final TransientDemoDataRow dataRow = rowInstance.get();
        dataRow.setVorname(vorname);
        dataRow.setNachname(nachname);
        dataRow.setDatum(sdf.parse(datum));
        dataRow.setBetrag(betrag);
        observableList.add(dataRow);
    }


}
