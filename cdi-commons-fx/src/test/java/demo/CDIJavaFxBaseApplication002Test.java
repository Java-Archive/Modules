package demo;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rapidpm.demo.cdi.commons.format.CDISimpleDateFormatter;
import org.rapidpm.demo.cdi.commons.fx.JavaFXBaseTest;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;

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
        protected Class<? extends JavaFXBaseTest> getParentTestClass() {
            return CDIJavaFxBaseApplication002Test.class;
        }

        @Inject
        @CDISimpleDateFormatter(value = "date.yyyyMMdd")
        SimpleDateFormat sdf;

        @Inject @CDILogger
        Logger logger;


        @Override
        public void testImpl(Stage stage) {
            try {
                final FXMLLoader fxmlLoader = fxmlLoaderSingleton.getFXMLLoader(LoginPane.class);
                Parent root = (Parent) fxmlLoader.load();
                stage.setTitle("Login");
                stage.setScene(new Scene(root, 300, 275));
                //stage.show();
                final Scene scene = stage.getScene();

                //TestCode
                final TextField login = (TextField) scene.lookup("#loginField");
                login.setText("Hoppel");
                final PasswordField passwd = (PasswordField) scene.lookup("#passwordField");
                passwd.setText("LOGIN");

                final LoginController controller = fxmlLoader.getController();
                controller.handleSubmitButtonAction(new ActionEvent());

                final Text feedback = (Text) scene.lookup("#feedback");
                Assert.assertNotEquals("LOGIN logged in successfully", feedback.getText());
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
}
