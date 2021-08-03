package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoCaseMapper {

    int insert(AutoCasePO autoCasePO);

    int remove(Integer caseId);

    int update(AutoCasePO autoCasePO);

    AutoCasePO selectById(Integer caseId);

    List<AutoCasePO> list(@Param("status") Integer status,
                          @Param("name") String name,
                          @Param("ownerId") String ownerId,
                          @Param("startIndex") Integer startIndex);

}
