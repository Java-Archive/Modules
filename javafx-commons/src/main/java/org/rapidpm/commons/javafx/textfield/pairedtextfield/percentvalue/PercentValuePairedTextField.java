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

package org.rapidpm.commons.javafx.textfield.pairedtextfield.percentvalue;

import java.util.concurrent.Callable;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.rapidpm.commons.javafx.textfield.pairedtextfield.BasePairedTextField;

/**
 * User: Sven Ruppert
 * Date: 09.10.13
 * Time: 12:44
 */
public class PercentValuePairedTextField extends BasePairedTextField {

    @Inject Instance<ValueToPercentageLogic> valueToPercentageLogicInstance;
    @Inject Instance<PercentageToValueLogic> percentageToValueLogicInstance;

    @Inject Instance<ValueFormatter> valueFormatterInstance;
    @Inject Instance<PercentageFormatter> percentageFormatterInstance;

    private double baseValue = 0.0;

    @Override public Callable<String> getCallableForLeftToRightTransformation() {
        return () -> {
            final String leftTextFieldText = leftTextField.getText();
            return valueToPercentageLogicInstance.get().convert(getBaseValue(), leftTextFieldText);
        };
    }

    @Override public Callable<String> getCallableForRightToLeftTransformation() {
        return () -> {
            final String rightTextFieldText = rightTextField.getText();
            return percentageToValueLogicInstance.get().convert(getBaseValue(), rightTextFieldText);
        };
    }

    public double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }
}
