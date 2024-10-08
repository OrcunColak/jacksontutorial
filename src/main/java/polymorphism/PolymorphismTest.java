package polymorphism;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class PolymorphismTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Cat cat = new Cat();
        cat.setName("Whiskers");
        cat.setIndoor(true);

        nonPolymorphic(objectMapper, cat);

        polymorphic(objectMapper, cat);
    }

    private static void nonPolymorphic(ObjectMapper objectMapper, Cat cat) throws JsonProcessingException {
        // Cat : {"name":"Whiskers","indoor":true}
        log.info("Cat : {}", objectMapper.writeValueAsString(cat));
    }

    private static void polymorphic(ObjectMapper objectMapper, Cat cat) throws JsonProcessingException {
        ObjectMapper clonedObjectMapper = objectMapper.copy();
        clonedObjectMapper
                .activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        String json = clonedObjectMapper.writeValueAsString(cat);

        // Cat : {"@class":"polymorphism.PolymorphismTest$Cat","name":"Whiskers","indoor":true}
        log.info("Cat : {}", json);

        Animal animal = clonedObjectMapper.readValue(json, Animal.class);
        log.info("Animal : {}", animal);
    }


    @Getter
    @Setter
    @ToString
    static class Animal {
        private String name;
    }

    @Getter
    @Setter
    @ToString(callSuper = true)
    static class Dog extends Animal {
        private String breed;

    }

    @Getter
    @Setter
    @ToString(callSuper = true)
    static class Cat extends Animal {
        private boolean isIndoor;
    }
}
