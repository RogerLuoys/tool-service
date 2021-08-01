package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;

public interface AutoCaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AutoCasePO record);

    int insertSelective(AutoCasePO record);

    AutoCasePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AutoCasePO record);

    int updateByPrimaryKey(AutoCasePO record);
}