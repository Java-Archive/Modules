package org.rapidpm.demo.cdi.commons.tx;

import javax.inject.Named;

import org.rapidpm.lang.cache.generic.Cacheable;

@Named
@Cacheable(primaryKeyAttributeName = "version", className = DemoClassA.class)
@CDITransactionScope
public class DemoClassA {

    private String version = System.nanoTime() + "";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DemoClassA{");
        sb.append("version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}