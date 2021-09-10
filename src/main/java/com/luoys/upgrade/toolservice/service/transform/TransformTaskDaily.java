package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.TaskDailyPO;
import com.luoys.upgrade.toolservice.web.vo.TaskDailyVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 每日任务的数据转换
 *
 * @author luoys
 */
public class TransformTaskDaily {

    public static TaskDailyPO transformVO2PO(TaskDailyVO vo) {
        if (vo == null) {
            return null;
        }
        TaskDailyPO po = new TaskDailyPO();
        po.setComment(vo.getComment());
        po.setDescription(vo.getDescription());
        po.setEndTime(vo.getEndTime());
        po.setStartTime(vo.getStartTime());
        po.setStatus(vo.getStatus());
        po.setTaskDailyId(vo.getTaskDailyId());
        po.setName(vo.getName());
        po.setOwnerId(vo.getOwnerId());
        po.setOwnerName(vo.getOwnerName());
        return po;
    }


    public static TaskDailyVO TransformPO2VO(TaskDailyPO po) {
        if (po == null) {
            return null;
        }
        TaskDailyVO vo = new TaskDailyVO();
        vo.setStatus(po.getStatus());
        vo.setTaskDailyId(po.getTaskDailyId());
        vo.setComment(po.getComment());
        vo.setDescription(po.getDescription());
        vo.setEndTime(po.getEndTime());
        vo.setName(po.getName());
        vo.setStartTime(po.getStartTime());
        vo.setOwnerName(po.getOwnerName());
        vo.setOwnerId(po.getOwnerId());
        return vo;
    }

    public static List<TaskDailyVO> TransformPO2VO(List<TaskDailyPO> poList) {
        List<TaskDailyVO> voList = new ArrayList<>();
        for (TaskDailyPO po : poList) {
            voList.add(TransformPO2VO(po));
        }
        return voList;
    }
}
