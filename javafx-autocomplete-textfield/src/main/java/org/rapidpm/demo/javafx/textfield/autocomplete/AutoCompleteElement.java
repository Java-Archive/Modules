package org.rapidpm.demo.javafx.textfield.autocomplete;

/**
 * User: Sven Ruppert
 * Date: 13.05.13
 * Time: 08:42
 */
public class AutoCompleteElement {


    private String key; //das was der Anwender eingetippt hat
    private String shortinfo; //das was der Anwender sehen wird
    private Long id; //ID der referenzierten Einheit

    public AutoCompleteElement key(final String key) {
        this.key = key;
        return this;
    }

    public AutoCompleteElement shortinfo(final String shortinfo) {
        this.shortinfo = shortinfo;
        return this;
    }

    public AutoCompleteElement id(final Long id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShortinfo() {
        return shortinfo;
    }

    public void setShortinfo(String shortinfo) {
        this.shortinfo = shortinfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AutoCompleteElement{");
        sb.append("key='").append(key).append('\'');
        sb.append(", shortinfo='").append(shortinfo).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AutoCompleteElement)) return false;

        AutoCompleteElement that = (AutoCompleteElement) o;

        if (!id.equals(that.id)) return false;
        if (!key.equals(that.key)) return false;
        if (!shortinfo.equals(that.shortinfo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + shortinfo.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}