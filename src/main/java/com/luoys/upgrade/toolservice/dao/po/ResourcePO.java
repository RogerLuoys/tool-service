package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;

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
    private String deviceModel;

    /**
     * 屏幕尺寸
     */
    private String deviceSize;

    /**
     * 分辨率
     */
    private String deviceDpi;

    /**
     * 设备操作系统
     */
    private String deviceOs;

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
