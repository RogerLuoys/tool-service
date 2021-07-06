package com.luoys.upgrade.toolservice.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class SqlDTO {
    DataSourceDTO dataSource;

    List<String> sqlList;
}
