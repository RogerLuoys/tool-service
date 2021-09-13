package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 通用查询类
 *
 * @author luoys
 */
@Data
public class CommonQuery {
    private String uuid;
    private String name;
    private Integer type;
    private Integer status;
    private Integer pageIndex;
}
