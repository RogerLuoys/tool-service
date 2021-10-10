package com.luoys.upgrade.toolservice.service.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.AreaEnum;
import com.luoys.upgrade.toolservice.service.enums.SqlTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共参数数据转换
 *
 * @author luoys
 */
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
            // 如果是正常类型就直接插入，否则根据sql判断类型
            if (!sqlDTO.getType().equals(SqlTypeEnum.UNKNOWN.getValue())) {
                commonDTO.setComment(sqlDTO.getType());
            } else if (sqlDTO.getSql().toUpperCase().matches("^INSERT INTO .+")) {
                commonDTO.setComment(SqlTypeEnum.INSERT.getValue());
            } else if (sqlDTO.getSql().toUpperCase().matches("^DELETE FROM [A-Z0-9_]+ WHERE .+")) {
                commonDTO.setComment(SqlTypeEnum.DELETE.getValue());
            } else if (sqlDTO.getSql().toUpperCase().matches("^UPDATE [A-Z0-9_]+ SET .+ WHERE .+")) {
                commonDTO.setComment(SqlTypeEnum.UPDATE.getValue());
            } else if (sqlDTO.getSql().toUpperCase().matches("^SELECT .+ FROM [A-Z0-9_]+ WHERE .+")) {
                commonDTO.setComment(SqlTypeEnum.SELECT.getValue());
            } else {
                commonDTO.setComment(SqlTypeEnum.UNKNOWN.getValue());
            }
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

    public static Map<String, List<StepDTO>> toMultipleStep(String step) {
        Map<String, List<StepDTO>> stepMap = new HashMap<>();
        List<StepDTO> ifStepList = new ArrayList<>();
        List<StepDTO> thenStepList = new ArrayList<>();
        List<StepDTO> elseStepList = new ArrayList<>();
        stepMap.put(AreaEnum.IF.getValue(), ifStepList);
        stepMap.put(AreaEnum.THEN.getValue(), thenStepList);
        stepMap.put(AreaEnum.ELSE.getValue(), elseStepList);
        if (StringUtil.isBlank(step)) {
            return stepMap;
        }
        List<CommonDTO> commonList = JSON.parseArray(step, CommonDTO.class);
        for (CommonDTO commonDTO: commonList) {
            StepDTO stepDTO = new StepDTO();
            stepDTO.setStepId(commonDTO.getValue());
            stepDTO.setArea(commonDTO.getTitle());
            stepDTO.setSequence(commonDTO.getIdx());
            if (stepDTO.getArea().equals(AreaEnum.IF.getValue())) {
                ifStepList.add(stepDTO);
            } else if (stepDTO.getArea().equals(AreaEnum.THEN.getValue())) {
                thenStepList.add(stepDTO);
            } else if (stepDTO.getArea().equals(AreaEnum.ELSE.getValue())) {
                elseStepList.add(stepDTO);
            }
        }
        return stepMap;
    }

    public static String toMultipleStep(List<StepDTO> stepList) {
        if (stepList == null || stepList.size() == 0) {
            return null;
        }
        List<CommonDTO> commonList = new ArrayList<>();
        for (StepDTO step: stepList) {
            CommonDTO commonDTO = new CommonDTO();
            commonDTO.setValue(step.getStepId());
            commonDTO.setIdx(step.getSequence());
            commonDTO.setTitle(step.getArea());
            commonList.add(commonDTO);
        }
        return JSON.toJSONString(commonList);
    }

}
