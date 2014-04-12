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

package org.rapidpm.modul.javafx.chart.pie.drilldown;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.commons.cdi.registry.property.CDIPropertyRegistryService;
import org.rapidpm.commons.cdi.registry.property.PropertyRegistryService;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 02.09.13
 * Time: 15:12
 */
public abstract class DrillDownPieChart<T> extends PieChart {

    private @Inject @CDILogger Logger logger;

//    private @Inject @CDIPropertyRegistryService PropertyRegistryService registryService;
    private @Inject DrillDownModulKeyMapper keyMapper;

    private ObservableList<PieChart.Data> rootDataList;
    private ObservableList<T> dataValueList;

    private DrillDownPieChartMapAggregator<T> rootAggregator;
    private DrillDownPieChartMapAggregator<T> activeAggregator;


    protected DrillDownPieChart() {
        this.rootDataList = FXCollections.observableArrayList();
    }

    public void init() {
        rootDataList.clear();
        this.setTitle("");

        activeAggregator = rootAggregator;
        setVisible(true);

        aggregateRootDataList(dataValueList);
        aggregateNextLevel(dataValueList);
    }


    private void aggregateRootDataList(ObservableList<T> dataValueList) {
        final Map<String, List<T>> aggregatedMap = activeAggregator.aggregate(dataValueList);
        for (final Map.Entry<String, List<T>> entry : aggregatedMap.entrySet()) {
            final String key = entry.getKey();
            final ObservableList<T> valuesForNextLevel = FXCollections.observableArrayList(entry.getValue());
            rootDataList.add(new Data(key, activeAggregator.aggregateValue(valuesForNextLevel)));
        }
    }

    private void aggregateNextLevel(final ObservableList<T> dataValueList) {
        ObservableList<Data> levelDataList = FXCollections.observableArrayList();
        setData(levelDataList);

        if (activeAggregator == null) {
            init();
        } else {
            final Map<String, List<T>> aggregatedMap = activeAggregator.aggregate(dataValueList);
            setTitle("..." + activeAggregator.getLevelName() + "...");
            for (final Map.Entry<String, List<T>> entry : aggregatedMap.entrySet()) {

                final String key = entry.getKey();

                final ObservableList<T> valuesForNextLevel = FXCollections.observableArrayList(entry.getValue());

                final Data data = new Data(key, activeAggregator.aggregateValue(valuesForNextLevel));
                levelDataList.add(data);

                final Node dataNode = data.getNode();
                dataNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent mouseEvent) {
                        final MouseButton mouseButton = mouseEvent.getButton();
                        if (mouseButton.equals(MouseButton.SECONDARY)) {
                            //level back
                            activeAggregator = activeAggregator.getParentLevelAggregator();
                            aggregateNextLevel(dataValueList);
                        } else {
                            activeAggregator = activeAggregator.getNextLevelAggregator();
                            aggregateNextLevel(valuesForNextLevel);
                        }
                    }
                });


            }
        }
    }


    public void setRootAggregator(DrillDownPieChartMapAggregator<T> rootAggregator) {
        this.rootAggregator = rootAggregator;
    }

    public void setDataValueList(ObservableList<T> dataValueList) {
        this.dataValueList = dataValueList;
    }
}
