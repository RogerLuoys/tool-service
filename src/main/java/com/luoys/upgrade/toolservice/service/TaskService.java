package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.common.TimeUtil;
import com.luoys.upgrade.toolservice.dao.TaskMapper;
import com.luoys.upgrade.toolservice.dao.po.TaskPO;
import com.luoys.upgrade.toolservice.service.transform.TransformTask;
import com.luoys.upgrade.toolservice.web.vo.TaskVO;
import com.luoys.upgrade.toolservice.web.vo.TaskWeeklyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    /**
     * 创建任务
     *
     * @param taskVO 任务对象
     * @return 成功返回taskId，失败为null
     */
    public String create(TaskVO taskVO) {
        taskVO.setTaskId(NumberSender.createTaskDailyId());
        return taskMapper.insert(TransformTask.transformVO2PO(taskVO)) == 1 ? taskVO.getTaskId() : null;
    }

    /**
     * 逻辑删除任务
     *
     * @param taskId 业务id
     * @return 成功为true
     */
    public Boolean remove(String taskId) {
        return taskMapper.remove(taskId) == 1;
    }

    /**
     * 更新任务状态
     *
     * @param taskId 业务id
     * @param status 要更新的状态
     * @return 成功为true
     */
    public Boolean updateStatus(String taskId, Integer status) {
        if (taskId == null || status == null) {
            log.error("--->入参不能为空，修改状态失败");
            return null;
        }
        return taskMapper.updateStatusByUUID(taskId, status) == 1;
    }

    /**
     * 更新备注
     *
     * @param taskId  任务id
     * @param comment 备注
     * @return 成功为true
     */
    public Boolean updateComment(String taskId, String comment) {
        if (taskId == null || comment == null) {
            log.error("--->入参不能为空，修改备注失败");
            return null;
        }
        return taskMapper.updateCommentByUUID(taskId, comment) == 1;
    }

    /**
     * 按时间段查询任务列表
     *
     * @param ownerId   任务所属人id
     * @param startTime 时间段开始时间
     * @param endTime   时间段结束时间
     * @return 任务列表
     */
    public List<TaskVO> query(String ownerId, Date startTime, Date endTime) {
        if (StringUtil.isBlank(ownerId)) {
            log.error("--->所有者不能为空");
            return null;
        }
        List<TaskPO> poList = taskMapper.list(
                ownerId, startTime, endTime);
        return TransformTask.TransformPO2VO(poList);
    }

    /**
     * 按周查询任务列表
     *
     * @param ownerId     所属人id
     * @param currentDate 当前时间，根据此时间查询周一和周日
     * @return 当前时间所在周的所有任务
     */
    public List<TaskWeeklyVO> queryByWeekly(String ownerId, Date currentDate) {
        List<TaskWeeklyVO> weekList = new ArrayList<>();
        Date monStart = TimeUtil.getDayStart(TimeUtil.getWeekDate(currentDate, 1));
        Date sunEnd = TimeUtil.getDayEnd(TimeUtil.getWeekDate(currentDate, 7));
        // 查询一周内的所有任务
        List<TaskVO> taskList = query(ownerId, monStart, sunEnd);
        TaskWeeklyVO monday = new TaskWeeklyVO();
        TaskWeeklyVO tuesday = new TaskWeeklyVO();
        TaskWeeklyVO wednesday = new TaskWeeklyVO();
        TaskWeeklyVO thursday = new TaskWeeklyVO();
        TaskWeeklyVO friday = new TaskWeeklyVO();
        TaskWeeklyVO saturday = new TaskWeeklyVO();
        TaskWeeklyVO sunday = new TaskWeeklyVO();
        List<TaskVO> monTaskList = new ArrayList<>();
        List<TaskVO> tueTaskList = new ArrayList<>();
        List<TaskVO> wedTaskList = new ArrayList<>();
        List<TaskVO> thuTaskList = new ArrayList<>();
        List<TaskVO> friTaskList = new ArrayList<>();
        List<TaskVO> satTaskList = new ArrayList<>();
        List<TaskVO> sunTaskList = new ArrayList<>();
        for (TaskVO vo : taskList) {
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
        monday.setTaskList(monTaskList);
        monday.setWeekName("周一");
        monday.setWeekDate(TimeUtil.getWeekDate(currentDate, 1));
        weekList.add(monday);
        tuesday.setTaskList(tueTaskList);
        tuesday.setWeekName("周二");
        tuesday.setWeekDate(TimeUtil.getWeekDate(currentDate, 2));
        weekList.add(tuesday);
        wednesday.setTaskList(wedTaskList);
        wednesday.setWeekName("周三");
        wednesday.setWeekDate(TimeUtil.getWeekDate(currentDate, 3));
        weekList.add(wednesday);
        thursday.setTaskList(thuTaskList);
        thursday.setWeekName("周四");
        thursday.setWeekDate(TimeUtil.getWeekDate(currentDate, 4));
        weekList.add(thursday);
        friday.setTaskList(friTaskList);
        friday.setWeekName("周五");
        friday.setWeekDate(TimeUtil.getWeekDate(currentDate, 5));
        weekList.add(friday);
        saturday.setTaskList(satTaskList);
        saturday.setWeekName("周六");
        saturday.setWeekDate(TimeUtil.getWeekDate(currentDate, 6));
        weekList.add(saturday);
        sunday.setTaskList(sunTaskList);
        sunday.setWeekName("周日");
        sunday.setWeekDate(TimeUtil.getWeekDate(currentDate, 7));
        weekList.add(sunday);
        return weekList;
    }
}
