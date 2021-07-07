package com.luoys.upgrade.toolservice.controller.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SqlTypeEnum {

    INSERT(1, "select"),
    DELETE(2, "delete"),
    UPDATE(3, "update"),
    SELECT(4, "select");

    private final Integer code;
    private final String description;

    SqlTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    private static final Map<Integer, SqlTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (SqlTypeEnum e : SqlTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static SqlTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }


}
