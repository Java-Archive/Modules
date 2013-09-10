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

package gui.org.rapidpm.demo.javafx.searchbox.demo;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import gui.org.rapidpm.demo.javafx.searchbox.demo.model.TransientDemoDataRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * User: Sven Ruppert
 * Date: 30.08.13
 * Time: 09:36
 */
public class DemoDataBuilder {

    private @Inject Instance<TransientDemoDataRow> rowInstance;

    public ObservableList<TransientDemoDataRow> create() {
        final ObservableList<TransientDemoDataRow> observableList = FXCollections.observableArrayList();

        createRow(observableList, 1L, "Holger", "Mueller", "2013.10.02", 120.0);
        createRow(observableList, 2L, "Holger", "Mueller", "2013.10.03", 121.0);

        createRow(observableList, 3L, "Holger", "Hoppel", "2013.10.02", 120.0);
        createRow(observableList, 4L, "Holger", "Hoppel", "2013.10.01", 121.0);
        createRow(observableList, 5L, "Holger", "Hoppel", "2013.10.01", 122.0);
        createRow(observableList, 6L, "Holger", "Hoppel", "2013.10.01", 123.0);

        createRow(observableList, 7L, "Willi", "Hampel", "2013.10.04", 10.0);
        createRow(observableList, 8L, "Willi", "Hampel", "2013.10.05", 10.0);

        createRow(observableList, 9L, "Willi", "Pampel", "2013.10.04", 11.0);
        createRow(observableList, 10L, "Willi", "Pampel", "2013.10.05", 11.0);

        return observableList;
    }

    private void createRow(ObservableList<TransientDemoDataRow> observableList, Long id, String vorname, String nachname, String datum, Double betrag) {
        final TransientDemoDataRow dataRow = rowInstance.get();
        dataRow.setID(id);
        dataRow.setVorname(vorname);
        dataRow.setNachname(nachname);
        dataRow.setDatum(datum);
        dataRow.setBetrag(betrag);
        observableList.add(dataRow);
    }

}
