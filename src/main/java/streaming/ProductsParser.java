package streaming;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// See https://medium.com/@vino7tech/efficient-large-json-processing-in-spring-boot-using-jackson-streaming-api-a19366aa4942
// The streaming API (JsonParser) processes JSON tokens one by one without loading the entire file into memory, making it ideal for large data.
@Slf4j
class ProductsParser {

    @Getter
    @Setter
    @ToString
    private static class Product {
        private int id;
        private String name;
        private double price;
    }

    public static void main(String[] args) {

        List<Product> matchingProducts = Collections.emptyList();

        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/json/products.json")) {

            JsonFactory jsonFactory = new JsonFactory();
            try (JsonParser jsonParser = jsonFactory.createParser(inputStream)) {

                while (!jsonParser.isClosed()) {
                    // Start parsing JSON array
                    matchingProducts = readArray(jsonParser);
                }
            }
        } catch (IOException exception) {
            log.error("Exception ", exception);
        }
        log.info("Product List : {}", matchingProducts);
    }

    private static List<Product> readArray(JsonParser jsonParser) throws IOException {
        List<Product> matchingProducts = new ArrayList<>();

        if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                Product product = readProduct(jsonParser);

                // Filter logic - Example: Only process products with price > 100
                if (product.getPrice() > 100) {
                    matchingProducts.add(product);
                }
            }
        }
        return matchingProducts;
    }

    // The Product objects are deserialized incrementally.
    // Only those matching certain criteria (e.g., price > 100) are stored in memory and returned, reducing memory usage.
    private static Product readProduct(JsonParser jsonParser) throws IOException {
        Product product = new Product();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.currentName();
            jsonParser.nextToken(); // move to value

            if ("id".equals(fieldName)) {
                product.setId(jsonParser.getIntValue());
            } else if ("name".equals(fieldName)) {
                product.setName(jsonParser.getText());
            } else if ("price".equals(fieldName)) {
                product.setPrice(jsonParser.getDoubleValue());
            }
        }
        return product;
    }
}
