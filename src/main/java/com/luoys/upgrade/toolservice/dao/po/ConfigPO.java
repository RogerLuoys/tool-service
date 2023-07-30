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
     * 1 变量，2 driver启动项
     */
    private Integer type;

    /**
     * 1 文本框，2 单选框，3 下拉选项
     */
    private Integer paramType;

    /**
     * 变量引用名称
     */
    private String paramName;

    /**
     * 变量默认值
     */
    private String paramValue;

    /**
     * 备注
     */
    private String paramComment;

    /**
     * 目录名称
     */
    private String folderName;

    /**
     * 所属类id(实现带参数封装PO、带参数数据工厂和父类专属配置项)
     */
    private Integer caseId;

}