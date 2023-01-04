package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 自动化用例类型
 *
 * @author luoys
 */
@Getter
public enum AutoCaseTypeEnum {


    SCRIPT_CLASS(1, "自动化脚本类"),
    SUPPER_CLASS(2, "自动化超类"),
    PO_FUNCTION(3, "封装的PO方法"),
    DATA_FACTORY(4, "数据工厂");
//    INTERFACE_CASE(1, "接口测试用例"),
//    UI_CASE(2, "ui测试用例");


    private final Integer code;
    private final String description;

    AutoCaseTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
