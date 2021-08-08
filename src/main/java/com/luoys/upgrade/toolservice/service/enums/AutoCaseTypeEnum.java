package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

@Getter
public enum AutoCaseTypeEnum {


    INTERFACE_CASE(1, "接口测试用例"),
    UI_CASE(2, "ui测试用例");


    private final Integer code;
    private final String description;

    AutoCaseTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
