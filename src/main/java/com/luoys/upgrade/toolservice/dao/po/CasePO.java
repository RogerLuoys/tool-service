package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import lombok.Data;

/**
 * case
 * @author
 */
@Data
public class CasePO implements Serializable {
    private Integer id;

    private String title;

    private String description;

    private String preDetail;

    private String mainDetail;

    private String afterDetail;

    private String ownerId;

    private String ownerName;

    private static final long serialVersionUID = 1L;
}
