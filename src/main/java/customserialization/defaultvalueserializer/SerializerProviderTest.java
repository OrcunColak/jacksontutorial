package customserialization.defaultvalueserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * See <a href="https://phsophea101.medium.com/data-types-and-default-values-in-rest-api-spring-boot-4a994980cf91">...</a>
 */
@Slf4j
class SerializerProviderTest {

    @Getter
    @Setter
    static class UserData {
        private String id;
        private String name;
        private String gender;
        private String username;
        private String status;
        @JsonProperty("is_graduated")
        private Boolean isGraduated;
        private String skills;
        private Integer age;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SerializerProvider serializerProvider = mapper.getSerializerProvider();
        if (serializerProvider != null) {
            mapper.setSerializerProvider(new CustomDefaultSerializerProvider());
        }
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        UserData userData = new UserData();
        String json = mapper.writeValueAsString(userData);

        log.info(json);
    }
}
