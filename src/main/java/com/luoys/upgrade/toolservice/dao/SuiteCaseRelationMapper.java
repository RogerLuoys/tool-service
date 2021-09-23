package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.SuiteCaseRelationPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 测试集用例关联关系dao层接口
 *
 * @author luoys
 */
@Repository
public interface SuiteCaseRelationMapper {

    /**
     * 按测试suiteId，批量逻辑删除关联的用例
     *
     * @param suiteId 测试集业务id
     * @return 影响行数
     */
    int removeBySuiteId(@Param("suiteId") String suiteId);

    /**
     * 按suiteId和caseId，逻辑删除单个用例
     *
     * @param record 测试集用例关联关系对象
     * @return 成功为1
     */
    int remove(SuiteCaseRelationPO record);

    /**
     * 插入数据
     *
     * @param record 关系对象
     * @return 成功为1
     */
    int insert(SuiteCaseRelationPO record);

    /**
     * 批量插入
     *
     * @param recordList 对象列表
     * @return 插入行数
     */
    int batchInsert(@Param("list") List<SuiteCaseRelationPO> recordList);

    /**
     * 查询
     *
     * @param id 自增编号
     * @return -
     */
    SuiteCaseRelationPO selectById(Integer id);

    /**
     * 按suiteId和caseId，更新数据
     *
     * @param record 对象
     * @return 成功为1
     */
    int update(SuiteCaseRelationPO record);

    /**
     * 按suiteId和caseId，更新数据
     *
     * @param suiteId 测试集业务id
     * @param caseId  用例业务id
     * @param status  要更新的状态
     * @return 成功为1
     */
    int updateStatus(@Param("suiteId") String suiteId,
                     @Param("caseId") String caseId,
                     @Param("status") Integer status);

    /**
     * 根据suiteId重置关联的用例状态
     *
     * @param suiteId 测试集业务id
     * @return 影响行数
     */
    int resetStatusBySuiteId(@Param("suiteId") String suiteId);

    /**
     * 按条件查询用例列表
     *
     * @param suiteId    测试集id
     * @param startIndex 分页，为空则查全部
     * @param retry      true查询状态不通过的用例
     * @return 关联用例列表
     */
    List<SuiteCaseRelationPO> listCaseBySuiteId(@Param("suiteId") String suiteId,
                                                @Param("startIndex") Integer startIndex,
                                                @Param("retry") Boolean retry);

    /**
     * 查询总数
     *
     * @param suiteId 测试集id
     * @param status  用例状态，为空则查询所有状态
     * @return 总数
     */
    int countBySuiteId(@Param("suiteId") String suiteId,
                       @Param("status") Integer status);

}
