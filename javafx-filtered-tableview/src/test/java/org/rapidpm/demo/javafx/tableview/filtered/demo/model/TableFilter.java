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

import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.registry.property.CDIPropertyRegistryService;
import org.rapidpm.demo.cdi.commons.registry.property.PropertyRegistryService;
import org.rapidpm.demo.javafx.tableview.filtered.FilteredTableView;
import org.rapidpm.demo.javafx.tableview.filtered.demo.DemoKeyMapper;
import org.rapidpm.demo.javafx.tableview.filtered.filter.StringFilter;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 30.08.13
 * Time: 09:04
 */
public class TableFilter {
    private @Inject @CDILogger Logger logger;
    private @Inject @CDIPropertyRegistryService PropertyRegistryService registryService;
    private @Inject DemoKeyMapper keyMapper;

    private FilteredTableView<TransientDemoDataRow> filteredTableView;

    private String map(final String key) {
        return registryService.getRessourceForKey(keyMapper.map(key));
    }

    public void applyFilters() {
        filterOnVorname();
        filterOnNachName();
        filterOnDatum();
        filterOnBetrag();
    }

    private void filterOnBetrag() {
        if (logger.isDebugEnabled()) {
            logger.debug("filterOnBetrag");
        }
        final StringFilter<TransientDemoDataRow> it = new StringFilter<TransientDemoDataRow>(filteredTableView) {
            @Override
            public String getValue(TransientDemoDataRow rowElement) {
                return rowElement.getBetrag();
            }
        };
        it.filter(map("betrag"));
    }

    private void filterOnDatum() {
        if (logger.isDebugEnabled()) {
            logger.debug("filterOnDatum");
        }
        final StringFilter<TransientDemoDataRow> it = new StringFilter<TransientDemoDataRow>(filteredTableView) {
            @Override
            public String getValue(TransientDemoDataRow rowElement) {
                return rowElement.getDatum();
            }
        };
        it.filter(map("datum"));

    }

    private void filterOnNachName() {
        if (logger.isDebugEnabled()) {
            logger.debug("filterOnNachName");
        }
        final StringFilter<TransientDemoDataRow> it = new StringFilter<TransientDemoDataRow>(filteredTableView) {
            @Override
            public String getValue(TransientDemoDataRow rowElement) {
                return rowElement.getNachname();
            }
        };
        it.filter(map("nachname"));

    }

    private void filterOnVorname() {
        if (logger.isDebugEnabled()) {
            logger.debug("filterOnVorname");
        }
        final StringFilter<TransientDemoDataRow> it = new StringFilter<TransientDemoDataRow>(filteredTableView) {
            @Override
            public String getValue(TransientDemoDataRow rowElement) {
                return rowElement.getVorname();
            }
        };
        it.filter(map("vorname"));
    }

    public FilteredTableView<TransientDemoDataRow> getFilteredTableView() {
        return filteredTableView;
    }

    public void setFilteredTableView(FilteredTableView<TransientDemoDataRow> filteredTableView) {
        this.filteredTableView = filteredTableView;
    }
}
