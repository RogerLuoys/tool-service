package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 自动化用例类型
 *
 * @author luoys
 */
@Getter
public enum AutoCaseTypeEnum {


    SCRIPT_CASE(1, "自动化脚本"),
    SUPPER_CASE(1, "自动化超类"),
    PO_CASE(1, "封装的PO方法"),
    DATA_CASE(1, "数据工厂");
//    INTERFACE_CASE(1, "接口测试用例"),
//    UI_CASE(2, "ui测试用例");


    private final Integer code;
    private final String description;

    AutoCaseTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
