package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 通用查询类(暂未使用)
 *
 * @author luoys
 */
@Data
public class CommonQuery {
    /**
     * 各模块业务id
     */
    private String uuid;

    /**
     * 要查询的名字
     */
    private String name;

    /**
     * 要查询的类型
     */
    private Integer type;

    /**
     * 要查询的状态
     */
    private Integer status;

    /**
     * 数据的ownerId
     */
    private String ownerId;

    /**
     * 是否只看owner为自己的数据
     */
    private Boolean isOnlyOwner;

    /**
     * 页码，注意在sql中 startIndex = (pageIndex-1)*pageNumber
     */
    private Integer pageIndex;
}
