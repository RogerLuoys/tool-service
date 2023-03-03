package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

import java.util.List;

@Data
public class TotalCaseVO {
    List<String> columns;

    List<CaseModuleStatVO> rows;
}
