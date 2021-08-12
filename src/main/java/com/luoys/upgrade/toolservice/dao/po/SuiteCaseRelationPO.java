package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * suite_case_relation
 * @author
 */
@Data
public class SuiteCaseRelationPO implements Serializable {
    private Integer id;

    /**
     * 业务id
     */
    private String suiteId;

    /**
     * 业务id
     */
    private String caseId;

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

    private static final long serialVersionUID = 1L;
}
