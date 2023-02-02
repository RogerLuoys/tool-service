package com.luoys.upgrade.toolservice.service.enums.autoStep.methodType;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * ui操作类型
 *
 * @author luoys
 */
@Getter
public enum UtilEnum {

    SLEEP(1, "sleep", "强制睡眠"),
    GET_JSON(2, "getJson", "根据json key取对应的值"),
    GET_JSON_ANY(3, "getJsonAny", "根据json和子json第一个key取对应的值"),
    GET_RANDOM(4, "getRandom", "获取随机数"),
    GET_TIME(5, "getTime", "获取当前linux时间");

    private final Integer code;
    private final String name; //util 步骤的方法名
    private final String description;

    UtilEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private static final Map<Integer, UtilEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, UtilEnum> NAME_MAP = new HashMap<>();

    static {
        for (UtilEnum e : UtilEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            NAME_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static UtilEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static UtilEnum fromName(String name) {
        return name == null ? null : NAME_MAP.get(name.toLowerCase());
    }

}
