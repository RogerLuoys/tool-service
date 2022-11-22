package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * auto_case
 *
 * @author luoys
 */
@Data
public class AutoCasePO {

    /**
     * 业务id
     */
    private Integer id;

    /**
     * 业务id
     */
    private Integer supperCaseId;

//    /**
//     * 业务id
//     */
//    private String caseId;

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

//    /**
//     * 用例执行环境（参数）
//     */
//    private String environment;

//    /**
//     * 入参列表，List<CommonDTO>类型
//     */
//    private String parameter;

    /**
     * 用例计划完成时间
     */
    private Date finishTime;

//    /**
//     * 用例的脚本模式
//     */
//    private String mainSteps;

    /**
     * 所属人id
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
