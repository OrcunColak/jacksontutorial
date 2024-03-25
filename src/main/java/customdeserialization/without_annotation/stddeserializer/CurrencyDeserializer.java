package customdeserialization.without_annotation.stddeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.util.Currency;

@Slf4j
class CurrencyDeserializer extends StdDeserializer<Currency> {

    public CurrencyDeserializer() {
        this(null);
    }

    public CurrencyDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Currency deserialize(final JsonParser jsonParser, final DeserializationContext context) {
        try {
            return Currency.getInstance(jsonParser.getText());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return null;
        }
    }
}
