package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum AssertTypeEnum {

    NO_ASSERT(-1, "不断言"),
    EQUALS(1, "完全匹配"),
    CONTAINS(2, "模糊匹配");


    private final Integer code;
    private final String description;

    AssertTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, AssertTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (AssertTypeEnum e : AssertTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static AssertTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
