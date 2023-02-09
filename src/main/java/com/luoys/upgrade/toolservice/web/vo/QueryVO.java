package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 通用查询类
 *
 * @author luoys
 */
@Data
public class QueryVO {

    /**
     * 账号id
     */
    private Integer userId;

    /**
     * 账号名
     */
    private String username;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 超类id
     */
    private Integer supperCaseId;

    /**
     * 套件id
     */
    private Integer suiteId;

    /**
     * 业务类型(通用)
     */
    private Integer type;

    /**
     * 业务名(通用)
     */
    private String name;

    /**
     * 业务状态(通用)
     */
    private Integer status;

    /**
     * 页码(通用)
     */
    private Integer pageIndex;

    /**
     * 每页大小(通用)
     */
    private Integer pageSize;

}
