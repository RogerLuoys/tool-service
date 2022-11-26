package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.UserProjectRelationPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRelationMapper {
    int remove(@Param("userId") Integer userId, @Param("projectId") Integer project);

    int insert(UserProjectRelationPO record);

    UserProjectRelationPO select(@Param("userId") Integer userId);

    int update(UserProjectRelationPO record);

}
