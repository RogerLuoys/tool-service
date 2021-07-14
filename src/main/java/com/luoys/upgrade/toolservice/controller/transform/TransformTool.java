package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.controller.dto.*;
import com.luoys.upgrade.toolservice.controller.enums.SqlTypeEnum;
import com.luoys.upgrade.toolservice.controller.vo.ToolSimpleVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolVO;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TransformTool {

    private static final String SEPARATOR=" &&& ";
    private static final String PARAM_SYMBOL="$$${";
    private static final String INSERT="insert";
    private static final String DELETE="delete";
    private static final String UPDATE="update";
    private static final String SELECT="select";

    public static ToolSimpleVO transformPO2SimpleVO(ToolPO po) {
        if (po == null)
            return null;
        ToolSimpleVO vo = new ToolSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setToolId(po.getToolId());
        vo.setOwner(po.getOwnerId());
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        vo.setPermission(po.getPermission());
        return vo;
    }

    public static List<ToolSimpleVO> transformPO2VO(List<ToolPO> poList) {
        List<ToolSimpleVO> voList = new ArrayList<>();
        for (ToolPO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    /**
     * 将参数合并入sql列表中
     * @param toolVO -
     */
    public static void mergeSql(ToolVO toolVO) {
        // 无变量
        if (toolVO.getParamList().size() == 0) {
            return;
        }
        log.info("---->合并前sql列表：{}", toolVO.getJdbc().getSqlList());
        List<SqlDTO> actualSqlList = new ArrayList<>();
        String oneSql, fullParamSymbol;
        for (SqlDTO sqlDTO : toolVO.getJdbc().getSqlList()) {
            oneSql = sqlDTO.getSql();
            //先判断指定sql模板中是否有参数占位符，有则进入替换逻辑
            if (oneSql.contains(PARAM_SYMBOL)) {
                //将所有实际参数与其中一条sql模板的占位符替换
                for (ParamDTO paramDTO : toolVO.getParamList()) {
                    fullParamSymbol = PARAM_SYMBOL+paramDTO.getName()+"}";
                    if (oneSql.contains(fullParamSymbol)) {
                        sqlDTO.setSql(oneSql.replace(fullParamSymbol, paramDTO.getValue()));
                    }
                }
            }
            actualSqlList.add(sqlDTO);
        }
        toolVO.getJdbc().setSqlList(actualSqlList);
        log.info("---->合并后sql列表：{}", toolVO.getJdbc().getSqlList());
    }

    public static void mergeHttp(ToolVO toolVO) {
        // 无变量
        if (toolVO.getParamList().size() == 0) {
            return;
        }
        log.info("---->合并前http请求：{}", toolVO.getHttpRequest());
        String fullParamSymbol;
        String url = toolVO.getHttpRequest().getHttpURL();
        String body = toolVO.getHttpRequest().getHttpBody();
        //将参数一个个替换入url和body中
        for (ParamDTO paramDTO : toolVO.getParamList()) {
            fullParamSymbol = PARAM_SYMBOL+paramDTO.getName()+"}";
            if (url.contains(fullParamSymbol)) {
                url = url.replace(fullParamSymbol, paramDTO.getValue());
            }
            if (body.contains(fullParamSymbol)) {
                body = body.replace(fullParamSymbol, paramDTO.getValue());
            }
        }
        toolVO.getHttpRequest().setHttpURL(url);
        toolVO.getHttpRequest().setHttpBody(body);
        log.info("---->合并后http请求：{}", toolVO.getHttpRequest());
    }

    public static ToolVO transformPO2VO(ToolPO po) {
        if (po == null) {
            return null;
        }
        ToolVO vo = new ToolVO();
        vo.setDescription(po.getDescription());
        vo.setToolId(po.getToolId());
        vo.setStatus(1);
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        vo.setPermission(po.getPermission());
        // {"name":"name1", "value":""};{"name":"name2", "value":"value1"}
        vo.setParamList(toParam(po.getParams()));
        // 模板转换
        switch (po.getType()) {
            case 1:
                vo.setJdbc(toSql(po.getTemplate()));
                break;
            case 2:
                vo.setHttpRequest(toHttpRequest(po.getTemplate()));
                break;
            case 3:
                //todo
                vo.setRpc(null);
                break;
        }
        return vo;
    }

    public static ToolPO transformVO2PO(ToolVO vo) {
        if (vo == null) {
            return null;
        }
        ToolPO po = new ToolPO();
        po.setDescription(vo.getDescription());
        po.setType(vo.getType());
        po.setToolId(vo.getToolId());
        po.setPermission(vo.getPermission());
        po.setTitle(vo.getTitle());
        po.setOwnerId(vo.getOwnerId());
        po.setParams(toParam(vo.getParamList()));
        // 模板转换
        switch (vo.getType()) {
            case 1:
                po.setTemplate(toSql(vo.getJdbc()));
                break;
            case 2:
                po.setTemplate(toHttpRequest(vo.getHttpRequest()));
                break;
            case 3:
                //todo
                po.setTemplate(null);
                break;
        }
        return po;
    }

    /**
     * 参数值转换成参数对象列表
     * @param params 数据库中的param值
     * @return 参数对象列表
     */
    private static List<ParamDTO> toParam(String params){
        if (StringUtil.isBlank(params)) {
            return null;
        }
        String[] paramArray = params.split(SEPARATOR);
        List<ParamDTO> paramList = new ArrayList<>();
        for (String param : paramArray) {
            paramList.add(JSON.parseObject(param, ParamDTO.class));
        }
        return paramList;
    }

    /**
     * 参数对象列表转换成参数值
     * @param paramList 参数对象列表
     * @return 数据库中的参数值
     */
    private static String toParam(List<ParamDTO> paramList){
        if (paramList == null || paramList.size() == 0) {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (ParamDTO paramDTO : paramList) {
            params.append(JSON.toJSONString(paramDTO));
            params.append(SEPARATOR);
        }
        params.delete(params.length()-SEPARATOR.length(), params.length());
        return params.toString();
    }

    // {"name":"header","value":"http://"}
    private static HttpRequestDTO toHttpRequest(String template) {
        if (template == null) {
            return null;
        }
        return JSON.parseObject(template, HttpRequestDTO.class);
    }

    private static String toHttpRequest(HttpRequestDTO httpRequest) {
        if (httpRequest == null) {
            return null;
        }
        return JSON.toJSONString(httpRequest);
    }

    /**
     * 模板转jdbc对象
     * @param template 数据库的模块值
     * @return jdbc对象
     */
    // {DataBaseDTO} &&& {type="",sql1=""} &&& ...
    private static JdbcDTO toSql(String template) {
        log.info("---->开始将模板转换成jdbc对象：{}", template);
        if (StringUtil.isBlank(template)) {
            return null;
        }
        List<String> sqlList = Arrays.asList(template.split(SEPARATOR));
        //第一段需要是数据库
        if (!sqlList.get(0).startsWith("{")) {
            return null;
        }
        JdbcDTO jdbcDTO = new JdbcDTO();
        jdbcDTO.setDataSource(JSON.parseObject(sqlList.get(0), DataSourceDTO.class));
        List<SqlDTO> sqlDTOList = new ArrayList<>();
        //第一个数据是datasource，从第二个开始取sql
//        String currentSql;
        for (int i = 1; i < sqlList.size(); i++) {
            sqlDTOList.add(JSON.parseObject(sqlList.get(i), SqlDTO.class));
//            currentSql = sqlList.get(i);
//            SqlDTO sqlDTO = (SqlDTO) JSON.parse(currentSql);
//            //新增的sql模板，默认类型为-1
//            if (!sqlDTO.getType().equals(SqlTypeEnum.UNKNOWN.getCode())) {
//                continue;
//            }
//            //当模板内无sql类型，取(0-空格)判断并设置sql类型
//            switch (currentSql.substring(0, currentSql.indexOf(" ")).toLowerCase()) {
//                case INSERT:
//                    sqlDTO.setType(SqlTypeEnum.INSERT.getCode());
//                    sqlDTOList.add(sqlDTO);
//                    break;
//                case DELETE:
//                    sqlDTO.setType(SqlTypeEnum.DELETE.getCode());
//                    sqlDTOList.add(sqlDTO);
//                    break;
//                case UPDATE:
//                    sqlDTO.setType(SqlTypeEnum.UPDATE.getCode());
//                    sqlDTOList.add(sqlDTO);
//                    break;
//                case SELECT:
//                    sqlDTO.setType(SqlTypeEnum.SELECT.getCode());
//                    sqlDTOList.add(sqlDTO);
//                    break;
//                default:
//                    sqlDTOList.add(new SqlDTO(SqlTypeEnum.UNKNOWN.getCode(), currentSql));
//            }
        }
        jdbcDTO.setSqlList(sqlDTOList);
        return jdbcDTO;
    }

    /**
     * jdbc对象转模板
     * @param jdbcDTO jdbc对象
     * @return 模板值
     */
    private static String toSql(JdbcDTO jdbcDTO) {
        if (jdbcDTO == null || jdbcDTO.getDataSource() == null || jdbcDTO.getSqlList() == null) {
            return null;
        }
        StringBuilder template = new StringBuilder();
        //先把datasource对象序列化
        template.append(JSON.toJSONString(jdbcDTO.getDataSource()));
        //再把sql对象列表序列化
        String currentSql;
        for (SqlDTO sqlDTO : jdbcDTO.getSqlList()) {
            template.append(SEPARATOR);
            if (sqlDTO.getType() == null || sqlDTO.getType() < 1) {
                //当模板内无sql类型，取(0-空格)判断并设置sql类型
                currentSql = sqlDTO.getSql();
                switch (currentSql.substring(0, currentSql.indexOf(" ")).toLowerCase()) {
                    case INSERT:
                        sqlDTO.setType(SqlTypeEnum.INSERT.getCode());
                        break;
                    case DELETE:
                        sqlDTO.setType(SqlTypeEnum.DELETE.getCode());
                        break;
                    case UPDATE:
                        sqlDTO.setType(SqlTypeEnum.UPDATE.getCode());
                        break;
                    case SELECT:
                        sqlDTO.setType(SqlTypeEnum.SELECT.getCode());
                        break;
                    default:
                        sqlDTO.setType(SqlTypeEnum.UNKNOWN.getCode());
                }
            }
            template.append(JSON.toJSONString(sqlDTO));
        }
        return template.toString();
    }
}
