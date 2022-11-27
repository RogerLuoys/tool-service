package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ProjectPO;
import org.apache.ibatis.annotations.Param;

public interface ProjectMapper {
    int remove(@Param("projectId") Integer projectId);

    int insert(ProjectPO record);

    ProjectPO select(@Param("projectId") Integer projectId);

    int update(ProjectPO record);

}