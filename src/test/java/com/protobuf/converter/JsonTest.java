package com.protobuf.converter;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {

    @Data
    static class Json {
        private Integer num;
        private String color;
        private List<Integer> list;
        private Map<String, Integer> map;
    }

    @Test
    public void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("aaaa", "111");
        map.put("bbbb", "222");

        final String s = JSONObject.toJSONString(map);
        System.out.println(s);
    }

    @Test
    public void test2() {
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("x", "y");
        Map<String, String> map2 = new HashMap<>();
        map2.put("x", "z");
        map.put("aaaa", map1);
        map.put("bbbb", map2);

        final String s = JSONObject.toJSONString(map);
        System.out.println(s);
    }

    @Test
    public void test3() {
        final Json json = new Json();
        json.setNum(1111);
        json.setColor("red");
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        json.setList(list);
        Map<String, Integer> map = new HashMap<>();
        map.put("s1", 1);
        map.put("s2", 2);
        json.setMap(map);

        final Object o = JSONObject.toJSON(json);
        System.out.println(o);

        final String s = JSONObject.toJSONString(json);
        System.out.println(s);

        final Json r = JSONObject.parseObject(s, Json.class);
        System.out.println(r);
    }
}
