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

package org.rapidpm.demo.cdi.commons.messagebus.model;

/**
 * User: Sven Ruppert
 * Date: 01.08.13
 * Time: 15:03
 */
public class TestCallbackData {

    private String valueTxt;
    private Long valueLong;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCallbackData)) return false;

        TestCallbackData testCallbackData = (TestCallbackData) o;

        if (!valueLong.equals(testCallbackData.valueLong)) return false;
        if (!valueTxt.equals(testCallbackData.valueTxt)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = valueTxt.hashCode();
        result = 31 * result + valueLong.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestCallbackData{");
        sb.append("valueLong=").append(valueLong);
        sb.append(", valueTxt='").append(valueTxt).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public TestCallbackData valueLong(final Long valueLong) {
        this.valueLong = valueLong;
        return this;
    }

    public TestCallbackData valueTxt(final String valueTxt) {
        this.valueTxt = valueTxt;
        return this;
    }


    public Long getValueLong() {
        return valueLong;
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    public String getValueTxt() {
        return valueTxt;
    }

    public void setValueTxt(String valueTxt) {
        this.valueTxt = valueTxt;
    }
}
