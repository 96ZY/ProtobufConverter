package com.protobuf.util;

import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.google.protobuf.Descriptors;

import java.util.List;

public class DynamicSchemaUtil {
    public static DynamicSchema buildDynamicSchema(String schemaName, List<MessageDefinition> messageDefinitionList) {
        if (schemaName == null) {
            return null;
        }

        DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
        schemaBuilder.setName(schemaName);

        if (messageDefinitionList != null) {
            messageDefinitionList.forEach(schemaBuilder::addMessageDefinition);
        }

        DynamicSchema dynamicSchema = null;

        try {
            dynamicSchema = schemaBuilder.build();
        } catch (Descriptors.DescriptorValidationException e) {
            e.printStackTrace();
        }

        return dynamicSchema;
    }
}
