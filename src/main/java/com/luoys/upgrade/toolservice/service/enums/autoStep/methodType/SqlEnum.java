package com.luoys.upgrade.toolservice.service.enums.autoStep.methodType;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SqlEnum {

    DB_NAME(1, "dbName", "在指定的数据源中执行sql"),
    SQL_EXECUTE_BY_JSON(2, "executeByJson", "json格式执行sql");

    private final Integer code;
    private final String name; //sql 步骤的方法名
    private final String description;

    SqlEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private static final Map<Integer, SqlEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, SqlEnum> NAME_MAP = new HashMap<>();

    static {
        for (SqlEnum e : SqlEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
            NAME_MAP.put(e.getName().toLowerCase(), e);
        }
    }

    public static SqlEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

    public static SqlEnum fromName(String name) {
        return name == null ? null : NAME_MAP.get(name.toLowerCase());
    }

}
