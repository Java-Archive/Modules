package org.rapidpm.demo.cdi.commons.se;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;

import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 05.06.13
 * Time: 22:07
 *
 * A Singleton for the SE Applikation.
 *
 *
 */
public class CDIContainerSingleton {

    private final static CDIContainerSingleton ourInstance = new CDIContainerSingleton();
    private final WeldContainer weldContainer;
    private final Logger logger;

    public static CDIContainerSingleton getInstance() {
        return ourInstance;
    }

    private CDIContainerSingleton() {
        weldContainer = new Weld().initialize();
        logger = weldContainer.instance().select(Logger.class).get();  //bootstrapping but with Weld itself ;-)
    }

    public <T> T getManagedInstance(final Class<T> clazz){
        if (logger.isDebugEnabled()) {
            logger.debug("managed instance " + clazz);
        }
        final Instance<T> ref = getInstanceReference(clazz);
        return ref.get();
    }

    public <T> Instance<T> getInstanceReference(final Class<T> clazz) {
        if (logger.isDebugEnabled()) {
            logger.debug("InstanceReference - class " + clazz);
        }
        return weldContainer.instance().select(clazz);
    }
    public <T> T getManagedInstance(final AnnotationLiteral literal,final Class<T> clazz){
        if (logger.isDebugEnabled()) {
            logger.debug("managed instance " + clazz);
            logger.debug("AnnotationLiteral - literal " + literal);
        }
        final Instance<T> ref = getInstanceReference(literal,clazz);
        return ref.get();
    }

    public <T> Instance<T> getInstanceReference(final AnnotationLiteral literal, final Class<T> clazz) {
        if (logger.isDebugEnabled()) {
            logger.debug("InstanceReference - class " + clazz);
            logger.debug("AnnotationLiteral - literal " + literal);
        }
        return weldContainer.instance().select(clazz, literal);
    }

    public void fireEvent(final Object o){
        weldContainer.event().fire(o);
    }

    public Event<Object> event(){
        return weldContainer.event();
    }



}
