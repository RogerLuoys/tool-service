package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.TaskPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskMapper {

    /**
     * 插入数据
     *
     * @param record 任务对象
     * @return 成功为1
     */
    int insert(TaskPO record);

    /**
     * 按业务id更新数据
     *
     * @param record 任务对象
     * @return 成功为1
     */
    int update(TaskPO record);

    /**
     * 按业务id更新任务状态
     *
     * @param taskId 业务id
     * @param status 要更新的状态
     * @return 成功为1
     */
    int updateStatusById(@Param("taskId") Integer taskId,
                           @Param("status") Integer status);

    /**
     * 按业务id更新任务备注
     *
     * @param taskId  业务id
     * @param comment 要更新的备份
     * @return 成功为1
     */
    int updateCommentById(@Param("taskId") Integer taskId,
                            @Param("comment") String comment);

    /**
     * 按业务id逻辑删除
     *
     * @param taskId 业务id
     * @return 成功为1
     */
    int remove(@Param("taskId") Integer taskId);

    /**
     * 查询任务信息
     *
     * @param taskId 业务id
     * @return 任务对象
     */
    TaskPO selectById(@Param("taskId") Integer taskId);

    /**
     * 按条件查询任务列表
     *
     * @param ownerId   任务所有者
     * @param startTime 任务开始实际
     * @param endTime   任务结束实际
     * @return 任务对象列表
     */
    List<TaskPO> list(@Param("ownerId") Integer ownerId,
                      @Param("startTime") Date startTime,
                      @Param("endTime") Date endTime);

}
