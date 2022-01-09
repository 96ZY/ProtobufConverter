package com.protobuf.basics;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Describe the Enum type defined in a .proto file
 */
@Data
public class Enum {

    @Data
    @AllArgsConstructor
    public static class EnumField {
        private String enumFieldName;
        private Integer enumFieldNum;
    }

    /**
     * The name of this Enum type
     */
    private String enumName;

    /**
     * All the fields within this Enum type
     */
    private List<EnumField> enumFieldList;
}
