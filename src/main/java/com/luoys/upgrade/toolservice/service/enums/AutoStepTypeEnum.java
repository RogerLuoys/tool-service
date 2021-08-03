package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum AutoStepTypeEnum {

    STEP_SQL(1, "sql类型步骤"),
    STEP_HTTP(2, "http请求类型步骤"),
    STEP_RPC(3, "rpc请求类型步骤"),
    STEP_UI(4, "ui操作类型步骤");


    private final Integer code;
    private final String description;

    AutoStepTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, AutoStepTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (AutoStepTypeEnum e : AutoStepTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static AutoStepTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
