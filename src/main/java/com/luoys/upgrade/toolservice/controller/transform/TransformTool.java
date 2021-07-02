package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.controller.dto.DataBaseDTO;
import com.luoys.upgrade.toolservice.controller.dto.HttpRequestDTO;
import com.luoys.upgrade.toolservice.controller.dto.ParamDTO;
import com.luoys.upgrade.toolservice.controller.dto.SqlDTO;
import com.luoys.upgrade.toolservice.controller.vo.ParamVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolDetailVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolVO;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransformTool {

    private static final String SEPARATOR=" &&& ";

    public static ToolVO transformPO2ToolVO(ToolPO po) {
        if (po == null)
            return null;
        ToolVO vo = new ToolVO();
        vo.setDescription(po.getDescription());
        vo.setToolId(po.getToolId());
        vo.setOwner(po.getOwnerId());
        vo.setTitle(po.getTitle());
        vo.setPermission(po.getPermission());
        return vo;
    }

    public static List<ToolVO> transformPO2VO(List<ToolPO> poList) {
        List<ToolVO> voList = new ArrayList<>();
        for (ToolPO po : poList) {
            voList.add(transformPO2ToolVO(po));
        }
        return voList;
    }

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
        vo.setParamList(toParam(po.getParams()));
        // 模板转换
        switch (po.getType()) {
            case 1:
                vo.setSqlDTO(toSql(po.getTemplate()));
                break;
            case 2:
                vo.setHttpRequestDTO(toHttpRequest(po.getTemplate()));
                break;
            case 3:
                //todo
                vo.setRpcDTO(null);
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
        po.setParams(toParam(vo.getParamList()));
        // 模板转换
        switch (vo.getType()) {
            case 1:
                po.setTemplate(toSql(vo.getSqlDTO()));
                break;
            case 2:
                po.setTemplate(toHttpRequest(vo.getHttpRequestDTO()));
                break;
            case 3:
                //todo
                po.setTemplate(null);
                break;
        }
        return po;
    }

    /**
     * 参数值转换成参数对象列表
     * @param params 数据库中的param值
     * @return 参数对象列表
     */
    private static List<ParamDTO> toParam(String params){
        String[] paramArray = params.split(SEPARATOR);
        List<ParamDTO> paramList = new ArrayList<>();
        for (String param : paramArray) {
            paramList.add((ParamDTO) JSON.parse(param));
        }
        return paramList;
    }

    /**
     * 参数对象列表转换成参数值
     * @param paramList 参数对象列表
     * @return 数据库中的参数值
     */
    private static String toParam(List<ParamDTO> paramList){
        if (paramList == null) {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (ParamDTO paramDTO : paramList) {
            params.append(JSON.toJSONString(paramDTO));
            params.append(SEPARATOR);
        }
        params.delete(params.length()-SEPARATOR.length(), params.length());
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

    /**
     * 模板转sql对象
     * @param template 数据库的模块值
     * @return sql对象
     */
    // {DataBaseDTO} &&& sql1 &&& sql2
    private static SqlDTO toSql(String template) {
        if (template == null) {
            return null;
        }
        List<String> sqlList = Arrays.asList(template.split(SEPARATOR));
        //第一段需要是数据库
        if (!sqlList.get(0).startsWith("{")) {
            return null;
        }
        SqlDTO sqlDTO = new SqlDTO();
        sqlDTO.setDataBaseDTO((DataBaseDTO) JSON.parse(sqlList.get(0)));
        sqlList.remove(0);
        sqlDTO.setSqlList(sqlList);
        return sqlDTO;
    }

    /**
     * sql对象转模板
     * @param sqlDTO sql对象
     * @return 模板值
     */
    private static String toSql(SqlDTO sqlDTO) {
        if (sqlDTO == null || sqlDTO.getDataBaseDTO() == null || sqlDTO.getSqlList() == null) {
            return null;
        }
        StringBuilder template = new StringBuilder();
        template.append(sqlDTO.getDataBaseDTO().toString());
        for (String sql : sqlDTO.getSqlList()) {
            template.append(SEPARATOR);
            template.append(sql);
        }
        return template.toString();
    }
}
