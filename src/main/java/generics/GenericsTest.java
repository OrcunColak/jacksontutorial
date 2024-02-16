package generics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericsTest {

    record Person(int id, String name) {
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person(1, "person1");
        String json = writeValueAsString(objectMapper, person);
        Person person1 = readValue(objectMapper, json, Person.class);
        boolean equals = person.equals(person1);
        log.info("Person equals : {}", equals);
    }

    public static <T> String writeValueAsString(ObjectMapper objectMapper, T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T readValue(ObjectMapper objectMapper, String json, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(json, valueType);
    }
}
