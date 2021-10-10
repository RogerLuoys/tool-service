package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.*;
import lombok.Data;

import java.util.List;

/**
 * 全量的步骤类
 *
 * @author luoys
 */
@Data
public class AutoStepVO {

    /**
     * 业务id
     */
    private String stepId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 步骤类型：1 SQL，2 HTTP，3 RPC，4 UI
     */
    private Integer type;

//    /**
//     * 聚合工具
//     */
//    private List<StepDTO> stepList;

    /**
     * 聚合工具-IF区域
     */
    private List<StepDTO> ifStepList;

    /**
     * 聚合工具-THEN区域
     */
    private List<StepDTO> thenStepList;

    /**
     * 聚合工具-ELSE区域
     */
    private List<StepDTO> elseStepList;

    /**
     * 数据库操作类
     */
    private JdbcDTO jdbc;

    /**
     * http操作类
     */
    private HttpRequestDTO httpRequest;

    /**
     * rpc操作类
     */
    private RpcDTO rpc;

    /**
     * ui操作类
     */
    private UiDTO ui;

    /**
     * 等待时间，在实际结果取到后、断言之前等待
     */
    private Integer afterSleep;

    /**
     * 断言类型：1 完全匹配，2 模糊匹配，-1 不校验
     */
    private Integer assertType;

    /**
     * 实际结果
     */
    private String assertActual;

    /**
     * 预期结果
     */
    private String assertExpect;

    /**
     * 断言结果
     */
    private Boolean assertResult;

    /**
     * 用例执行环境（参数）
     */
    private String environment;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

    /**
     * 是否公用
     */
    private Boolean isPublic;

}
