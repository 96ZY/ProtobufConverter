package com.protobuf.converter;

import com.alibaba.fastjson.JSONObject;
import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.EnumDefinition;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.Message;
import com.protobuf.converter.proto.Person;
import com.protobuf.util.DynamicSchemaUtil;
import com.protobuf.util.EnumDefinitionUtil;
import com.protobuf.util.MessageDefinitionUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
        System.out.println(personSchema);
        Message message = buildPersonMessage();

        byte[] bytes = message.toByteArray();

        DynamicMessage.Builder msgBuilder = personSchema.newMessageBuilder("Person");
        DynamicMessage.Builder builder = msgBuilder.mergeFrom(bytes);
        DynamicMessage personMessage = builder.build();
        String s = ProtobufToJson.toJson(personMessage);

        System.out.println(s);
    }

    @Test
    public void testConvertProtobufToJsonByDynamicSchemaByUtil() throws Descriptors.DescriptorValidationException, IOException {
        DynamicSchema personSchema = buildDynamicSchemaOfPersonByUtil();
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
                .addEnumDefinition(phoneTypeEnumDefinition)
                .addField("optional", "PhoneType", "type", 2, "HOME")
                .build();

        MessageDefinition personMessageDefinition = MessageDefinition.newBuilder("Person") // message Person
                .addField("optional", "string", "name", 1)	// required string name = 1
                .addField("optional", "int32", "id", 2)		// required int32 id = 2
                .addField("optional", "string", "email", 3)	// optional string email = 3
                .addMessageDefinition(phoneNumberMessageDefinition)
                .addField("repeated", "PhoneNumber", "phones", 4)
                .build();

        schemaBuilder.addMessageDefinition(personMessageDefinition);

        return schemaBuilder.build();
    }

    private DynamicSchema buildDynamicSchemaOfPersonByUtil() {

        EnumDefinition phoneTypeEnumDefinition = buildEnumDefinition();

        List<EnumDefinition> enumDefinitionList = new ArrayList<>();
        enumDefinitionList.add(phoneTypeEnumDefinition);
        MessageDefinition phoneNumberMessageDefinition = buildMessageDefinitionOfPhoneNumber(enumDefinitionList);

        List<MessageDefinition> messageDefinitionList = new ArrayList<>();
        messageDefinitionList.add(phoneNumberMessageDefinition);
        MessageDefinition personMessageDefinition = buildMessageDefinitionOfPerson(messageDefinitionList);

        List<MessageDefinition> messageDefinitionList1 = new ArrayList<>();
        messageDefinitionList1.add(personMessageDefinition);

        return DynamicSchemaUtil.buildDynamicSchema("addressbook.proto", messageDefinitionList1);
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

    private EnumDefinition buildEnumDefinition() {
        List<EnumDefinitionUtil.EnumDefinitionField> enumDefinitionFieldList = new ArrayList<>();
        EnumDefinitionUtil.EnumDefinitionField enumDefinitionField1 = new EnumDefinitionUtil.EnumDefinitionField("MOBILE", 0);
        EnumDefinitionUtil.EnumDefinitionField enumDefinitionField2 = new EnumDefinitionUtil.EnumDefinitionField("HOME", 1);
        EnumDefinitionUtil.EnumDefinitionField enumDefinitionField3 = new EnumDefinitionUtil.EnumDefinitionField("WORK", 2);
        enumDefinitionFieldList.add(enumDefinitionField1);
        enumDefinitionFieldList.add(enumDefinitionField2);
        enumDefinitionFieldList.add(enumDefinitionField3);

        return EnumDefinitionUtil.buildEnumDefinition("PhoneType", enumDefinitionFieldList);
    }

    private MessageDefinition buildMessageDefinitionOfPhoneNumber(List<EnumDefinition> enumDefinitionList) {
        List<MessageDefinitionUtil.MessageDefinitionField> messageDefinitionFieldList1 = new ArrayList<>();
        MessageDefinitionUtil.MessageDefinitionField messageDefinitionField1 = new MessageDefinitionUtil.MessageDefinitionField("optional", "string", "number", 1);
        MessageDefinitionUtil.MessageDefinitionField messageDefinitionField2 = new MessageDefinitionUtil.MessageDefinitionField("optional", "PhoneType", "type", 2, "HOME");
        messageDefinitionFieldList1.add(messageDefinitionField1);
        messageDefinitionFieldList1.add(messageDefinitionField2);

        return MessageDefinitionUtil.buildMessageDefinition("PhoneNumber", null, enumDefinitionList, messageDefinitionFieldList1);
    }

    private MessageDefinition buildMessageDefinitionOfPerson(List<MessageDefinition> messageDefinitionList) {
        List<MessageDefinitionUtil.MessageDefinitionField> messageDefinitionFieldList2 = new ArrayList<>();
        MessageDefinitionUtil.MessageDefinitionField messageDefinitionField21 = new MessageDefinitionUtil.MessageDefinitionField("optional", "string", "name", 1);
        MessageDefinitionUtil.MessageDefinitionField messageDefinitionField22 = new MessageDefinitionUtil.MessageDefinitionField("optional", "int32", "id", 2);
        MessageDefinitionUtil.MessageDefinitionField messageDefinitionField23 = new MessageDefinitionUtil.MessageDefinitionField("optional", "string", "email", 3);
        MessageDefinitionUtil.MessageDefinitionField messageDefinitionField24 = new MessageDefinitionUtil.MessageDefinitionField("repeated", "PhoneNumber", "phones", 4);
        messageDefinitionFieldList2.add(messageDefinitionField21);
        messageDefinitionFieldList2.add(messageDefinitionField22);
        messageDefinitionFieldList2.add(messageDefinitionField23);
        messageDefinitionFieldList2.add(messageDefinitionField24);

        return MessageDefinitionUtil.buildMessageDefinition("Person", messageDefinitionList, null, messageDefinitionFieldList2);
    }

}
