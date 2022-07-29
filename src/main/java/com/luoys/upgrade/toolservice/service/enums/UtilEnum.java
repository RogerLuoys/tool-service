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
public enum UtilEnum {

    SLEEP(1, "auto.task.sleep", "强制睡眠"),
    GET_JSON_VALUE(2, "auto.task.getJsonValue", "根据json key 取对应的值"),
    GET_TIME(3, "auto.task.getTime", "获取当前linux时间"),
    GET_RANDOM_NUMBER(1, "auto.task.getRandomNumber", "获取随机数");
//    SLEEP(1, "auto.task.sleep", "强制睡眠"),
//    SLEEP(1, "auto.task.sleep", "强制睡眠"),
//    SLEEP(1, "auto.task.sleep", "强制睡眠"),
//    SLEEP(1, "auto.task.sleep", "强制睡眠");

    private final Integer code;
    /**
     * 工具相关步骤，脚本模式的调用模板
     */
    private final String scriptTemplate;
    private final String description;

    UtilEnum(Integer code, String scriptTemplate, String description) {
        this.code = code;
        this.scriptTemplate = scriptTemplate;
        this.description = description;
    }

    private static final Map<Integer, UtilEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, UtilEnum> TEMPLATE_MAP = new HashMap<>();

    static {
        for (UtilEnum e : UtilEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            TEMPLATE_MAP.put(e.getScriptTemplate().toLowerCase(), e);
        }
    }

    public static UtilEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static UtilEnum fromScriptTemplate(String scriptTemplate) {
        return scriptTemplate == null ? null : TEMPLATE_MAP.get(scriptTemplate.toLowerCase());
    }

}
