package com.luoys.upgrade.toolservice.controller.vo;

import com.luoys.upgrade.toolservice.controller.dto.HttpRequestDTO;
import com.luoys.upgrade.toolservice.controller.dto.RpcDTO;
import com.luoys.upgrade.toolservice.controller.dto.SqlDTO;
import lombok.Data;

import java.util.List;

@Data
public class ToolDetailVO {
    private Long toolId;
    private String title;
    private String description;
    private Integer type;
    private Integer status;
    private String ownerId;
    private Integer permission;
    private List<ParamVO> paramList;
    private SqlDTO sqlDTO;
    private HttpRequestDTO httpRequestDTO;
    private RpcDTO rpcDTO;
}
