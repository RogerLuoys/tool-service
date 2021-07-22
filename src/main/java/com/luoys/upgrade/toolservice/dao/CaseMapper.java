package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.CasePO;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CasePO record);

    int insertSelective(CasePO record);

    CasePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CasePO record);

    int updateByPrimaryKey(CasePO record);
}
