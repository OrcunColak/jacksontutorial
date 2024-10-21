package defaultserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.extern.slf4j.Slf4j;

// See https://medium.com/@davenkin_93074/jacksons-default-serialization-deserialization-behavior-ed3d6dcf239b
// By default, Jackson uses getter methods for serialization. Without getters, it throws InvalidDefinitionException:
@Slf4j
class DefaultSerializationNoGetterTest {

    private static class User {
        private final String name;

        public User(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User("Andy");
        try {
            objectMapper.writeValueAsString(user);
        } catch (InvalidDefinitionException exception) {
            log.error("InvalidDefinitionException", exception);
        }
    }
}
