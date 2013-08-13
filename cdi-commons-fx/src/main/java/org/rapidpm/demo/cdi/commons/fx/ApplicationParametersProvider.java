package org.rapidpm.demo.cdi.commons.fx;


import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import javafx.application.Application;

/**
 * User: Sven Ruppert
 * Date: 09.07.13
 * Time: 11:55
 */

@Singleton @CDIJavaFXBaseApp
public class ApplicationParametersProvider {
    private Application.Parameters parameters;

    void setParameters(Application.Parameters p) {
        this.parameters = p;
    }

    public @Produces @CDIJavaFXBaseApp
    Application.Parameters getParameters() {
        return this.parameters;
    }
}
