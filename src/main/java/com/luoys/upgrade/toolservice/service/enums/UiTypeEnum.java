package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * ui操作类型
 *
 * @author luoys
 */
@Getter
public enum UiTypeEnum {

    OPEN_URL(1, "打开网站"),
    CLICK(2, "单击控件"),
    SEND_KEY(3, "键盘输入"),
    IS_EXIST(4, "判断控件是否存在"),
    SWITCH_FRAME(5, "切换frame"),
    HOVER(6, "鼠标悬停");


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
