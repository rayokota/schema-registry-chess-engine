/*
 * Copyright 2018 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.yokota.schemaregistry.chess;

import io.confluent.kafka.schemaregistry.rest.SchemaRegistryConfig;

import java.io.Closeable;
import java.io.IOException;

public class ModeRepository implements Closeable {

    // Used to represent all subjects
    public static final String SUBJECT_WILDCARD = "*";

    public ModeRepository(SchemaRegistryConfig schemaRegistryConfig) {
    }

    public Mode getMode(String subject) {
        if (subject == null) subject = SUBJECT_WILDCARD;
        return null;
    }

    public void setMode(String subject, Mode mode) {
        if (subject == null) subject = SUBJECT_WILDCARD;
    }

    @Override
    public void close() throws IOException {
    }
}
