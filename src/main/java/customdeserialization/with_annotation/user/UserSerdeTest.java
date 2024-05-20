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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// See https://medium.com/@ak123aryan/efficient-handling-of-large-json-files-in-java-using-custom-serialization-and-deserialization-eb9d822b990c
public class UserSerdeTest {


    private static final Logger log = LoggerFactory.getLogger(UserSerdeTest.class);

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = UserDeserializer.class)
    @JsonSerialize(using = UserSerializer.class)

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
}
