package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 未定义
 *
 * @author luoys
 */
@Getter
public enum ResourceSuiteTypeEnum {

    SUITE_SLAVE(1, "套件指定的服务器"),
    SLAVE_USAGE(2, "执行服务器的使用情况");

    private final Integer code;
    private final String description;

    ResourceSuiteTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


}
