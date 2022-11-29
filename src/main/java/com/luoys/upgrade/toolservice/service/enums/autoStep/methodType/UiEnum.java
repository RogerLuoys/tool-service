package com.luoys.upgrade.toolservice.service.enums.autoStep.methodType;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * ui操作类型
 *
 * @author luoys
 */
@Getter
public enum UiEnum {

    // auto.ui.openUrl(url);
    OPEN_URL(1, "openUrl", "打开网站"),
    // auto.ui.click(xpath);
    CLICK(2, "click", "单击控件"),
    // auto.ui.clickByJs(xpath);
    CLICK_BY_JS(3, "clickByJs", "通过js点击"),
    // auto.ui.clickByMove(xpath);
    CLICK_BY_MOVE(4, "clickByMove", "先移动再点击"),
    // auto.ui.sendKey(xpath, keyword);
    SEND_KEY(5, "sendKey", "键盘输入"),
    // auto.ui.sendKeyByEnter(xpath, keyword);
    SEND_KEY_BY_ENTER(6, "isExist", "输入后按Enter"),
    // auto.ui.move(xpath);
    MOVE(7, "move", "鼠标移动到指定元素上"),
    // auto.ui.hover(xpath);
    DRAG(8, "drag", "从一个元素位置拖拽到另一个元素位置"),
    // auto.ui.executeJs(javaScript);
    EXECUTE_JS(9, "executeJs", "执行java script脚本"),
    // auto.ui.switchTab();
    SWITCH_TAB(10, "switchTab", "切换到最后一个标签页，并关闭其它"),
    // auto.ui.clearCookies();
    CLEAR_COOKIES(11, "clearCookies", "删除所有cookies");


    private final Integer code;
    private final String name;
    private final String description;

    UiEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private static final Map<Integer, UiEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, UiEnum> TEMPLATE_MAP = new HashMap<>();

    static {
        for (UiEnum e : UiEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            TEMPLATE_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static UiEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static UiEnum fromName(String name) {
        return name == null ? null : TEMPLATE_MAP.get(name.toLowerCase());
    }

}
