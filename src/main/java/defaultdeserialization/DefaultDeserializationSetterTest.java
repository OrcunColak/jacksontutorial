package defaultdeserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.beans.ConstructorProperties;

// See https://medium.com/@davenkin_93074/jacksons-default-serialization-deserialization-behavior-ed3d6dcf239b

// In order to deserialize objects, Jackson needs to call constructors to create and object. There are multiple types of constructors in Jackson:
//
// 1. Default constructor
// 2. Constructor with @JsonCreator
// 3. Constructor with @ConstructorProperties
// 4. Raw multi arguments constructor

// If fields are not populated by constructor, its setter will be called to do the population.
@Slf4j
class DefaultDeserializationSetterTest {

    @Getter
    private static class User {
        private String name;
        private int age;
        private String address;

        public User(String name, int age, String address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        @ConstructorProperties({"name"})
        public User(String name) {
            System.out.println("1 arg constructor called.");
            this.name = name;
        }

        public void setName(String name) {
            System.out.println("setName called.");
            this.name = name;
        }

        public void setAge(int age) {
            System.out.println("setAge called.");
            this.age = age;
        }

        public void setAddress(String address) {
            System.out.println("setAddress called.");
            this.address = address;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        User user = new User("Andy", 49, "Address");
        String json = objectMapper.writeValueAsString(user);

        User userFromJson = objectMapper.readValue(json, User.class);

    }
}
