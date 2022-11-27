package com.luoys.upgrade.toolservice.dao.po;

import java.util.Date;
import lombok.Data;

/**
 * project
 * @author luoys
 */
@Data
public class ProjectPO {
    /**
     * 主键
     */
    private Integer id;

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