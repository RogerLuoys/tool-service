package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.CaseStepRelationPO;
import com.luoys.upgrade.toolservice.dao.po.SuiteCaseRelationPO;
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

    /**
     * 根据caseId批量逻辑删除关联的步骤
     *
     * @param caseId 用例业务id
     * @return 影响行数
     */
    int removeByCaseId(@Param("caseId") String caseId);

    /**
     * 根据caseId和stepId逻辑删除关联的步骤
     *
     * @param record
     * @return
     */
    int remove(CaseStepRelationPO record);

    /**
     * 插入数据
     *
     * @param record 对象
     * @return 成功为1
     */
    int insert(CaseStepRelationPO record);

    /**
     * 批量插入
     *
     * @param recordList 对象列表
     * @return 插入行数
     */
    int batchInsert(@Param("list") List<CaseStepRelationPO> recordList);

    /**
     * 根据caseId查询关联的步骤列表
     *
     * @param caseId 用例业务id
     * @return 对象列表
     */
    List<CaseStepRelationPO> listStepByCaseId(@Param("caseId") String caseId);

    /**
     * 根据caseId和stepId更新数据
     *
     * @param record 对象
     * @return 成功为1
     */
    int update(CaseStepRelationPO record);

}
