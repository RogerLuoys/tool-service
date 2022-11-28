package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 自动化用例类型
 *
 * @author luoys
 */
@Getter
@Deprecated
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
