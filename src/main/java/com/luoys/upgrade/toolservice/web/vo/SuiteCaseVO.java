package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 套件关联的用例类
 *
 * @author luoys
 */
@Data
public class SuiteCaseVO {

    /**
     * 套件业务id
     */
    private String suiteId;

    /**
     * 用例的业务id
     */
    private String caseId;

    /**
     * 套件关联用例的执行状态：1 未执行，2 失败，3成功
     */
    private Integer status;

    /**
     * 用例执行顺序，默认999
     */
    private Integer sequence;

    /**
     * 套件关联用例的基本信息
     */
    private AutoCaseSimpleVO autoCase;

}
