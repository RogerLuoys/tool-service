package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;

public interface AutoSuiteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AutoSuitePO record);

    int insertSelective(AutoSuitePO record);

    AutoSuitePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AutoSuitePO record);

    int updateByPrimaryKey(AutoSuitePO record);
}