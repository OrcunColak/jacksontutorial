package annotations.jsoninclude;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class JsonIncludeTest {

    @Getter
    @Setter
    @ToString
    private static class Person {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        private int age;

    }

    public static void main(String[] args) {
        writeEmpty();
        writeOnlyAge();
    }

    private static void writeEmpty() {
        // Name will not be serialized because it is null
        // Age will not be serialized because it is equal to default value
        Person person = new Person();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String personJson = objectMapper.writeValueAsString(person);

            // "personJson: {}"
            log.info("personJson: {}", personJson);
        } catch (Exception exception) {
            log.error("Exception :", exception);
        }
    }

    private static void writeOnlyAge() {
        // Name will not be serialized because it is null
        // Age is serialized because it is different from default value
        Person person = new Person();
        person.setAge(1);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String personJson = objectMapper.writeValueAsString(person);

            // "personJson: {"age":1}"
            log.info("personJson: {}", personJson);
        } catch (Exception exception) {
            log.error("Exception :", exception);
        }
    }
}
