package com.luoys.upgrade.toolservice.service.enums.method;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 断言类型
 *
 * @author luoys
 */
@Getter
public enum AssertTypeEnum {

//    NO_ASSERT(-1, "不断言"),
    IS_EQUALS(1, "auto.assertion.isEquals", "完全匹配"),
    IS_CONTAINS(2, "auto.assertion.isContains", "模糊匹配"),
    IS_BE_CONTAINS(3, "auto.assertion.isBeContains", "判断是否为真"),
    IS_DELETED(4, "auto.assertion.isDeleted", "判断是否为假"),
    IS_NOT_DELETED(5, "auto.assertion.isNotDeleted", "判断是否为假"),
    IS_GREATER(6, "auto.assertion.isGreater", "判断是否为假"),
    IS_SMALLER(7, "auto.assertion.isSmaller", "判断是否为假"),
    IS_XPATH_EXIST(8, "auto.assertion.isXpathExist", "判断页面元素是否存在"),
    IS_XPATH_NOT_EXIST(8, "auto.assertion.isXpathNotExist", "判断页面元素是否存在");


    private final Integer code;
    private final String scriptTemplate;
    private final String description;

    AssertTypeEnum(Integer code, String scriptTemplate, String description) {
        this.code = code;
        this.scriptTemplate = scriptTemplate;
        this.description = description;
    }

    private static final Map<Integer, AssertTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (AssertTypeEnum e : AssertTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static AssertTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
