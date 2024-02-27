package annotations.jsoncreator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

// See https://medium.com/@shreeyaruias/using-enums-in-spring-boot-88d3115fa7ee
@Slf4j
class ContactTypeEnumTest {

    public static void main(String[] args) throws JsonProcessingException {
        serializeAndDeserializeWithJsonCreator();
    }

    private static void serializeAndDeserializeWithJsonCreator() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ContactDto contactDto = new ContactDto();
        contactDto.setContactType(ContactType.WORK);

        // Serialize enum to JSON. Result is : {"contactType":"work"}
        String json = objectMapper.writeValueAsString(contactDto);
        log.info("json : {}", json);

        // Deserialize JSON
        // Instead of com.fasterxml.jackson.core.JsonParseException we are getting IllegalArgumentException
        ContactDto deserializedEnum = objectMapper.readValue("{\"contactType\":\"work1\"}", ContactDto.class);

        log.info("deserializedEnum : {}", deserializedEnum);
    }
}
