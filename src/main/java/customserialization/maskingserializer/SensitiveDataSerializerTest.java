package customserialization.maskingserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SensitiveDataSerializerTest {

    @Getter
    @Setter
    @ToString
    static class User {
        private Long id;
        private String username;

        @JsonSerialize(using = SensitiveDataSerializer.class)
        private String email;

    }
    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.setId(1L);
        user.setUsername("NGU");
        user.setEmail("ngu@mail.com");

        String json = mapper.writeValueAsString(user);
        log.info("After Data Desensitization: {}", json);
    }
}
