package com.luoys.upgrade.toolservice.dao.po;

import lombok.Data;

/**
 * resource-资源表
 *
 * @author luoys
 */
@Data
public class ResourcePO {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 资源类型：1 数据库，2 从节点
     */
    private Integer type;

    /**
     * 查看权限：1 公开，2 仅自己可见合
     */
    private Integer permission;

    /**
     * 数据源地址
     */
    private String jdbcUrl;

    /**
     * 数据源驱动
     */
    private String jdbcDriver;

    /**
     * 数据源用户名
     */
    private String jdbcUsername;

    /**
     * 数据源密码
     */
    private String jdbcPassword;

    /**
     * 设备型号
     */
    private String slaveIp;

    /**
     * 分辨率
     */
    private String slavePort;

    /**
     * 屏幕尺寸
     */
    private Integer slaveThread;

    /**
     * 使用人id
     */
    private Integer projectId;

    /**
     * 所属人id
     */
    private Integer ownerId;

    /**
     * 所属人昵称
     */
    private String ownerName;

}
