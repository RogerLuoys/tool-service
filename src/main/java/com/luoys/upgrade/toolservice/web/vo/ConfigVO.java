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
    private Integer type;
    private Integer paramType;
    private String name;
    private String value;
    private String comment;
}
