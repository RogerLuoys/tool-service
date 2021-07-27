package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

@Data
public class StepDTO {

    private Integer type;

    private SqlDTO sql;

    private HttpRequestDTO httpRequest;

    private RpcDTO rpc;
}
