package org.rapidpm.demo.cdi.commons.registry.property;

/**
 * User: Sven Ruppert
 * Date: 10.06.13
 * Time: 07:46
 */
public interface PropertyRegistry {

    public void loadProperties();
    public String getProperty(final String key);
    public boolean hasProperty(final String key);
}
