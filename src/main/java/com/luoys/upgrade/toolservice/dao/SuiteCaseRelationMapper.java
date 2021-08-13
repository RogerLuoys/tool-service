package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import com.luoys.upgrade.toolservice.dao.po.SuiteCaseRelationPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuiteCaseRelationMapper {

    int removeBySuiteId(@Param("suiteId") String suiteId);

    int remove(SuiteCaseRelationPO record);

    int insert(SuiteCaseRelationPO record);

    SuiteCaseRelationPO selectById(Integer id);

    int update(SuiteCaseRelationPO record);

    /**
     * 按条件查询用例列表
     *
     * @param suiteId    测试集id
     * @return
     */
    List<SuiteCaseRelationPO> listCaseBySuiteId(@Param("suiteId") String suiteId);

}
