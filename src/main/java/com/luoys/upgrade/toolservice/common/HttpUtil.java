//package com.luoys.upgrade.toolservice.common;
//
//import com.luoys.upgrade.toolservice.service.dto.HttpRequestDTO;
//import com.luoys.upgrade.toolservice.service.dto.ParameterDTO;
//import com.luoys.upgrade.toolservice.service.enums.HttpTypeEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//public class HttpUtil {
//    private static RestTemplateBuilder restTemplateBuilder;
//    private static RestTemplate restTemplate;
//
//    public static String execute(HttpRequestDTO httpRequestDTO) {
//        String url = httpRequestDTO.getHttpURL();
//        String body = httpRequestDTO.getHttpBody();
//        Map<String, String> uriVariables = new HashMap<>();
////        if (httpRequestDTO.getHttpHeaderList() != null && httpRequestDTO.getHttpHeaderList().size() > 0) {
////            for (ParamDTO paramDTO : httpRequestDTO.getHttpHeaderList()) {
////                uriVariables.put(paramDTO.getName(), paramDTO.getValue());
////            }
////        }
//        restTemplateBuilder = new RestTemplateBuilder();
//        restTemplate = restTemplateBuilder.build();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        if (httpRequestDTO.getHttpHeaderList() != null && httpRequestDTO.getHttpHeaderList().size() > 0) {
//            for (ParameterDTO parameterDTO : httpRequestDTO.getHttpHeaderList()) {
//                headers.add(parameterDTO.getName(), parameterDTO.getValue());
//            }
//        }
//        HttpEntity<String> entity = new HttpEntity<>(body, headers);
////        restTemplate.exchange(url, HttpMethod.POST, entity, String.class, uriVariables);
////        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//        switch (HttpTypeEnum.fromValue(httpRequestDTO.getHttpType())) {
//            case GET:
//                return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, uriVariables).getBody();
//            case POST:
//                return restTemplate.exchange(url, HttpMethod.POST, entity, String.class, uriVariables).getBody();
//            case PUT:
//                return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, uriVariables).getBody();
//            case DELETE:
//                return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class, uriVariables).getBody();
//            default:
//                return "unknown http type";
//        }
//    }
//
//
//}
