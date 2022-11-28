package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * 聚合步骤类
 *
 * @author luoys
 */
@Data
@Deprecated
public class StepDTO {
    /**
     * 步骤所属区域，if、then、else
     */
    private String area;

    /**
     * 步骤uuid
     */
    private String stepId;

    /**
     * 步骤执行顺序
     */
    private Integer sequence;
}
