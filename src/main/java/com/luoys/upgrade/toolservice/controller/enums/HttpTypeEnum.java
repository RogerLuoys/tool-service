package com.luoys.upgrade.toolservice.controller.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum HttpTypeEnum {

    GET(1, "get请求"),
    POST(2, "post请求"),
    PUT(3, "put请求"),
    DELETE(4, "delete请求");

    private final Integer code;
    private final String description;

    HttpTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    private static final Map<Integer, HttpTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (HttpTypeEnum e : HttpTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static HttpTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
