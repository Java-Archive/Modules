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

package org.rapidpm.demo.javafx.tableview.filtered.demo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.rapidpm.demo.cdi.commons.fx.CDIJavaFxBaseController;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.CDIPropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.javafx.tableview.control.cell.callback.EditingAutoCompleteStringCellFactory;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableView;
import org.rapidpm.demo.javafx.tableview.filtered.contextmenue.FilteredTableContextMenu;
import org.rapidpm.demo.javafx.tableview.filtered.demo.controll.cell.BetragComboBoxCell;
import org.rapidpm.demo.javafx.tableview.filtered.demo.model.TableFilter;
import org.rapidpm.demo.javafx.tableview.filtered.demo.model.TransientDemoDataRow;
import org.rapidpm.demo.javafx.tableview.filtered.demo.model.TransientDemoRowComparator;
import org.rapidpm.demo.javafx.tableview.filtered.operators.IFilterOperator;
import org.rapidpm.demo.javafx.tableview.filtered.tablecolumn.ColumnFilterEvent;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert Date: 30.08.13 Time: 07:18
 */
public class FilteredTableViewDemoPaneController implements CDIJavaFxBaseController {

    private @Inject @CDILogger Logger logger;
    private @Inject TableFilter tableFilter;
    private @Inject FilteredTableContextMenu contextMenu;

    @Inject @CDIPropertyRegistryService PropertyRegistryService propertyRegistryService;
    @Inject DemoKeyMapper keyMapper;
    @Inject Instance<TransientDemoRowComparator> comparatorInstance;

    @FXML public FilteredTableView<TransientDemoDataRow> tableView;
    @FXML private ChoiceBox zoomChoiceBox;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        contextMenu.setFilteredTableView(tableView);
        contextMenu.init();
        tableView.setContextMenu(contextMenu);

        tableFilter.setFilteredTableView(tableView);
        tableView.addEventHandler(ColumnFilterEvent.FILTER_CHANGED_EVENT, new EventHandler<ColumnFilterEvent>() {
            @Override
            public void handle(ColumnFilterEvent t) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Filtered column count: " + tableView.getFilteredColumns().size());
                    logger.debug("Filtering changed on column: " + t.sourceColumn().getText());
                    logger.debug("Current filters on column " + t.sourceColumn().getText() + " are:");
                    final List<IFilterOperator> filters = t.sourceColumn().getFilters();
                    for (IFilterOperator filter : filters) {
                        logger.debug("  Type=" + filter.getType() + ", Value=" + filter.getValue());
                    }
                }
                //load fresh Data
                tableView.resetTableViewDataFromBackup();
                tableFilter.applyFilters();
                sortTableView();
            }
        });

        initTable();

        zoomChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                final String zooStyle = "-fx-font-size: " + zoomChoiceBox.getItems().get(number2.intValue());
                logger.debug("zooStyle = " + zooStyle);
                tableView.setStyle(zooStyle);

                final ObservableList<TableColumn<TransientDemoDataRow, ?>> columns = tableView.getVisibleLeafColumns();
                for (final TableColumn<TransientDemoDataRow, ?> column : columns) {
                    column.setStyle(zooStyle);
                }
            }
        });

        setI18n();
    }

    @Inject Instance<BetragComboBoxCell> betragComboBoxCellInstance;
    @Inject BeanManager beanManager;

    private void initTable() {
        final BetragComboBoxCell cell = betragComboBoxCellInstance.get();
        cell.associateWithCol(tableView, "betrag");

        final ObservableList<TableColumn<TransientDemoDataRow, ?>> columns = tableView.getColumns();
        for (final TableColumn<TransientDemoDataRow, ?> column : columns) {
            final Callback cellFactory = column.getCellFactory();
            final AnnotatedType annotationType = beanManager.createAnnotatedType(cellFactory.getClass()); ///per getter from instance
            final InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotationType);
            final CreationalContext creationalContext = beanManager.createCreationalContext(null);
            injectionTarget.inject(cellFactory, creationalContext);
            injectionTarget.postConstruct(cellFactory);
        }
    }


    private void setI18n() {
        final ObservableList<TableColumn<TransientDemoDataRow, ?>> col = tableView.getColumns();
        for (final TableColumn filteredColumn : col) {
            final String text = filteredColumn.getText();
            final String map = map(text);
            if (logger.isDebugEnabled()) {
                logger.debug("text -> " + text);
                logger.debug("map -> " + map);
            }
            filteredColumn.setText(map);
        }
    }

    private String map(final String key) {
        return propertyRegistryService.getRessourceForKey(keyMapper.map(key));
    }

    private void sortTableView() {
        final TransientDemoRowComparator rowComparator = comparatorInstance.get();
        final ObservableList<TransientDemoDataRow> items = tableView.getItems();
        FXCollections.sort(items, rowComparator);
    }
}
