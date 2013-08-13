package org.rapidpm.demo.cdi.commons.cache;

import org.rapidpm.lang.cache.generic.Cacheable;

/**
 * Created by Sven Ruppert on 31.07.13.
 */


@Cacheable(className = CachedClass.class, primaryKeyAttributeName = "txt")
public class CachedClass {

    private String txt;
    private Long value;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CachedClass)) return false;

        CachedClass that = (CachedClass) o;

        if (!txt.equals(that.txt)) return false;
        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = txt.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }


}
