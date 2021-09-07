package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.DeviceDTO;
import lombok.Data;

/**
 * 全量的资源类
 *
 * @author luoys
 */
@Data
public class ResourceVO {

    /**
     * 业务id
     */
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 资源类型：1 数据库，2 设备，3 测试环境，4 从节点
     */
    private Integer type;

    /**
     * 查看权限：1 公开，2 仅自己可见合
     */
    private Integer permission;

    /**
     * 数据源
     */
    private DataSourceDTO dataSource;

    /**
     * 设备
     */
    private DeviceDTO device;

    /**
     * 容器域名或虚拟机ip port
     */
    private String envUrl;

    /**
     * 从节点ip port
     */
    private String slaveUrl;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人昵称
     */
    private String ownerName;

    /**
     * 使用人id
     */
    private String userId;

    /**
     * 使用人昵称
     */
    private String userName;

}
