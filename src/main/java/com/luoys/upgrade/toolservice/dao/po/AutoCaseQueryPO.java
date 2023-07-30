package com.luoys.upgrade.toolservice.dao.po;

import lombok.Data;

import java.util.Date;


/**
 * auto_case 搜索专用
 *
 * @author luoys
 */
@Data
public class AutoCaseQueryPO {

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
     * 1 自动化脚本，2 自动化超类，3 封装的方法(PO)，4 数据工厂
     */
    private Integer type;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 所属人id
     */
    private Integer ownerId;

    /**
     * 页码
     */
    private Integer startIndex;

    private Date finishTime;

}
