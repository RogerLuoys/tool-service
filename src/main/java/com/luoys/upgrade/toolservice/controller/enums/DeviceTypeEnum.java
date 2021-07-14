package com.luoys.upgrade.toolservice.controller.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum DeviceTypeEnum {

    DATA_SOURCE(1, "数据库"),
    MOBILE_PHONE(2, "手机"),
    CONTAINER(3, "容器");

    private final Integer code;
    private final String description;

    DeviceTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    private static final Map<Integer, DeviceTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (DeviceTypeEnum e : DeviceTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static DeviceTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
