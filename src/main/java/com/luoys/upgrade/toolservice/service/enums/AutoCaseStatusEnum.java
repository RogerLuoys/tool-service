package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 自动化用例状态
 *
 * @author luoys
 */
@Getter
public enum AutoCaseStatusEnum {

    PLANNING(1, "未执行过，计划中"),
    FAIL(2, "最近一次执行失败"),
    SUCCESS(3, "最近一次执行成功");

    private final Integer code;
    private final String description;

    AutoCaseStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
