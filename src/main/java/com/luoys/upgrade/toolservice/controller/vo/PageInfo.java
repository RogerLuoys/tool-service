package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

@Data
public class PageInfo <T> {
    T data;
    Integer total;
}
