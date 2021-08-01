package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

@Data
public class DataSourceDTO {

    /**
     * 数据源地址
     */
    private String url;

    /**
     * 数据源驱动
     */
    private String driver;

    /**
     * 数据源用户名
     */
    private String username;

    /**
     * 数据源密码
     */
    private String password;

}
