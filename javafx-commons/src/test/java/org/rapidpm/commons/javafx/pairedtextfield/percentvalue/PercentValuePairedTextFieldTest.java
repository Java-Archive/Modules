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

package org.rapidpm.commons.javafx.pairedtextfield.percentvalue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.commons.javafx.textfield.pairedtextfield.percentvalue.PercentValuePairedTextField;

/**
 * User: Sven Ruppert
 * Date: 08.10.13
 * Time: 14:47
 */
public class PercentValuePairedTextFieldTest {

    public static class MainWindow extends Application {

        @Override public void start(Stage stage) throws Exception {
            AnchorPane root = new AnchorPane();

            final PercentValuePairedTextField e = new PercentValuePairedTextField();
            e.setBaseValue(100.0);

            root.getChildren().add(e);
            stage.setScene(new Scene(root, 300, 250));
            stage.show();
        }
    }

    @Test  @Ignore
    public void testPairedTextField001() throws Exception {
        Application.launch(MainWindow.class);
    }
}
