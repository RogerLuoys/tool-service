package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 自动化套件状态
 *
 * @author luoys
 */
@Getter
public enum AutoSuiteStatusEnum {

    SUITE_FREE(1, "套件空闲"),
    SUITE_RUNNING(2, "套件执行中"),
    SUITE_PENDING(3, "套件排队中"),
    SUITE_BLOCK(4, "套件停用");

    private final Integer code;
    private final String description;

    AutoSuiteStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
