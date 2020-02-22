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

package io.yokota.schemaregistry.chess.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ModeGetResponse {

    private String mode;

    public static ModeGetResponse fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, ModeGetResponse.class);
    }

    public ModeGetResponse(@JsonProperty("mode") String mode) {
        this.mode = mode;
    }

    @JsonProperty("mode")
    public String getMode() {
        return this.mode;
    }

    @JsonProperty("mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String toJson() throws IOException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
