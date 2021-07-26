package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.TestSuitePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestSuiteMapper {
    int delete(Integer id);

    int insert(TestSuitePO testSuitePO);

    TestSuitePO selectById(Integer id);

    List<TestSuitePO> list(@Param("title") String title,
                           @Param("startIndex") Integer startIndex);

    int update(TestSuitePO testSuitePO);

}
