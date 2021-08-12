package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * case_step_relation
 * @author 
 */
@Data
public class CaseStepRelationPO implements Serializable {
    private Integer id;

    /**
     * 业务id
     */
    private String caseId;

    /**
     * 业务id
     */
    private String stepId;

    /**
     * 步骤执行顺序
     */
    private Integer sequence;

    /**
     * 1 前置步骤，2 主要步骤，3 收尾步骤
     */
    private Byte type;

    /**
     * 逻辑删除
     */
    private Byte isDelete;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 最近修改时间
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}