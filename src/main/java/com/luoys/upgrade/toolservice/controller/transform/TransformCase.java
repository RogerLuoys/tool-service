package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.controller.dto.ParamDTO;
import com.luoys.upgrade.toolservice.controller.vo.CaseVO;
import com.luoys.upgrade.toolservice.dao.po.CasePO;

import java.util.ArrayList;
import java.util.List;

public class TransformCase {
    private static final String SEPARATOR=" &&& ";

    public static CaseVO transformPO2VO(CasePO po) {
        if (po == null) {
            return null;
        }
        CaseVO vo = new CaseVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setCaseId(po.getId());
        vo.setOwnerName(po.getOwnerName());
        vo.setTitle(po.getTitle());
        vo.setPreStepList(toParam(po.getPreDetail()));
        vo.setMainStepList(toParam(po.getMainDetail()));
        vo.setAfterStepList(toParam(po.getAfterDetail()));
        return vo;
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

}
