package com.luoys.upgrade.toolservice.service.enums;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 作用区域
 *
 * @author luoys
 */
@Getter
@Deprecated
public enum AreaEnum {

    IF(1, "IF", "if 判断区域"),
    THEN(2, "THEN", "if 判断为true后执行区域"),
    ELSE(3, "ELSE", "if 判断为false后执行区域");

    private final Integer code;
    private final String value;
    private final String description;

    AreaEnum(Integer code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }


    private static final Map<Integer, AreaEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, AreaEnum> VALUE_MAP = new HashMap<>();

    static {
        for (AreaEnum e : AreaEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            VALUE_MAP.put(e.getValue(), e);
        }
    }

    public static AreaEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static AreaEnum fromValue(String value) {
        if (value == null || VALUE_MAP.get(value) == null) {
//            return ERROR;
            return null;
        } else {
            return VALUE_MAP.get(value);
        }
    }

}
