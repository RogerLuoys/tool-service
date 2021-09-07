package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 自动化测试集状态
 *
 * @author luoys
 */
@Getter
public enum AutoSuiteStatusEnum {

    SUITE_FREE(1, "测试集空闲"),
    SUITE_RUNNING(2, "测试集执行中");

    private final Integer code;
    private final String description;

    AutoSuiteStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
