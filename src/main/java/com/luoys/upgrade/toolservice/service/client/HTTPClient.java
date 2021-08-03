package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.HttpRequestDTO;
import com.luoys.upgrade.toolservice.service.dto.ParameterDTO;
import com.luoys.upgrade.toolservice.service.enums.HttpTypeEnum;
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

@Slf4j
@Component
public class HTTPClient {
    private final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private final RestTemplate restTemplate = restTemplateBuilder.build();

    public String execute(HttpRequestDTO httpRequestDTO) {
        String url = httpRequestDTO.getHttpURL();
        String body = httpRequestDTO.getHttpBody();
        Map<String, String> uriVariables = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (httpRequestDTO.getHttpHeaderList() != null && httpRequestDTO.getHttpHeaderList().size() > 0) {
            for (ParameterDTO parameterDTO : httpRequestDTO.getHttpHeaderList()) {
                headers.add(parameterDTO.getName(), parameterDTO.getValue());
            }
        }
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        switch (HttpTypeEnum.fromValue(httpRequestDTO.getHttpType())) {
            case GET:
                return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, uriVariables).getBody();
            case POST:
                return restTemplate.exchange(url, HttpMethod.POST, entity, String.class, uriVariables).getBody();
            case PUT:
                return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, uriVariables).getBody();
            case DELETE:
                return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class, uriVariables).getBody();
            default:
                return "unknown http type";
        }
    }
}
