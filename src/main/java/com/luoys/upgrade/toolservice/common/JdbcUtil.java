package com.luoys.upgrade.toolservice.common;

import com.luoys.upgrade.toolservice.controller.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.controller.dto.JdbcDTO;
import com.luoys.upgrade.toolservice.controller.dto.SqlDTO;
import com.luoys.upgrade.toolservice.controller.enums.SqlTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
public class JdbcUtil {

    private final static String SELECT = "select";
    private final static String UPDATE = "update";
    private final static String COUNT = "count(1)";
    private final static String DELETE = "delete";
    private final static String DEFAULT_ORDER = " order by id desc";
    private final static String DEFAULT_LIMIT = " limit 10";

    private static final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate();

//    public static void init(String driver, String url, String userName, String password) {
//        dataSource.setDriverClassName(driver);
//        dataSource.setUrl(url);
//        dataSource.setUsername(userName);
//        dataSource.setPassword(password);
//        jdbcTemplate.setDataSource(dataSource);
//    }

    public static void init(DataSourceDTO dataSourceDTO) {
        dataSource.setDriverClassName(dataSourceDTO.getDriver());
        dataSource.setUrl(dataSourceDTO.getUrl());
        dataSource.setUsername(dataSourceDTO.getUserName());
        dataSource.setPassword(dataSourceDTO.getPassword());
        jdbcTemplate.setDataSource(dataSource);
    }

    /**
     * 执行sql
     * @param jdbcDTO -
     * @return 全部执行成功则为true
     */
    public static boolean execute(JdbcDTO jdbcDTO) {
        if (jdbcDTO == null || jdbcDTO.getDataSource() == null || jdbcDTO.getSqlList() == null) {
            return false;
        }
        init(jdbcDTO.getDataSource());
        List<SqlDTO> sqlDTOList = jdbcDTO.getSqlList();
        for (SqlDTO sqlDTO : sqlDTOList) {
            log.info("---->执行sql：" + sqlDTO.getSql());
            switch (SqlTypeEnum.fromValue(sqlDTO.getType())) {
                // 查询
                case INSERT:
                    updateNoLimit(sqlDTO.getSql());
                    break;
                case DELETE:
                    delete(sqlDTO.getSql());
                    break;
                case UPDATE:
                    update(sqlDTO.getSql());
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    /**
     * 把更新sql转换为查询总行数的sql，查询sql以更新sql的条件为条件
     *
     * @param updateSql 更新sql，注意字段间空格只能有一个
     * @return select count(1) from 更新的表名 + 更新的条件
     */
    private static String transformUpdate2Select(String updateSql) {
        int endIndex = updateSql.toLowerCase().indexOf(" set ");
        String tableName = updateSql.substring(7, endIndex);
        int startIndex = updateSql.toLowerCase().indexOf(" where ");
        String condition = updateSql.substring(startIndex);
        return "select count(1) from " + tableName + condition;
    }

    /**
     * 把删除sql转换为查询总行数的sql，查询sql以更新sql的条件为条件
     *
     * @param deleteSql 删除sql，注意字段间空格只能有一个
     * @return select count(1) from 删除的表名 + 删除的条件
     */
    private static String transformDelete2Select(String deleteSql) {
        int startIndex = deleteSql.toLowerCase().indexOf(" from ");
        String condition = deleteSql.substring(startIndex);
        return "select count(1) " + condition;
    }

//    /**
//     * 检查sql格式是否正确
//     *
//     * @param sql 注意字段间空格只能有一个
//     * @param type -
//     * @return -
//     */
//    private static boolean checkSqlType(String sql, String type) {
//        String[] sqlArray = sql.toLowerCase().split(" ");
//        switch (type) {
//            case SELECT:
//                if (!sqlArray[0].equals(SELECT)) {
//                    log.warn("---->查询sql类型不匹配");
//                    return false;
//                }
//                return true;
//            case UPDATE:
//                if (!sqlArray[0].equals(UPDATE)) {
//                    log.warn("---->更新sql类型不匹配");
//                    return false;
//                }
//                return true;
//            case COUNT:
//                if (!sqlArray[0].equals(SELECT)) {
//                    log.warn("---->查行数sql类型不匹配");
//                    return false;
//                }
//                if (!sqlArray[1].equals(COUNT)) {
//                    log.warn("---->查行数sql类型未包含count(1)");
//                    return false;
//                }
//                return true;
//            case DELETE:
//                if (!sqlArray[0].equals(DELETE)) {
//                    log.warn("---->更新sql类型不匹配");
//                    return false;
//                }
//                return true;
//            default:
//                return false;
//        }
//    }

    /**
     * 加默认查询规则，默认按ID倒序，最多查10条，避免没必要的全表查询
     *
     * @param sql 完整的sql
     * @return 拼接默认查询规则后的sql
     */
    private static String addSelectDefault(String sql) {
        String defaultSql = sql.replace(";", "");
        // 截取sql后缀，避免字符串中有同样的值
        int startIndex = Math.max(defaultSql.lastIndexOf("\""), defaultSql.lastIndexOf("'"));
        startIndex = Math.max(startIndex, defaultSql.lastIndexOf(")"));
        String suffixSql = defaultSql.substring(startIndex != -1 ? startIndex : 0).toLowerCase();

        if (!suffixSql.contains(" order by ")) {
            defaultSql = defaultSql + DEFAULT_ORDER;
        }
        if (!suffixSql.contains(" limit ")) {
            defaultSql = defaultSql + DEFAULT_LIMIT;
        }
        return defaultSql + ";";
    }

    public static Integer update(String sql) {
//        String executeSql = sql.replace(";", "") + ";";
//        if (!checkSqlType(executeSql, UPDATE)) {
//            log.warn("---->更新语句的sql格式错误：{}", sql);
//            return null;
//        }
        int effectRow = count(transformUpdate2Select(sql));
        if (effectRow > 10) {
            log.warn("---->一次更新超过10行，请确认sql条件是否正确");
            return null;
        } else if (effectRow == 0) {
            log.warn("---->查无此类数据，不需要更新");
            return null;
        }
        log.info("---->最终执行sql：" + sql);
        return jdbcTemplate.update(sql);
    }

    public static Integer updateNoLimit(String sql) {
//        String executeSql = sql.replace(";", "") + ";";
//        if (!checkSqlType(executeSql, UPDATE)) {
//            log.warn("---->更新语句的sql格式错误：{}", sql);
//            return null;
//        }
        int effectRow = count(transformUpdate2Select(sql));
        if (effectRow > 100) {
            log.warn("---->一次更新超过100行，请确认sql条件是否正确");
            return 0;
        } else if (effectRow == 0) {
            log.warn("---->查无此类数据，不需要更新");
            return 0;
        }
        return jdbcTemplate.update(sql);
    }

    public static Integer count(String sql) {
//        String executeSql = sql.replace(";", "") + ";";
//        if (!checkSqlType(sql, COUNT)) {
//            log.warn("---->查总数语句的sql格式错误：{}", sql);
//            return null;
//        }
//        log.info("---->最终执行sql：" + sql);
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        return Integer.valueOf(result.get(COUNT).toString());
    }

    public static Map<String, Object> select(String sql) {
//        if (!checkSqlType(sql, SELECT)) {
//            log.warn("---->查询语句的sql格式错误：{}", sql);
//            return null;
//        }
//        String executeSql = addSelectDefault(sql);
//        log.info("---->最终执行sql：" + executeSql);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        return result.size() == 0 ? null : result.get(0);
    }

    public static List<Map<String, Object>> selectMultiRow(String sql) {
//        if (!checkSqlType(sql, SELECT)) {
//            log.warn("---->查询语句的sql格式错误：{}", sql);
//            return null;
//        }
        String executeSql = addSelectDefault(sql);
        return jdbcTemplate.queryForList(executeSql);
    }

    public static String selectOneCell(String sql) {
        String[] sqlList = sql.split(" ");
        //查询语句只能查询一列数据
        if (!sqlList[2].equalsIgnoreCase("from") || sqlList[1].equalsIgnoreCase("*") || sqlList[1].contains(",")) {
            log.warn("---->查询单格数据的sql格式不合规：{}", sql);
            return null;
        }
        Map<String, Object> result = select(sql);
        Object value = result.get(sqlList[1]);
        //时间格式转换
        if (value.getClass().getName().equals("java.time.LocalDateTime")) {
            LocalDateTime time = (LocalDateTime) value;
            DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));
            return dateTimeFormatter.format(time);
        } else {
            return value.toString();
        }
    }

    public static Integer delete(String sql) {
//        String executeSql = sql.replace(";", "") + ";";
//        if (!checkSqlType(executeSql, DELETE)) {
//            log.warn("---->删除语句的sql格式错误：{}", sql);
//            return null;
//        }
        int effectRow = count(transformDelete2Select(sql));
        if (effectRow > 10) {
            log.warn("---->一次删除超过10行，请确认sql条件是否正确");
            return -1;
        } else if (effectRow == 0) {
            log.warn("---->查无此类数据，不需要更新");
            return 0;
        }
        return jdbcTemplate.update(sql);
    }
}
