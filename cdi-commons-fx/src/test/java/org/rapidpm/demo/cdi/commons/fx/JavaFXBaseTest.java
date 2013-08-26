package org.rapidpm.demo.cdi.commons.fx;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 24.07.13
 * Time: 11:37
 */
public abstract class JavaFXBaseTest {


    @Before
    public void beforeTest() {
//        JavaFXTestSingleton.getInstance().getSemaphore().acquireUninterruptibly();
    }

    @Test @Ignore
    public void testGo() {
        JavaFXTestSingleton.getInstance().setClazz(getTestClass());
        Application.launch(JavaFXTestApplication.class, "Go Test Go");
    }

    protected abstract Class<? extends JavaFXBaseTest> getTestClass();

    @After
    public void afterTest() {
//        JavaFXTestSingleton.getInstance().getSemaphore().release();
    }


    public static abstract class JavaFXBaseTestImpl {

        public abstract boolean isExitAfterTest();

        protected abstract Class<? extends JavaFXBaseTest> getParentTestClass();

        @Inject
        @CDILogger
        Logger logger;

        @Inject
        public FXMLLoaderSingleton fxmlLoaderSingleton;

        public void launchJavaFXApplication(@Observes @CDIStartupScene Stage stage) {
            final String simpleName = JavaFXTestSingleton.getInstance().getClazz().getSimpleName();
            logger.debug("JavaFXTestSingleton.simpleName = " + simpleName);
            final String testClassName = getParentTestClass().getSimpleName();
            if (simpleName.equals(testClassName)) {
                testImpl(stage);
                if (isExitAfterTest()) {
                    stage.close();
                    Platform.exit();
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("isExitAfterTest -> " + isExitAfterTest());
                    }
                    stage.show();
                }
            } else {
                logger.debug("JavaFXTestSingleton.simpleName (nicht aktiv)= " + testClassName);
            }
        }

        public abstract void testImpl(final Stage stage);
    }
}
