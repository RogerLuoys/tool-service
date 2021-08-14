package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

@Getter
public enum KeywordEnum {

//    SEPARATOR(" &&& ", "通用分隔符"),
    PARAM_SYMBOL("$$${", "参数占位符"),
    PARAMETER_NAME("[A-Za-z0-9]", "参数名字"),
    JSON_START("{\"", "Json开始字符"),
    DEFAULT_USER("101", "默认用户"),
    DEFAULT_STEP_NAME("新增测试步骤", "默认步骤名称");

    private final String code;
    private final String description;

    KeywordEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
