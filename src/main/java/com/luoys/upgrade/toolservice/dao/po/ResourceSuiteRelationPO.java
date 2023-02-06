package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * resource_suite_relation
 * @author 
 */
@Data
public class ResourceSuiteRelationPO {

    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 业务id
     */
    private Integer suiteId;

    /**
     * 1 套件指定资源，2 资源中执行的套件
     */
    private Integer type;

    /**
     * 名称
     */
    private String resourceName;

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

}