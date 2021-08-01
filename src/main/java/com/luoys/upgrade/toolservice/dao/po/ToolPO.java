package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tool
 * @author 
 */
@Data
public class ToolPO implements Serializable {

    /**
     * uuid
     */
    private String toolId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 步骤类型：1 SQL，2 HTTP，3 RPC，4 聚合
     */
    private Integer type;

    /**
     * 查看权限：1 公开，2 仅自己可见合
     */
    private Integer permission;

    /**
     * 入参列表，List<CommonDTO>类型
     */
    private String parameter;

    /**
     * 当type为4时使用，List<CommonDTO>类型
     */
    private String tools;

    /**
     * sql语句列表，List<CommonDTO>类型
     */
    private String jdbcSql;

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
     * http完整路径，可带参数
     */
    private String httpUrl;

    /**
     * http请求类型
     */
    private String httpType;

    /**
     * http请求头
     */
    private String httpHeader;

    /**
     * http请求体
     */
    private String httpBody;

    /**
     * rpc域名或ip:prot
     */
    private String rpcUrl;

    /**
     * rpc接口路径，class name
     */
    private String rpcInterface;

    /**
     * rpc方法
     */
    private String rpcMethod;

    /**
     * rpc入参，List<CommonDTO>类型
     */
    private String rpcParameter;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

    private static final long serialVersionUID = 1L;
}