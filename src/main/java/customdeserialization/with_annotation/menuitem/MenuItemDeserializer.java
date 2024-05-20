package customdeserialization.with_annotation.menuitem;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
class MenuItemDeserializer extends JsonDeserializer<MenuItem> {

    @Override
    public MenuItem deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {

        List<Items> itemsList = new ArrayList<>();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if (!node.has("menu") || !node.get("menu").has("items")) {
            return null;
        }

        log.info(node.findValuesAsText("totalItems").get(0));

        JsonNode itemsNode = node.findPath("items");
        for (JsonNode jsonNode : itemsNode) {
            ObjectNode menuItemNode = (ObjectNode) jsonNode;

            List<String> dishName = menuItemNode.findValuesAsText("dish_name");
            List<String> price = menuItemNode.findValuesAsText("price");
            List<String> available = menuItemNode.findValuesAsText("available");

            Items i = Items.builder().dishName(dishName.get(0)).price(Float.parseFloat(price.get(0)))
                    .isAvailable(available.get(0).equals("true")).build();
            itemsList.add(i);
        }

        return MenuItem.builder().items(itemsList).build();

    }

}
