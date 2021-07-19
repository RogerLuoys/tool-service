package com.luoys.upgrade.toolservice.controller.vo;

import com.luoys.upgrade.toolservice.controller.dto.ContainerDTO;
import com.luoys.upgrade.toolservice.controller.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.controller.dto.MobilePhoneDTO;
import lombok.Data;

/**
 * 转换后的完整设备详情
 * @author luoys
 */
@Data
public class DeviceVO {

    private Integer deviceId;

    private String title;

    private String description;

    private DataSourceDTO dataSource;

    private MobilePhoneDTO mobilePhone;

    private ContainerDTO container;

    /**
     * 1 数据源, 2 测试环境, 3 移动设备
     */
    private Integer type;

    private String ownerId;

    private String ownerName;

    private Integer permission;


}
