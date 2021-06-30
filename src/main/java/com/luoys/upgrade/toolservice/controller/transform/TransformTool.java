package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.controller.dto.HttpRequestDTO;
import com.luoys.upgrade.toolservice.controller.vo.ParamVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolDetailVO;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransformTool {

    private static final String SEPARATOR=" &&& ";

    public static ToolDetailVO transformPO2VO(ToolPO po) {
        if (po == null) {
            return null;
        }
        ToolDetailVO vo = new ToolDetailVO();
        vo.setDescription(po.getDescription());
        vo.setToolId(po.getToolId());
        vo.setStatus(1);
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        vo.setPermission(po.getPermission());
        // {"name":"name1", "value":""};{"name":"name2", "value":"value1"}
        vo.setParamList(toParamVO(po.getParams()));
        // 模板转换
        switch (po.getType()) {
            case 1:
                vo.setSqlDTO(toSql(po.getTemplate()));
                break;
            case 2:
                vo.setHttpRequestDTO(toHttpRequest(po.getTemplate()));
                break;
            case 3:
                vo.setRpcDTO(po.getTemplate());
                break;
        }
        return vo;
    }

    public static ToolPO transformVO2PO(ToolDetailVO vo) {
        if (vo == null) {
            return null;
        }
        ToolPO po = new ToolPO();
        po.setDescription(vo.getDescription());
        po.setType(vo.getType());
        po.setToolId(vo.getToolId());
        po.setPermission(vo.getPermission());
        po.setTitle(vo.getTitle());
        po.setOwnerId(vo.getOwnerId());
        po.setParams(toParamPO(vo.getParamList()));
        // 模板转换
        switch (vo.getType()) {
            case 1:
                po.setTemplate(toSql(vo.getSqlList()));
                break;
            case 2:
                po.setTemplate(toHttpRequest(vo.getHttpRequestDTO()));
                break;
            case 3:
                po.setTemplate(vo.getRpcProvider());
                break;
        }
        return po;
    }

    private static List<ParamVO> toParamVO(String params){
        String[] paramArray = params.split(SEPARATOR);
        List<ParamVO> paramVOList = new ArrayList<>();
        for (String param : paramArray) {
            paramVOList.add((ParamVO) JSON.parse(param));
        }
        return paramVOList;
    }

    private static String toParamPO(List<ParamVO> paramVOList){
        if (paramVOList == null) {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (ParamVO paramVO : paramVOList) {
            params.append(JSON.toJSONString(paramVO));
            params.append(SEPARATOR);
        }
        params.delete(params.length()-5, params.length());
        return params.toString();
    }

    // {"name":"header","value":"http://"}
    private static HttpRequestDTO toHttpRequest(String template) {
        if (template == null) {
            return null;
        }
        return (HttpRequestDTO) JSON.parse(template);
    }

    private static String toHttpRequest(HttpRequestDTO httpRequest) {
        if (httpRequest == null) {
            return null;
        }
        return JSON.toJSONString(httpRequest);
    }

    // {"name":"sql", "value":"select"} &&
    private static List<String> toSql(String template) {
        if (template == null) {
            return null;
        }
        return Arrays.asList(template.split(SEPARATOR));
    }
    private static String toSql(List<String> sqlList) {
        if (sqlList == null && sqlList.size() == 0) {
            return null;
        }
        StringBuilder template = new StringBuilder();
        for (String sql : sqlList) {
            template.append(sql);
            template.append(SEPARATOR);
        }
        template.delete(template.length()-5, template.length());
        return template.toString();
    }
}
