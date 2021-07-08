package com.luoys.upgrade.toolservice.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
public class SqlDTO {
//    DataSourceDTO dataSource;
//
//    List<String> sqlList;

    private Integer type;

    private String sql;

    public SqlDTO() {}

    public SqlDTO(int type, String sql) {
        this.type = type;
        this.sql = sql;
    }
}
