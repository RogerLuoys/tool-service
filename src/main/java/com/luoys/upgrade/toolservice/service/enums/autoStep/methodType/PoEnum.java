package com.luoys.upgrade.toolservice.service.enums.autoStep.methodType;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum PoEnum {

    PO_NAME(1, "poName", "调用封装好的po方法"),
    PO_EXECUTE_BY_JSON(2, "executeByJson", "json格式调用po方法");

    private final Integer code;
    private final String name; //Po步骤的方法名
    private final String description;

    PoEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private static final Map<Integer, PoEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, PoEnum> NAME_MAP = new HashMap<>();

    static {
        for (PoEnum e : PoEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            NAME_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static PoEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static PoEnum fromName(String name) {
        return name == null ? null : NAME_MAP.get(name.toLowerCase());
    }

}
