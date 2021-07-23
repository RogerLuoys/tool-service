package com.luoys.upgrade.toolservice.controller.vo;

import com.luoys.upgrade.toolservice.controller.dto.ParamDTO;
import lombok.Data;

import java.util.List;

@Data
public class CaseSimpleVO {
    private Integer caseId;

    private String title;

    private String description;

    private String ownerId;

    private String ownerName;

}
