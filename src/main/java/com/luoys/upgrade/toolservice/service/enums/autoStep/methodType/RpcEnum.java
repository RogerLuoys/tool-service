package com.luoys.upgrade.toolservice.service.enums.autoStep.methodType;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum RpcEnum {

    INVOKE(1, "invoke", "调用rpc接口"),
    RPC_EXECUTE_BY_JSON(2, "executeByJson", "json格式调用rpc接口"),
    SET_DEFAULT_URL(3, "setDefaultUrl", "设置调用环境");

    private final Integer code;
    private final String name; //rpc 步骤的方法名
    private final String description;

    RpcEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private static final Map<Integer, RpcEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, RpcEnum> NAME_MAP = new HashMap<>();

    static {
        for (RpcEnum e : RpcEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            NAME_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static RpcEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static RpcEnum fromName(String name) {
        return name == null ? null : NAME_MAP.get(name.toLowerCase());
    }

}
