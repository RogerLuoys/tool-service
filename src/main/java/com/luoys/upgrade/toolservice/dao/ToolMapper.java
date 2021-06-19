package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ToolPO;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ToolPO record);

    int insertSelective(ToolPO record);

    ToolPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ToolPO record);

    int updateByPrimaryKey(ToolPO record);
}