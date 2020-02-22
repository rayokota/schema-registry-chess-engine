package io.yokota.schemaregistry.chess.resources;

import io.confluent.kafka.schemaregistry.client.rest.Versions;
import io.confluent.kafka.schemaregistry.rest.exceptions.Errors;
import io.confluent.kafka.schemaregistry.rest.exceptions.RestSchemaRegistryException;
import io.confluent.kafka.schemaregistry.storage.KafkaSchemaRegistry;
import io.confluent.kafka.schemaregistry.storage.SchemaRegistry;
import io.confluent.rest.exceptions.RestConstraintViolationException;
import io.yokota.schemaregistry.chess.Mode;
import io.yokota.schemaregistry.chess.ModeRepository;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.Locale;

@Path("/mode")
@Produces({Versions.SCHEMA_REGISTRY_V1_JSON_WEIGHTED,
    Versions.SCHEMA_REGISTRY_DEFAULT_JSON_WEIGHTED,
    Versions.JSON_WEIGHTED})
@Consumes({Versions.SCHEMA_REGISTRY_V1_JSON,
    Versions.SCHEMA_REGISTRY_DEFAULT_JSON,
    Versions.JSON, Versions.GENERIC_REQUEST})
public class ModeResource {

    private static final int INVALID_MODE_ERROR_CODE = 42299;

    private final ModeRepository repository;
    private final KafkaSchemaRegistry schemaRegistry;

    public ModeResource(
        ModeRepository repository,
        SchemaRegistry schemaRegistry
    ) {
        this.repository = repository;
        this.schemaRegistry = (KafkaSchemaRegistry) schemaRegistry;
    }

    @Path("/{subject}")
    @PUT
    public ModeUpdateRequest updateMode(
        @PathParam("subject") String subject,
        @Context HttpHeaders headers,
        @NotNull ModeUpdateRequest request
    ) {
        Mode mode;
        try {
            mode = Enum.valueOf(
                Mode.class, request.getMode().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new RestConstraintViolationException(
                "Invalid mode. Valid values are READWRITE and READONLY.",
                INVALID_MODE_ERROR_CODE);
        }
        try {
            if (schemaRegistry.isMaster()) {
                repository.setMode(subject, mode);
            } else {
                throw new RestSchemaRegistryException(
                    "Failed to update mode, not the master");
            }
        } catch (Exception e) {
            throw Errors.storeException("Failed to update mode", e);
        }

        return request;
    }

    @Path("/{subject}")
    @GET
    public ModeGetResponse getMode(@PathParam("subject") String subject) {
        try {
            Mode mode = repository.getMode(subject);
            if (mode == null) {
                throw Errors.subjectNotFoundException(subject);
            }
            return new ModeGetResponse(mode.name());
        } catch (Exception e) {
            throw Errors.storeException("Failed to get mode", e);
        }
    }

    @PUT
    public ModeUpdateRequest updateTopLevelMode(
        @Context HttpHeaders headers,
        @NotNull ModeUpdateRequest request
    ) {
        return updateMode(ModeRepository.SUBJECT_WILDCARD, headers, request);
    }

    @GET
    public ModeGetResponse getTopLevelMode() {
        return getMode(ModeRepository.SUBJECT_WILDCARD);
    }
}
