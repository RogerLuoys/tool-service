package com.luoys.upgrade.toolservice.web.vo;


import lombok.Data;

import java.util.List;

/**
 * 脚本形式的用例
 *
 * @author luoys
 */
@Data
public class ScriptVO {

    /**
     * 业务id
     */
    private Integer caseId;

    /**
     * 业务id
     */
    private Integer supperCaseId;

    /**
     * 脚本类型：1 BeforeClass，2 Test，3 AfterClass，4 BeforeSuite，5 AfterSuite
     */
    private Integer type;

    /**
     * 脚本
     */
    private String script;

    /**
     * 步骤列表(UI模式)
     */
    private List<CaseStepVO> stepList;

    /**
     * 所属项目id
     */
    private Integer projectId;

}
