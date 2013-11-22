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

import org.rapidpm.commons.cdi.fx.components.CDIBaseAnchorPane;

/**
 * User: Sven Ruppert
 * Date: 02.09.13
 * Time: 16:49
 */
public class DrillDownPieChartPane extends CDIBaseAnchorPane<DrillDownPieChartPane, DrillDownPieChartPaneController> {

    @Override public Class<DrillDownPieChartPane> getPaneClass() {
        return DrillDownPieChartPane.class;
    }

//    @Inject DrillDownPieChartPaneController controller;
//
//    public DrillDownPieChartPaneController getController() {
//        return controller;
//    }
//
//    public void setController(DrillDownPieChartPaneController controller) {
//        this.controller = controller;
//    }
}
