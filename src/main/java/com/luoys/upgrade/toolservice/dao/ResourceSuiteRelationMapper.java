package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ResourceSuiteRelationPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceSuiteRelationMapper {
    int remove(@Param("resourceId") Integer resourceId,
               @Param("suiteId") Integer suiteId,
               @Param("type") Integer type);

    int insert(ResourceSuiteRelationPO record);


    ResourceSuiteRelationPO selectByResourceId(@Param("resourceId") Integer resourceId,
                                               @Param("type") Integer type);

    List<ResourceSuiteRelationPO> selectBySuiteId(@Param("suiteId") Integer suiteId,
                                                  @Param("type") Integer type);

    int update(ResourceSuiteRelationPO record);

}