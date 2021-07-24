package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import lombok.Data;

/**
 * testcase
 * @author
 */
@Data
public class TestCasePO implements Serializable {
    private Integer id;

    private String title;

    private String description;

    private String preDetail;

    private String mainDetail;

    private String afterDetail;

    private String ownerId;

    private String ownerName;

    private Integer status;

    private static final long serialVersionUID = 1L;
}
