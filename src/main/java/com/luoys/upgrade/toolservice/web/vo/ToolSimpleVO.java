package com.luoys.upgrade.toolservice.web.vo;
import lombok.Data;


/**
 * 转换后的简要工具信息，用于列表展示
 * @author luoys
 */
@Data
public class ToolSimpleVO {

    /**
     * 业务id
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
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

}
