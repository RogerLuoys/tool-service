package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.*;
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
    private Boolean isTestStep;
    private List<ParamDTO> paramList;
    private JdbcDTO jdbc;
    private HttpRequestDTO httpRequest;
    private RpcDTO rpc;
}
