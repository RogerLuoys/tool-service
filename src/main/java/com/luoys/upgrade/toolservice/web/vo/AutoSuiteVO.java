package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.ParameterDTO;
import lombok.Data;

import java.util.List;

/**
 * 全量的套件类
 *
 * @author luoys
 */
@Data
public class AutoSuiteVO {

    /**
     * 业务id
     */
    private Integer suiteId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 套件状态：1 空闲，2 执行中
     */
    private Integer status;

    /**
     * 用例执行的环境
     */
    private String environment;

    /**
     * 入参列表，List<CommonDTO>类型
     */
    private List<ParameterDTO> parameterList;

    /**
     * 步骤间的等待时间
     */
    private Integer stepSleep;

    /**
     * 用例执行的最长时间
     */
    private Integer caseMaxTime;

    /**
     * 用例列表，PageInfo类型，通过getList取列表
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
    private Integer ownerId;

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
