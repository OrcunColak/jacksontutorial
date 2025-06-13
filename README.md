# JSON
Consider using Protobuf, Avro, or FlatBuffers instead of JSON

# Heavy Object
See https://medium.com/@kanishksinghpujari/the-one-java-optimization-that-made-my-code-10x-faster-without-changing-frameworks-1541cf79ec35

ObjectMapper is far from lightweight. It builds serializers, introspection caches, and various internal buffers that can be reused — if you let them.
This is not good
```java
public String processRecord(Data data) {
    ObjectMapper mapper = new ObjectMapper();
    try {
        return mapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
    }
}
```

# Singleton Object
This immediately helped by eliminating redundant object creation. Since ObjectMapper is thread-safe after configuration, this is a legitimate optimization.

However, under load testing with multiple threads, a new problem emerged — throughput plateaued. Despite the mapper being thread-safe, concurrent access caused minor contention, particularly during simultaneous writes involving shared internal buffers.

```java
public class MapperProvider {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static ObjectMapper get() {
        return MAPPER;
    }
}
```
# Thread-Scoped Singleton
This means every thread had its own instance of ObjectMapper, initialized once and reused throughout its lifecycle.

```java
public class ThreadLocalMapper {
    private static final ThreadLocal<ObjectMapper> mapper =
        ThreadLocal.withInitial(ObjectMapper::new);

    public static ObjectMapper get() {
        return mapper.get();
    }
}
```

# JsonFormat
Original idea is from  
https://towardsdev.com/data-transfer-object-dto-in-spring-boot-c00678cc5946
