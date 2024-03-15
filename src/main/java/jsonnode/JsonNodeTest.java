package jsonnode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class JsonNodeTest {
    public static void main(String[] args) {
        // Your JSON string
        String jsonString = "{\"key\": \"value\"}";

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read JSON string into JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Get value of the "key" attribute
            String attributeValue = jsonNode.get("key").asText();

            // Print the attribute value
            log.info("Attribute Value: " + attributeValue);
        } catch (Exception exception) {
            log.error("Exception", exception);
        }
    }
}
