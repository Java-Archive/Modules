package org.rapidpm.demo.javafx.searchbox.searchbox;

/**
 * User: Sven Ruppert
 * Date: 23.05.13
 * Time: 10:55
 */
public enum DocumentType {

    SAMPLE("Samples"), //TODO muss

    CLASS("Classes"),

    PROPERTY("Properties"),

    METHOD("Methods"),

    FIELD("Fields"),

    ENUM("Enums");

    private final String pluralDisplayName;

    DocumentType(String pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public String getPluralDisplayName() {
        return pluralDisplayName;
    }
}