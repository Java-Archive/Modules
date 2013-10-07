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

package demo;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Assert;
import org.rapidpm.demo.cdi.commons.format.CDISimpleDateFormatter;
import org.rapidpm.demo.cdi.commons.fx.JavaFXBaseTest;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 24.07.13
 * Time: 14:26
 */

public class CDIJavaFxBaseApplication002Test extends JavaFXBaseTest {

    @Override
    protected Class<? extends JavaFXBaseTest> getTestClass() {
        return CDIJavaFxBaseApplication002Test.class;
    }

    public static class TestImpl extends JavaFXBaseTest.JavaFXBaseTestImpl {

        @Override
        public boolean isExitAfterTest() {
            return true;
        }

        @Override
        protected Class<? extends JavaFXBaseTest> getParentTestClass() {
            return CDIJavaFxBaseApplication002Test.class;
        }

        @Inject
        @CDISimpleDateFormatter(value = "date.yyyyMMdd")
        SimpleDateFormat sdf;

        @Inject
        @CDILogger
        Logger logger;
        @Inject
        LoginPane root;

        @Override
        public void testImpl(Stage stage) {
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 300, 275));
            //stage.show();
            final Scene scene = stage.getScene();

            //TestCode
            final TextField login = (TextField) scene.lookup("#loginField");
            login.setText("Hoppel");
            final PasswordField passwd = (PasswordField) scene.lookup("#passwordField");
            passwd.setText("LOGIN");

            final LoginPaneController controller = root.getController();
            controller.handleSubmitButtonAction(new ActionEvent());

            final Text feedback = (Text) scene.lookup("#feedback");
            Assert.assertNotEquals("LOGIN logged in successfully", feedback.getText());
        }
    }
}
