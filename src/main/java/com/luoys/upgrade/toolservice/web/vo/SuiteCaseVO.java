package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class SuiteCaseVO {

    /**
     * 测试集业务id
     */
    private String suiteId;

    /**
     * 用例的业务id
     */
    private String caseId;

    /**
     * 测试集关联用例的执行状态：1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 用例执行顺序，默认999
     */
    private Integer sequence;

    /**
     * 测试集关联的用例
     */
    private AutoCaseSimpleVO autoCase;

}
