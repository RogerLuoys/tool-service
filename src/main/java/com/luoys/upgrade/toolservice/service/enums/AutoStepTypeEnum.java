package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动化步骤类型
 *
 * @author luoys
 */
@Getter
@Deprecated
public enum AutoStepTypeEnum {

    STEP_SQL(1, "sql模块"),
    STEP_HTTP(2, "http模块"),
    STEP_RPC(3, "rpc模块"),
    STEP_UI(4, "ui模块"),
    ASSERTION(5, "断言模块"),
    UTIL(6, "util模块"),
    PO(7, "po模块");


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
