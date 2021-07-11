package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ToolPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolMapper {

    int insert(ToolPO toolPO);

    int deleteByUID(String toolId);

    int updateByUID(ToolPO toolPO);

    ToolPO selectByUID(@Param("toolId") String toolId);

    List<ToolPO> list(@Param("type") Integer type,
                      @Param("title") String title,
                      @Param("ownerId") String ownerId,
                      @Param("startIndex") Integer startIndex);

}
