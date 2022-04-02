package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求类型
 *
 * @author luoys
 */
@Getter
public enum HttpTypeEnum {

    GET(1, "GET", "auto.http.doGet", "get请求"),
    POST(2, "POST", "auto.http.doPost", "post请求"),
    PUT(3, "PUT", "auto.http.doPut", "put请求"),
    DELETE(4, "DELETE", "auto.http.doDelete", "delete请求");

    private final Integer code;
    private final String value;

    /**
     * Http接口相关步骤，脚本模式的调用模板
     */
    private final String scriptTemplate;
    private final String description;

    HttpTypeEnum(Integer code, String value, String scriptTemplate, String description) {
        this.code = code;
        this.value = value;
        this.scriptTemplate = scriptTemplate;
        this.description = description;
    }


    private static final Map<Integer, HttpTypeEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, HttpTypeEnum> VALUE_MAP = new HashMap<>();
    private static final Map<String, HttpTypeEnum> TEMPLATE_MAP = new HashMap<>();

    static {
        for (HttpTypeEnum e : HttpTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            VALUE_MAP.put(e.getValue(), e);
            TEMPLATE_MAP.put(e.getScriptTemplate().toLowerCase(), e);
        }
    }

    public static HttpTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static HttpTypeEnum fromValue(String value) {
        return value == null ? null : VALUE_MAP.get(value);
    }

    public static HttpTypeEnum fromScriptTemplate(String scriptTemplate) {
        return scriptTemplate == null ? null : TEMPLATE_MAP.get(scriptTemplate);
    }

}
