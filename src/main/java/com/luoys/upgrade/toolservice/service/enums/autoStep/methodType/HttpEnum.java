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

    GET(1, "get", "get请求"),
    POST(2, "post", "post请求"),
    PUT(3, "put", "put请求"),
    DELETE(4, "delete", "delete请求"),
    GET_FOR_HEADER(5, "getForHeader", "通过get请求获取header"),
    POST_FOR_HEADER(6, "postForHeader", "通过post请求获取header"),
    SET_DEFAULT_HEADER(7, "setDefaultHeader", "设置默认请求头");

    private final Integer code;
//    private final String value;
    private final String name; //Http步骤的方法名
    private final String description;

    HttpEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }


    private static final Map<Integer, HttpEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, HttpEnum> NAME_MAP = new HashMap<>();

    static {
        for (HttpEnum e : HttpEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            NAME_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static HttpEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }


    public static HttpEnum fromName(String name) {
        return name == null ? null : NAME_MAP.get(name);
    }

}
