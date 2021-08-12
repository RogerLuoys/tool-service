package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.CaseStepRelationPO;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseStepRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CaseStepRelationPO record);

    int insertSelective(CaseStepRelationPO record);

    CaseStepRelationPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CaseStepRelationPO record);

    int updateByPrimaryKey(CaseStepRelationPO record);
}
