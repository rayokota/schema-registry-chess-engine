/**
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

import io.confluent.kafka.schemaregistry.AbstractSchemaProvider;
import io.confluent.kafka.schemaregistry.CompatibilityChecker;
import io.confluent.kafka.schemaregistry.CompatibilityLevel;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.rest.entities.SchemaReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ChessSchemaProvider extends AbstractSchemaProvider {

    private static final Logger log = LoggerFactory.getLogger(ChessSchemaProvider.class);

    @Override
    public String schemaType() {
        return ChessSchema.TYPE;
    }

    @Override
    public Optional<ParsedSchema> parseSchema(String schemaString,
                                              List<SchemaReference> references) {
        try {
            return Optional.of(
                new ChessSchema(schemaString, references, resolveReferences(references), null));
        } catch (Exception e) {
            log.error("Could not parse Chess schema", e);
            return Optional.empty();
        }
    }
}
