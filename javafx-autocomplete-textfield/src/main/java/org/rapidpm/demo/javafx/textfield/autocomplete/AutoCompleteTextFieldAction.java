package org.rapidpm.demo.javafx.textfield.autocomplete;

/**
 * User: Sven Ruppert
 * Date: 13.05.13
 * Time: 14:59
 */
public interface AutoCompleteTextFieldAction<T extends AutoCompleteElement> {

    public void execute(T selectedItem);
}
