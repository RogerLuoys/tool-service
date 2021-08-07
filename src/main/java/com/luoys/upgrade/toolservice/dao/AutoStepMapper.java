package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoStepMapper {

    int insert(AutoStepPO toolPO);

    int remove(Integer toolId);

    int update(AutoStepPO toolPO);

    int updateResult(@Param("id") Integer id,
                     @Param("actualResult") String actualResult);

    AutoStepPO selectById(Integer toolId);

    List<AutoStepPO> list(@Param("type") Integer type,
                      @Param("name") String name,
                      @Param("ownerId") String ownerId,
                      @Param("isPublic") Boolean isPublic,
                      @Param("startIndex") Integer startIndex);

}
