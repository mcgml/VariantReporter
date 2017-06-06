package nhs.genetics.cardiff.framework.vep;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import nhs.genetics.cardiff.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MafDeserializer extends StdDeserializer<HashMap<String, Double>> {

    private static final Logger log = Logger.getLogger(MafDeserializer.class.getName());

    public MafDeserializer() {
        this(null);
    }

    public MafDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HashMap<String, Double> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);

        HashMap<String, Double> maf = new HashMap<>();

        String value = jsonNode.asText().trim();

        //skip empty fields
        if (value.isEmpty()){
            return null;
        }

        for (String field : value.split("&")){
            String[] subFields = field.trim().split(":");

            try {
                maf.put(subFields[0], Double.parseDouble(subFields[1]));
            } catch (ArrayIndexOutOfBoundsException e){
                log.log(Level.FINE, "Could not parse maf fields: " + value);
            }

        }

        return maf;
    }
}