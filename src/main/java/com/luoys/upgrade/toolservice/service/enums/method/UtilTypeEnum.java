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
public enum UtilTypeEnum {

    SLEEP(1, "auto.task.sleep", "强制睡眠"),
    GET_JSON(2, "auto.task.getJson", "根据json key取对应的值"),
    GET_JSON_ANY(3, "auto.task.getJsonAny", "根据json和子json第一个key取对应的值"),
    GET_RANDOM(4, "auto.task.getRandom", "获取随机数"),
    GET_TIME(5, "auto.task.getTime", "获取当前linux时间");

    private final Integer code;
    /**
     * 工具相关步骤，脚本模式的调用模板
     */
    private final String scriptTemplate;
    private final String description;

    UtilTypeEnum(Integer code, String scriptTemplate, String description) {
        this.code = code;
        this.scriptTemplate = scriptTemplate;
        this.description = description;
    }

    private static final Map<Integer, UtilTypeEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, UtilTypeEnum> TEMPLATE_MAP = new HashMap<>();

    static {
        for (UtilTypeEnum e : UtilTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            TEMPLATE_MAP.put(e.getScriptTemplate().toLowerCase(), e);
        }
    }

    public static UtilTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static UtilTypeEnum fromScriptTemplate(String scriptTemplate) {
        return scriptTemplate == null ? null : TEMPLATE_MAP.get(scriptTemplate.toLowerCase());
    }

}
