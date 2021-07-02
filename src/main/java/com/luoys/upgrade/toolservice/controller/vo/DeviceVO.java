package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

@Data
public class DeviceVO {

    private Integer toolConfigId;

    private String title;

    private String description;

//    private String items;

    /**
     * 1 数据源, 2 测试环境, 3 移动设备
     */
    private Integer type;

    private String ownerId;

    private Integer permission;

}
