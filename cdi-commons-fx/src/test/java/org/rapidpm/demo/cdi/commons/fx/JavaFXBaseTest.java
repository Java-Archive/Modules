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
