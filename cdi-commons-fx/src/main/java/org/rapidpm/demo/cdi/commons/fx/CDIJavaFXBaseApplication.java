package org.rapidpm.demo.cdi.commons.fx;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.util.AnnotationLiteral;

import javafx.application.Application;
import javafx.stage.Stage;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;

/**
 * User: Sven Ruppert
 * Date: 09.07.13
 * Time: 10:35
 */
public abstract class CDIJavaFXBaseApplication  extends Application {

    private static List<BeforeStartAction> afterCDIBootstrapActions = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        final CDIContainerSingleton cdi = CDIContainerSingleton.getInstance();
        // Make the application parameters injectable with a standard CDI annotation
        final AnnotationLiteral<CDIJavaFXBaseApp> annotationLiteral = new AnnotationLiteral<CDIJavaFXBaseApp>() {};
        final ApplicationParametersProvider applicationParametersProvider
                = cdi.getManagedInstance(annotationLiteral,ApplicationParametersProvider.class);
        applicationParametersProvider.setParameters(getParameters());
        for (final BeforeStartAction initAction : afterCDIBootstrapActions) {
            initAction.execute(primaryStage);
        }
        startImpl(primaryStage);
        cdi.event().select(Stage.class, new AnnotationLiteral<CDIStartupScene>() {}).fire(primaryStage);
    }

    public abstract void startImpl(Stage primaryStage) throws Exception;

    public static abstract class BeforeStartAction {
      public abstract void execute(Stage primaryStage);
    }
}
