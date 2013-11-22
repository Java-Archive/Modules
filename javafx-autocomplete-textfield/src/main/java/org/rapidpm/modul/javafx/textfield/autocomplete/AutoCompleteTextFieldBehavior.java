
/*
 * Copyright [2013] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.rapidpm.modul.javafx.textfield.autocomplete;

import java.util.ArrayList;


import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;

/**
 * @author Narayan G. Maharjan
 */
public class AutoCompleteTextFieldBehavior<T extends AutoCompleteElement> extends BehaviorBase<AutoCompleteTextField<T>> {

    public AutoCompleteTextFieldBehavior(AutoCompleteTextField<T> textBox) {
        super(textBox,new ArrayList<KeyBinding>());  //for JDK8
//        super(textBox);
    }
}
