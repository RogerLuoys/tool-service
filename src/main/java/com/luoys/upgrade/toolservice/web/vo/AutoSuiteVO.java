package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

import java.util.List;

/**
 * 全量的测试集类
 *
 * @author luoys
 */
@Data
public class AutoSuiteVO {

    /**
     * 业务id
     */
    private String suiteId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 测试集状态：1 空闲，2 执行中
     */
    private Integer status;

    /**
     * 用例执行的环境
     */
    private String environment;

    /**
     * 步骤间的等待时间
     */
    private Integer stepSleep;

    /**
     * 用例执行的最长时间
     */
    private Integer caseMaxTime;

    /**
     * 用例列表
     */
//    private List<SuiteCaseVO> caseList;

    private PageInfo<SuiteCaseVO> relatedCase;

    /**
     * 用例总数
     */
    private Integer total;

    /**
     * 成功用例数
     */
    private Integer passed;

    /**
     * 失败用例数
     */
    private Integer failed;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

    /**
     * ui用例是否执行完成
     */
    private Boolean isUiCompleted;

    /**
     * api用例是否执行完成
     */
    private Boolean isApiCompleted;

}
