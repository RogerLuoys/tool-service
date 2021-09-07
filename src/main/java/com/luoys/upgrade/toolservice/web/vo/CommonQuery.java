package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 通用查询类
 *
 * @author luoys
 */
@Data
public class CommonQuery {
    private Integer type;
    private String name;
    private Integer pageIndex;
    private Boolean isTestStep;
}
