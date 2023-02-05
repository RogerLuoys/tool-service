package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 自动化套件的机器类型
 *
 * @author luoys
 */
@Getter
public enum AutoSuiteSlaveTypeEnum {

    LOCALHOST(1, "localhost执行套件"),
    ANY_SLAVE(2, "任意机器执行套件"),
    ASSIGN_SLAVE(3, "指定机器执行套件");

    private final Integer code;
    private final String description;

    AutoSuiteSlaveTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
