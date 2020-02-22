/*
 * Copyright 2019 Confluent Inc.
 */

package io.yokota.schemaregistry.chess;

import io.confluent.kafka.schemaregistry.rest.SchemaRegistryConfig;
import io.confluent.kafka.schemaregistry.rest.extensions.SchemaRegistryResourceExtension;
import io.confluent.kafka.schemaregistry.storage.SchemaRegistry;
import io.yokota.schemaregistry.chess.filter.ModeFilter;
import io.yokota.schemaregistry.chess.resources.ModeResource;

import javax.ws.rs.core.Configurable;
import java.io.IOException;

public class SchemaRegistryModeResourceExtension
    implements SchemaRegistryResourceExtension {

    private ModeRepository repository;

    @Override
    public void register(
        Configurable<?> configurable,
        SchemaRegistryConfig schemaRegistryConfig,
        SchemaRegistry schemaRegistry
    ) {
        repository = new ModeRepository(schemaRegistryConfig);
        configurable.register(new ModeResource(repository, schemaRegistry));
        configurable.register(new ModeFilter(repository));
    }

    @Override
    public void close() throws IOException {
        repository.close();
    }
}
