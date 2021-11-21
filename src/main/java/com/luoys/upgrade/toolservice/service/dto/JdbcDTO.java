package com.luoys.upgrade.toolservice.service.dto;

import com.luoys.upgrade.toolservice.common.StringUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * sql执行类，包含数据源和执行的sql
 *
 * @author luoys
 */
@Data
public class JdbcDTO {

    private DataSourceDTO dataSource;

    private List<SqlDTO> sqlList;


    /**
     * 用真实参数替换参数占位符，只处理sql，不处理数据源
     * @param parameterList -
     */
    public void replace(List<ParameterDTO> parameterList) {
        // 无变量
        if (parameterList == null || parameterList.size() == 0) {
//            log.info("---->无参数需要合并");
            return;
        }
//        log.info("---->合并前sql列表：{}", jdbcDTO.getSqlList());
        List<SqlDTO> actualSqlList = new ArrayList<>();
        String oneSql, fullParamSymbol;

        for (int i = 0; i < sqlList.size(); i++) {
            oneSql = sqlList.get(i).getSql();
            //先判断指定sql模板中是否有参数占位符，有则进入替换逻辑
            if (!oneSql.matches(StringUtil.PARAM_REGEX)) {
                continue;
            }
            //将所有实际参数与其中一条sql模板的占位符替换
            for (ParameterDTO parameterDTO : parameterList) {
                fullParamSymbol = "${"+parameterDTO.getName()+"}";
                if (oneSql.contains(fullParamSymbol)) {
                    // 设置最终sql
                    sqlList.get(i).setSql(oneSql.replace(fullParamSymbol, parameterDTO.getValue()));
                }
            }
        }
//        for (SqlDTO sqlDTO : sqlList) {
//            oneSql = sqlDTO.getSql();
//            //先判断指定sql模板中是否有参数占位符，有则进入替换逻辑
//            if (oneSql.matches(".*\\$\\{[A-Za-z0-9]{1,20}}.*")) {
//                //将所有实际参数与其中一条sql模板的占位符替换
//                for (ParameterDTO parameterDTO : parameterList) {
//                    fullParamSymbol = "${"+parameterDTO.getName()+"}";
//                    if (oneSql.contains(fullParamSymbol)) {
//                        sqlDTO.setSql(oneSql.replace(fullParamSymbol, parameterDTO.getValue()));
//                    }
//                }
//            }
//            actualSqlList.add(sqlDTO);
//        }
//        jdbcDTO.setSqlList(actualSqlList);
//        log.info("---->合并后sql列表：{}", jdbcDTO.getSqlList());
    }

}
