package com.protobuf.converter;

import com.alibaba.fastjson.JSONObject;
import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.EnumDefinition;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.Message;
import com.protobuf.converter.proto.Person;
import org.junit.Test;

import java.io.IOException;


public class ProtobufToJsonTest {

    @Test
    public void testBuildingPerson() {
        Person.Builder personBuilder = Person.newBuilder();
        personBuilder.setName("Tom");
        personBuilder.setId(99);
        personBuilder.setEmail("tom@gmail.com");

        Person.PhoneNumber.Builder phoneNumberBuilder = Person.PhoneNumber.newBuilder();
        phoneNumberBuilder.setNumber("949-505-5555");
        phoneNumberBuilder.setType(Person.PhoneType.MOBILE);
        Person.PhoneNumber phoneNumber = phoneNumberBuilder.build();

        personBuilder.addPhones(phoneNumber);
        Person person = personBuilder.build();

        System.out.println(person);
    }

    @Test
    public void testConvertProtobufByteArrayToJson() throws IOException {
        Person.Builder personBuilder = Person.newBuilder();
        personBuilder.setName("Alan");
        personBuilder.setId(98);
        personBuilder.setEmail("alan@gmail.com");

        Person.PhoneNumber.Builder phoneNumberBuilder = Person.PhoneNumber.newBuilder();
        phoneNumberBuilder.setNumber("949-505-6666");
        phoneNumberBuilder.setType(Person.PhoneType.MOBILE);
        Person.PhoneNumber phoneNumber = phoneNumberBuilder.build();

        personBuilder.addPhones(phoneNumber);
        Person person = personBuilder.build();
        byte[] bytes = person.toByteArray();

        Person personMessage = Person.parseFrom(bytes);

        String s = ProtobufToJson.toJson(personMessage);

        JSONObject jsonObject = JSONObject.parseObject(s);

        System.out.println(jsonObject);
    }

    @Test
    public void testBuildDynamicSchema() throws Descriptors.DescriptorValidationException {
        // Create dynamic schema
        DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
        schemaBuilder.setName("addressbook.proto");

        EnumDefinition phoneTypeEnumDefinition = EnumDefinition.newBuilder("PhoneType")
                .addValue("MOBILE", 0)
                .addValue("HOME", 1)
                .addValue("WORK", 2)
                .build();

        MessageDefinition phoneNumberMessageDefinition = MessageDefinition.newBuilder("PhoneNumber")
                .addField("optional", "string", "number", 1)
                .addField("optional", "PhoneType", "type", 2, "HOME")
                .build();

        MessageDefinition PersonMessageDefinition = MessageDefinition.newBuilder("Person") // message Person
                .addField("optional", "string", "name", 1)	// required string name = 1
                .addField("optional", "int32", "id", 2)		// required int32 id = 2
                .addField("optional", "string", "email", 3)	// optional string email = 3
                .addEnumDefinition(phoneTypeEnumDefinition)
                .addMessageDefinition(phoneNumberMessageDefinition)
                .addField("repeated", "PhoneNumber", "phones", 4)
                .build();

        schemaBuilder.addMessageDefinition(PersonMessageDefinition);
        DynamicSchema schema = schemaBuilder.build();

        System.out.println(schema);
    }

    @Test
    public void testConvertProtobufToJsonByDynamicSchema() throws Descriptors.DescriptorValidationException, IOException {
        DynamicSchema personSchema = buildDynamicSchemaOfPerson();
        Message message = buildPersonMessage();

        byte[] bytes = message.toByteArray();

        DynamicMessage.Builder msgBuilder = personSchema.newMessageBuilder("Person");
        DynamicMessage.Builder builder = msgBuilder.mergeFrom(bytes);
        DynamicMessage personMessage = builder.build();
        String s = ProtobufToJson.toJson(personMessage);

        System.out.println(s);
    }


    private DynamicSchema buildDynamicSchemaOfPerson() throws Descriptors.DescriptorValidationException {
        // Create dynamic schema
        DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
        schemaBuilder.setName("addressbook.proto");

        EnumDefinition phoneTypeEnumDefinition = EnumDefinition.newBuilder("PhoneType")
                .addValue("MOBILE", 0)
                .addValue("HOME", 1)
                .addValue("WORK", 2)
                .build();

        MessageDefinition phoneNumberMessageDefinition = MessageDefinition.newBuilder("PhoneNumber")
                .addField("optional", "string", "number", 1)
                .addField("optional", "PhoneType", "type", 2, "HOME")
                .build();

        MessageDefinition PersonMessageDefinition = MessageDefinition.newBuilder("Person") // message Person
                .addField("optional", "string", "name", 1)	// required string name = 1
                .addField("optional", "int32", "id", 2)		// required int32 id = 2
                .addField("optional", "string", "email", 3)	// optional string email = 3
                .addEnumDefinition(phoneTypeEnumDefinition)
                .addMessageDefinition(phoneNumberMessageDefinition)
                .addField("repeated", "PhoneNumber", "phones", 4)
                .build();

        schemaBuilder.addMessageDefinition(PersonMessageDefinition);

        return schemaBuilder.build();
    }

    private Message buildPersonMessage() {
        Person.Builder personBuilder = Person.newBuilder();
        personBuilder.setName("Tom");
        personBuilder.setId(99);
        personBuilder.setEmail("tom@gmail.com");

        Person.PhoneNumber.Builder phoneNumberBuilder = Person.PhoneNumber.newBuilder();
        phoneNumberBuilder.setNumber("949-505-5555");
        phoneNumberBuilder.setType(Person.PhoneType.MOBILE);
        Person.PhoneNumber phoneNumber = phoneNumberBuilder.build();

        personBuilder.addPhones(phoneNumber);

        return personBuilder.build();
    }
}
