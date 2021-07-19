package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

/**
 * 转换后的简要工具信息，用于列表展示
 * @author luoys
 */
@Data
public class ToolSimpleVO {
    private String toolId;
    private String title;
    private String description;
    private Integer type;
    private Integer permission;
    private String ownerId;
    private String ownerName;
}
