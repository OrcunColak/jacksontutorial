package customdeserialization.with_annotation.menuitem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * See <a href="https://medium.com/@sehgal.mohit06/custom-json-deserialization-and-jsonproperty-spring-boot-example-8405b92f685d">...</a>
 * This example demonstrates how to deserialize an object from a large JSON file. Subsequently, a partial view of the object is serialized back into JSON format.
 */
@Slf4j
class DeserializationTest {

    public static void main(String[] args) throws JsonProcessingException {

        String json = """
                {
                   "menu": {
                     "totalItems": 2,
                     "items": [
                       {
                         "dish_name": "Pizza",
                         "price": 9.99,
                         "available": true
                       },
                       {
                         "dish_name": "Burger",
                         "price": 5.99,
                         "available": true
                       }
                     ]
                   }
                 }
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MenuItem menuItem = objectMapper.readValue(json, MenuItem.class);
        List<Items> items = menuItem.getItems();
        for (int index = 0; index < items.size(); index++) {
            Items item = items.get(index);
            item.setDelicacy("delicacy" + index);
            item.setOrderable("orderable" + index);
        }


        // {"items":[{"delicacy":"delicacy0","orderable":"orderable0"},{"delicacy":"delicacy1","orderable":"orderable1"}]}
        String string = objectMapper.writeValueAsString(menuItem);
        log.info(string);

    }
}
