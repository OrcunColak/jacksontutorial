package customdeserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.Currency;

/**
 * See <a href="https://medium.com/@rajdeepify/custom-deserialization-using-jackson-7303be55dc34">...</a>
 */
@Slf4j
public class CustomDeserializationTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = createObjectMapper();

        // InvalidFormatException: Cannot deserialize value of type `java.util.Currency` from String "ABC": not a valid textual representation
        String json = """
                {"amount":12345,"currency":"ABC","date":[2024,2,27,16,54,19,611764000]}
                """;
        Transaction transaction = objectMapper.readValue(json, Transaction.class);
        log.info("Transaction : {}", transaction);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Currency.class, new CurrencyDeserializer());

        objectMapper.registerModules(module, new JavaTimeModule());
        return objectMapper;
    }
}
