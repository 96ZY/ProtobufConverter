package com.protobuf.converter;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;

public class ProtobufToJson {
    public static String toJson(Message sourceMessage) throws IOException {
        String json = JsonFormat.printer().print(sourceMessage);
        return json;
    }
}
