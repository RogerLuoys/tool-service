package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * 全局通用参数类
 *
 * @author luoys
 */
@Data
public class ParameterDTO {

    private Integer type;

    private String name;

    private String value;

    private String comment;
}
