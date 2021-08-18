package com.luoys.upgrade.toolservice.service.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.SqlTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.ToolTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.ToolSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.ToolVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TransformTool {

    public static ToolSimpleVO transformPO2SimpleVO(ToolPO po) {
        if (po == null)
            return null;
        ToolSimpleVO vo = new ToolSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setToolId(po.getToolId());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        vo.setName(po.getName());
        vo.setType(po.getType());
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
        // 无变量
        if (toolVO.getParameterList() == null || toolVO.getParameterList().size() == 0) {
            log.info("---->无参数需要合并");
            return;
        }
        log.info("---->合并前sql列表：{}", toolVO.getJdbc().getSqlList());
        List<SqlDTO> actualSqlList = new ArrayList<>();
        String oneSql, fullParamSymbol;
        for (SqlDTO sqlDTO : toolVO.getJdbc().getSqlList()) {
            oneSql = sqlDTO.getSql();
            //先判断指定sql模板中是否有参数占位符，有则进入替换逻辑
            if (oneSql.contains(KeywordEnum.PARAM_SYMBOL.getValue())) {
                //将所有实际参数与其中一条sql模板的占位符替换
                for (ParameterDTO parameterDTO : toolVO.getParameterList()) {
                    fullParamSymbol = KeywordEnum.PARAM_SYMBOL.getValue()+parameterDTO.getName()+"}";
                    if (oneSql.contains(fullParamSymbol)) {
                        sqlDTO.setSql(oneSql.replace(fullParamSymbol, parameterDTO.getValue()));
                    }
                }
            }
            actualSqlList.add(sqlDTO);
        }
        toolVO.getJdbc().setSqlList(actualSqlList);
        log.info("---->合并后sql列表：{}", toolVO.getJdbc().getSqlList());
    }

    public static void mergeHttp(ToolVO toolVO) {
        // 无变量
        if (toolVO.getParameterList() == null || toolVO.getParameterList().size() == 0) {
            return;
        }
        log.info("---->合并前http请求：{}", toolVO.getHttpRequest());
        String fullParamSymbol;
        String url = toolVO.getHttpRequest().getHttpURL();
        String body = toolVO.getHttpRequest().getHttpBody();
        //将参数一个个替换入url和body中
        for (ParameterDTO parameterDTO : toolVO.getParameterList()) {
            fullParamSymbol = KeywordEnum.PARAM_SYMBOL.getCode()+parameterDTO.getName()+"}";
            if (url.contains(fullParamSymbol)) {
                url = url.replace(fullParamSymbol, parameterDTO.getValue());
            }
            if (body != null && body.contains(fullParamSymbol)) {
                body = body.replace(fullParamSymbol, parameterDTO.getValue());
            }
        }
        toolVO.getHttpRequest().setHttpURL(url);
        toolVO.getHttpRequest().setHttpBody(body);
        log.info("---->合并后http请求：{}", toolVO.getHttpRequest());
    }


    public static void mergeRpc(ToolVO toolVO) {
        // 无变量
        if (toolVO.getParameterList() == null || toolVO.getParameterList().size() == 0) {
            return;
        }
        log.info("---->合并前rpc请求：{}", toolVO.getRpc());
        String fullParamSymbol;
        List<ParameterDTO> rpcParamList = new ArrayList<>();
        //先遍历目标，rpc入参
        for (ParameterDTO rpcParam : toolVO.getRpc().getParameterList()) {
            String oneValue = rpcParam.getValue();
            //判断入参中是否有指定占位符，无则不用替换直接插入
            if (oneValue.contains(KeywordEnum.PARAM_SYMBOL.getValue())) {
                //将所有实际参数与其中一个rpc入参值中的占位符替换
                for (ParameterDTO parameterDTO : toolVO.getParameterList()) {
                    fullParamSymbol = KeywordEnum.PARAM_SYMBOL.getValue()+parameterDTO.getName()+"}";
                    if (oneValue.contains(fullParamSymbol)) {
                        rpcParam.setValue(oneValue.replace(fullParamSymbol, parameterDTO.getValue()));
                    }
                }
            }
            rpcParamList.add(rpcParam);
        }
        toolVO.getRpc().setParameterList(rpcParamList);
        log.info("---->合并后rpc请求：{}", toolVO.getRpc());
    }

    public static ToolVO transformPO2VO(ToolPO po) {
        if (po == null) {
            return null;
        }
        ToolVO vo = new ToolVO();
        // 设置基本信息
        vo.setDescription(po.getDescription());
        vo.setToolId(po.getToolId());
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setPermission(po.getPermission());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        // 设置工具入参
        vo.setParameterList(TransformCommon.toParameter(po.getParameter()));
        // 模板转换
        switch (ToolTypeEnum.fromCode(po.getType())) {
            case SQL:
                JdbcDTO jdbcDTO = new JdbcDTO();
                //设置sql对象
                jdbcDTO.setSqlList(TransformCommon.toSql(po.getJdbcSql()));
                //设置数据源对象
                DataSourceDTO dataSourceDTO = new DataSourceDTO();
                dataSourceDTO.setDriver(po.getJdbcDriver());
                dataSourceDTO.setUrl(po.getJdbcUrl());
                dataSourceDTO.setUsername(po.getJdbcUsername());
                dataSourceDTO.setPassword(po.getJdbcPassword());
                jdbcDTO.setDataSource(dataSourceDTO);
                //设置数据库对象
                vo.setJdbc(jdbcDTO);
                break;
            case HTTP:
                HttpRequestDTO httpRequestDTO = new HttpRequestDTO();
                httpRequestDTO.setHttpURL(po.getHttpUrl());
                httpRequestDTO.setHttpType(po.getHttpType());
                httpRequestDTO.setHttpBody(po.getHttpBody());
                httpRequestDTO.setHttpHeaderList(TransformCommon.toParameter(po.getHttpHeader()));
                vo.setHttpRequest(httpRequestDTO);
                break;
            case RPC:
                RpcDTO rpcDTO = new RpcDTO();
                rpcDTO.setUrl(po.getRpcUrl());
                rpcDTO.setInterfaceName(po.getRpcInterface());
                rpcDTO.setMethodName(po.getRpcMethod());
                rpcDTO.setParameterType(po.getRpcParameterType());
                rpcDTO.setParameterList(TransformCommon.toParameter(po.getRpcParameter()));
                vo.setRpc(rpcDTO);
                break;
            case MULTIPLE:
                vo.setToolList(TransformCommon.toMultipleTool(po.getTools()));
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
        po.setName(vo.getName());
        po.setOwnerId(vo.getOwnerId());
        po.setOwnerName(vo.getOwnerName());
        po.setParameter(TransformCommon.toParameter(vo.getParameterList()));
        // 模板转换
        switch (ToolTypeEnum.fromCode(vo.getType())) {
            case SQL:
                po.setJdbcDriver(vo.getJdbc().getDataSource().getDriver());
                po.setJdbcUrl(vo.getJdbc().getDataSource().getUrl());
                po.setJdbcUsername(vo.getJdbc().getDataSource().getUsername());
                po.setJdbcPassword(vo.getJdbc().getDataSource().getPassword());
                po.setJdbcSql(TransformCommon.toSql(vo.getJdbc().getSqlList()));
                break;
            case HTTP:
                po.setHttpUrl(vo.getHttpRequest().getHttpURL());
                po.setHttpBody(vo.getHttpRequest().getHttpBody());
                po.setHttpHeader(TransformCommon.toParameter(vo.getHttpRequest().getHttpHeaderList()));
                po.setHttpType(vo.getHttpRequest().getHttpType());
                break;
            case RPC:
                po.setRpcInterface(vo.getRpc().getInterfaceName());
                po.setRpcMethod(vo.getRpc().getMethodName());
                po.setRpcUrl(vo.getRpc().getUrl());
                po.setRpcParameterType(vo.getRpc().getParameterType());
                po.setRpcParameter(TransformCommon.toParameter(vo.getRpc().getParameterList()));
                break;
            case MULTIPLE:
                po.setTools(TransformCommon.toMultipleTool(vo.getToolList()));
                break;
        }
        return po;
    }

}
