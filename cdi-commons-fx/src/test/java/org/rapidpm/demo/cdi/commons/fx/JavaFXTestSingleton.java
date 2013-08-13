package org.rapidpm.demo.cdi.commons.fx;

import java.util.concurrent.Semaphore;

/**
 * User: Sven Ruppert
 * Date: 24.07.13
 * Time: 11:50
 */
public class JavaFXTestSingleton {
    private static JavaFXTestSingleton ourInstance = new JavaFXTestSingleton();

    public static JavaFXTestSingleton getInstance() {
        return ourInstance;
    }

    private JavaFXTestSingleton() {

    }

    private Semaphore semaphore = new Semaphore(1);

    private Class<?> clazz;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
