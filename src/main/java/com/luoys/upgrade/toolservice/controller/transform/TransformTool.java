package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.controller.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.controller.dto.HttpRequestDTO;
import com.luoys.upgrade.toolservice.controller.dto.ParamDTO;
import com.luoys.upgrade.toolservice.controller.dto.SqlDTO;
import com.luoys.upgrade.toolservice.controller.vo.ToolSimpleVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolVO;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransformTool {

    private static final String SEPARATOR=" &&& ";
    private static final String PARAM_SYMBOL="$$${";

    public static ToolSimpleVO transformPO2SimpleVO(ToolPO po) {
        if (po == null)
            return null;
        ToolSimpleVO vo = new ToolSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setToolId(po.getToolId());
        vo.setOwner(po.getOwnerId());
        vo.setTitle(po.getTitle());
        vo.setPermission(po.getPermission());
        return vo;
    }

    public static List<ToolSimpleVO> transformPO2VO(List<ToolPO> poList) {
        List<ToolSimpleVO> voList = new ArrayList<>();
        for (ToolPO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    /**
     * 将参数合并入sql列表中
     * @param toolVO -
     */
    public static void mergeSql(ToolVO toolVO) {
        List<String> sqlTemplateList = toolVO.getSql().getSqlList();
        List<ParamDTO> paramDTOList = toolVO.getParamList();
        // 无变量
        if (paramDTOList.size() == 0) {
            return;
        }
        List<String> actualSqlList = new ArrayList<>();
        for (String sqlTemplate : sqlTemplateList) {
            if (sqlTemplate.contains(PARAM_SYMBOL)) {
                for (ParamDTO paramDTO : paramDTOList) {
                    //替换sql模板内的变量占位符
                    if (sqlTemplate.contains(PARAM_SYMBOL+paramDTO.getName()+"}")) {
                        actualSqlList.add(sqlTemplate.replace(PARAM_SYMBOL+paramDTO.getName()+"}", paramDTO.getValue()));
                    }
                }
            }
        }
        toolVO.getSql().setSqlList(actualSqlList);
    }

    public static ToolVO transformPO2VO(ToolPO po) {
        if (po == null) {
            return null;
        }
        ToolVO vo = new ToolVO();
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
                vo.setSql(toSql(po.getTemplate()));
                break;
            case 2:
                vo.setHttpRequest(toHttpRequest(po.getTemplate()));
                break;
            case 3:
                //todo
                vo.setRpc(null);
                break;
        }
        return vo;
    }

    public static ToolPO transformVO2PO(ToolVO vo) {
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
                po.setTemplate(toSql(vo.getSql()));
                break;
            case 2:
                po.setTemplate(toHttpRequest(vo.getHttpRequest()));
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
        sqlDTO.setDataSource((DataSourceDTO) JSON.parse(sqlList.get(0)));
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
        if (sqlDTO == null || sqlDTO.getDataSource() == null || sqlDTO.getSqlList() == null) {
            return null;
        }
        StringBuilder template = new StringBuilder();
        template.append(sqlDTO.getDataSource().toString());
        for (String sql : sqlDTO.getSqlList()) {
            template.append(SEPARATOR);
            template.append(sql);
        }
        return template.toString();
    }
}
