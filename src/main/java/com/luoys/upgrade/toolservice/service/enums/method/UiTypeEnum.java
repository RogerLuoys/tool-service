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

    // auto.ui.clickByJs(xpath);
    CLICK_BY_JS(3, "auto.ui.clickByJs", "通过js点击"),

    // auto.ui.clickByMove(xpath);
    CLICK_BY_MOVE(4, "auto.ui.clickByMove", "先移动再点击"),

    // auto.ui.sendKey(xpath, keyword);
    SEND_KEY(5, "auto.ui.sendKey", "键盘输入"),

    // auto.ui.sendKeyByEnter(xpath, keyword);
    SEND_KEY_BY_ENTER(6, "auto.ui.isExist", "输入后按Enter"),

    // auto.ui.move(xpath);
    MOVE(7, "auto.ui.move", "鼠标移动到指定元素上"),

    // auto.ui.hover(xpath);
    DRAG(8, "auto.ui.drag", "从一个元素位置拖拽到另一个元素位置"),

    // auto.ui.executeJs(javaScript);
    EXECUTE_JS(9, "auto.ui.executeJs", "执行java script脚本"),

    // auto.ui.switchTab();
    SWITCH_TAB(10, "auto.ui.switchTab", "切换到最后一个标签页，并关闭其它"),

    // auto.ui.clearCookies();
    CLEAR_COOKIES(11, "auto.ui.clearCookies", "删除所有cookies");


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
