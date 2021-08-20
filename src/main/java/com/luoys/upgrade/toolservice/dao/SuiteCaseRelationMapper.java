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

    int updateStatus(@Param("suiteId") String suiteId,
                     @Param("caseId") String caseId,
                     @Param("status") Integer status);

    int resetStatusBySuiteId(@Param("suiteId") String suiteId);

    /**
     * 按条件查询用例列表
     *
     * @param suiteId    测试集id
     * @param startIndex    分页，为空则查全部
     * @param retry    true查询状态不通过的用例
     * @return 关联用例列表
     */
    List<SuiteCaseRelationPO> listCaseBySuiteId(@Param("suiteId") String suiteId,
                                                @Param("startIndex") Integer startIndex,
                                                @Param("retry") Boolean retry);

    /**
     * 查询总数
     * @param suiteId 测试集id
     * @param status 用例状态，为空则查询所有状态
     * @return -
     */
    int countBySuiteId(@Param("suiteId") String suiteId,
                       @Param("status") Integer status);

}
