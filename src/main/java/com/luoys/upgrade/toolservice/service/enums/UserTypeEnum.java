package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {

    ADMIN(1, "管理员"),
    REGULAR(2, "普通账号");

    private final Integer code;
    private final String description;

    UserTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

}
