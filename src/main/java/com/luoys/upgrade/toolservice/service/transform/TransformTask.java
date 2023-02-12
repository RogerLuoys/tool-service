package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.TaskPO;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.web.vo.TaskVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务的数据转换
 *
 * @author luoys
 */
public class TransformTask {

    public static TaskPO transformVO2PO(TaskVO vo) {
        if (vo == null) {
            return null;
        }
        TaskPO po = new TaskPO();
        po.setComment(vo.getComment());
        po.setDescription(vo.getDescription());
        po.setEndTime(vo.getEndTime());
        po.setStartTime(vo.getStartTime());
        po.setStatus(vo.getStatus());
        po.setId(vo.getTaskId());
        po.setName(vo.getName());
        po.setOwnerId(vo.getOwnerId());
        return po;
    }


    public static TaskVO TransformPO2VO(TaskPO po) {
        if (po == null) {
            return null;
        }
        TaskVO vo = new TaskVO();
        vo.setStatus(po.getStatus());
        vo.setTaskId(po.getId());
        vo.setComment(po.getComment());
        vo.setDescription(po.getDescription());
        vo.setEndTime(po.getEndTime());
        vo.setName(po.getName());
        vo.setStartTime(po.getStartTime());
        vo.setOwnerName(po.getOwnerId() == null ? null : CacheUtil.getUserById(po.getOwnerId()));
        vo.setOwnerId(po.getOwnerId());
        return vo;
    }

    public static List<TaskVO> TransformPO2VO(List<TaskPO> poList) {
        List<TaskVO> voList = new ArrayList<>();
        for (TaskPO po : poList) {
            voList.add(TransformPO2VO(po));
        }
        return voList;
    }
}
