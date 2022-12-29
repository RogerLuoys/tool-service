package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.ProjectPO;
import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.SlaveDTO;
import com.luoys.upgrade.toolservice.service.enums.ResourceTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.ProjectVO;
import com.luoys.upgrade.toolservice.web.vo.ResourceVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目数据转换
 *
 * @author luoys
 */
public class TransformProject {

    public static ProjectVO transformPO2VO(ProjectPO po) {
        if (po == null) {
            return null;
        }
        ProjectVO vo = new ProjectVO();
        vo.setDescription(po.getDescription());
        vo.setProjectId(po.getId());
        vo.setName(po.getName());
        vo.setParentProjectId(po.getParentProjectId());
        return vo;
    }

    public static List<ProjectVO> transformPO2VO(List<ProjectPO> poList) {
        List<ProjectVO> voList = new ArrayList<>();
        for (ProjectPO po : poList) {
            voList.add(transformPO2VO(po));
        }
        return voList;
    }

    public static ProjectPO transformVO2PO(ProjectVO vo) {
        if (vo == null) {
            return null;
        }
        ProjectPO po = new ProjectPO();
        po.setDescription(vo.getDescription());
        po.setId(vo.getProjectId());
        po.setName(vo.getName());
        po.setParentProjectId(vo.getParentProjectId());
        return po;
    }

}
