package com.luoys.upgrade.toolservice.service.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 用例关联步骤的类型
 *
 * @author luoys
 */
@Getter
public enum RelatedStepTypeEnum {

    BEFORE_TEST(1, "前置步骤@BeforeClass"),
    MAIN_TEST(2, "主要步骤@Test"),
    AFTER_TEST(3, "收尾步骤@AfterClass"),
    BEFORE_SUITE(4, "相当于@BeforeSuite"),
    AFTER_SUITE(5, "相当于@AfterSuite");


    private final Integer code;
    private final String description;

    RelatedStepTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, RelatedStepTypeEnum> CODE_MAP = new HashMap<>();

    static {
        for (RelatedStepTypeEnum e : RelatedStepTypeEnum.values()) {
            CODE_MAP.put(e.getCode(), e);
        }
    }

    public static RelatedStepTypeEnum fromCode(Integer code) {
        return code == null ? null : CODE_MAP.get(code);
    }

}
