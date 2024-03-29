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
    private CloseableHttpClient httpClient = null;
    private String defaultUrl = null;

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
            case SET_DEFAULT_URL:
                this.setDefaultUrl(stepDTO.getParameter1());
                return DefaultEnum.DEFAULT_CLIENT_SUCCESS.getValue();
            default:
                return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
        }
    }

    /**
     * 关闭客户端
     */
    public void close() {
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            log.error("===>关闭http客户端失败！");
        }
    }

    /**
     * 设置环境
     */
    private void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    /**
     * 处理get请求参数，并转换成HttpGet对象
     * @param url 完整url(可带参数)
     * @param headers 请求头
     * @return HttpGet
     */
    private HttpGet transformGet(String url, String headers) {
        if (!url.startsWith("http")) {
            url = this.defaultUrl + url;
        }
        // 创建 HttpGet 请求
        HttpGet httpGet = new HttpGet(url);
        // 设置请求头
        httpGet.setHeaders(this.transformHeaders(headers));
        return httpGet;
    }

    /**
     * 处理post请求参数，并转换成HttpGet对象
     * @param url 完整url(可带参数)
     * @param body 请求体
     * @param headers 请求头
     * @return HttpPost
     */
    private HttpPost transformPost(String url, String body, String headers) {
        if (!url.startsWith("http")) {
            url = this.defaultUrl + url;
        }
        // 创建 HttpPost 请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(this.transformHeaders(headers));
        if (body != null) {
            //设置编码格式避免中文乱码
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            stringEntity.setContentType("application/json");
            // 设置 HttpPost 参数
            httpPost.setEntity(stringEntity);
        }
        return httpPost;
    }

    /**
     * 处理delete请求参数，并转换成HttpDelete对象
     * @param url 完整url(可带参数)
     * @param headers 请求头
     * @return HttpDelete
     */
    private HttpDelete transformDelete(String url, String headers) {
        if (!url.startsWith("http")) {
            url = this.defaultUrl + url;
        }
        // 创建 HttpDelete 请求
        HttpDelete httpDelete = new HttpDelete(url);
        // 设置请求头
        httpDelete.setHeaders(this.transformHeaders(headers));
        return httpDelete;
    }

    /**
     * 处理put请求参数，并转换成HttpPut对象
     * @param url 完整url(可带参数)
     * @param body 请求体
     * @param headers 请求头
     * @return HttpPut
     */
    private HttpPut transformPut(String url, String body, String headers) {
        if (!url.startsWith("http")) {
            url = this.defaultUrl + url;
        }
        // 创建 HttpPut 请求
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeaders(this.transformHeaders(headers));
        if (body != null) {
            //设置编码格式避免中文乱码
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            stringEntity.setContentType("application/json");
            // 设置 HttpPost 参数
            httpPut.setEntity(stringEntity);
        }
        return httpPut;
    }

    private Header[] transformHeaders(String headers) {
        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("Connection", "keep-alive"));
        headerList.addAll(DEFAULT_HEADER);
        if (headers == null) {
            return headerList.toArray(new Header[0]);
        }
        JSONObject jsonObject = JSON.parseObject(headers);
        for (String key : jsonObject.keySet()) {
            headerList.add(new BasicHeader(key, jsonObject.get(key).toString()));
        }
        return headerList.toArray(new Header[0]);
    }

    /**
     * 执行http请求
     * @param request 请求对象
     * @return 调用结果
     */
    private CloseableHttpResponse executeRequest(HttpUriRequest request) throws IOException {
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
        return httpClient.execute(request);
    }

    /**
     * 执行请求并获取结果中的body值
     * @param request 请求
     * @return response body
     */
    private String httpBody(HttpUriRequest request) {
        try {
            CloseableHttpResponse httpResponse = this.executeRequest(request);
            String result = EntityUtils.toString(httpResponse.getEntity());
            httpResponse.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
        }
    }

    /**
     * 执行请求并获取结果中的header
     * @param request 请求
     * @return response body
     */
    private String httpHeader(HttpUriRequest request, String headerName) {
        try {
            CloseableHttpResponse httpResponse = this.executeRequest(request);
            Header[] header;
            if (headerName == null) {
                header = httpResponse.getAllHeaders();
            } else {
                header = httpResponse.getHeaders(headerName);
            }
            httpResponse.close();
            return JSON.toJSONString(header);
        } catch (IOException e) {
            e.printStackTrace();
            return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
        }
    }

    private void setDefaultHeader(String defaultHeader) {
        DEFAULT_HEADER.clear();
        JSONObject jsonObject = JSON.parseObject(defaultHeader);
        for (String key : jsonObject.keySet()) {
            DEFAULT_HEADER.add(new BasicHeader(key, jsonObject.get(key).toString()));
        }
    }

    /**
     * 执行http get请求
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String get(String url) {
        HttpGet httpGet = this.transformGet(url, null);
        return this.httpBody(httpGet);
    }

    /**
     * 执行http get请求
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param header 请求头，需要json格式
     * @return 返回json格式
     */
    private String get(String url, String header) {
        HttpGet httpGet = this.transformGet(url, header);
        return this.httpBody(httpGet);
    }

    /**
     * 执行http get请求，并返回所有header
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String getForHeader(String url) {
        HttpGet httpGet = this.transformGet(url, null);
        return this.httpHeader(httpGet, null);
    }

    /**
     * 执行http get请求，并返回指定header
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param headerName  指定获取的返回头
     * @return 返回json格式
     */
    private String getForHeader(String url, String headerName) {
        HttpGet httpGet = this.transformGet(url, null);
        return this.httpHeader(httpGet, headerName);
    }

    /**
     * 执行http post请求
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String post(String url) {
        HttpPost httpPost = this.transformPost(url, null, null);
        return this.httpBody(httpPost);
    }

    /**
     * 执行http post请求
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param body 接口传入的请求体，application/json格式
     * @return 返回json格式
     */
    private String post(String url, String body) {
        HttpPost httpPost = this.transformPost(url, body, null);
        return this.httpBody(httpPost);
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
        HttpPost httpPost = this.transformPost(url, body, header);
        return this.httpBody(httpPost);
    }

    /**
     * 执行http post请求，返回header
     *
     * @param url  完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param body 接口传入的请求体，application/json格式
     * @return 返回json格式
     */
    private String postForHeader(String url, String body) {
        HttpPost httpPost = this.transformPost(url, body, null);
        return this.httpHeader(httpPost, null);
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
        HttpPost httpPost = this.transformPost(url, body, null);
        return this.httpHeader(httpPost, headerName);
    }

    /**
     * 执行http delete请求
     *
     * @param url 完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @return 返回json格式
     */
    private String delete(String url) {
        HttpDelete httpDelete = this.transformDelete(url, null);
        return this.httpBody(httpDelete);
    }

    /**
     * 执行http delete请求
     *
     * @param url 完整的url地址，可带参数-http://ip:port/path?param1=value1
     * @param header 请求头，需要json格式
     * @return 返回json格式
     */
    private String delete(String url, String header) {
        HttpDelete httpDelete = this.transformDelete(url, header);
        return this.httpBody(httpDelete);
    }

    /**
     * 执行http put请求
     *
     * @param url  完整的url地址-http://ip:port/path
     * @return 返回json格式
     */
    private String put(String url) {
        HttpPut httpPut = this.transformPut(url, null, null);
        return this.httpBody(httpPut);
    }

    /**
     * 执行http put请求
     *
     * @param url  完整的url地址-http://ip:port/path
     * @param body 接口对应的POJO对象或Map对象，传入body中，application/json格式
     * @return 返回json格式
     */
    private String put(String url, String body) {
        HttpPut httpPut = this.transformPut(url, body, null);
        return this.httpBody(httpPut);
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
        HttpPut httpPut = this.transformPut(url, body, header);
        return this.httpBody(httpPut);
    }

}
