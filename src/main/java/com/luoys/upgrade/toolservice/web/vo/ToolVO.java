package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.*;
import lombok.Data;
import java.util.List;

/**
 * 转换后的完整工具详情
 * @author luoys
 */
@Data
public class ToolVO {

    /**
     * uuid
     */
    private String toolId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 步骤类型：1 SQL，2 HTTP，3 RPC，4 聚合
     */
    private Integer type;

    /**
     * 查看权限：1 公开，2 仅自己可见合
     */
    private Integer permission;

    /**
     * 入参列表，List<CommonDTO>类型
     */
    private List<ParameterDTO> parameterList;

    /**
     * 当type为4时使用，List<CommonDTO>类型
     */
    private List<String> toolList;

    /**
     * 数据库请求
     */
    private JdbcDTO jdbc;

    /**
     * http请求
     */
    private HttpRequestDTO httpRequest;

    /**
     * rpc请求
     */
    private RpcDTO rpc;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
