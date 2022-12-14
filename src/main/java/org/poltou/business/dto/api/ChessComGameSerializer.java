package org.poltou.business.dto.api;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ChessComGameSerializer extends JsonDeserializer<ChessComGame> {

    @Override
    public ChessComGame deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String pgn = node.get("pgn").asText();
        return new ChessComGame(pgn);
    }

}
