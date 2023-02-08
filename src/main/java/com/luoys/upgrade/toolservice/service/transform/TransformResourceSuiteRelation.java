package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.ResourceSuiteRelationPO;
import com.luoys.upgrade.toolservice.service.dto.SlaveDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 套件和资源关联关系数据转换
 *
 * @author luoys
 */
public class TransformResourceSuiteRelation {

    public static SlaveDTO transformPO2DTO(ResourceSuiteRelationPO po) {
        if (po == null) {
            return null;
        }
        SlaveDTO dto = new SlaveDTO();
        dto.setResourceId(po.getResourceId());
        dto.setName(po.getResourceName());
        dto.setThread(po.getSlaveThread());
        dto.setUrl(po.getSlaveUrl());
        return dto;
    }

    public static List<SlaveDTO> transformPO2DTO(List<ResourceSuiteRelationPO> poList) {
        List<SlaveDTO> dtoList = new ArrayList<>();
        for (ResourceSuiteRelationPO po : poList) {
            dtoList.add(transformPO2DTO(po));
        }
        return dtoList;
    }

}
