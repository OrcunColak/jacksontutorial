package javatime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * If JavaTimeModule is not registered we get
 * Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
 */
@Slf4j
class JavaTimeTest {

    public static void main(String[] args) throws JsonProcessingException {
        LocalDate localDate = LocalDate.of(1976, 2, 6);

        // There are two ways of creating an ObjectMapper
        test1(localDate);
        test2(localDate);
    }

    private static void test1(LocalDate localDate) throws JsonProcessingException {
        ObjectMapper objectMapper = createObjectMapper1();
        String json = objectMapper.writeValueAsString(localDate);
        log.info("JSON : {}", json);
    }

    private static void test2(LocalDate localDate) throws JsonProcessingException {
        ObjectMapper objectMapper = createObjectMapper2();
        String json = objectMapper.writeValueAsString(localDate);
        log.info("JSON : {}", json);
    }

    private static ObjectMapper createObjectMapper1() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    // See https://stackoverflow.com/questions/47120363/java-8-date-time-types-serialized-as-object-with-spring-boot
    private static ObjectMapper createObjectMapper2() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
}
