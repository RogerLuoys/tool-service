package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    ERROR_FOR_MESSAGE(-1, null, "前端会直接把message作为提示"),
    ERROR_FOR_CUSTOM(0, "业务处理失败", "前端自定义处理"),
    SUCCESS_FOR_MESSAGE(1, null, "前端会直接把message作为提示"),
    SUCCESS_FOR_CUSTOM(2, "业务处理完成", "前端自定义处理");

    private final Integer code;
    private final String value;
    private final String description;

    ResultEnum(Integer code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }
}
