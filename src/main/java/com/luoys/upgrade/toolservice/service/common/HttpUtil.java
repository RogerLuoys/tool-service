package com.luoys.upgrade.toolservice.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.autoStep.methodType.HttpEnum;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    private static final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private static final RestTemplate restTemplate = restTemplateBuilder.build();

    public static Result<String> doPost(String url, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new HashMap<>());
        return (Result<String>) responseEntity.getBody();
    }

    /**
     * 执行http请求，有同步锁
     *
     * @param stepDTO -
     * @return 执行结果
     */
    public String execute(StepDTO stepDTO) {
        Map<String, String> uriVariables = new HashMap<>();
        HttpEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        switch (HttpEnum.fromCode(stepDTO.getMethodType())) {
            case GET:
                // 第二个入参不为空，表示有header
                if (StringUtil.isBlank(stepDTO.getParameter2())) {
                    JSONObject jsonObject = JSON.parseObject(stepDTO.getParameter2());
                    for (String key : jsonObject.keySet()) {
                        headers.add(key, jsonObject.getString(key));
                    }
                }
                entity = new HttpEntity<>(null, headers);
                return restTemplate.exchange(stepDTO.getParameter1(), HttpMethod.GET, entity, String.class, uriVariables).getBody();
            case POST:
                // 第三个入参不为空，表示有header
                if (StringUtil.isBlank(stepDTO.getParameter3())) {
                    JSONObject jsonObject = JSON.parseObject(stepDTO.getParameter3());
                    for (String key : jsonObject.keySet()) {
                        headers.add(key, jsonObject.getString(key));
                    }
                }
                entity = new HttpEntity<>(stepDTO.getParameter2(), headers);
                return restTemplate.exchange(stepDTO.getParameter1(), HttpMethod.POST, entity, String.class, uriVariables).getBody();
            case PUT:
                // 第三个入参不为空，表示有header
                if (StringUtil.isBlank(stepDTO.getParameter3())) {
                    JSONObject jsonObject = JSON.parseObject(stepDTO.getParameter3());
                    for (String key : jsonObject.keySet()) {
                        headers.add(key, jsonObject.getString(key));
                    }
                }
                entity = new HttpEntity<>(stepDTO.getParameter2(), headers);
                return restTemplate.exchange(stepDTO.getParameter1(), HttpMethod.PUT, entity, String.class, uriVariables).getBody();
            case DELETE:
                // 第三个入参不为空，表示有header
                if (StringUtil.isBlank(stepDTO.getParameter3())) {
                    JSONObject jsonObject = JSON.parseObject(stepDTO.getParameter3());
                    for (String key : jsonObject.keySet()) {
                        headers.add(key, jsonObject.getString(key));
                    }
                }
                entity = new HttpEntity<>(stepDTO.getParameter2(), headers);
                return restTemplate.exchange(stepDTO.getParameter1(), HttpMethod.DELETE, entity, String.class, uriVariables).getBody();
            default:
                return "unknown http type";
        }
    }
}
