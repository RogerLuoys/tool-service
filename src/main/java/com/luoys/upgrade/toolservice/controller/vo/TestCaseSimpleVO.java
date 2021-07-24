package com.luoys.upgrade.toolservice.controller.vo;

import com.luoys.upgrade.toolservice.controller.dto.ParamDTO;
import lombok.Data;

import java.util.List;

@Data
public class TestCaseSimpleVO {
    private Integer testCaseId;

    private String title;

    private String description;

    private String ownerId;

    private String ownerName;

    private Integer status;

}
