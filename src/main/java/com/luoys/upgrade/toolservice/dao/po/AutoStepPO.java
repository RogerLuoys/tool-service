package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * auto_step
 *
 * @author luoys
 */
@Data
public class AutoStepPO {

    private Integer id;

    /**
     * 业务id
     */
    private String stepId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 步骤类型：1 SQL，2 HTTP，3 RPC，4 UI，5 STEPS
     */
    private Integer type;

    /**
     * 当type为4时使用，聚合步骤
     */
    private String steps;

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
     * rpc入参类型
     */
    private String rpcParameterType;

    /**
     * rpc入参，List<CommonDTO>类型
     */
    private String rpcParameter;

    /**
     * 控件操作类型：1 click，2 sendKey，3 is exist
     */
    private Integer uiType;

    /**
     * 被测网址
     */
    private String uiUrl;

    /**
     * 自动化元素，默认xpath
     */
    private String uiElement;

    /**
     * 元素序号，从1开始
     */
    private Integer uiElementId;

    /**
     * 键盘输入的值
     */
    private String uiKey;

    /**
     * 实际结果取到后的等待时间
     */
    private Integer afterSleep;

    /**
     * 断言类型：1 完全匹配，2 模糊匹配，-1 不校验
     */
    private Integer assertType;

    /**
     * 实际结果
     */
    private String assertActual;

    /**
     * 预期结果
     */
    private String assertExpect;

    /**
     * 断言结果
     */
    private Boolean assertResult;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

    /**
     * 是否公用
     */
    private Boolean isPublic;

}
