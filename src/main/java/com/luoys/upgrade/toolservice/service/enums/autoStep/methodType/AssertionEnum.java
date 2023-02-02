package com.luoys.upgrade.toolservice.service.enums.autoStep.methodType;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 断言类型
 *
 * @author luoys
 */
@Getter
public enum AssertionEnum {

    //    NO_ASSERT(-1, "不断言"),
    IS_EQUALS(1, "isEquals", "完全匹配"),
    IS_CONTAINS(2, "isContains", "模糊匹配"),
    IS_BE_CONTAINS(3, "isBeContains", "判断是否为真"),
    IS_DELETED(4, "isDeleted", "判断是否为假"),
    IS_NOT_DELETED(5, "isNotDeleted", "判断是否为假"),
    IS_GREATER(6, "isGreater", "判断是否为假"),
    IS_SMALLER(7, "isSmaller", "判断是否为假"),
    IS_XPATH_EXIST(8, "isXpathExist", "判断页面元素是否存在"),
    IS_XPATH_NOT_EXIST(9, "isXpathNotExist", "判断页面元素是否存在");


    private final Integer code;
    private final String name; //Assertion步骤的方法名
    private final String description;

    AssertionEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private static final Map<Integer, AssertionEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, AssertionEnum> NAME_MAP = new HashMap<>();

    static {
        for (AssertionEnum e : AssertionEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            NAME_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static AssertionEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }
    public static AssertionEnum fromName(String name) {
        return name == null ? null : NAME_MAP.get(name.toLowerCase());
    }

}
