package com.luoys.upgrade.toolservice.dao.po;

import lombok.Data;

/**
 * config 配置表
 * @author luoys
 */
@Data
public class ConfigPO {
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
     * 1 变量，2 driver启动项
     */
    private Byte type;

    /**
     * 1 文本框，2 单选框，3 下拉选项
     */
    private Byte paramType;

    /**
     * 变量引用名称
     */
    private String paramName;

    /**
     * 变量默认值
     */
    private String paramValue;

    /**
     * 1 chrome web，2chrome h5，3android
     */
    private Byte uiType;

    /**
     * driver 启动项
     */
    private String uiArgument;

    /**
     * 所属类id(实现带参数封装PO、带参数数据工厂和父类专属配置项)
     */
    private Integer caseId;

    /**
     * 项目id
     */
    private Integer projectId;

}