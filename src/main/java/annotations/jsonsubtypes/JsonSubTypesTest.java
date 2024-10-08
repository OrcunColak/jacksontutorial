package annotations.jsonsubtypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

class JsonSubTypesTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Serialize Cat
        Cat cat = new Cat();
        cat.setName("Whiskers");
        cat.setIndoor(true);

        String catJson = objectMapper.writeValueAsString(cat);
        // Cat: {"type":"cat","name":"Whiskers","isIndoor":true,"indoor":true}
        System.out.println("Serialized Cat: " + catJson);


        // Deserialize Cat
        Animal deserializedCat = objectMapper.readValue(catJson, Animal.class);
        System.out.println("Deserialized Cat: " + deserializedCat.getName() + ", Is Indoor: " + ((Cat) deserializedCat).isIndoor());
    }

    // Base class
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type" // This property will determine the subtype
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Dog.class, name = "dog"),
            @JsonSubTypes.Type(value = Cat.class, name = "cat")
    })
    @Getter
    @Setter
    static abstract class Animal {
        @JsonProperty
        private String name; // The name of the animal
    }

    // Subclass Dog
    @Getter
    @Setter
    static final class Dog extends Animal {
        @JsonProperty
        private String breed; // The breed of the dog
    }

    // Subclass Cat
    @Getter
    @Setter
    static final class Cat extends Animal {
        @JsonProperty
        private boolean isIndoor; // Indicates if the cat is indoor
    }
}