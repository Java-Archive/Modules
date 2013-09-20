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

package org.rapidpm.demo.cdi.commons.fx.components;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.rapidpm.demo.cdi.commons.fx.CDIJavaFxBaseController;
import org.rapidpm.demo.cdi.commons.fx.FXMLLoaderSingleton;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;

/**
 * User: Sven Ruppert
 * Date: 30.08.13
 * Time: 07:03
 */
public abstract class CDIBaseAnchorPane<T, C extends CDIJavaFxBaseController> extends AnchorPane implements CDIBaseFxComponent<T> {

    public @Inject @CDILogger org.rapidpm.module.se.commons.logger.Logger logger;
    public @Inject FXMLLoaderSingleton fxmlLoaderSingleton;

    public CDIBaseAnchorPane() {

    }


    @PostConstruct
    public void init() {
        final Class<T> paneClass = getPaneClass();
        final FXMLLoader fxmlLoader = fxmlLoaderSingleton.getFXMLLoader(paneClass);
        fxmlLoader.setRoot(this);
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("controller " + getController());
            }
            fxmlLoader.setController(getController());
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public abstract C getController();

    public abstract void setController(C controller);
}
