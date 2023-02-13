package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.UserProjectRelationPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRelationMapper {
    int remove(@Param("userId") Integer userId, @Param("projectId") Integer projectId);

    int insert(UserProjectRelationPO record);

    List<UserProjectRelationPO> listProject(@Param("userId") Integer userId);

    List<UserProjectRelationPO> listMember(@Param("projectId") Integer project,
                                           @Param("nickname") String nickname,
                                           @Param("startIndex") Integer startIndex);

    int count(@Param("projectId") Integer project,
              @Param("nickname") String nickname);

    int update(UserProjectRelationPO record);

}
