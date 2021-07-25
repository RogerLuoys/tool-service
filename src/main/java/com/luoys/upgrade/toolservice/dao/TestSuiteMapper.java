package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.TestSuitePO;

public interface TestSuiteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestSuitePO record);

    int insertSelective(TestSuitePO record);

    TestSuitePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestSuitePO record);

    int updateByPrimaryKey(TestSuitePO record);
}