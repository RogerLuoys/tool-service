package com.luoys.upgrade.toolservice.service.dto;

import com.luoys.upgrade.toolservice.common.StringUtil;
import lombok.Data;

import java.util.List;

/**
 * ui类
 *
 * @author luoys
 */
@Data
public class UiDTO {

    /**
     * 控件操作类型：1 click，2 sendkey，3 is exist，4 初始化
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

    /**
     * 键盘输入的值
     */
    private String key;

    /**
     * 用实际值替换参数占位符，只处理url、key
     * @param parameterList
     */
    public void merge(List<ParameterDTO> parameterList) {
        if (parameterList == null || parameterList.size() == 0) {
            return;
        }
        StringBuilder fullParamSymbol = new StringBuilder();
        // 替换url字段中的变量
        if (url.matches(StringUtil.PARAM_REGEX)) {
            for (ParameterDTO parameterDTO : parameterList) {
                fullParamSymbol.delete(0, fullParamSymbol.length());
                fullParamSymbol.append("${").append(parameterDTO.getName()).append("}");
                if (url.contains(fullParamSymbol)) {
                    url = url.replace(fullParamSymbol, parameterDTO.getValue());
                }
            }
        }
    }

}
