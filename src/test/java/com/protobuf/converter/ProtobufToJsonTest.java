package com.protobuf.converter;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.InvalidProtocolBufferException;
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
}
