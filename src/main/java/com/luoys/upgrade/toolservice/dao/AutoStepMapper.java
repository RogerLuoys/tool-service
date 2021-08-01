package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;

public interface AutoStepMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AutoStepPO record);

    int insertSelective(AutoStepPO record);

    AutoStepPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AutoStepPO record);

    int updateByPrimaryKey(AutoStepPO record);
}