package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.common.TimeUtil;
import com.luoys.upgrade.toolservice.dao.TaskDailyMapper;
import com.luoys.upgrade.toolservice.dao.po.TaskDailyPO;
import com.luoys.upgrade.toolservice.service.transform.TransformTaskDaily;
import com.luoys.upgrade.toolservice.web.vo.TaskDailyVO;
import com.luoys.upgrade.toolservice.web.vo.TaskWeeklyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TaskDailyService {

    @Autowired
    private TaskDailyMapper taskDailyMapper;

    public String newTaskDaily(TaskDailyVO taskDailyVO) {
        taskDailyVO.setTaskDailyId(NumberSender.createTaskDailyId());
        TaskDailyPO taskDailyPO = TransformTaskDaily.transformVO2PO(taskDailyVO);
        return taskDailyMapper.insert(taskDailyPO) == 1 ? taskDailyPO.getTaskDailyId() : null;
    }


    public Integer modifyTaskDailyStatus(String taskDailyId, Integer status) {
        if (taskDailyId == null || status == null) {
            log.error("----》入参不能为空，修改状态失败");
            return null;
        }
        return taskDailyMapper.updateStatusByTaskDailyId(taskDailyId, status);
    }

    public Integer modifyTaskDailyComment(String taskDailyId, String comment) {
        if (taskDailyId == null || comment == null) {
            log.error("----》入参不能为空，修改备注失败");
            return null;
        }
        return taskDailyMapper.updateCommentByTaskDailyId(taskDailyId, comment);
    }

    public List<TaskDailyVO> queryUserTaskDaily(String ownerId, Date startTime, Date endTime) {
        if (StringUtil.isBlank(ownerId)) {
            log.error("----》所有者不能为空");
            return null;
        }
        List<TaskDailyPO> poList = taskDailyMapper.listUserTaskDaily(
                ownerId, startTime, endTime);
        return TransformTaskDaily.TransformPO2VO(poList);
    }

    public TaskWeeklyVO queryByWeekly(String ownerId, Date currentDate) {
        List<TaskDailyVO> taskList = queryUserTaskDaily(ownerId, TimeUtil.getMonday(currentDate), TimeUtil.getSunday(currentDate));
        TaskWeeklyVO taskWeeklyVO = new TaskWeeklyVO();
        List<TaskDailyVO> monTaskList = new ArrayList<>();
        List<TaskDailyVO> tueTaskList = new ArrayList<>();
        List<TaskDailyVO> wedTaskList = new ArrayList<>();
        List<TaskDailyVO> thuTaskList = new ArrayList<>();
        List<TaskDailyVO> friTaskList = new ArrayList<>();
        List<TaskDailyVO> satTaskList = new ArrayList<>();
        List<TaskDailyVO> sunTaskList = new ArrayList<>();
        for (TaskDailyVO vo : taskList) {
            switch (TimeUtil.getWeek(vo.getStartTime())) {
                case 1:
                    monTaskList.add(vo);
                    break;
                case 2:
                    tueTaskList.add(vo);
                    break;
                case 3:
                    wedTaskList.add(vo);
                    break;
                case 4:
                    thuTaskList.add(vo);
                    break;
                case 5:
                    friTaskList.add(vo);
                    break;
                case 6:
                    satTaskList.add(vo);
                    break;
                case 7:
                    sunTaskList.add(vo);
                    break;
            }
        }
        taskWeeklyVO.setMonTaskList(monTaskList);
        taskWeeklyVO.setTueTaskList(tueTaskList);
        taskWeeklyVO.setWedTaskList(wedTaskList);
        taskWeeklyVO.setThuTaskList(thuTaskList);
        taskWeeklyVO.setFriTaskList(friTaskList);
        taskWeeklyVO.setSatTaskList(satTaskList);
        taskWeeklyVO.setSunTaskList(sunTaskList);
        return taskWeeklyVO;
    }
}
