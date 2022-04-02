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

    private String sqlType;

    private String sql;

    /**
     * 用真实参数替换参数占位符，只处理sql，不处理数据源
     * @param parameterList -
     */
    public void merge(List<ParameterDTO> parameterList) {
        // 无变量
        if (parameterList == null || parameterList.size() == 0) {
            return;
        }
        String oneSql;
        StringBuilder fullParamSymbol = new StringBuilder();
        for (int i = 0; i < sqlList.size(); i++) {
            oneSql = sqlList.get(i).getSql();
            //先判断指定sql模板中是否有参数占位符，有则进入替换逻辑
            if (!oneSql.matches(StringUtil.PARAM_REGEX)) {
                continue;
            }
            //将所有实际参数与其中一条sql模板的占位符替换
            for (ParameterDTO parameterDTO : parameterList) {
                fullParamSymbol.delete(0, fullParamSymbol.length());
                fullParamSymbol.append("${").append(parameterDTO.getName()).append("}");
                if (oneSql.contains(fullParamSymbol)) {
                    // 设置最终sql
                    sqlList.get(i).setSql(oneSql.replace(fullParamSymbol, parameterDTO.getValue()));
                }
            }
        }
    }

}
