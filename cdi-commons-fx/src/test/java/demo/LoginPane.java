package demo;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import org.rapidpm.demo.cdi.commons.fx.FXMLLoaderSingleton;

/**
 * User: Sven Ruppert
 * Date: 09.07.13
 * Time: 14:44
 */
public class LoginPane extends GridPane {

    private @Inject FXMLLoaderSingleton fxmlLoaderSingleton;
    private @Inject LoginController controller;

    public LoginPane() {

    }

    @PostConstruct
    public void init(){
        final FXMLLoader fxmlLoader = fxmlLoaderSingleton.getFXMLLoader(LoginPane.class);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.setController(controller);
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public LoginController getController() {
        return controller;
    }

}
