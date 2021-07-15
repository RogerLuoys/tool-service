package com.luoys.upgrade.toolservice.controller.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum HttpTypeEnum {

    GET(1, "GET", "get请求"),
    POST(2, "POST", "post请求"),
    PUT(3, "PUT", "put请求"),
    DELETE(4, "DELETE", "delete请求");

    private final Integer code;
    private final String value;
    private final String description;

    HttpTypeEnum(Integer code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }


    private static final Map<Integer, HttpTypeEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, HttpTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (HttpTypeEnum e : HttpTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            VALUE_MAP.put(e.getValue(), e);
        }
    }

    public static HttpTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static HttpTypeEnum fromValue(String value) {
        return value == null ? null : VALUE_MAP.get(value);
    }
}
