
package org.rapidpm.demo.javafx.textfield.autocomplete;

import com.sun.javafx.scene.control.behavior.BehaviorBase;

/**
 *
 * @author Narayan G. Maharjan
 *
 */
public class AutoCompleteTextFieldBehavior<T extends AutoCompleteElement> extends BehaviorBase<AutoCompleteTextField<T>>{

    public AutoCompleteTextFieldBehavior(AutoCompleteTextField<T> textBox) {
        super(textBox);      
    }
}
