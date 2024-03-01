package annotations.jsoncreator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


enum ContactType {
    WORK("work"),HOME("home"),PERSONAL("personal");

    private final String value;

    ContactType(String contactType){
        this.value =contactType;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ContactType fromValue(String value) {
        for (ContactType contact : values()) {
            String currentContact = contact.getValue();
            if (currentContact.equals(value)) {
                return contact;
            }
        }

        // Return a response entity with a 400 Bad Request status
        throw new IllegalArgumentException("Invalid value for Contact type Enum: " + value);
    }

}
