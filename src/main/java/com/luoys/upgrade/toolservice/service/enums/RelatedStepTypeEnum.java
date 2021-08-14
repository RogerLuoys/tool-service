package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum RelatedStepTypeEnum {

    PRE_STEP(1, "前置步骤"),
    MAIN_STEP(2, "主要步骤"),
    AFTER_STEP(3, "收尾步骤");


    private final Integer code;
    private final String description;

    RelatedStepTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, RelatedStepTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (RelatedStepTypeEnum e : RelatedStepTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static RelatedStepTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
