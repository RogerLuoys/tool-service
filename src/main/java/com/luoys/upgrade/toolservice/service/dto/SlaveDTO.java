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
     * 设备型号
     */
    private String ip;

    /**
     * 分辨率
     */
    private String port;

    /**
     * 屏幕尺寸
     */
    private Integer thread;

}
