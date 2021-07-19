package com.luoys.upgrade.toolservice.controller.vo;

import com.luoys.upgrade.toolservice.controller.dto.*;
import lombok.Data;

import java.util.List;

/**
 * 转换后的完整工具详情
 * @author luoys
 */
@Data
public class ToolVO {

    private String toolId;
    private String title;
    private String description;
    private Integer type;
    private Integer status;
    private String ownerId;
    private String ownerName;
    private Integer permission;
    private List<ParamDTO> paramList;
    private JdbcDTO jdbc;
    private HttpRequestDTO httpRequest;
    private RpcDTO rpc;
}
