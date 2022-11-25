package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * 全局通用参数类
 *
 * @author luoys
 */
@Data
@Deprecated
public class ParameterDTO {
    private String name;
    private String value;
    private String comment;
}
