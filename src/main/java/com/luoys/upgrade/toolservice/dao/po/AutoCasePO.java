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

    private Integer id;

    /**
     * 业务id
     */
    private String caseId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 用例执行的最长时间
     */
    private Integer maxTime;

    /**
     * 1 接口，2 UI
     */
    private Integer type;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
