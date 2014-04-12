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

package org.rapidpm.modul.javafx.tableview.filtered;

import javax.inject.Inject;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.rapidpm.commons.cdi.fx.JavaFXBaseTest;
import org.rapidpm.modul.javafx.tableview.filtered.demo.DemoDataBuilder;
import org.rapidpm.modul.javafx.tableview.filtered.demo.FilteredTableViewDemoPane;
import org.rapidpm.modul.javafx.tableview.filtered.demo.FilteredTableViewDemoPaneController;

/**
 * User: Sven Ruppert Date: 30.08.13 Time: 06:56
 */
public class FilteredTableViewTest extends JavaFXBaseTest {


    public static class TestImpl extends JavaFXBaseTest.JavaFXBaseTestImpl {


        @Inject
        FilteredTableViewDemoPane root;
        @Inject DemoDataBuilder dataBuilder;

        @Override public boolean isExitAfterTest() {
            return false;
        }

        @Override public void testImpl(Stage stage) {
            stage.setTitle("FilteredTableViewTest");  //i18n
            stage.setScene(new Scene(root, 1024, 786));

            final FilteredTableViewDemoPaneController controller = root.getController();
            controller.tableView.setTableViewData(dataBuilder.create());


        }
    }
}
