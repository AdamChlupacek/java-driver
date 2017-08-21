/*
 * Copyright (C) 2017-2017 DataStax Inc.
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
package com.datastax.oss.driver.internal.core.metadata;

import com.datastax.oss.protocol.internal.ProtocolConstants;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/** The different kinds of objects in a schema. */
public enum SchemaElementKind {
  // Note that FULL_SCHEMA and VIEW are not defined at the protocol level, they're only used
  // internally.
  FULL_SCHEMA("FULL_SCHEMA"),
  KEYSPACE(ProtocolConstants.SchemaChangeTarget.KEYSPACE),
  TABLE(ProtocolConstants.SchemaChangeTarget.TABLE),
  VIEW("VIEW"),
  TYPE(ProtocolConstants.SchemaChangeTarget.TYPE),
  FUNCTION(ProtocolConstants.SchemaChangeTarget.FUNCTION),
  AGGREGATE(ProtocolConstants.SchemaChangeTarget.AGGREGATE),
  ;

  private final String protocolString;

  SchemaElementKind(String protocolString) {
    this.protocolString = protocolString;
  }

  private static final Map<String, SchemaElementKind> BY_PROTOCOL_STRING;

  static {
    ImmutableMap.Builder<String, SchemaElementKind> builder = ImmutableMap.builder();
    for (SchemaElementKind kind : values()) {
      builder.put(kind.protocolString, kind);
    }
    BY_PROTOCOL_STRING = builder.build();
  }

  public static SchemaElementKind fromProtocolString(String protocolString) {
    SchemaElementKind kind = BY_PROTOCOL_STRING.get(protocolString);
    if (kind == null) {
      throw new IllegalArgumentException("Unsupported schema type: " + protocolString);
    }
    return kind;
  }
}
