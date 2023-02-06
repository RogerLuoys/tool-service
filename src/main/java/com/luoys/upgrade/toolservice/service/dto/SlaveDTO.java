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
    private String resourceName;

    /**
     * 机器ip
     */
    private String ip;

    /**
     * 机器端口
     */
    private String port;

    /**
     * 最大线程数
     */
    private Integer thread;

}
