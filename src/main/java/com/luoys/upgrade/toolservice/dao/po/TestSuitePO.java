package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import lombok.Data;

/**
 * test_suite
 * @author 
 */
@Data
public class TestSuitePO implements Serializable {
    private Integer id;

    private String title;

    private String description;

    private String configDetail;

    private String caseDetail;

    private Integer passed;

    private Integer failed;

    private Byte isDelete;

    private static final long serialVersionUID = 1L;
}