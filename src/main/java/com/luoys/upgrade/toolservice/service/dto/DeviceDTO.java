package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * 设备类
 *
 * @author luoys
 */
@Data
@Deprecated
public class DeviceDTO {

    /**
     * 设备型号
     */
    private String model;

    /**
     * 屏幕尺寸
     */
    private String size;

    /**
     * 分辨率
     */
    private String dpi;

    /**
     * 设备操作系统
     */
    private String os;

}
