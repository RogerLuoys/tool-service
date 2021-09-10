package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.TaskDailyPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskDailyMapper {

    int insert(TaskDailyPO record);

    int updateStatusByTaskDailyId(@Param("taskDailyId") String taskDailyId,
                                  @Param("status") Integer status);

    int updateCommentByTaskDailyId(@Param("taskDailyId") String taskDailyId,
                                   @Param("comment") String comment);

    int deleteByTaskDailyId(@Param("taskDailyId") String taskDailyId);

    TaskDailyPO selectByTaskDailyId(@Param("taskDailyId") String taskDailyId);

    List<TaskDailyPO> listUserTaskDaily(@Param("ownerId") String ownerId,
                                        @Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime);

}
