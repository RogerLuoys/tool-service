package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * 工具类
 *
 * @author luoys
 */
@Data
public class UtilDTO {

    /**
     * 工具类型：1 睡眠，2
     */
    private Integer type;
    private String param1;
    private String param2;
}
