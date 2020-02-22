/**
 * Copyright 2014 Confluent Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.yokota.schemaregistry.chess.schema;

import io.confluent.kafka.schemaregistry.CompatibilityChecker;
import io.confluent.kafka.schemaregistry.CompatibilityLevel;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.rest.entities.SchemaReference;
import io.confluent.kafka.schemaregistry.exceptions.InvalidSchemaException;
import io.yokota.schemaregistry.chess.model.Game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChessSchema implements ParsedSchema {

    public static final String TYPE = "CHESS";

    private static final int MOVE_TIME_MS = 3000;

    private String move;
    private String pgn;
    private final Integer version;
    private final List<SchemaReference> references;
    private final Map<String, String> resolvedReferences;

    public ChessSchema(String schemaString) throws InvalidSchemaException {
        this(schemaString, Collections.emptyList(), Collections.emptyMap(), null);
    }

    public ChessSchema(String schemaString,
                       List<SchemaReference> references,
                       Map<String, String> resolvedReferences,
                       Integer version) throws InvalidSchemaException {
        this.references = Collections.unmodifiableList(references);
        this.resolvedReferences = Collections.unmodifiableMap(resolvedReferences);
        this.version = version;

        Map<String, String> props = new HashMap<>();
        props.put("whiteId", String.valueOf(1));
        props.put("blackId", String.valueOf(-1));
        props.put("white", "Player");
        props.put("black", "Computer");
        if (schemaString.trim().contains(" ")) {
            System.out.println("** found pgn " + schemaString);
            move = null;
            pgn = schemaString;
        } else {
            System.out.println("** found move " + schemaString);
            move = schemaString;
            pgn = null;
        }
    }

    @Override
    public String rawSchema() {
        return canonicalString();
    }

    @Override
    public String schemaType() {
        return TYPE;
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public String canonicalString() {
        return move != null ? move : pgn;
    }

    public Integer version() {
        return version;
    }

    @Override
    public List<SchemaReference> references() {
        return references;
    }

    public Map<String, String> resolvedReferences() {
        return resolvedReferences;
    }

    @Override
    public boolean isBackwardCompatible(ParsedSchema previousSchema) {
        ChessSchema chessSchema = (ChessSchema) previousSchema;
        String lastBoard = chessSchema.canonicalString();
        Game game = new Game();
        game.setPgnMoves(lastBoard);
        boolean legal = game.addMove(move);
        game.addMove(game.getBestMove(MOVE_TIME_MS));
        move = null;
        pgn = game.getPgnMoves();
        return legal;
    }

    @Override
    public boolean isCompatible(CompatibilityLevel level,
                                List<? extends ParsedSchema> previousSchemas) {
        for (ParsedSchema previousSchema : previousSchemas) {
            if (!schemaType().equals(previousSchema.schemaType())) {
                return false;
            }
        }
        System.out.println("*** prev size " + previousSchemas.size());
        System.out.println("*** move " + move);
        if (move != null && previousSchemas.isEmpty()) {
            Game game = new Game();
            game.addMove(move);
            game.addMove(game.getBestMove(MOVE_TIME_MS));
            move = null;
            pgn = game.getPgnMoves();
            System.out.println("*** yes move " + move);
        } else {
            System.out.println("*** no move " + move);

        }
        return CompatibilityChecker.checker(level).isCompatible(this, previousSchemas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessSchema that = (ChessSchema) o;
        return Objects.equals(canonicalString(), that.canonicalString())
            && Objects.equals(references, that.references)
            && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canonicalString(), references, version);
    }

    @Override
    public String toString() {
        return canonicalString();
    }
}
