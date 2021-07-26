package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.TestCasePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseMapper {
    int delete(Integer id);

    int insert(TestCasePO testCasePO);

    TestCasePO selectById(Integer id);

    List<TestCasePO> list(@Param("ownerId") String ownerId,
                          @Param("status") Integer status,
                          @Param("title") String title,
                          @Param("startIndex") Integer startIndex);

    int update(TestCasePO testCasePO);

}
