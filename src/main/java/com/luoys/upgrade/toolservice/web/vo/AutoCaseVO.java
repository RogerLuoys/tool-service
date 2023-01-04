package com.luoys.upgrade.toolservice.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luoys.upgrade.toolservice.service.dto.ParameterDTO;
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
     * 用例计划完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    /**
     * 前置步骤(UI模式)，即@BeforeTest
     */
    private List<CaseStepVO> preStepList;

//    /**
//     * 前置步骤(脚本模式)，即@BeforeTest
//     */
//    private String preSteps;

    /**
     * 主要步骤(UI模式)
     */
    private List<CaseStepVO> mainStepList;

    /**
     * 主要步骤(脚本模式)，即@Test
     */
    private String mainSteps;

    /**
     * 收尾步骤(UI模式)，即@AfterTest
     */
    private List<CaseStepVO> afterStepList;

//    /**
//     * 收尾步骤(脚本模式)，即@AfterTest
//     */
//    private String afterSteps;

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
