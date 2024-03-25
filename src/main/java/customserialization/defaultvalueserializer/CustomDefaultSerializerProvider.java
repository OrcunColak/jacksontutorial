package customserialization.defaultvalueserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;

class CustomDefaultSerializerProvider extends DefaultSerializerProvider {
    public CustomDefaultSerializerProvider() {
        super();
    }

    public CustomDefaultSerializerProvider(CustomDefaultSerializerProvider src) {
        super(src);
    }

    protected CustomDefaultSerializerProvider(CustomDefaultSerializerProvider provider, SerializationConfig config, SerializerFactory jsf) {
        super(provider, config, jsf);
    }

    @Override
    public CustomDefaultSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        return new CustomDefaultSerializerProvider(this, config, jsf);
    }

    @Override
    public DefaultSerializerProvider copy() {
        if (getClass() != CustomDefaultSerializerProvider.class)
            return super.copy();
        return new CustomDefaultSerializerProvider(this);
    }

    @Override
    public DefaultSerializerProvider withCaches(CacheProvider cacheProvider) {
        return null;
    }

    @Override
    public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
        if (String.class.isAssignableFrom(property.getType().getRawClass()))
            return new DefaultJsonSerializer("");
        else if (this.isClassDecimal(property.getType().getRawClass()))
            return new DefaultJsonSerializer(BigDecimal.valueOf(BigDecimal.ZERO.doubleValue()));
        else if (Number.class.isAssignableFrom(property.getType().getRawClass()))
            return new DefaultJsonSerializer(BigInteger.ZERO);
        else if (Boolean.class.isAssignableFrom(property.getType().getRawClass()))
            return new DefaultJsonSerializer(Boolean.FALSE);
        else if (this.isClassDate(property.getType().getRawClass()))
            return new DefaultJsonSerializer(null);
        else if (Collection.class.isAssignableFrom(property.getType().getRawClass()))
            return new DefaultJsonSerializer(new ArrayList<>());
        else if (Object.class.isAssignableFrom(property.getType().getRawClass()))
            return new DefaultJsonSerializer(null);
        else
            return super.findNullValueSerializer(property);
    }

    private boolean isClassDate(Class<?> clazz) {
        return Date.class.isAssignableFrom(clazz) || LocalDate.class.isAssignableFrom(clazz) || LocalDateTime.class.isAssignableFrom(clazz);
    }

    private boolean isClassDecimal(Class<?> clazz) {
        return Float.class.isAssignableFrom(clazz) || BigDecimal.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz);
    }

    private static class DefaultJsonSerializer extends JsonSerializer<Object> {
        private final Object defaultValue;

        public DefaultJsonSerializer(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeObject(this.defaultValue);
        }
    }
}
