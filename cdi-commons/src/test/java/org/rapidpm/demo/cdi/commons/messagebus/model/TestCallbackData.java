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
