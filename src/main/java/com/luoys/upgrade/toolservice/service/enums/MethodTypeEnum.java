package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动化步骤，脚本模式的模板类型
 */
@Getter
public enum MethodTypeEnum {

    UTIL(1, "auto.util", "工具相关的方法"),
    UI(2, "auto.ui", "UI相关的方法"),
    SQL(3, "auto.dbName", "执行sql相关的方法"),
    HTTP(4, "auto.http", "调用http接口相关的方法"),
    RPC(5, "auto.rpc", "调用rpc接口相关的方法"),
    TASK(6, "auto.task", "封装步骤");


    private final Integer code;

    /**
     * 方法类型，脚本模式的调用模板
     */
    private final String scriptTemplate;
    private final String description;

    MethodTypeEnum(Integer code, String scriptTemplate, String description) {
        this.code = code;
        this.scriptTemplate = scriptTemplate;
        this.description = description;
    }

    private static final Map<String, MethodTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (MethodTypeEnum e : MethodTypeEnum.values()) {
            VALUE_MAP.put(e.getScriptTemplate().toLowerCase(), e);
        }
    }

    public static MethodTypeEnum fromScriptTemplate(String scriptTemplate) {
        return scriptTemplate == null ? null : VALUE_MAP.get(scriptTemplate.toLowerCase());
    }
}
