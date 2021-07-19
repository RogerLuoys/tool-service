package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

/**
 * 转换后的简要设备信息，用于列表展示
 * @author luoys
 */
@Data
public class DeviceSimpleVO {

    private Integer deviceId;

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

}
