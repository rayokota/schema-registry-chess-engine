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

package io.yokota.schemaregistry.chess;

import io.confluent.kafka.schemaregistry.exceptions.SchemaRegistryException;
import io.confluent.kafka.schemaregistry.rest.SchemaRegistryConfig;
import io.confluent.kafka.schemaregistry.rest.extensions.SchemaRegistryResourceExtension;
import io.confluent.kafka.schemaregistry.storage.SchemaRegistry;
import org.glassfish.jersey.servlet.ServletProperties;

import javax.ws.rs.core.Configurable;

public class SchemaRegistryChessResourceExtension
    implements SchemaRegistryResourceExtension {

    @Override
    public void register(
        Configurable<?> configurable,
        SchemaRegistryConfig schemaRegistryConfig,
        SchemaRegistry kafkaSchemaRegistry
    ) throws SchemaRegistryException {
        configurable.property(ServletProperties.FILTER_STATIC_CONTENT_REGEX,
            "/(static/.*|.*\\.html|.*\\.js)");
    }

    @Override
    public void close() {
    }
}
