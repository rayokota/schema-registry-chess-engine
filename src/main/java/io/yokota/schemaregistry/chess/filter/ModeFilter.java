/*
 * Copyright 2017 Confluent Inc.
 */

package io.yokota.schemaregistry.chess.filter;

import io.confluent.kafka.schemaregistry.rest.resources.ConfigResource;
import io.confluent.kafka.schemaregistry.rest.resources.SubjectVersionsResource;
import io.confluent.kafka.schemaregistry.rest.resources.SubjectsResource;
import io.yokota.schemaregistry.chess.Mode;
import io.yokota.schemaregistry.chess.ModeRepository;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashSet;
import java.util.Set;


@Priority(Priorities.AUTHORIZATION)
public class ModeFilter implements ContainerRequestFilter {

    private static final Set<ResourceActionKey> subjectWriteActions =
        new HashSet<>();

    private ModeRepository repository;

    @Context
    ResourceInfo resourceInfo;

    @Context
    UriInfo uriInfo;

    @Context
    HttpServletRequest httpServletRequest;

    static {
        initializeSchemaRegistrySubjectWriteActions();
    }

    private static void initializeSchemaRegistrySubjectWriteActions() {
        subjectWriteActions.add(
            new ResourceActionKey(SubjectVersionsResource.class, "POST"));
        subjectWriteActions.add(
            new ResourceActionKey(SubjectVersionsResource.class, "DELETE"));
        subjectWriteActions.add(
            new ResourceActionKey(SubjectsResource.class, "DELETE"));
        subjectWriteActions.add(
            new ResourceActionKey(ConfigResource.class, "PUT"));
    }

    public ModeFilter(ModeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Class resource = resourceInfo.getResourceClass();
        String restMethod = requestContext.getMethod();
        String subject = uriInfo.getPathParameters().getFirst("subject");

        Mode mode = repository.getMode(subject);
        if (mode == null) {
            // Check the top level mode
            mode = repository.getMode(ModeRepository.SUBJECT_WILDCARD);
        }
        if (mode == Mode.READONLY) {
            ResourceActionKey key = new ResourceActionKey(resource, restMethod);
            if (subjectWriteActions.contains(key)) {
                requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Subject is read-only.")
                        .build());
            }
        }
    }

    private static class ResourceActionKey {

        private final Class resourceClass;
        private final String restMethod;

        public ResourceActionKey(Class resourceClass, String restMethod) {
            this.resourceClass = resourceClass;
            this.restMethod = restMethod;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ResourceActionKey that = (ResourceActionKey) o;
            if (!resourceClass.equals(that.resourceClass)) {
                return false;
            }
            return restMethod.equals(that.restMethod);
        }

        @Override
        public int hashCode() {
            int result = resourceClass.hashCode();
            result = 31 * result + restMethod.hashCode();
            return result;
        }
    }
}
