package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.CaseStepRelationPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用例步骤关联关系dao层接口
 *
 * @author luoys
 */
@Repository
public interface CaseStepRelationMapper {
    int removeByCaseId(@Param("caseId") String caseId);

    int remove(CaseStepRelationPO record);

    int insert(CaseStepRelationPO record);

    List<CaseStepRelationPO> listStepByCaseId(@Param("caseId") String caseId);

    int update(CaseStepRelationPO record);

}
