package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * suite_case_relation，suite-case关联类，包含各自部分信息
 * @author luoys
 */
@Data
public class SuiteCaseRelationPO {
    private Integer id;

    /**
     * 套件主键id
     */
    private Integer suiteId;

    /**
     * 用例主键id
     */
    private Integer caseId;

    /**
     * 测试集关联用例的执行状态：1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 用例执行顺序，默认999
     */
    private Integer sequence;

    /**
     * 用例名称
     */
    private String caseName;

    /**
     * 1 接口，2 UI
     */
    private Integer caseType;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Integer caseStatus;

}
