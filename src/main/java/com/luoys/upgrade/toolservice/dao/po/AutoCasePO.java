package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * auto_case
 * @author 
 */
@Data
public class AutoCasePO implements Serializable {
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 用例执行的最长时间
     */
    private Integer maxTime;

    /**
     * 1 未执行，2 失败，3成功
     */
    private Byte status;

    /**
     * 前置步骤，List<ParamDTO>的json格式
     */
    private String preStep;

    /**
     * 主要步骤，List<ParamDTO>的json格式
     */
    private String mainStep;

    /**
     * 收尾步骤，List<ParamDTO>的json格式
     */
    private String afterStep;

    /**
     * 所属人id
     */
    private String ownerId;

    /**
     * 所属人
     */
    private String ownerName;

    /**
     * 逻辑删除
     */
    private Byte isDelete;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 最近修改时间
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}