package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class ProjectVO {

    /**
     * 主键
     */
    private Integer projectId;

    /**
     * 父项目id
     */
    private Integer parentProjectId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

}
