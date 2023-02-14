package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

/**
 * 通用默认参数
 *
 * @author luoys
 */
@Getter
public enum DefaultEnum {

    DEFAULT_DEMO_PROJECT(-1, "演示项目", "演示项目"),
    DEFAULT_DEMO_USER(-1, "演示账号", "03f798da9d3e6a10cfd620229fe122d4"),
    DEFAULT_UNKNOWN_USER(-2, "未知用户", "数据库中未查到指定用户"),
    DEFAULT_PAGE_SIZE(10, null, "默认分页大小"),
    DEFAULT_CASE_SEQUENCE(999, null, "默认用例执行优先级");

    private final Integer code;
    private final String value;
    private final String description;

    DefaultEnum(Integer code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

}
