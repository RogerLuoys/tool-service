package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;
import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoSuiteMapper {

    int insert(AutoSuitePO autoSuitePO);

    int remove(Integer suiteId);

    int update(AutoSuitePO autoSuitePO);

    AutoSuitePO selectById(Integer suiteId);

    List<AutoSuitePO> list(@Param("name") String name,
                          @Param("ownerId") String ownerId,
                          @Param("startIndex") Integer startIndex);

}
