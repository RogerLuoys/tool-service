package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceMapper {

    int insert(ResourcePO resourcePO);

    int remove(Integer id);

    int update(ResourcePO resourcePO);

    List<ResourcePO> list(@Param("type") Integer type,
                        @Param("name") String name,
                        @Param("ownerId") String ownerId,
                        @Param("startIndex") Integer startIndex);

    ResourcePO selectById(Integer id);

}
