package com.luoys.upgrade.toolservice.dao.po;

import lombok.Data;

/**
 * auto_suite专用查询
 *
 * @author luoys
 */
@Data
public class AutoSuiteQueryPO {

    /**
     * 业务id
     */
    private Integer suiteId;

    /**
     * 名称
     */
    private String name;

    /**
     * 套件状态：1 空闲，2 执行中，3 排队中
     */
    private Integer status;

    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 所属责任人id
     */
    private Integer ownerId;

    /**
     * 页码
     */
    private Integer startIndex;

}
