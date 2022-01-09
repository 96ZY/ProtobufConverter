package com.protobuf.basics;

import lombok.Data;

import java.util.List;

/**
 * Describe a .proto file
 */
@Data
public class Protobuf {

    /**
     * All the Message types in a .proto file
     */
    private List<Message> messages;
}
