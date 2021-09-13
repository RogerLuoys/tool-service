package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.TaskPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskMapper {

    int insert(TaskPO record);

    int update(TaskPO record);

    int updateStatusByUUID(@Param("taskId") String taskId,
                                  @Param("status") Integer status);

    int updateCommentByUUID(@Param("taskId") String taskId,
                                   @Param("comment") String comment);

    int remove(@Param("taskId") String taskId);

    TaskPO selectByUUID(@Param("taskId") String taskId);

    List<TaskPO> list(@Param("ownerId") String ownerId,
                      @Param("startTime") Date startTime,
                      @Param("endTime") Date endTime);

}
