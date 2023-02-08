package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * 执行服务器
 *
 * @author luoys
 */
@Data
public class SlaveDTO {

    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 机器地址 ip+port
     */
    private String url;

    /**
     * 最大线程数
     */
    private Integer thread;

}
