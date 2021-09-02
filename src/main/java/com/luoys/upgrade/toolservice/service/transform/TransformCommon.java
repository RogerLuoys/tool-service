package com.luoys.upgrade.toolservice.service.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.SqlTypeEnum;

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
            // 如果是正常类型就直接插入，否则根据sql判断类型
            if (!sqlDTO.getType().equals(SqlTypeEnum.UNKNOWN.getValue())) {
                commonDTO.setComment(sqlDTO.getType());
            } else if (sqlDTO.getSql().toUpperCase().matches("^INSERT INTO")) {
                commonDTO.setComment(SqlTypeEnum.INSERT.getValue());
            } else if (sqlDTO.getSql().toUpperCase().matches("^DELETE FROM [A-Z0-9_]+ WHERE")) {
                commonDTO.setComment(SqlTypeEnum.DELETE.getValue());
            } else if (sqlDTO.getSql().toUpperCase().matches("^UPDATE [A-Z0-9_]+ SET .+ WHERE")) {
                commonDTO.setComment(SqlTypeEnum.UPDATE.getValue());
            } else if (sqlDTO.getSql().toUpperCase().matches("^SELECT .+ FROM [A-Z0-9_]+ WHERE")) {
                commonDTO.setComment(SqlTypeEnum.SELECT.getValue());
            } else {
                commonDTO.setComment(SqlTypeEnum.UNKNOWN.getValue());
            }
//            // 如果是正常类型就直接插入，否则根据sql判断类型
//            if (!sqlDTO.getType().equals(SqlTypeEnum.UNKNOWN.getValue())) {
//                commonDTO.setComment(sqlDTO.getType());
//            } else if (sqlDTO.getSql().toUpperCase().startsWith(SqlTypeEnum.INSERT.getValue())) {
//                commonDTO.setComment(SqlTypeEnum.INSERT.getValue());
//            } else if (sqlDTO.getSql().toUpperCase().startsWith(SqlTypeEnum.DELETE.getValue())) {
//                commonDTO.setComment(SqlTypeEnum.DELETE.getValue());
//            } else if (sqlDTO.getSql().toUpperCase().startsWith(SqlTypeEnum.UPDATE.getValue())) {
//                commonDTO.setComment(SqlTypeEnum.UPDATE.getValue());
//            } else if (sqlDTO.getSql().toUpperCase().startsWith(SqlTypeEnum.SELECT.getValue())) {
//                commonDTO.setComment(SqlTypeEnum.SELECT.getValue());
//            } else {
//                commonDTO.setComment(SqlTypeEnum.UNKNOWN.getValue());
//            }
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

//    public static List<StepDTO> toStep(String step) {
//        if (StringUtil.isBlank(step)) {
//            return null;
//        }
//        List<CommonDTO> commonList = JSON.parseArray(step, CommonDTO.class);
//        List<StepDTO> stepList = new ArrayList<>();
//        for (CommonDTO commonDTO: commonList) {
//            StepDTO stepDTO = new StepDTO();
//            stepDTO.setStepId(commonDTO.getValue());
//            stepDTO.setName(commonDTO.getTitle());
//            stepDTO.setSequence(commonDTO.getIdx());
//            stepList.add(stepDTO);
//        }
//        return stepList;
//    }
//
//    public static String toStep(List<StepDTO> stepList) {
//        if (stepList == null || stepList.size() == 0) {
//            return null;
//        }
//        List<CommonDTO> commonList = new ArrayList<>();
//        for (StepDTO stepDTO: stepList) {
//            CommonDTO commonDTO = new CommonDTO();
//            commonDTO.setValue(stepDTO.getStepId().toString());
//            commonDTO.setIdx(stepDTO.getSequence());
//            commonDTO.setTitle(stepDTO.getName());
//            commonList.add(commonDTO);
//        }
//        return JSON.toJSONString(commonList);
//    }


//    public static List<CaseDTO> toCase(String cases) {
//        if (StringUtil.isBlank(cases)) {
//            return null;
//        }
//        List<CommonDTO> commonList = JSON.parseArray(cases, CommonDTO.class);
//        List<CaseDTO> caseList = new ArrayList<>();
//        for (CommonDTO commonDTO: commonList) {
//            CaseDTO caseDTO = new CaseDTO();
//            caseDTO.setCaseId(commonDTO.getValue());
//            caseDTO.setName(commonDTO.getTitle());
//            caseDTO.setSequence(commonDTO.getIdx());
//            caseList.add(caseDTO);
//        }
//        return caseList;
//    }
//
//    public static String toCase(List<CaseDTO> caseList) {
//        if (caseList == null || caseList.size() == 0) {
//            return null;
//        }
//        List<CommonDTO> commonList = new ArrayList<>();
//        for (CaseDTO caseDTO: caseList) {
//            CommonDTO commonDTO = new CommonDTO();
//            commonDTO.setValue(caseDTO.getCaseId().toString());
//            commonDTO.setIdx(caseDTO.getSequence());
//            commonDTO.setTitle(caseDTO.getName());
//            commonList.add(commonDTO);
//        }
//        return JSON.toJSONString(commonList);
//    }
}
