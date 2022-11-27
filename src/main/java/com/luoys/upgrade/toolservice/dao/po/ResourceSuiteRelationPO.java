package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * resource_suite_relation
 * @author 
 */
@Data
public class ResourceSuiteRelationPO {
    private Integer id;

    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 业务id
     */
    private Integer suiteId;

    /**
     * 1 套件指定资源，2 资源中执行的套件
     */
    private Byte type;

}