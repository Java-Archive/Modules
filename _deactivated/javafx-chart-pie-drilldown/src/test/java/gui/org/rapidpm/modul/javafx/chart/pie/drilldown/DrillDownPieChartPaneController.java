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

package gui.org.rapidpm.modul.javafx.chart.pie.drilldown;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import gui.org.rapidpm.modul.javafx.chart.pie.drilldown.model.TransientDemoDataRow;
import javafx.fxml.FXML;
import org.rapidpm.commons.cdi.fx.CDIJavaFxBaseController;
import org.rapidpm.modul.javafx.chart.pie.drilldown.DrillDownPieChartMapAggregator;

/**
 * User: Sven Ruppert
 * Date: 02.09.13
 * Time: 16:49
 */
public class DrillDownPieChartPaneController implements CDIJavaFxBaseController {

    @FXML DemoDrillDownPieChart piechart;


    @Inject Instance<VornameAggregator> vornameAggregatorInstance;
    @Inject Instance<NachnameAggregator> nachnameAggregatorInstance;
    @Inject Instance<DatumAggregator> datumAggregatorInstance;
    @Inject Instance<BetragAggregator> betragAggregatorInstance;


    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        //setze Aggregatoren
        final VornameAggregator vornameAggregator = vornameAggregatorInstance.get();

        final NachnameAggregator nachnameAggregator = nachnameAggregatorInstance.get();
        vornameAggregator.setNextLevelAggregator(nachnameAggregator);

        final DatumAggregator datumAggregator = datumAggregatorInstance.get();
        nachnameAggregator.setNextLevelAggregator(datumAggregator);

        final BetragAggregator betragAggregator = betragAggregatorInstance.get();
        datumAggregator.setNextLevelAggregator(betragAggregator);

        piechart.setRootAggregator(vornameAggregator);

    }


    public void init() {
        piechart.init();
    }


    public static class BetragAggregator extends DrillDownPieChartMapAggregator<TransientDemoDataRow> {
        @Override public double aggregateValue(List<TransientDemoDataRow> aggregatedValues) {
            double betrag = 0;
            for (final TransientDemoDataRow aggregatedValue : aggregatedValues) {
                betrag = betrag + aggregatedValue.getBetrag();
            }

            return betrag;
        }

        @Override public String getLevelName() {
            return "Betrag";
        }

        @Override public String getKeyElement(TransientDemoDataRow transientDemoDataRow) {
            return transientDemoDataRow.getBetrag() + " € Beträge";
        }
    }

    public static class DatumAggregator extends DrillDownPieChartMapAggregator<TransientDemoDataRow> {
        @Override public double aggregateValue(List<TransientDemoDataRow> aggregatedValues) {
            return aggregatedValues.size();
        }

        @Override public String getLevelName() {
            return "Datum";
        }

        @Override public String getKeyElement(TransientDemoDataRow transientDemoDataRow) {
            return transientDemoDataRow.getDatum();
        }
    }

    public static class VornameAggregator extends DrillDownPieChartMapAggregator<TransientDemoDataRow> {
        @Override public double aggregateValue(List<TransientDemoDataRow> aggregatedValues) {
            return aggregatedValues.size();
        }

        @Override public String getLevelName() {
            return "Vorname";
        }

        @Override public String getKeyElement(TransientDemoDataRow transientDemoDataRow) {
            return transientDemoDataRow.getVorname();
        }
    }


    public static class NachnameAggregator extends DrillDownPieChartMapAggregator<TransientDemoDataRow> {
        @Override public double aggregateValue(List<TransientDemoDataRow> aggregatedValues) {
            return aggregatedValues.size();
        }

        @Override public String getLevelName() {
            return "Nachname";
        }

        @Override public String getKeyElement(TransientDemoDataRow transientDemoDataRow) {
            return transientDemoDataRow.getNachname();
        }
    }


    public DemoDrillDownPieChart getPiechart() {
        return piechart;
    }

    public void setPiechart(DemoDrillDownPieChart piechart) {
        this.piechart = piechart;
    }
}
