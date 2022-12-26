package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.UserProjectRelationPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRelationMapper {
    int remove(@Param("userId") Integer userId, @Param("projectId") Integer project);

    int insert(UserProjectRelationPO record);

    UserProjectRelationPO select(@Param("userId") Integer userId);

    List<UserProjectRelationPO> listMember(UserProjectRelationPO record);

    int update(UserProjectRelationPO record);

}
