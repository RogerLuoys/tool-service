package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 数据库客户端，用于自动化步骤数据库类型的实现
 *
 * @author luoys
 */
@Slf4j
public class DBClient {

    private JdbcTemplate jdbcTemplate;
    private HikariDataSource dataSource;

//    /**
//     * 执行sql
//     * 支持增删改查，删改查一定要带条件
//     *
//     * @param autoStepPO 要执行的sql
//     * @return -
//     */
//    public String execute(AutoStepPO autoStepPO) {
//        // 初始化数据库链接
//        init(CacheUtil.getJdbcById(autoStepPO.getMethodId()));
//        String result, sql;
//        sql = autoStepPO.getParameter1();
//        // 根据sql语句类型，选择不同的方法执行
//        if (sql.toUpperCase().matches("^INSERT INTO .+")) {
//            result = this.insert(sql);
//        } else if (sql.toUpperCase().matches("^DELETE FROM [A-Z0-9_]+ WHERE .+")) {
//            result = this.delete(sql);
//        } else if (sql.toUpperCase().matches("^UPDATE [A-Z0-9_]+ SET .+ WHERE .+")) {
//            result = this.update(sql);
//        } else if (sql.toUpperCase().matches("^SELECT .+ FROM [A-Z0-9_]+")) {//todo 还要改
//            // 查询单列
//            if (sql.toUpperCase().matches("^SELECT [^,*]+ FROM [A-Z0-9_]+ WHERE .+")) {
//                result = this.selectOneCell(sql);
//            } else {
//                result = this.select(sql);
//            }
//        } else {
//            result = "未识别sql类型";
//        }
//        // 关闭数据库链接
//        this.close();
//        return result;
//    }

    /**
     * 执行sql
     * 支持增删改查，删改查一定要带条件
     *
     * @param autoStepPO 要执行的sql
     * @return -
     */
    public String execute(StepDTO stepDTO) {
        // 初始化数据库链接
        init(stepDTO.getDataSource());
        String result, sql;
        sql = stepDTO.getParameter1();
        // 根据sql语句类型，选择不同的方法执行
        if (sql.toUpperCase().matches("^INSERT INTO .+")) {
            result = this.insert(sql);
        } else if (sql.toUpperCase().matches("^DELETE FROM [A-Z0-9_]+ WHERE .+")) {
            result = this.delete(sql);
        } else if (sql.toUpperCase().matches("^UPDATE [A-Z0-9_]+ SET .+ WHERE .+")) {
            result = this.update(sql);
        } else if (sql.toUpperCase().matches("^SELECT .+ FROM [A-Z0-9_]+")) {//todo 还要改
            // 查询单列
            if (sql.toUpperCase().matches("^SELECT [^,*]+ FROM [A-Z0-9_]+ WHERE .+")) {
                result = this.selectOneCell(sql);
            } else {
                result = this.select(sql);
            }
        } else {
            result = "未识别sql类型";
        }
        // 关闭数据库链接
        this.close();
        return result;
    }

//    /**
//     * 执行sql，有同步锁
//     *
//     * @param jdbcDTO -
//     * @return 全部执行成功则为true
//     */
//    public String execute(JdbcDTO jdbcDTO) {
//        if (jdbcDTO == null || jdbcDTO.getSql() == null) {
//            log.error("--->执行sql时参数异常");
//            return "执行参数异常";
//        }
//        // 初始化数据库链接
//        init(jdbcDTO);
//        String result;
//        // 根据sql类型选择不同的执行方法
//        switch (SqlTypeEnum.fromValue(jdbcDTO.getSqlType())) {
//            case INSERT:
//                result = insert(jdbcDTO.getSql());
//                break;
//            case DELETE:
//                result = delete(jdbcDTO.getSql());
//                break;
//            case UPDATE:
//                result = update(jdbcDTO.getSql());
//                break;
//            case SELECT:
//                result = select(jdbcDTO.getSql());
//                break;
//            default:
//                result = "不支持sql类型 ";
//        }
//        // 关闭数据库链接
//        close();
//        return result;
//    }

    /**
     * 初始化数据源，使用HikariDataSource
     *
     * @param dataSourceDTO 数据源
     */
    private void init(DataSourceDTO dataSourceDTO) {
        jdbcTemplate = new JdbcTemplate();
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dataSourceDTO.getDriver());
        config.setJdbcUrl(dataSourceDTO.getUrl());
        config.setUsername(dataSourceDTO.getUsername());
        config.setPassword(dataSourceDTO.getPassword());
        dataSource = new HikariDataSource(config);
        jdbcTemplate.setDataSource(dataSource);
    }

    /**
     * 关闭HikariDataSource连接
     */
    private void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }


//    /**
//     * 初始化数据源
//     * 如果已初始化，则跳过
//     *
//     */
//    public void init(String driver, String url, String userName, String password) {
//        if (this.jdbcTemplate != null) {
//            return;
//        }
//        this.jdbcTemplate = new JdbcTemplate();
//        this.dataSource = new DriverManagerDataSource();
//        this.dataSource.setDriverClassName(driver);
//        this.dataSource.setUrl(url);
//        this.dataSource.setUsername(userName);
//        this.dataSource.setPassword(password);
//        this.jdbcTemplate.setDataSource(this.dataSource);
//    }


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
     * 执行更新sql语句
     *
     * @param sql 要执行的sql
     * @return 执行结果
     */
    private String update(String sql) {
        int effectRow = this.count(transformUpdate2Select(sql));
        if (effectRow > 200) {
            return "一次更新超过200行，请确认sql条件是否正确";
        } else if (effectRow == 0) {
            return "查无此类数据，不需要更新";
        }
        return String.valueOf(this.jdbcTemplate.update(sql));
    }

    /**
     * 执行插入sql语句
     *
     * @param sql 要执行的sql
     * @return 执行结果
     */
    private String insert(String sql) {
        return String.valueOf(this.jdbcTemplate.update(sql));
    }

    /**
     * 执行查询sql语句
     *
     * @param sql 要执行的sql
     * @return 执行结果
     */
    private String select(String sql) {
        return this.jdbcTemplate.queryForList(sql).toString();
    }

    /**
     * 执行删除sql语句
     *
     * @param sql 要执行的sql
     * @return 执行结果
     */
    private String delete(String sql) {
        int effectRow = this.count(transformDelete2Select(sql));
        if (effectRow > 50) {
            return "一次删除超过50行，请确认sql条件是否正确";
        } else if (effectRow == 0) {
            return "查无此类数据，不需要删除";
        }
        return String.valueOf(this.jdbcTemplate.update(sql));
    }

    /**
     * 查询总条数
     *
     * @param sql 查询语句
     * @return 总条数
     */
    private Integer count(String sql) {
        Map<String, Object> result = this.jdbcTemplate.queryForMap(sql);
        String COUNT = "count(1)";
        return Integer.valueOf(result.get(COUNT).toString());
    }

    /**
     * 查询单列
     * 时间会转为yyyy-MM-dd HH:mm:ss格式
     *
     * @param sql 查询sql
     * @return 只返回第一行的值，不带列名
     */
    private String selectOneCell(String sql) {
        String[] sqlList = sql.split("[ ]+");
        Map<String, Object> result = this.jdbcTemplate.queryForList(sql).get(0);
        // 根据列名取值
        Object value = result.get(sqlList[1]);
        //时间格式要转换
        if (value.getClass().getName().equals("java.time.LocalDateTime")) {
            LocalDateTime time = (LocalDateTime) value;
            DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));
            return dateTimeFormatter.format(time);
        } else {
            return value.toString();
        }
    }

//    /**
//     * 把更新sql转换为查询总行数的sql，查询sql以更新sql的条件为条件
//     *
//     * @param updateSql 更新sql，注意字段间空格只能有一个
//     * @return select count(1) from 更新的表名 + 更新的条件
//     */
//    private String transformUpdate2Select(String updateSql) {
//        int endIndex = updateSql.toLowerCase().indexOf(" set ");
//        String tableName = updateSql.substring(7, endIndex);
//        int startIndex = updateSql.toLowerCase().indexOf(" where ");
//        String condition = updateSql.substring(startIndex);
//        return "select count(1) from " + tableName + condition;
//    }
//
//    /**
//     * 把删除sql转换为查询总行数的sql，查询sql以更新sql的条件为条件
//     *
//     * @param deleteSql 删除sql，注意字段间空格只能有一个
//     * @return select count(1) from 删除的表名 + 删除的条件
//     */
//    private String transformDelete2Select(String deleteSql) {
//        int startIndex = deleteSql.toLowerCase().indexOf(" from ");
//        String condition = deleteSql.substring(startIndex);
//        return "select count(1) " + condition;
//    }
//
//    /**
//     * 执行更新sql语句
//     *
//     * @param sql 要执行的sql
//     * @return 执行结果
//     */
//    private String update(String sql) {
//        int effectRow = count(transformUpdate2Select(sql));
//        if (effectRow > 200) {
//            return "一次更新超过200行，请确认sql条件是否正确";
//        } else if (effectRow == 0) {
//            return "查无此类数据，不需要更新";
//        }
//        return String.valueOf(jdbcTemplate.update(sql));
//    }
//
//    /**
//     * 执行插入sql语句
//     *
//     * @param sql 要执行的sql
//     * @return 执行结果
//     */
//    private String insert(String sql) {
//        return String.valueOf(jdbcTemplate.update(sql));
//    }
//
//    /**
//     * 执行查询sql语句
//     *
//     * @param sql 要执行的sql
//     * @return 执行结果
//     */
//    private String select(String sql) {
//        return jdbcTemplate.queryForList(sql).toString();
//    }
//
//    /**
//     * 执行删除sql语句
//     *
//     * @param sql 要执行的sql
//     * @return 执行结果
//     */
//    private String delete(String sql) {
//        int effectRow = count(transformDelete2Select(sql));
//        if (effectRow > 50) {
//            return "一次删除超过50行，请确认sql条件是否正确";
//        } else if (effectRow == 0) {
//            return "查无此类数据，不需要删除";
//        }
//        return String.valueOf(jdbcTemplate.update(sql));
//    }
//
//    /**
//     * 查询总条数
//     *
//     * @param sql 查询语句
//     * @return 总条数
//     */
//    private Integer count(String sql) {
//        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
//        String COUNT = "count(1)";
//        return Integer.valueOf(result.get(COUNT).toString());
//    }

}
