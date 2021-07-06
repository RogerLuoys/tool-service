package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import lombok.Data;

/**
 * tool
 * @author
 */
@Data
public class ToolPO implements Serializable {
    private Integer id;

    private String toolId;

    private String title;

    private String description;

    private String params;

    private String template;

    /**
     * 1 SQL, 2 HTTP, 3 DUBBO
     */
    private Integer type;

    /**
     * 1 仅自己可见, 2 公开
     */
    private Integer permission;

    private String ownerId;

    private static final long serialVersionUID = 1L;
}
