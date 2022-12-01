package com.luoys.upgrade.toolservice.service.enums.autoStep;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ModuleTypeEnum {

    UNDEFINED_MODULE(-1, "--", "未知方法"),
    PO(1, "po", "封装好的方法，包含多个步骤"),
    SQL(2, "sql", "执行sql相关的方法"),
    RPC(3, "rpc", "调用rpc接口相关的方法"),
    HTTP(4, "http", "调用http接口相关的方法"),
    UI(5, "ui", "UI相关的方法"),
    UTIL(6, "util", "工具相关的方法"),
    ASSERTION(7, "assertion", "断言");


    private final Integer code;

    /**
     * 方法类型，脚本模式的调用模板
     */
    private final String name;
    private final String description;

    ModuleTypeEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private static final Map<String, ModuleTypeEnum> VALUE_MAP = new HashMap<>();
    private static final Map<Integer, ModuleTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (ModuleTypeEnum e : ModuleTypeEnum.values()) {
            VALUE_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static ModuleTypeEnum fromName(String name) {
        return name == null ? null : VALUE_MAP.get(name.toLowerCase());
    }
    public static ModuleTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
