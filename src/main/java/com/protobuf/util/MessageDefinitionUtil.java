package com.protobuf.util;

import com.github.os72.protobuf.dynamic.EnumDefinition;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class MessageDefinitionUtil {

    @Data
    @AllArgsConstructor
    public static class MessageDefinitionField {
        private String label;
        private String type;
        private String name;
        private Integer num;
        private String defaultVal;

        public MessageDefinitionField(String label, String type, String name, int num) {
            this.label = label;
            this.type = type;
            this.name = name;
            this.num = num;;
        }
    }

    public static MessageDefinition buildMessageDefinition(String msgTypeName,
                                                           List<MessageDefinition> messageDefinitionList,
                                                           List<EnumDefinition> enumDefinitionList,
                                                           List<MessageDefinitionField> messageDefinitionFieldList) {

        if (msgTypeName == null) {
            return null;
        }

        MessageDefinition.Builder messageDefinitionBuilder = MessageDefinition.newBuilder(msgTypeName);

        if (messageDefinitionList != null) {
            messageDefinitionList.forEach(messageDefinitionBuilder::addMessageDefinition);
        }

        if (enumDefinitionList != null) {
            enumDefinitionList.forEach(messageDefinitionBuilder::addEnumDefinition);
        }

        if (messageDefinitionList != null) {
            messageDefinitionFieldList.forEach(e -> {
                if (e.getDefaultVal() != null) {
                    messageDefinitionBuilder.addField(e.getLabel(), e.getType(), e.getName(), e.getNum(), e.getDefaultVal());
                } else {
                    messageDefinitionBuilder.addField(e.getLabel(), e.getType(), e.getName(), e.getNum());
                }
            });
        }

        return messageDefinitionBuilder.build();

    }
}
