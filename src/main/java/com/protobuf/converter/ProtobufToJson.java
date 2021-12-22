package com.protobuf.converter;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;

public class ProtobufToJson {
    public static String toJson(Message sourceMessage) throws IOException {
        return JsonFormat.printer().print(sourceMessage);
    }

    public static Message toProtoBean(Message.Builder builder, String s) throws InvalidProtocolBufferException {
        JsonFormat.parser().merge(s, builder);
        return builder.build();
    }
}
