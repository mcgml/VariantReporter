package nhs.genetics.cardiff.framework.vep;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class YesNoBoolDeserializer extends StdDeserializer<Boolean> {

    public YesNoBoolDeserializer() {
        this(null);
    }

    public YesNoBoolDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);

        String value = jsonNode.asText().trim();

        if (value.equals("YES")){
            return true;
        } else if (value.equals("NO")){
            return false;
        } else {
            return null;
        }

    }
}