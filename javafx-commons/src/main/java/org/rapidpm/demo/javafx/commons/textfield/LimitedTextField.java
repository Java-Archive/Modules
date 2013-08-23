package org.rapidpm.demo.javafx.commons.textfield;

import javafx.scene.control.TextField;

/**
 * User: Sven Ruppert
 * Date: 20.08.13
 * Time: 10:54
 */
public class LimitedTextField extends TextField {


    private int limit;

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        super.replaceText(start, end, text);
        verify();
    }

    @Override
    public void replaceSelection(String text) {
        super.replaceSelection(text);
        verify();
    }

    private void verify() {
        if (getText().length() > limit) {
            setText(getText().substring(0, limit));
        }
    }

}
