package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SqlTypeEnum {

    //全部都是6个字符
    INSERT(1, "INSERT", "insert"),
    DELETE(2, "DELETE", "delete"),
    UPDATE(3, "UPDATE", "update"),
    SELECT(4, "SELECT", "select"),
    UNKNOWN(-1, "UNKNOWN", "unknown");

    private final Integer code;
    private final String value;
    private final String description;

    SqlTypeEnum(Integer code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }


    private static final Map<Integer, SqlTypeEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, SqlTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (SqlTypeEnum e : SqlTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            VALUE_MAP.put(e.getValue(), e);
        }
    }

    public static SqlTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static SqlTypeEnum fromValue(String value) {
        return value == null ? null : VALUE_MAP.get(value);
    }

}
