package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.HttpRequestDTO;
import com.luoys.upgrade.toolservice.service.dto.JdbcDTO;
import com.luoys.upgrade.toolservice.service.dto.RpcDTO;
import com.luoys.upgrade.toolservice.service.dto.UiDTO;
import lombok.Data;

@Data
public class AutoStepVO {

    private Integer stepId;

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
     * 实际结果取到后的等待时间
     */
    private Integer afterSleep;

    /**
     * 断言类型：1 完全匹配，2 模糊匹配，-1 不校验
     */
    private Integer assertType;

    /**
     * 实际结果
     */
    private String actualResult;

    /**
     * 预期结果
     */
    private String expectResult;

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
    private Integer isPublic;

}
