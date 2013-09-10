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

import java.net.URL;
import java.util.ResourceBundle;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import gui.org.rapidpm.demo.javafx.searchbox.demo.model.TransientDemoDataRow;
import gui.org.rapidpm.demo.javafx.searchbox.demo.model.TransientDemoRowComparator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.rapidpm.demo.cdi.commons.fx.CDIJavaFxBaseController;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.CDIPropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.javafx.searchbox.searchbox.SearchBox;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 30.08.13
 * Time: 07:18
 */
public class SearchBoxDemoPaneController implements CDIJavaFxBaseController {

    private @Inject @CDILogger Logger logger;

    @Inject @CDIPropertyRegistryService PropertyRegistryService propertyRegistryService;
    @Inject DemoKeyMapper keyMapper;
    @Inject Instance<TransientDemoRowComparator> comparatorInstance;

    @FXML public TableView<TransientDemoDataRow> tableView;
    @FXML public SearchBox SearchBox;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        setI18n();

        //init SearchBox
        refreshIndex();

    }

    private void refreshIndex() {
        final ObservableList<TransientDemoDataRow> items = tableView.getItems();
        SearchBox.refreshIndex(items);
    }

    private void initTable() {

    }

    private void setI18n() {
        final ObservableList<TableColumn<TransientDemoDataRow, ?>> col = tableView.getColumns();
        for (final TableColumn column : col) {
            final String text = column.getText();
            final String map = map(text);
            if (logger.isDebugEnabled()) {
                logger.debug("text -> " + text);
                logger.debug("map -> " + map);
            }
            column.setText(map);
        }
    }

    private String map(final String key) {
        return propertyRegistryService.getRessourceForKey(keyMapper.map(key));
    }

}
