package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ProjectPO;
import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {
    int remove(@Param("projectId") Integer projectId);

    int insert(ProjectPO record);

    ProjectPO select(@Param("projectId") Integer projectId);

    int update(ProjectPO record);

    /**
     * 按条件分页查询，默认10条数据一页
     *
     * @param name       名字
     * @param startIndex 页码，从0开始
     * @return 对象列表
     */
    List<ProjectPO> list(@Param("name") String name,
                         @Param("startIndex") Integer startIndex);

    Integer count(@Param("name") String name);
}