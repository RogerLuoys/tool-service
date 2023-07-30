package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 用例下关联的配置
 *
 * @author luoys
 */
@Data
public class ConfigVO {
    private Integer configId;

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
    private String name;

    /**
     * 变量默认值
     */
    private String value;

    /**
     * 备注
     */
    private String comment;

    /**
     * 所属类id(实现带参数封装PO、带参数数据工厂和父类专属配置项)
     */
    private Integer caseId;

}
