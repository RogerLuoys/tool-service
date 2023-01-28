package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.ConfigPO;
import com.luoys.upgrade.toolservice.web.vo.ConfigVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformConfig {

    public static ConfigVO transformPO2VO(ConfigPO po) {
        if (po == null) {
            return null;
        }
        ConfigVO vo = new ConfigVO();
        vo.setConfigId(po.getId());
        vo.setValue(po.getParamValue());
        vo.setComment(po.getParamComment());
        vo.setName(po.getParamName());
        vo.setType(po.getType());
        vo.setParamType(po.getParamType());
        vo.setCaseId(po.getCaseId());
        return vo;
    }

    public static List<ConfigVO> transformPO2VO(List<ConfigPO> poList) {
        List<ConfigVO> voList = new ArrayList<>();
        for (ConfigPO po : poList) {
            voList.add(transformPO2VO(po));
        }
        return voList;
    }

    public static ConfigPO transformVO2PO(ConfigVO vo) {
        if (vo == null) {
            return null;
        }
        ConfigPO po = new ConfigPO();
        po.setCaseId(vo.getCaseId());
        po.setParamComment(vo.getComment());
        po.setId(vo.getConfigId());
        po.setParamName(vo.getName());
        po.setParamValue(vo.getValue());
        po.setType(vo.getType());
        po.setParamType(vo.getParamType());
        return po;
    }

//    public static ParameterDTO transformVO2DTO(ConfigVO vo) {
//        if (vo == null) {
//            return null;
//        }
//        ParameterDTO dto = new ParameterDTO();
//        dto.setType(vo.getType());
//        dto.setComment(vo.getComment());
//        dto.setName(vo.getName());
//        dto.setValue(vo.getValue());
//        return dto;
//    }
//
//    public static List<ParameterDTO> transformVO2DTO(List<ConfigVO> voList) {
//        if (voList == null) {
//            return null;
//        }
//        List<ParameterDTO> dtoList = new ArrayList<>();
//        for (ConfigVO vo : voList) {
//            dtoList.add(transformVO2DTO(vo));
//        }
//        return dtoList;
//    }

    public static Map<String, String> transformVO2Map(List<ConfigVO> voList) {
        if (voList == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        for (ConfigVO vo : voList) {
            map.put(vo.getName(), vo.getValue());
        }
        return map;
    }

    public static String[] transformVO2Array(List<ConfigVO> voList) {
        if (voList == null) {
            return null;
        }
        String[] str = new String[voList.size()];
        for (int i = 0; i < voList.size(); i++) {
            str[i] = voList.get(i).getValue();
        }
        return str;
    }

}
