package annotations.jsonignoreproperties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

// See https://medium.com/@salvipriya97/how-to-convert-json-to-java-pojo-using-jackson-c522bc67462c
@Slf4j
class JsonIgnorePropertiesTest {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @ToString
    private static class Person {
        @JsonProperty("name")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;

        @JsonProperty("age")
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        private int age;

    }

    public static void main(String[] args) {

        String json = "{\"name\":\"John\", \"age\":30, \"unknownField\":\"value\"}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Person person = objectMapper.readValue(json, Person.class);

            log.info("Person: {}", person);
        } catch (Exception exception) {
            log.error("Exception :", exception);
        }

    }
}
