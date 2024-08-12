package customdeserialization.with_annotation.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

// See https://medium.com/@ak123aryan/efficient-handling-of-large-json-files-in-java-using-custom-serialization-and-deserialization-eb9d822b990c
// In this example @JsonDeserialize and @JsonSerialize annotations are used to indicate custom serde classses to be used by Jackson
@Slf4j
public class UserSerdeTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User user = new User();
        user.setUserId("1");
        user.setJsonString("foo");
        String string = objectMapper.writeValueAsString(user);

        User user1 = objectMapper.readValue(string, User.class);

        log.info("Equals : {}", user.getUserId().equals(user1.getUserId()));
    }

    // The domain model object
    // The jackson annotations for domain model object
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = UserDeserializer.class)
    @JsonSerialize(using = UserSerializer.class)
    // The lombok annotations for domain model object
    @Getter
    @Setter
    @EqualsAndHashCode
    private static class User {
        private String userId;
        // This field is not serialized
        private String jsonString;
    }

    private static class UserDeserializer extends JsonDeserializer<User> {
        @Override
        public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            User user = new User();
            String userIdLiteral = "userId";
            user.setUserId(node.has(userIdLiteral) ? node.get(userIdLiteral).asText() : null);
            return user;
        }
    }

    private static class UserSerializer extends JsonSerializer<User> {
        @Override
        public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("userId", user.getUserId());
            jsonGenerator.writeEndObject();
        }
    }


}
