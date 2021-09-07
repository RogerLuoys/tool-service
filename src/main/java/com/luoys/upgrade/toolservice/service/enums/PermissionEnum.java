package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据权限
 *
 * @author luoys
 */
@Getter
public enum PermissionEnum {

    OPEN(1, "公开"),
    OWNER(2, "仅自己可见");

    private final Integer code;
    private final String description;

    PermissionEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    private static final Map<Integer, PermissionEnum> CODE_MAP = new HashMap<>();

    static {
        for (PermissionEnum e : PermissionEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static PermissionEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
