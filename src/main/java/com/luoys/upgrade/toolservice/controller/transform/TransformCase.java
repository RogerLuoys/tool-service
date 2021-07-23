package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.controller.dto.ParamDTO;
import com.luoys.upgrade.toolservice.controller.vo.CaseSimpleVO;
import com.luoys.upgrade.toolservice.controller.vo.CaseVO;
import com.luoys.upgrade.toolservice.controller.vo.DeviceSimpleVO;
import com.luoys.upgrade.toolservice.dao.po.CasePO;
import com.luoys.upgrade.toolservice.dao.po.DevicePO;

import java.util.ArrayList;
import java.util.List;

public class TransformCase {

    public static CaseSimpleVO transformPO2SimpleVO(CasePO po) {
        if (po == null) {
            return null;
        }
        CaseSimpleVO vo = new CaseSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setTitle(po.getTitle());
        vo.setCaseId(po.getId());
        return vo;
    }

    public static List<CaseSimpleVO> transformPO2SimpleVO(List<CasePO> poList) {
        List<CaseSimpleVO> voList = new ArrayList<>();
        for (CasePO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

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
        vo.setPreStepList(Transform.toParam(po.getPreDetail()));
        vo.setMainStepList(Transform.toParam(po.getMainDetail()));
        vo.setAfterStepList(Transform.toParam(po.getAfterDetail()));
        return vo;
    }

    public static CasePO transformVO2PO(CaseVO vo) {
        if (vo == null) {
            return null;
        }
        CasePO po = new CasePO();
        po.setDescription(vo.getDescription());
        po.setOwnerId(vo.getOwnerId());
        po.setId(vo.getCaseId());
        po.setOwnerName(vo.getOwnerName());
        po.setTitle(vo.getTitle());
        po.setPreDetail(Transform.toParam(vo.getPreStepList()));
        po.setMainDetail(Transform.toParam(vo.getMainStepList()));
        po.setAfterDetail(Transform.toParam(vo.getAfterStepList()));
        return po;
    }

//    /**
//     * 参数值转换成参数对象列表
//     * @param params 数据库中的param值
//     * @return 参数对象列表
//     */
//    private static List<ParamDTO> toParam(String params){
//        if (StringUtil.isBlank(params)) {
//            return null;
//        }
//        String[] paramArray = params.split(SEPARATOR);
//        List<ParamDTO> paramList = new ArrayList<>();
//        for (String param : paramArray) {
//            paramList.add(JSON.parseObject(param, ParamDTO.class));
//        }
//        return paramList;
//    }
//
//    /**
//     * 参数对象列表转换成参数值
//     * @param paramList 参数对象列表
//     * @return 数据库中的参数值
//     */
//    private static String toParam(List<ParamDTO> paramList){
//        if (paramList == null || paramList.size() == 0) {
//            return null;
//        }
//        StringBuilder params = new StringBuilder();
//        for (ParamDTO paramDTO : paramList) {
//            params.append(JSON.toJSONString(paramDTO));
//            params.append(SEPARATOR);
//        }
//        params.delete(params.length()-SEPARATOR.length(), params.length());
//        return params.toString();
//    }

}
