package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo <T> {
    private List<T> data;
    private Integer total;
}
