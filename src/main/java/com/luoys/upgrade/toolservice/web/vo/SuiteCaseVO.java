package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class SuiteCaseVO {

    /**
     * 测试集业务id
     */
    private String suiteId;

    /**
     * 用例业务id
     */
    private String caseId;

    /**
     * 用例执行顺序，默认999
     */
    private Integer sequence;

    /**
     * 用例名称
     */
    private String name;

    /**
     * 1 接口，2 UI
     */
    private Integer type;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Integer status;

}
