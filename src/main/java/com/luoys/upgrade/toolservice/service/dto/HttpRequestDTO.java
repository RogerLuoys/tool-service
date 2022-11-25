package com.luoys.upgrade.toolservice.service.dto;

import com.luoys.upgrade.toolservice.service.common.StringUtil;
import lombok.Data;

import java.util.List;

/**
 * http类
 *
 * @author luoys
 */
@Data
@Deprecated
public class HttpRequestDTO {

    private String httpType;
    private String httpURL;
    private List<ParameterDTO> httpHeaderList;
    private String httpBody;


    /**
     * 将参数合并入http请求中，处理httpURL和httpBody
     * @param parameterList -
     */
    public void replace(List<ParameterDTO> parameterList) {
        // 无变量
        if (parameterList == null || parameterList.size() == 0) {
            return;
        }
        // 无参数符号需要替换
        if (!httpURL.matches(StringUtil.PARAM_REGEX) && !httpBody.matches(StringUtil.PARAM_REGEX)) {
            return;
        }
//        log.info("---->合并前http请求：{}", httpRequestDTO);
        String fullParamSymbol;
        //将参数一个个替换入httpURL和httpBody中
        for (ParameterDTO parameterDTO : parameterList) {
            fullParamSymbol = "${"+parameterDTO.getName()+"}";
            if (httpURL.contains(fullParamSymbol)) {
                httpURL = httpURL.replace(fullParamSymbol, parameterDTO.getValue());
            }
            if (httpBody != null && httpBody.contains(fullParamSymbol)) {
                httpBody = httpBody.replace(fullParamSymbol, parameterDTO.getValue());
            }
        }
//        httpRequestDTO.setHttpURL(httpURL);
//        httpRequestDTO.setHttpBody(httpBody);
//        log.info("---->合并后http请求：{}", httpRequestDTO);
    }

}
