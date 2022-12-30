package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MemberEnum {

    OWNER(1, "负责人"),
    ADMIN(2, "管理员"),
    ORDINARY(3, "普通成员");

    private final Integer code;
    private final String description;

    MemberEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    private static final Map<Integer, MemberEnum> CODE_MAP = new HashMap<>();

    static {
        for (MemberEnum e : MemberEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static MemberEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
