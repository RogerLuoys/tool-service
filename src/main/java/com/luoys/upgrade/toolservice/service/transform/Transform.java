package com.luoys.upgrade.toolservice.service.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.ParamDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Transform {

    /**
     * 参数值转换成参数对象列表
     * @param paramDetail 数据库中的param值
     * @return 参数对象列表
     */
    public static List<ParamDTO> toParam(String paramDetail){
        if (StringUtil.isBlank(paramDetail)) {
            return null;
        }
        return JSON.parseArray(paramDetail, ParamDTO.class);

//        String[] paramArray = paramDetail.split(KeywordEnum.SEPARATOR.getCode());
//        List<ParamDTO> paramList = new ArrayList<>();
//        for (String param : paramArray) {
//            paramList.add(JSON.parseObject(param, ParamDTO.class));
//        }
//        return paramList;
    }

    /**
     * 参数对象列表转换成参数值
     * @param paramList 参数对象列表
     * @return 数据库中的参数值
     */
    public static String toParam(List<ParamDTO> paramList){
        if (paramList == null || paramList.size() == 0) {
            return null;
        }
        return JSON.toJSONString(paramList);

//        StringBuilder params = new StringBuilder();
//        for (ParamDTO paramDTO : paramList) {
//            params.append(JSON.toJSONString(paramDTO));
//            params.append(KeywordEnum.SEPARATOR.getCode());
//        }
//        params.delete(params.length()-KeywordEnum.SEPARATOR.getCode().length(), params.length());
//        return params.toString();
    }

}
