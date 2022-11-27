package com.luoys.upgrade.toolservice.service.enums.method;

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

    // auto.ui.openUrl(url);
    OPEN_URL(1, "auto.ui.openUrl", "打开网站"),

    // auto.ui.click(xpath);
    CLICK(2, "auto.ui.click", "单击控件"),

    // auto.ui.sendKey(xpath, keyword);
    SEND_KEY(3, "auto.ui.sendKey", "键盘输入"),

    // auto.ui.isExist(xpath);
    IS_EXIST(4, "auto.ui.isExist", "判断控件是否存在"),

    // auto.ui.switchFrame(xpath);
    SWITCH_FRAME(5, "auto.ui.switchFrame", "切换frame"),

    // auto.ui.hover(xpath);
    HOVER(6, "auto.ui.hover", "鼠标悬停");


    private final Integer code;
    private final String scriptTemplate;
    private final String description;

    UiTypeEnum(Integer code, String scriptTemplate, String description) {
        this.code = code;
        this.scriptTemplate = scriptTemplate;
        this.description = description;
    }

    private static final Map<Integer, UiTypeEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, UiTypeEnum> TEMPLATE_MAP = new HashMap<>();

    static {
        for (UiTypeEnum e : UiTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            TEMPLATE_MAP.put(e.getScriptTemplate().toLowerCase(), e);
        }
    }

    public static UiTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static UiTypeEnum fromScriptTemplate(String scriptTemplate) {
        return scriptTemplate == null ? null : TEMPLATE_MAP.get(scriptTemplate.toLowerCase());
    }

}
