package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 资源类型
 *
 * @author luoys
 */
@Getter
@Deprecated
public enum ResourceTypeEnum {

    DATA_SOURCE(1, "数据库"),
    DEVICE(2, "手机"),
    TEST_ENV(3, "测试环境"),
    SLAVE_NODE(4, "从节点");

    private final Integer code;
    private final String description;

    ResourceTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    private static final Map<Integer, ResourceTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (ResourceTypeEnum e : ResourceTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static ResourceTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
