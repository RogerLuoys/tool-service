package com.luoys.upgrade.toolservice.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class SqlDTO {
    DataBaseDTO dataBaseDTO;

    List<String> sqlList;
}
