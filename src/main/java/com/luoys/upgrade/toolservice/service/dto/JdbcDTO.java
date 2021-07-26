package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class JdbcDTO {

    private DataSourceDTO dataSource;

    private List<SqlDTO> sqlList;
}
