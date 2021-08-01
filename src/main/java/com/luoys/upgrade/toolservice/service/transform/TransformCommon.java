package com.luoys.upgrade.toolservice.service.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.CommonDTO;
import com.luoys.upgrade.toolservice.service.dto.ParameterDTO;
import com.luoys.upgrade.toolservice.service.dto.SqlDTO;

import java.util.ArrayList;
import java.util.List;

public class TransformCommon {

    /**
     * 参数值转换成参数对象列表
     * @param parameter 数据库中的parameter值
     * @return 参数对象列表
     */
    public static List<ParameterDTO> toParameter(String parameter){
        if (StringUtil.isBlank(parameter)) {
            return null;
        }
        List<CommonDTO> commonList = JSON.parseArray(parameter, CommonDTO.class);
        List<ParameterDTO> parameterList = new ArrayList<>();
        for (CommonDTO commonDTO: commonList) {
            ParameterDTO parameterDTO = new ParameterDTO();
            parameterDTO.setComment(commonDTO.getComment());
            parameterDTO.setValue(commonDTO.getValue());
            parameterDTO.setName(commonDTO.getTitle());
            parameterList.add(parameterDTO);

        }
        return parameterList;
    }

    public static String toParameter(List<ParameterDTO> parameterList){
        if (parameterList == null || parameterList.size() == 0) {
            return null;
        }
        List<CommonDTO> commonList = new ArrayList<>();
        for (ParameterDTO parameterDTO: parameterList) {
            CommonDTO commonDTO = new CommonDTO();
            commonDTO.setTitle(parameterDTO.getName());
            commonDTO.setComment(parameterDTO.getComment());
            commonDTO.setValue(parameterDTO.getValue());
            commonList.add(commonDTO);

        }
        return JSON.toJSONString(commonList);
    }


    /**
     * 参数值转换成参数对象列表
     * @param sql 数据库中的param值
     * @return 参数对象列表
     */
    public static List<SqlDTO> toSql(String sql){
        if (StringUtil.isBlank(sql)) {
            return null;
        }
        List<CommonDTO> commonList = JSON.parseArray(sql, CommonDTO.class);
        List<SqlDTO> sqlList = new ArrayList<>();
        for (CommonDTO commonDTO: commonList) {
            SqlDTO sqlDTO = new SqlDTO();
            sqlDTO.setSql(commonDTO.getValue());
            sqlDTO.setType(commonDTO.getComment());
            sqlList.add(sqlDTO);

        }
        return sqlList;

    }

    public static String toSql(List<SqlDTO> sqlList){
        if (sqlList == null || sqlList.size() == 0) {
            return null;
        }
        List<CommonDTO> commonList = new ArrayList<>();
        for (SqlDTO sqlDTO: sqlList) {
            CommonDTO commonDTO = new CommonDTO();
            commonDTO.setValue(sqlDTO.getSql());
            commonDTO.setComment(sqlDTO.getType());
            commonList.add(commonDTO);

        }
        return JSON.toJSONString(commonList);
    }

    public static List<String> toMultipleTool(String tools) {
        if (StringUtil.isBlank(tools)) {
            return null;
        }
        List<CommonDTO> commonList = JSON.parseArray(tools, CommonDTO.class);
        List<String> toolList = new ArrayList<>();
        for (CommonDTO commonDTO: commonList) {
            toolList.add(commonDTO.getValue());
        }
        return toolList;
    }

    public static String toMultipleTool(List<String> toolList) {
        if (toolList == null || toolList.size() == 0) {
            return null;
        }
        List<CommonDTO> commonList = new ArrayList<>();
        for (String tool: toolList) {
            CommonDTO commonDTO = new CommonDTO();
            commonDTO.setValue(tool);
            commonList.add(commonDTO);
        }
        return JSON.toJSONString(commonList);
    }
}
