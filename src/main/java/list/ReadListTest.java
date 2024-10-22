package list;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// Example that shows how to read a list
@Slf4j
class ReadListTest {

    @Data
    @NoArgsConstructor
    private static class User {
        private Long id;
        private String name;
        private String username;
        private String email;
        private String phone;
        private String website;
        private Address address;
        private Company company;
    }

    @Data
    @NoArgsConstructor
    private static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;
    }

    @Data
    @NoArgsConstructor
    private static class Geo {
        private String lat;
        private String lng;
    }

    @Data
    @NoArgsConstructor
    private static class Company {
        private String name;
        private String catchPhrase;
        private String bs;
    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<User>> typeReference = new TypeReference<>() {
        };
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json")) {
            List<User> users = mapper.readValue(inputStream, typeReference);
            log.info("Users List : {}", users);
        } catch (IOException exception) {
            log.error("Exception ", exception);
        }
    }
}
