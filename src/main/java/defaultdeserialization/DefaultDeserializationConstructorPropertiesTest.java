package defaultdeserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.beans.ConstructorProperties;

// See https://medium.com/@davenkin_93074/jacksons-default-serialization-deserialization-behavior-ed3d6dcf239b

// In order to deserialize objects, Jackson needs to call constructors to create and object. There are multiple types of constructors in Jackson:
//
// 1. Default constructor
// 2. Constructor with @JsonCreator
// 3. Constructor with @ConstructorProperties
// 4. Raw multi arguments constructor

// If @JsonCreator and @ConstructorProperties constructors are provided, they will be called regardless of the existence of other
// types of constructors, but the two does not work together
@Slf4j
class DefaultDeserializationConstructorPropertiesTest {

    private static class User {
        private String name;
        private int age;
        private String address;

        private boolean constructorPropertiesConstructorCalled = false;

        //will not be called
        public User() {
            System.out.println("No arg constructor called.");
        }

        //this constructor will be called
        @ConstructorProperties({"name", "age"})
        public User(String name, int age) {
            this.name = name;
            this.age = age;
            System.out.println("@ConstructorProperties constructor called.");
            this.constructorPropertiesConstructorCalled = true;
        }

        //will not be called
        public User(String name, int age, String address) {
            this.name = name;
            this.age = age;
            this.address = address;
            System.out.println("Raw multi arg constructor called.");
        }
    }

    // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Conflicting property-based creators: ...
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User user = new User("Andy", 49, "Address");
        String json = objectMapper.writeValueAsString(user);

        User userFromJson = objectMapper.readValue(json, User.class);

    }
}
