package com.luoys.upgrade.toolservice.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 全量的用例类
 *
 * @author luoys
 */
@Data
public class AutoCaseVO {

    /**
     * 业务id
     */
    private Integer caseId;

    /**
     * 业务id
     */
    private Integer supperCaseId;

    /**
     * 目录id
     */
    private Integer folderId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 1 接口，2 UI
     */
    private Integer type;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 用例执行的最长时间
     */
    private Integer timeOut;

    /**
     * 脚本参数列表
     */
    private List<ConfigVO> parameterList;

    /**
     * UI启动参数列表
     */
    private List<ConfigVO> argumentList;

    /**
     * 目录列表
     */
    private List<ConfigVO> folderList;

    /**
     * 用例计划完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    /**
     * 前置步骤(UI模式)，即@BeforeSuite
     */
    private List<AutoStepVO> beforeSuiteList;

    /**
     * 前置步骤(脚本模式)，即@BeforeSuite
     */
    private String beforeSuiteScript;

    /**
     * 前置步骤(UI模式)，即@BeforeClass
     */
    private List<AutoStepVO> beforeClassList;

    /**
     * 前置步骤(脚本模式)，即@BeforeClass
     */
    private String beforeClassScript;

    /**
     * 主要步骤(UI模式)
     */
    private List<AutoStepVO> testList;

    /**
     * 主要步骤(脚本模式)，即@Test
     */
    private String testScript;

    /**
     * 收尾步骤(UI模式)，即@AfterClass
     */
    private List<AutoStepVO> afterClassList;

    /**
     * 收尾步骤(脚本模式)，即@AfterClass
     */
    private String afterClassScript;

    /**
     * 前置步骤(UI模式)，即@AfterSuite
     */
    private List<AutoStepVO> afterSuiteList;

    /**
     * 前置步骤(脚本模式)，即@BeforeSuite
     */
    private String afterSuiteScript;

    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 所属人id
     */
    private Integer ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
