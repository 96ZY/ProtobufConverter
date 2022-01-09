package com.protobuf.basics;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Describe the Message Type in a .proto file
 */
@Data
public class Message {

    @Data
    @AllArgsConstructor
    public static class MessageField {
        private String label;
        private String type;
        private String name;
        private Integer num;
        private Object defaultValue;

        public MessageField(String label, String type, String name, Integer num) {
            this.label = label;
            this.type = type;
            this.name = name;
            this.num = num;
        }
    }

    /**
     * the name of this Message type
     */
    private String MessageName;

    /**
     * All the Enum type defined within this Message type
     */
    private List<Enum> enumList;

    /**
     * All the Message type defined within this Message type
     */
    private List<Message> messageList;

    /**
     * All the fields within this Message type
     */
    private List<MessageField> messageFieldList;
}
