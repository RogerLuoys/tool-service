package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动化套件状态
 *
 * @author luoys
 */
@Getter
public enum ConfigTypeEnum {

    NORMAL(1, "普通参数"),
    CHROME(2, "chrome参数"),
    FIREFOX(3, "firefox参数"),
    ANDROID(4, "android参数");

    private final Integer code;
    private final String description;

    ConfigTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    private static final Map<Integer, ConfigTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (ConfigTypeEnum e : ConfigTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static ConfigTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
