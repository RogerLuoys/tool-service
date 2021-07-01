package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import lombok.Data;

/**
 * device
 * @author 
 */
@Data
public class DevicePO implements Serializable {
    private Integer id;

    private Integer toolConfigId;

    private String title;

    private String descrition;

    private String items;

    private Byte type;

    private String ownerId;

    private Byte permission;

    private Byte isDelete;

    private static final long serialVersionUID = 1L;
}