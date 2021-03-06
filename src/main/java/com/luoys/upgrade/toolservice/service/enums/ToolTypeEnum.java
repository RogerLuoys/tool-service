package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据工厂工具类型
 *
 * @author luoys
 */
@Getter
public enum ToolTypeEnum {

    SQL(1, "sql类型工具"),
    HTTP(2, "http请求类型工具"),
    RPC(3, "rpc请求类型工具"),
    MULTIPLE(4, "多个子工具聚合");

    private final Integer code;
    private final String description;

    ToolTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, ToolTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (ToolTypeEnum e : ToolTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static ToolTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
