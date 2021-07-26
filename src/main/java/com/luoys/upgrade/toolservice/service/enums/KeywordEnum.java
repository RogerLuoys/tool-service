package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

@Getter
public enum KeywordEnum {

    SEPARATOR(" &&& ", "通用分隔符"),
    PARAM_SYMBOL("$$${", "参数占位符"),
    JSON_START("{\"", "Json开始字符"),
    DEFAULT_USER("101", "default");

    private final String code;
    private final String description;

    KeywordEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}