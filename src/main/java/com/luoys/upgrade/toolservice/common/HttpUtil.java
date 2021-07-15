package com.luoys.upgrade.toolservice.common;

import com.luoys.upgrade.toolservice.controller.dto.HttpRequestDTO;
import com.luoys.upgrade.toolservice.controller.dto.ParamDTO;
import com.luoys.upgrade.toolservice.controller.enums.HttpTypeEnum;
import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpUtil {
    private static RestTemplateBuilder restTemplateBuilder;
    private static RestTemplate restTemplate;

//    public void test(String url) {
//
//        // 设置请求的 Content-Type 为 application/jason
//        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
//        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//        // 将请求参数以键值对形式存储在 MultiValueMap 集合，发送请求时使用
//        MultiValueMap<String,Object> map = new LinkedMultiValueMap();
//        map.add("name","test");
//        map.add("age",1);
//        HttpEntity<MultiValueMap> request = new HttpEntity(map,header);
//        // RestTemplate设定发送UTF-8编码数据
//        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        ResponseEntity<Result> exchangeResult = restTemplate.exchange(url,HttpMethod.POST, request, Result.class);
//        System.out.println("返回结果："+exchangeResult);
//    }
//
//    public void test2() {
//        //RestTemplate restTemplate = new RestTemplate();
//        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        //创建请求头
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //也可以这样设置contentType
//        //MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        //headers.setContentType(type);
//
//        //加不加Accept都可以
//        //headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//
//        String url = "http://localhost:8087/callBackFor";
//
//        String jsonString = "JSONObject.toJSONString(student)";
//        System.out.println(jsonString);//{"age":10,"name":"sansan"}
//
//        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//        String result = responseEntity.getBody();//{"msg":"调用成功！","code":1}
//        System.out.println(result);
//        Map<String, Object> params = new HashMap<>();
//
//        restTemplate.exchange(url, HttpMethod.POST, entity, String.class, params);
//    }

    public static String execute(HttpRequestDTO httpRequestDTO) {
        String url = httpRequestDTO.getHttpURL();
        String body = httpRequestDTO.getHttpBody();
        Map<String, String> uriVariables = new HashMap<>();
        if (httpRequestDTO.getHttpHeaderList() != null || httpRequestDTO.getHttpHeaderList().size() > 0) {
            for (ParamDTO paramDTO : httpRequestDTO.getHttpHeaderList()) {
                uriVariables.put(paramDTO.getName(), paramDTO.getValue());
            }
        }
        restTemplateBuilder = new RestTemplateBuilder();
        restTemplate = restTemplateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class, uriVariables);
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

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


//    /**
//     * 执行http get请求
//     *
//     * @param url    带入参（如果有）的完整url
//     * @param header 请求头
//     * @return get请求结果
//     */
//    private String httpGet(String url, Map<String, String> header) {
//        log.info("\n====>执行get请求开始：{}", url);
//        // 创建 HttpClient 客户端
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String result = null;
//        HttpGet httpGet = new HttpGet(url);
//        // 设置请求头
//        for (String key : header.keySet()) {
//            httpGet.setHeader(key, header.get(key));
//        }
//        CloseableHttpResponse httpResponse = null;
//        try {
//            httpResponse = httpClient.execute(httpGet);
//            org.apache.http.HttpEntity httpEntity = httpResponse.getEntity();
//            result = EntityUtils.toString(httpEntity);
//        } catch (IOException e) {
//            log.error("\n====>执行get请求失败！");
////            e.printStackTrace();
//        } finally {
//            try {
//                if (httpResponse != null) {
//                    httpResponse.close();
//                }
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//            } catch (IOException e) {
//                log.error("\n====>关闭请求失败！");
////                e.printStackTrace();
//            }
//        }
//        log.info("\n====>执行get请求成功：{}", result);
//        return result;
//    }
//
//    /**
//     * 执行http post请求，通用方法
//     *
//     * @param url      带入参（如果有）的完整url
//     * @param jsonData json格式的body入参
//     * @param header   请求头
//     * @return post请求结果
//     */
//    private String httpPost(String url, String jsonData, Map<String, String> header) {
//        log.info("\n====>执行post请求开始：{}\n====>post请求body：{}", url, jsonData);
//        // 创建 HttpClient 客户端
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String result = null;
//        // 创建 HttpPost 请求
//        HttpPost httpPost = new HttpPost(url);
//        for (String key : header.keySet()) {
//            httpPost.setHeader(key, header.get(key));
//        }
//        CloseableHttpResponse httpResponse = null;
//        StringEntity stringEntity;
//        //设置编码格式避免中文乱码
//        stringEntity = new StringEntity(jsonData, StandardCharsets.UTF_8);
//        stringEntity.setContentType("application/json");
//        try {
//            // 设置 HttpPost 参数
//            httpPost.setEntity(stringEntity);
//            httpResponse = httpClient.execute(httpPost);
//            org.apache.http.HttpEntity httpEntity = httpResponse.getEntity();
//            result = EntityUtils.toString(httpEntity);
//        } catch (IOException e) {
//            log.error("\n====>执行post请求失败！");
////            e.printStackTrace();
//        } finally {
//            try {
//                if (httpResponse != null) {
//                    httpResponse.close();
//                }
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//            } catch (IOException e) {
//                log.error("\n====>关闭请求失败！");
////                e.printStackTrace();
//            }
//        }
//        log.info("\n====>执行post请求成功：{}", result);
//        return result;
//    }
//
//
//    /**
//     * 执行http delete请求
//     *
//     * @param url    带入参（如果有）的完整url
//     * @param header 请求头
//     * @return delete请求结果
//     */
//    private String httpDelete(String url, Map<String, String> header) {
//        log.info("\n====>执行delete请求开始：{}", url);
//        // 创建 HttpClient 客户端
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String result = null;
//        HttpDelete httpDelete = new HttpDelete(url);
//        for (String key : header.keySet()) {
//            httpDelete.setHeader(key, header.get(key));
//        }
//        CloseableHttpResponse httpResponse = null;
//        try {
//            httpResponse = httpClient.execute(httpDelete);
//            org.apache.http.HttpEntity httpEntity = httpResponse.getEntity();
//            result = EntityUtils.toString(httpEntity);
//        } catch (IOException e) {
//            log.error("\n====>执行delete请求失败！");
////            e.printStackTrace();
//        } finally {
//            try {
//                if (httpResponse != null) {
//                    httpResponse.close();
//                }
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//            } catch (IOException e) {
//                log.error("\n====>关闭请求失败！");
////                e.printStackTrace();
//            }
//        }
//        log.info("\n====>执行delete请求成功：{}", result);
//        return result;
//    }
//
//    /**
//     * 执行http put请求，通用方法
//     *
//     * @param url      带入参（如果有）的完整url
//     * @param jsonData json格式的body入参
//     * @param header   请求头
//     * @return put请求结果
//     */
//    private String httpPut(String url, String jsonData, Map<String, String> header) {
//        log.info("\n====>执行put请求开始：{}\n====>put请求body：{}", url, jsonData);
//        // 创建 HttpClient 客户端
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String result = null;
//        HttpPut httpPut = new HttpPut(url);
//        for (String key : header.keySet()) {
//            httpPut.setHeader(key, header.get(key));
//        }
//        CloseableHttpResponse httpResponse = null;
//        //设置请求体
//        if (jsonData != null) {
//            StringEntity stringEntity = new StringEntity(jsonData, StandardCharsets.UTF_8);
//            stringEntity.setContentType("application/json");
//            httpPut.setEntity(stringEntity);
//        }
//        try {
//            httpResponse = httpClient.execute(httpPut);
//            HttpEntity httpEntity = httpResponse.getEntity();
//            result = EntityUtils.toString(httpEntity);
//        } catch (IOException e) {
//            log.error("\n====>执行put请求失败！");
////            e.printStackTrace();
//        } finally {
//            try {
//                if (httpResponse != null) {
//                    httpResponse.close();
//                }
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//            } catch (IOException e) {
//                log.error("\n====>关闭请求失败！");
////                e.printStackTrace();
//            }
//        }
//        log.info("\n====>执行put请求成功：{}", result);
//        return result;
//    }
//
//
//    /**
//     * 把url字符串转换成URIBuilder对象
//     *
//     * @param url 完整url
//     * @return -
//     */
//    private URIBuilder getURIBuilder(String url) {
//        try {
//            return new URIBuilder(url);
//        } catch (URISyntaxException e) {
//            log.error("\n====>处理请求头url异常");
////            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 把Map变量转换成http的请求头参数
//     *
//     * @param url    完整的地址
//     * @param params 需要往请求头传的参数
//     * @return 带参数的完整url地址
//     */
//    private String transformMap2URL(String url, Map<String, ?> params) {
//        URIBuilder uriBuilder = getURIBuilder(url);
//        for (String key : params.keySet()) {
//            uriBuilder.setParameter(key, params.get(key).toString());
//        }
//        return uriBuilder.toString();
//    }

}
