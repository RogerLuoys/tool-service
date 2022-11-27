package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ResourceSuiteRelationPO;
import org.apache.ibatis.annotations.Param;

public interface ResourceSuiteRelationMapper {
    int remove(@Param("resourceId") Integer resourceId,
               @Param("suiteId") Integer suiteId,
               @Param("type") Integer type);

    int insert(ResourceSuiteRelationPO record);


    ResourceSuiteRelationPO selectByResourceId(Integer id);
    ResourceSuiteRelationPO selectBySuiteId(Integer id);

    int update(ResourceSuiteRelationPO record);

}