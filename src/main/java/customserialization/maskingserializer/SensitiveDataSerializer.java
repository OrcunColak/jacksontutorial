package customserialization.maskingserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SensitiveDataSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator jsonGen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            //Here you can desensitize the data according to business needs.
            String maskedValue = mask(value);
            jsonGen.writeString(maskedValue);
        }
    }

    private String mask(String value) {
        //Here you can write the desensitization logic, for simplicity, all characters are replaced with *.
        return "*".repeat(value.length());
    }
}
