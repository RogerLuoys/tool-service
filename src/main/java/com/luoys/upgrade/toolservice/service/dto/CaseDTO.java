package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

// 参考命令模式

/**
 * 一个完整的用例
 */
@Data
public class CaseDTO {

    /**
     * 用例id
     */
    private Integer caseId;

    /**
     * 用例父类的id
     */
    private Integer supperCaseId;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Integer status;

//    /**
//     * 脚本参数列表
//     */
//    private List<ParameterDTO> parameterList;

    /**
     * 脚本参数列表
     */
    private Map<String, String> params;

//    /**
//     * UI启动参数列表
//     */
//    private List<ParameterDTO> argumentList;

    /**
     * UI启动类型
     */
    private Integer uiType;

    /**
     * UI启动参数列表
     */
    private String[] uiArgument;

    /**
     * 相当于测试类的@Test
     */
    private List<StepDTO> test;

    /**
     * 相当于测试类的@BeforeClass
     */
    private List<StepDTO> beforeClass;

    /**
     * 相当于测试类的@AfterClass
     */
    private List<StepDTO> afterClass;

    /**
     * 相当于测试超类的@BeforeSuite
     */
    private List<StepDTO> beforeSuite;

    /**
     * 相当于测试超类的@AfterSuite
     */
    private List<StepDTO> afterSuite;

    /**
     * 相当于测试超类的@BeforeClass
     */
    private List<StepDTO> supperBeforeClass;

    /**
     * 相当于测试超类的@AfterClass
     */
    private List<StepDTO> supperAfterClass;

}
