package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.Map;

/**
 * rpc入参类
 *
 * @author luoys
 */
@Data
@Deprecated
public class RpcParameterDTO {
    private String type;
    private Map<String, Object> value;
}
