package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum UiTypeEnum {

    START(1, "启动浏览器"),
    CLICK(2, "单击控件"),
    SEND_KEY(3, "键盘输入"),
    IS_EXIST(4, "判断控件是否存在");


    private final Integer code;
    private final String description;

    UiTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, UiTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (UiTypeEnum e : UiTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static UiTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
