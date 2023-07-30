package com.luoys.upgrade.toolservice.dao.po;

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
     * 1 自动化脚本，2 自动化超类，3 封装的方法(PO)，4 数据工厂
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
     * 用例计划完成时间
     */
    private Date finishTime;

    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 所属人id
     */
    private Integer ownerId;

}
