package javatime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

// Serialized ZonedDateTime using another time zone
@Slf4j
class ZonedDateTimeTimeZoneTest {

    public static void main(String[] args) throws JsonProcessingException {
        LocalDate localDate = LocalDate.of(1976, 2, 6);
        LocalTime localTime = LocalTime.of(1, 2, 3);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, localTime, ZoneId.of("Europe/Istanbul"));

        // There are two ways of creating an ObjectMapper
        test1(zonedDateTime);
    }

    private static void test1(ZonedDateTime zonedDateTime) throws JsonProcessingException {
        ObjectMapper objectMapper = createObjectMapper1();
        String json = objectMapper.writeValueAsString(zonedDateTime);

        // 1976-02-05T23:02:03Z because of UTC output
        log.info("ZonedDateTime JSON for ObjectMapper : {}", json);
    }


    private static ObjectMapper createObjectMapper1() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setTimeZone(TimeZone.getTimeZone("UTC"));
        return objectMapper;
    }

}
