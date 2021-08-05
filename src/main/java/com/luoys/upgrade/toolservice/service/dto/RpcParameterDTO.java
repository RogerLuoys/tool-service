package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RpcParameterDTO {
    private String type;
    private Map<String, Object> value;
}
