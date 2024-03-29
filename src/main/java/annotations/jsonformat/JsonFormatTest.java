package annotations.jsonformat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Test JsonFormat annotation pattern
 */
@Slf4j
class JsonFormatTest {

    public static void main(String[] args) throws JsonProcessingException {

        UserDTO userDTO = new UserDTO();
        userDTO.setRegistrationDate(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userDTO);
        // Local time is "2024-01-12 16:54:44"
        // UTC time is "2024-01-12 13:54:44"
        log.info(jsonString);

    }
}
