package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import lombok.Data;

/**
 * device
 * @author
 */
@Data
public class DevicePO implements Serializable {
    private Integer id;

    private String title;

    private String description;

    private String detail;

    /**
     * 1 数据源, 2 测试环境, 3 移动设备
     */
    private Integer type;

    private String ownerId;

    private String ownerName;

    private Integer permission;

    private static final long serialVersionUID = 1L;
}
