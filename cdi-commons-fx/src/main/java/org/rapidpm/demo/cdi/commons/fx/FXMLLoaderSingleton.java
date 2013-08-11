package org.rapidpm.demo.cdi.commons.fx;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;
import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;

/**
 * User: Sven Ruppert
 * Date: 08.07.13
 * Time: 16:38
 */
@Singleton
public class FXMLLoaderSingleton {

    private @Inject @CDILogger Logger logger;
    private @Inject Instance<CDIJavaFxBaseController> instance;

    private final ClassLoader cachingClassLoader = new FXClassLoader(FXMLLoader.getDefaultClassLoader());
    private final Map<Class, FXMLLoader> class2LoaderMap = new HashMap<Class, FXMLLoader>();

    public FXMLLoader getFXMLLoader(Class clazz) {
        final Map<Class, FXMLLoader> loaderMap = class2LoaderMap;
        final String name = clazz.getName();
        if (loaderMap.containsKey(clazz)) {
            if (logger.isDebugEnabled()) {
                logger.debug("fx loader fuer diese klasse schon in der map " + name);
            }
        } else {
            final URL resource = clazz.getResource(clazz.getSimpleName() + ".fxml");
            FXMLLoader loader = new FXMLLoader(resource);
            loader.setClassLoader(cachingClassLoader);
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    final Class<CDIJavaFxBaseController> p = (Class<CDIJavaFxBaseController>) param;
                    return instance.select(p).get();
                }
            });
            loaderMap.put(clazz, loader);
        }
        return loaderMap.get(clazz);
    }

    private FXMLLoaderSingleton() {
    }
}
