package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo <T> {
    private List<T> list;
    private Integer total;

    public PageInfo() {}

    public PageInfo(List<T> list) {
        this.list = list;
        this.total = list.size();
    }
}
