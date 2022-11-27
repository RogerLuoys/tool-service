package com.luoys.upgrade.toolservice.service.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.HttpRequestDTO;
import com.luoys.upgrade.toolservice.service.dto.ParameterDTO;
import com.luoys.upgrade.toolservice.service.enums.method.HttpTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * http调用客户端，用于自动化步骤http类型的实现
 *
 * @author luoys
 */
@Slf4j
//@Component
public class HTTPClient {
    private final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private final RestTemplate restTemplate = restTemplateBuilder.build();

    /**
     * 执行http请求，有同步锁
     *
     * @param autoStepPO -
     * @return 执行结果
     */
    public String execute(AutoStepPO autoStepPO) {
//        String url, body, header;
//        url = autoStepPO.getHttpURL();
//        body = autoStepPO.getHttpBody();
        Map<String, String> uriVariables = new HashMap<>();
        HttpEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        if (httpRequestDTO.getHttpHeaderList() != null && httpRequestDTO.getHttpHeaderList().size() > 0) {
//            for (ParameterDTO parameterDTO : httpRequestDTO.getHttpHeaderList()) {
//                headers.add(parameterDTO.getName(), parameterDTO.getValue());
//            }
//        }
        switch (HttpTypeEnum.fromCode(autoStepPO.getMethodType())) {
            case GET:
                // 第二个入参不为空，表示有header
                if (StringUtil.isBlank(autoStepPO.getParameter2())) {
                    JSONObject jsonObject = JSON.parseObject(autoStepPO.getParameter2());
                    for (String key : jsonObject.keySet()) {
                        headers.add(key, jsonObject.getString(key));
                    }
                }
                entity = new HttpEntity<>(null, headers);
                return restTemplate.exchange(autoStepPO.getParameter1(), HttpMethod.GET, entity, String.class, uriVariables).getBody();
            case POST:
                // 第三个入参不为空，表示有header
                if (StringUtil.isBlank(autoStepPO.getParameter3())) {
                    JSONObject jsonObject = JSON.parseObject(autoStepPO.getParameter3());
                    for (String key : jsonObject.keySet()) {
                        headers.add(key, jsonObject.getString(key));
                    }
                }
                entity = new HttpEntity<>(autoStepPO.getParameter2(), headers);
                return restTemplate.exchange(autoStepPO.getParameter1(), HttpMethod.POST, entity, String.class, uriVariables).getBody();
            case PUT:
                // 第三个入参不为空，表示有header
                if (StringUtil.isBlank(autoStepPO.getParameter3())) {
                    JSONObject jsonObject = JSON.parseObject(autoStepPO.getParameter3());
                    for (String key : jsonObject.keySet()) {
                        headers.add(key, jsonObject.getString(key));
                    }
                }
                entity = new HttpEntity<>(autoStepPO.getParameter2(), headers);
                return restTemplate.exchange(autoStepPO.getParameter1(), HttpMethod.PUT, entity, String.class, uriVariables).getBody();
            case DELETE:
                // 第三个入参不为空，表示有header
                if (StringUtil.isBlank(autoStepPO.getParameter3())) {
                    JSONObject jsonObject = JSON.parseObject(autoStepPO.getParameter3());
                    for (String key : jsonObject.keySet()) {
                        headers.add(key, jsonObject.getString(key));
                    }
                }
                entity = new HttpEntity<>(autoStepPO.getParameter2(), headers);
                return restTemplate.exchange(autoStepPO.getParameter1(), HttpMethod.DELETE, entity, String.class, uriVariables).getBody();
            default:
                return "unknown http type";
        }
    }
}
