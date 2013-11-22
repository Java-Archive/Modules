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

package gui.org.rapidpm.modul.javafx.dialog;

import javax.inject.Inject;

import gui.org.rapidpm.modul.javafx.dialog.demo.DialogDemoPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.rapidpm.commons.cdi.fx.JavaFXBaseTest;

/**
 * User: Sven Ruppert Date: 10.09.13 Time: 14:34
 */
public class DialogTest extends JavaFXBaseTest {


    @Override protected Class<? extends JavaFXBaseTest> getTestClass() {
        return DialogTest.class;
    }


    public static class TestImpl extends JavaFXBaseTest.JavaFXBaseTestImpl {

        @Inject
        DialogDemoPane root;

        @Override public boolean isExitAfterTest() {
            return false;
        }

        @Override protected Class<? extends JavaFXBaseTest> getParentTestClass() {
            return DialogTest.class;
        }

        @Override public void testImpl(Stage stage) {
            stage.setTitle("DialogTest");  //i18n
            stage.setScene(new Scene(root, 200, 300));
        }
    }
}
