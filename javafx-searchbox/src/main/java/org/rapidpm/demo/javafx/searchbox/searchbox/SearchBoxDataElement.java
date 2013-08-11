package org.rapidpm.demo.javafx.searchbox.searchbox;

import java.util.List;

/**
 * User: Sven Ruppert
 * Date: 27.05.13
 * Time: 10:44
 */
public interface SearchBoxDataElement {

    public Long getID();
    public List<String> getValues();
    public String shortInfo();
}
