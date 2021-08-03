package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

@Data
public class UiDTO {

    /**
     * 控件操作类型：1 click，2 sendkey，3 is exist
     */
    private Integer type;

    /**
     * 被测网址
     */
    private String url;

    /**
     * 自动化元素，默认xpath
     */
    private String element;

    /**
     * 元素序号，从1开始
     */
    private Integer elementId;

}
