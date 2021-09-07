package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 通用默认参数
 *
 * @author luoys
 */
@Getter
public enum KeywordEnum {

    PRE_PARAM_ENV(null, "${env}", "自动化执行的环境参数"),
    DEFAULT_STEP_NAME(null, "新增测试步骤", "默认步骤名称"),
    DEFAULT_USER(101, "默认用户", "默认用户id和昵称"),
    DEFAULT_PAGE_SIZE(10, null, "默认分页大小"),
    DEFAULT_CASE_SEQUENCE(999, null, "默认用例执行优先级");

    private final Integer code;
    private final String value;
    private final String description;

    KeywordEnum(Integer code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

}
