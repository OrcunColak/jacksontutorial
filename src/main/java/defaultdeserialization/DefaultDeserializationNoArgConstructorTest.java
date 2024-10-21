package defaultdeserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

// See https://medium.com/@davenkin_93074/jacksons-default-serialization-deserialization-behavior-ed3d6dcf239b

// In order to deserialize objects, Jackson needs to call constructors to create and object. There are multiple types of constructors in Jackson:
//
// 1. Default constructor
// 2. Constructor with @JsonCreator
// 3. Constructor with @ConstructorProperties
// 4. Raw multi arguments constructor

// With no constructors annotated with @ConstructorProperties or @JsonCreator, the default no argument constructor will
// take priority than other constructors
@Slf4j
class DefaultDeserializationNoArgConstructorTest {

    @Getter
    private static class User {
        private String name;

        public User() {
            log.info("No arg constructor is called");
        }

        public User(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();


        User user = new User("Andy");
        String json = objectMapper.writeValueAsString(user);

        User userFromJson = objectMapper.readValue(json, User.class);

    }
}
