package com.luoys.upgrade.toolservice.service.enums.autoStep.methodType;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求类型
 *
 * @author luoys
 */
@Getter
public enum HttpEnum {

    GET(1, "GET", "get", "get请求"),
    POST(2, "POST", "post", "post请求"),
    PUT(3, "PUT", "put", "put请求"),
    DELETE(4, "DELETE", "delete", "delete请求");

    private final Integer code;
    private final String value;
    /**
     * Http步骤的方法名
     */
    private final String name;
    private final String description;

    HttpEnum(Integer code, String value, String name, String description) {
        this.code = code;
        this.value = value;
        this.name = name;
        this.description = description;
    }


    private static final Map<Integer, HttpEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, HttpEnum> VALUE_MAP = new HashMap<>();
    private static final Map<String, HttpEnum> TEMPLATE_MAP = new HashMap<>();

    static {
        for (HttpEnum e : HttpEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            VALUE_MAP.put(e.getValue(), e);
            TEMPLATE_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static HttpEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static HttpEnum fromValue(String value) {
        return value == null ? null : VALUE_MAP.get(value);
    }

    public static HttpEnum fromName(String name) {
        return name == null ? null : TEMPLATE_MAP.get(name);
    }

}
