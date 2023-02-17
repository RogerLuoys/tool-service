package com.luoys.upgrade.toolservice.service.automation.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.DefaultEnum;
import com.luoys.upgrade.toolservice.service.enums.autoStep.methodType.HttpEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * http调用客户端，用于自动化步骤http类型的实现
 *
 * @author luoys
 */
@Slf4j
public class HttpClient {

    private final List<Header> DEFAULT_HEADER = new ArrayList<>();
//    private final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//    private final RestTemplate restTemplate = restTemplateBuilder.build();
//
//
//    /**
//     * 执行http请求，有同步锁
//     *
//     * @param stepDTO -
//     * @return 执行结果
//     */
//    public String execute(StepDTO stepDTO) {
//        Map<String, String> uriVariables = new HashMap<>();
//        HttpEntity<String> entity;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        switch (HttpEnum.fromCode(stepDTO.getMethodType())) {
//            case GET:
//                // 第二个入参不为空，表示有header
//                if (!StringUtil.isBlank(stepDTO.getParameter2())) {
//                    JSONObject jsonObject = JSON.parseObject(stepDTO.getParameter2());
//                    for (String key : jsonObject.keySet()) {
//                        headers.add(key, jsonObject.getString(key));
//                    }
//                }
//                entity = new HttpEntity<>(null, headers);
//                return restTemplate.exchange(stepDTO.getParameter1(), HttpMethod.GET, entity, String.class, uriVariables).getBody();
//            case POST:
//                // 第三个入参不为空，表示有header
//                if (!StringUtil.isBlank(stepDTO.getParameter3())) {
//                    JSONObject jsonObject = JSON.parseObject(stepDTO.getParameter3());
//                    for (String key : jsonObject.keySet()) {
//                        headers.add(key, jsonObject.getString(key));
//                    }
//                }
//                entity = new HttpEntity<>(stepDTO.getParameter2(), headers);
//                return restTemplate.exchange(stepDTO.getParameter1(), HttpMethod.POST, entity, String.class, uriVariables).getBody();
//            case PUT:
//                // 第三个入参不为空，表示有header
//                if (!StringUtil.isBlank(stepDTO.getParameter3())) {
//                    JSONObject jsonObject = JSON.parseObject(stepDTO.getParameter3());
//                    for (String key : jsonObject.keySet()) {
//                        headers.add(key, jsonObject.getString(key));
//                    }
//                }
//                entity = new HttpEntity<>(stepDTO.getParameter2(), headers);
//                return restTemplate.exchange(stepDTO.getParameter1(), HttpMethod.PUT, entity, String.class, uriVariables).getBody();
//            case DELETE:
//                // 第三个入参不为空，表示有header
//                if (!StringUtil.isBlank(stepDTO.getParameter3())) {
//                    JSONObject jsonObject = JSON.parseObject(stepDTO.getParameter3());
//                    for (String key : jsonObject.keySet()) {
//                        headers.add(key, jsonObject.getString(key));
//                    }
//                }
//                entity = new HttpEntity<>(stepDTO.getParameter2(), headers);
//                return restTemplate.exchange(stepDTO.getParameter1(), HttpMethod.DELETE, entity, String.class, uriVariables).getBody();
//            default:
//                return "unknown http type";
//        }
//    }


    /**
     * 执行http请求，有同步锁
     *
     * @param stepDTO -
     * @return 执行结果
     */
    public String execute(StepDTO stepDTO) {
        switch (HttpEnum.fromCode(stepDTO.getMethodType())) {
            case GET:
                if (!StringUtil.isBlank(stepDTO.getParameter2())) {
                    // 第二个入参不为空
                    return this.get(stepDTO.getParameter1(), stepDTO.getParameter2());
                } else if (!StringUtil.isBlank(stepDTO.getParameter1())) {
                    // 第一个入参不为空，
                    return this.get(stepDTO.getParameter1());
                } else {
                    return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
                }
            case POST:
                if (!StringUtil.isBlank(stepDTO.getParameter3())) {
                    // 第三个入参不为空
                    return this.post(stepDTO.getParameter1(), stepDTO.getParameter2(), stepDTO.getParameter3());
                } else if (!StringUtil.isBlank(stepDTO.getParameter2())) {
                    // 第二个入参不为空
                    return this.post(stepDTO.getParameter1(), stepDTO.getParameter2());
                } else if (!StringUtil.isBlank(stepDTO.getParameter1())) {
                    // 第一个入参不为空，
                    return this.post(stepDTO.getParameter1());
                } else {
                    return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
                }
            case PUT:
                if (!StringUtil.isBlank(stepDTO.getParameter3())) {
                    // 第三个入参不为空
                    return this.put(stepDTO.getParameter1(), stepDTO.getParameter2(), stepDTO.getParameter3());
                } else if (!StringUtil.isBlank(stepDTO.getParameter2())) {
                    // 第二个入参不为空
                    return this.put(stepDTO.getParameter1(), stepDTO.getParameter2());
                } else if (!StringUtil.isBlank(stepDTO.getParameter1())) {
                    // 第一个入参不为空，
                    return this.put(stepDTO.getParameter1());
                } else {
                    return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
                }
            case DELETE:
                if (!StringUtil.isBlank(stepDTO.getParameter2())) {
                    // 第二个入参不为空
                    return this.delete(stepDTO.getParameter1(), stepDTO.getParameter2());
                } else if (!StringUtil.isBlank(stepDTO.getParameter1())) {
                    // 第一个入参不为空，
                    return this.delete(stepDTO.getParameter1());
                } else {
                    return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
                }
            case GET_FOR_HEADER:
                if (!StringUtil.isBlank(stepDTO.getParameter2())) {
                    // 第二个入参不为空
                    return this.getForHeader(stepDTO.getParameter1(), stepDTO.getParameter2());
                } else if (!StringUtil.isBlank(stepDTO.getParameter1())) {
                    // 第一个入参不为空，
                    return this.getForHeader(stepDTO.getParameter1());
                } else {
                    return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
                }
            case POST_FOR_HEADER:
                if (!StringUtil.isBlank(stepDTO.getParameter3())) {
                    // 第三个入参不为空
                    return this.postForHeader(stepDTO.getParameter1(), stepDTO.getParameter2(), stepDTO.getParameter3());
                } else {
                    return this.postForHeader(stepDTO.getParameter1(), stepDTO.getParameter2());
                }
            case SET_DEFAULT_HEADER:
                this.setDefaultHeader(stepDTO.getParameter1());
                return DefaultEnum.DEFAULT_CLIENT_SUCCESS.getValue();
            default:
                return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
        }
    }

    /**
     * 执行http get请求
     *
     * @param url    带入参（如果有）的完整url
     * @param headers 请求头
     * @return get请求结果
     */
    private CloseableHttpResponse httpGet(String url, String headers) {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 设置请求头
//        for (String key : header.keySet()) {
//            httpGet.setHeader(key, header.get(key));
//        }
        httpGet.setHeaders(this.transformHeaders(headers));
        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            log.error("===>执行get请求失败！");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error("===>关闭http客户端失败！");
            }
        }
    }

    /**
     * 执行http post请求，通用方法
     *
     * @param url      带入参（如果有）的完整url
     * @param body json格式的body入参
     * @param headers   json格式的请求头
     * @return post请求结果
     */
    private CloseableHttpResponse httpPost(String url, String body, String headers) {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建 HttpPost 请求
        HttpPost httpPost = new HttpPost(url);
//        for (String key : header.keySet()) {
//            httpPost.setHeader(key, header.get(key));
//        }
        httpPost.setHeaders(this.transformHeaders(headers));
        //设置编码格式避免中文乱码
        if (!StringUtil.isBlank(body)) {
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            stringEntity.setContentType("application/json");
            // 设置 HttpPost 参数
            httpPost.setEntity(stringEntity);
        }
        try {
            return httpClient.execute(httpPost);
        } catch (IOException e) {
            log.error("===>执行post请求失败！");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error("===>关闭请求失败！");
            }
        }
    }


    /**
     * 执行http delete请求(无请求体)
     *
     * @param url    带入参（如果有）的完整url
     * @param headers json格式的请求头
     * @return delete请求结果
     */
    private CloseableHttpResponse httpDelete(String url, String headers) {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
//        for (String key : header.keySet()) {
//            httpDelete.setHeader(key, header.get(key));
//        }
        httpDelete.setHeaders(this.transformHeaders(headers));
        try {
            return httpClient.execute(httpDelete);
        } catch (IOException e) {
            log.error("===>执行delete请求失败！");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error("===>关闭请求失败！");
            }
        }
    }

    /**
     * 执行http put请求，通用方法
     *
     * @param url      带入参（如果有）的完整url
     * @param body json格式的body入参
     * @param headers   json格式的请求头
     * @return put请求结果
     */
    private CloseableHttpResponse httpPut(String url, String body, String headers) {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
//        for (String key : header.keySet()) {
//            httpPut.setHeader(key, header.get(key));
//        }
        httpPut.setHeaders(this.transformHeaders(headers));
        if (!StringUtil.isBlank(body)) {
            //设置请求体
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            stringEntity.setContentType("application/json");
            httpPut.setEntity(stringEntity);
        }
        try {
            return httpClient.execute(httpPut);
        } catch (IOException e) {
            log.error("===>执行put请求失败！");
            return null;
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error("===>关闭请求失败！");
            }
        }
    }

    private Header[] transformHeaders(String headers) {
        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("Connection", "keep-alive"));
        headerList.addAll(DEFAULT_HEADER);
        if (StringUtil.isBlank(headers)) {
            return headerList.toArray(new Header[0]);
        }
        JSONObject jsonObject = JSON.parseObject(headers);
        for (String key : jsonObject.keySet()) {
            headerList.add(new BasicHeader(key, jsonObject.get(key).toString()));
        }
        return headerList.toArray(new Header[0]);
    }

    private void setDefaultHeader(String defaultHeader) {
        DEFAULT_HEADER.clear();
        JSONObject jsonObject = JSON.parseObject(defaultHeader);
        for (String key : jsonObject.keySet()) {
            DEFAULT_HEADER.add(new BasicHeader(key, jsonObject.get(key).toString()));
        }
    }

    private String getBody(CloseableHttpResponse httpResponse) {
        try {
            if (httpResponse == null) {
                return "false";
            }
            String result = EntityUtils.toString(httpResponse.getEntity());
            httpResponse.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    private String getHeader(CloseableHttpResponse httpResponse, String headerName) {
        try {
            if (httpResponse == null) {
                return "false";
            }
            Header[] header;
            if (StringUtil.isBlank(headerName)) {
                header = httpResponse.getAllHeaders();
            } else {
                header = httpResponse.getHeaders(headerName);
            }
            httpResponse.close();
            return JSON.toJSONString(header);
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 执行http get请求
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String get(String url) {
        CloseableHttpResponse httpResponse = this.httpGet(url, null);
        return this.getBody(httpResponse);
    }

    /**
     * 执行http get请求
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param header 请求头，需要json格式
     * @return 返回json格式
     */
    private String get(String url, String header) {
        CloseableHttpResponse httpResponse = this.httpGet(url, header);
        return this.getBody(httpResponse);
    }

    /**
     * 执行http get请求，并返回所有header
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String getForHeader(String url) {
        CloseableHttpResponse httpResponse = this.httpGet(url, null);
        return this.getHeader(httpResponse, null);
    }

    /**
     * 执行http get请求，并返回指定header
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String getForHeader(String url, String headerName) {
        CloseableHttpResponse httpResponse = this.httpGet(url, null);
        return this.getHeader(httpResponse, headerName);
    }

    /**
     * 执行http post请求
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String post(String url) {
        CloseableHttpResponse httpResponse = this.httpPost(url, null, null);
        return this.getBody(httpResponse);
    }


    /**
     * 执行http post请求
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param body 接口传入的请求体，application/json格式
     * @return 返回json格式
     */
    private String post(String url, String body) {
        CloseableHttpResponse httpResponse = this.httpPost(url, body, null);
        return this.getBody(httpResponse);
    }

    /**
     * 执行http post请求
     *
     * @param url  完整的url地址-http://ip:port/path
     * @param body 接口对应的POJO对象或Map对象，传入body中，application/json格式
     * @param header 请求头，json字符串
     * @return 返回json格式
     */
    private String post(String url, String body, String header) {
        CloseableHttpResponse httpResponse = this.httpPost(url, body, header);
        return this.getBody(httpResponse);
    }

    /**
     * 执行http post请求，返回header
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param body 接口传入的请求体，application/json格式
     * @return 返回json格式
     */
    private String postForHeader(String url, String body) {
        CloseableHttpResponse httpResponse = this.httpPost(url, body, null);
        return this.getHeader(httpResponse, null);
    }

    /**
     * 执行http post请求，返回指定header
     *
     * @param url  完整的url地址-http://ip:port/path
     * @param body 接口对应的POJO对象或Map对象，传入body中，application/json格式
     * @param headerName 要获取的header名
     * @return 返回json格式
     */
    private String postForHeader(String url, String body, String headerName) {
        CloseableHttpResponse httpResponse = this.httpPost(url, body, null);
        return this.getHeader(httpResponse, headerName);
    }

    /**
     * 执行http delete请求
     *
     * @param url 完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String delete(String url) {
        CloseableHttpResponse httpResponse = this.httpDelete(url, null);
        return this.getBody(httpResponse);
    }

    /**
     * 执行http delete请求
     *
     * @param url 完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param header 请求头，需要json格式
     * @return 返回json格式
     */
    private String delete(String url, String header) {
        CloseableHttpResponse httpResponse = this.httpDelete(url, header);
        return this.getBody(httpResponse);
    }

    /**
     * 执行http put请求
     *
     * @param url  完整的url地址-http://ip:port/path
     * @return 返回json格式
     */
    private String put(String url) {
        CloseableHttpResponse httpResponse = this.httpPut(url, null, null);
        return this.getBody(httpResponse);
    }

    /**
     * 执行http put请求
     *
     * @param url  完整的url地址-http://ip:port/path
     * @param body 接口对应的POJO对象或Map对象，传入body中，application/json格式
     * @return 返回json格式
     */
    private String put(String url, String body) {
        CloseableHttpResponse httpResponse = this.httpPut(url, body, null);
        return this.getBody(httpResponse);
    }

    /**
     * 执行http put请求
     *
     * @param url  完整的url地址-http://ip:port/path
     * @param body 接口对应的POJO对象或Map对象，传入body中，application/json格式
     * @param header 请求头，json字符串
     * @return 返回json格式
     */
    private String put(String url, String body, String header) {
        CloseableHttpResponse httpResponse = this.httpPut(url, body, header);
        return this.getBody(httpResponse);
    }

}
