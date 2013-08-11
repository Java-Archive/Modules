package demo;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.application.Application.Parameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.rapidpm.demo.cdi.commons.fx.CDIJavaFXBaseApp;
import org.rapidpm.demo.cdi.commons.fx.CDIJavaFxBaseController;

// Simple application controller that uses injected fields 
// to delegate login process and to get default values from the command line using: --user=SomeUser
public class LoginController implements CDIJavaFxBaseController {
    // Standard FXML injected fields
	@FXML TextField loginField;
	@FXML PasswordField passwordField;
	@FXML Text feedback;
	
	// CDI Injected field
	@Inject LoginService loginService;
	
    // Default application parameters
	@Inject @CDIJavaFXBaseApp
    Parameters applicationParameters;
	
	@FXML protected void handleSubmitButtonAction(ActionEvent event) {
		feedback.setText(loginService.login(loginField.getText(), passwordField.getText()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginField.setText(applicationParameters.getNamed().get("user"));
	}
}
