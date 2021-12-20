package com.protobuf.util;

import com.github.os72.protobuf.dynamic.EnumDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class EnumDefinitionUtil {

    @Data
    @AllArgsConstructor
    public static class EnumDefinitionField {
        private String name;
        private Integer num;
    }

    public static EnumDefinition buildEnumDefinition(String enumName, List<EnumDefinitionField> enumDefinitionFieldList) {
        if (enumName == null) {
            return null;
        }

        EnumDefinition.Builder enumDefinitionBuilder = EnumDefinition.newBuilder(enumName);
        enumDefinitionFieldList.forEach(e -> enumDefinitionBuilder.addValue(e.getName(), e.getNum()));

        return enumDefinitionBuilder.build();
    }
}
