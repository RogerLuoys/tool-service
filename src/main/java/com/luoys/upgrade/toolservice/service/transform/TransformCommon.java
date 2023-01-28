package com.luoys.upgrade.toolservice.service.transform;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共参数数据转换
 *
 * @author luoys
 */
@Slf4j
@Deprecated
public class TransformCommon {

//    // 变量格式 ${name}, 1<=name长度<=20
//    private static final String PARAM_REGEX = ".*\\$\\{[A-Za-z0-9]{1,20}}.*";
//
//
//    /**
//     * 参数值转换成参数对象列表
//     * @param parameter 数据库中的parameter值
//     * @return 参数对象列表
//     */
//    public static List<ParameterDTO> toParameter(String parameter){
//        if (StringUtil.isBlank(parameter)) {
//            return null;
//        }
//        List<CommonDTO> commonList = JSON.parseArray(parameter, CommonDTO.class);
//        List<ParameterDTO> parameterList = new ArrayList<>();
//        for (CommonDTO commonDTO: commonList) {
//            ParameterDTO parameterDTO = new ParameterDTO();
//            parameterDTO.setComment(commonDTO.getComment());
//            parameterDTO.setValue(commonDTO.getValue());
//            parameterDTO.setName(commonDTO.getTitle());
//            parameterList.add(parameterDTO);
//        }
//        return parameterList;
//    }
//
//    public static String toParameter(List<ParameterDTO> parameterList){
//        if (parameterList == null || parameterList.size() == 0) {
//            return null;
//        }
//        List<CommonDTO> commonList = new ArrayList<>();
//        for (ParameterDTO parameterDTO: parameterList) {
//            CommonDTO commonDTO = new CommonDTO();
//            commonDTO.setTitle(parameterDTO.getName());
//            commonDTO.setComment(parameterDTO.getComment());
//            commonDTO.setValue(parameterDTO.getValue());
//            commonList.add(commonDTO);
//
//        }
//        return JSON.toJSONString(commonList);
//    }
//
//
//    /**
//     * 参数值转换成参数对象列表
//     * @param sql 数据库中的param值
//     * @return 参数对象列表
//     */
//    public static List<SqlDTO> toSql(String sql){
//        if (StringUtil.isBlank(sql)) {
//            return null;
//        }
//        List<CommonDTO> commonList = JSON.parseArray(sql, CommonDTO.class);
//        List<SqlDTO> sqlList = new ArrayList<>();
//        for (CommonDTO commonDTO: commonList) {
//            SqlDTO sqlDTO = new SqlDTO();
//            sqlDTO.setSql(commonDTO.getValue());
//            sqlDTO.setType(commonDTO.getComment());
//            sqlList.add(sqlDTO);
//        }
//        return sqlList;
//
//    }
//
//    public static String toSql(List<SqlDTO> sqlList){
//        if (sqlList == null || sqlList.size() == 0) {
//            return null;
//        }
//        List<CommonDTO> commonList = new ArrayList<>();
//        for (SqlDTO sqlDTO: sqlList) {
//            CommonDTO commonDTO = new CommonDTO();
//            commonDTO.setValue(sqlDTO.getSql());
//            // 如果是正常类型就直接插入，否则根据sql判断类型
//            if (!sqlDTO.getType().equals(SqlTypeEnum.UNKNOWN.getValue())) {
//                commonDTO.setComment(sqlDTO.getType());
//            } else if (sqlDTO.getSql().toUpperCase().matches("^INSERT INTO .+")) {
//                commonDTO.setComment(SqlTypeEnum.INSERT.getValue());
//            } else if (sqlDTO.getSql().toUpperCase().matches("^DELETE FROM [A-Z0-9_]+ WHERE .+")) {
//                commonDTO.setComment(SqlTypeEnum.DELETE.getValue());
//            } else if (sqlDTO.getSql().toUpperCase().matches("^UPDATE [A-Z0-9_]+ SET .+ WHERE .+")) {
//                commonDTO.setComment(SqlTypeEnum.UPDATE.getValue());
//            } else if (sqlDTO.getSql().toUpperCase().matches("^SELECT .+ FROM [A-Z0-9_]+ WHERE .+")) {
//                commonDTO.setComment(SqlTypeEnum.SELECT.getValue());
//            } else {
//                commonDTO.setComment(SqlTypeEnum.UNKNOWN.getValue());
//            }
//            commonList.add(commonDTO);
//
//        }
//        return JSON.toJSONString(commonList);
//    }
//
//    public static List<String> toMultipleTool(String tools) {
//        if (StringUtil.isBlank(tools)) {
//            return null;
//        }
//        List<CommonDTO> commonList = JSON.parseArray(tools, CommonDTO.class);
//        List<String> toolList = new ArrayList<>();
//        for (CommonDTO commonDTO: commonList) {
//            toolList.add(commonDTO.getValue());
//        }
//        return toolList;
//    }
//
//    public static String toMultipleTool(List<String> toolList) {
//        if (toolList == null || toolList.size() == 0) {
//            return null;
//        }
//        List<CommonDTO> commonList = new ArrayList<>();
//        for (String tool: toolList) {
//            CommonDTO commonDTO = new CommonDTO();
//            commonDTO.setValue(tool);
//            commonList.add(commonDTO);
//        }
//        return JSON.toJSONString(commonList);
//    }
//
//    public static Map<String, List<StepDTO>> toMultipleStep(String step) {
//        Map<String, List<StepDTO>> stepMap = new HashMap<>();
//        List<StepDTO> ifStepList = new ArrayList<>();
//        List<StepDTO> thenStepList = new ArrayList<>();
//        List<StepDTO> elseStepList = new ArrayList<>();
//        stepMap.put(AreaEnum.IF.getValue(), ifStepList);
//        stepMap.put(AreaEnum.THEN.getValue(), thenStepList);
//        stepMap.put(AreaEnum.ELSE.getValue(), elseStepList);
//        if (StringUtil.isBlank(step)) {
//            return stepMap;
//        }
//        List<CommonDTO> commonList = JSON.parseArray(step, CommonDTO.class);
//        for (CommonDTO commonDTO: commonList) {
//            StepDTO stepDTO = new StepDTO();
//            stepDTO.setStepId(commonDTO.getValue());
//            stepDTO.setArea(commonDTO.getTitle());
//            stepDTO.setSequence(commonDTO.getIdx());
//            if (stepDTO.getArea().equals(AreaEnum.IF.getValue())) {
//                ifStepList.add(stepDTO);
//            } else if (stepDTO.getArea().equals(AreaEnum.THEN.getValue())) {
//                thenStepList.add(stepDTO);
//            } else if (stepDTO.getArea().equals(AreaEnum.ELSE.getValue())) {
//                elseStepList.add(stepDTO);
//            }
//        }
//        return stepMap;
//    }
//
//    public static String toMultipleStep(List<StepDTO> stepList) {
//        if (stepList == null || stepList.size() == 0) {
//            return null;
//        }
//        List<CommonDTO> commonList = new ArrayList<>();
//        for (StepDTO step: stepList) {
//            CommonDTO commonDTO = new CommonDTO();
//            commonDTO.setValue(step.getStepId());
//            commonDTO.setIdx(step.getSequence());
//            commonDTO.setTitle(step.getArea());
//            commonList.add(commonDTO);
//        }
//        return JSON.toJSONString(commonList);
//    }
//
//    /**
//     * 将参数合并入sql列表中-新
//     * @param parameterList -
//     * @param jdbcDTO -
//     */
//    public static void mergeSql(List<ParameterDTO> parameterList, JdbcDTO jdbcDTO) {
//        // 无变量
//        if (parameterList == null || parameterList.size() == 0) {
//            log.info("---->无参数需要合并");
//            return;
//        }
//        log.info("---->合并前sql列表：{}", jdbcDTO.getSql());
//        String fullParamSymbol;
//        //先判断指定sql模板中是否有参数占位符，有则进入替换逻辑
//        if (jdbcDTO.getSql().matches(PARAM_REGEX)) {
//            //将所有实际参数与其中一条sql模板的占位符替换
//            for (ParameterDTO parameterDTO : parameterList) {
//                fullParamSymbol = "${"+parameterDTO.getName()+"}";
//                if (jdbcDTO.getSql().contains(fullParamSymbol)) {
//                    jdbcDTO.setSql(jdbcDTO.getSql().replace(fullParamSymbol, parameterDTO.getValue()));
//                }
//            }
//        }
//        log.info("---->合并后sql列表：{}", jdbcDTO.getSql());
//    }
//
//    /**
//     * 将参数合并入http请求中
//     * @param parameterList -
//     * @param httpRequestDTO 工具对象
//     */
//    public static void mergeHttp(List<ParameterDTO> parameterList, HttpRequestDTO httpRequestDTO) {
//        // 无变量
//        if (parameterList == null || parameterList.size() == 0) {
//            return;
//        }
//        log.info("---->合并前http请求：{}", httpRequestDTO);
//        String fullParamSymbol;
//        String url = httpRequestDTO.getHttpURL();
//        String body = httpRequestDTO.getHttpBody();
//        //将参数一个个替换入url和body中
//        for (ParameterDTO parameterDTO : parameterList) {
//            fullParamSymbol = "${"+parameterDTO.getName()+"}";
//            if (url.contains(fullParamSymbol)) {
//                url = url.replace(fullParamSymbol, parameterDTO.getValue());
//            }
//            if (body != null && body.contains(fullParamSymbol)) {
//                body = body.replace(fullParamSymbol, parameterDTO.getValue());
//            }
//        }
//        httpRequestDTO.setHttpURL(url);
//        httpRequestDTO.setHttpBody(body);
//        log.info("---->合并后http请求：{}", httpRequestDTO);
//    }
//
//    /**
//     * 将参数合并入rpc请求中
//     * @param parameterList -
//     * @param rpcDTO 工具对象
//     */
//    public static void mergeRpc(List<ParameterDTO> parameterList, RpcDTO rpcDTO) {
//        // 无变量
//        if (parameterList == null || parameterList.size() == 0) {
//            return;
//        }
//        log.info("---->合并前rpc请求：{}", rpcDTO);
//        String fullParamSymbol;
//        List<ParameterDTO> rpcParamList = new ArrayList<>();
//        // 替换url字段中的变量
//        if (rpcDTO.getUrl().matches(PARAM_REGEX)) {
//            for (ParameterDTO parameterDTO : parameterList) {
//                fullParamSymbol = "${" + parameterDTO.getName() + "}";
//                if (rpcDTO.getUrl().contains(fullParamSymbol)) {
//                    rpcDTO.setUrl(rpcDTO.getUrl().replace(fullParamSymbol, parameterDTO.getValue()));
//                }
//            }
//        }
//        //遍历目标，替换rpc入参
//        for (ParameterDTO rpcParam : rpcDTO.getParameterList()) {
//            String oneValue = rpcParam.getValue();
//            //判断入参中是否有指定占位符，无则不用替换直接插入
//            if (oneValue.matches(PARAM_REGEX)) {
//                //将所有实际参数与其中一个rpc入参值中的占位符替换
//                for (ParameterDTO parameterDTO : parameterList) {
//                    fullParamSymbol = "${" + parameterDTO.getName() + "}";
//                    if (oneValue.contains(fullParamSymbol)) {
//                        rpcParam.setValue(oneValue.replace(fullParamSymbol, parameterDTO.getValue()));
//                    }
//                }
//            }
//            rpcParamList.add(rpcParam);
//        }
//        rpcDTO.setParameterList(rpcParamList);
//        log.info("---->合并后rpc请求：{}", rpcDTO);
//    }

}
