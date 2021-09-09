package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.TaskDailyPO;

public interface TaskDailyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyPO record);

    int insertSelective(TaskDailyPO record);

    TaskDailyPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyPO record);

    int updateByPrimaryKey(TaskDailyPO record);
}