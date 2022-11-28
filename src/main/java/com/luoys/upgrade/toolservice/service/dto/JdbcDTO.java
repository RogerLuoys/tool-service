package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * sql执行类，包含数据源和执行的sql
 *
 * @author luoys
 */
@Data
@Deprecated
public class JdbcDTO {

//    private DataSourceDTO dataSource;
//
//    private List<SqlDTO> sqlList;


    /**
     * 数据库名，用于引用
     */
    private String dbName;//新加的

    /**
     * 数据源地址
     */
    private String url;//新加的

    /**
     * 数据源驱动
     */
    private String driver;//新加的

    /**
     * 数据源用户名
     */
    private String username;//新加的

    /**
     * 数据源密码
     */
    private String password;//新加的

    // 新加的
    private String sqlType;

    // 新加的
    private String sql;

//    /**
//     * 用真实参数替换参数占位符，只处理sql，不处理数据源
//     * @param parameterList -
//     */
//    public void merge(List<ParameterDTO> parameterList) {
//        // 无变量
//        if (parameterList == null || parameterList.size() == 0) {
//            return;
//        }
//        String oneSql;
//        StringBuilder fullParamSymbol = new StringBuilder();
//        for (int i = 0; i < sqlList.size(); i++) {
//            oneSql = sqlList.get(i).getSql();
//            //先判断指定sql模板中是否有参数占位符，有则进入替换逻辑
//            if (!oneSql.matches(StringUtil.PARAM_REGEX)) {
//                continue;
//            }
//            //将所有实际参数与其中一条sql模板的占位符替换
//            for (ParameterDTO parameterDTO : parameterList) {
//                fullParamSymbol.delete(0, fullParamSymbol.length());
//                fullParamSymbol.append("${").append(parameterDTO.getName()).append("}");
//                if (oneSql.contains(fullParamSymbol)) {
//                    // 设置最终sql
//                    sqlList.get(i).setSql(oneSql.replace(fullParamSymbol, parameterDTO.getValue()));
//                }
//            }
//        }
//    }

}
