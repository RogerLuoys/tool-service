package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.JdbcDTO;
import com.luoys.upgrade.toolservice.service.dto.SqlDTO;
import com.luoys.upgrade.toolservice.service.enums.SqlTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DBClient {

    private final String COUNT = "count(1)";
    private final String DEFAULT_ORDER = " order by id desc";
    private final String DEFAULT_LIMIT = " limit 10";

    private final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void init(DataSourceDTO dataSourceDTO) {
        dataSource.setDriverClassName(dataSourceDTO.getDriver());
        dataSource.setUrl(dataSourceDTO.getUrl());
        dataSource.setUsername(dataSourceDTO.getUsername());
        dataSource.setPassword(dataSourceDTO.getPassword());
        jdbcTemplate.setDataSource(dataSource);
    }

    /**
     * 执行sql
     * @param jdbcDTO -
     * @return 全部执行成功则为true
     */
    public boolean execute(JdbcDTO jdbcDTO) {
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
    private String transformUpdate2Select(String updateSql) {
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
    private String transformDelete2Select(String deleteSql) {
        int startIndex = deleteSql.toLowerCase().indexOf(" from ");
        String condition = deleteSql.substring(startIndex);
        return "select count(1) " + condition;
    }

    /**
     * 加默认查询规则，默认按ID倒序，最多查10条，避免没必要的全表查询
     *
     * @param sql 完整的sql
     * @return 拼接默认查询规则后的sql
     */
    private String addSelectDefault(String sql) {
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

    public Integer update(String sql) {
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

    public Integer updateNoLimit(String sql) {
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

    public Integer count(String sql) {
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        return Integer.valueOf(result.get(COUNT).toString());
    }

    public Map<String, Object> select(String sql) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        return result.size() == 0 ? null : result.get(0);
    }

    public List<Map<String, Object>> selectMultiRow(String sql) {
        String executeSql = addSelectDefault(sql);
        return jdbcTemplate.queryForList(executeSql);
    }

    public String selectOneCell(String sql) {
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

    public Integer delete(String sql) {
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
