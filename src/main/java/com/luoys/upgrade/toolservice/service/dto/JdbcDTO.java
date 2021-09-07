package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.List;

/**
 * sql执行类，包含数据源和执行的sql
 *
 * @author luoys
 */
@Data
public class JdbcDTO {

    private DataSourceDTO dataSource;

    private List<SqlDTO> sqlList;
}
