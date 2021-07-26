package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class TestCaseSimpleVO {
    private Integer testCaseId;

    private String title;

    private String description;

    private String ownerId;

    private String ownerName;

    private Integer status;

}
