package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自动化步骤dao层接口
 *
 * @author luoys
 */
@Repository
public interface AutoStepMapper {

    /**
     * 插入数据
     *
     * @param autoStepPO 表对应的pojo对象
     * @return 插入成功为1
     */
    int insert(AutoStepPO autoStepPO);

    /**
     * 批量插入
     *
     * @param recordList 对象列表
     * @return 插入行数
     */
    int batchInsert(@Param("list") List<AutoStepPO> recordList);

    /**
     * 逻辑删除数据
     *
     * @param stepId 业务id
     * @return 删除成功为1
     */
    int remove(@Param("stepId") Integer stepId);

    /**
     * 逻辑删除数据
     *
     * @param caseId 用例业务id
     * @return 删除成功为1
     */
    int removeByCaseId(@Param("caseId") Integer caseId);

    /**
     * 更新数据
     *
     * @param autoStepPO 表对应的pojo对象
     * @return 成功为1
     */
    int update(AutoStepPO autoStepPO);

    /**
     * 更新结果
     *
     * @param stepId 业务id
     * @param result 执行结果
     * @return 成功为1
     */
    int updateResult(@Param("stepId") Integer stepId, @Param("result") String result);

    /**
     * 根据id查询详情
     *
     * @param stepId 业务id
     * @return 表对应的pojo对象
     */
    AutoStepPO select(@Param("stepId") Integer stepId);

    /**
     * 根据caseId查询关联的步骤列表
     *
     * @param caseId 用例业务id
     * @return 对象列表
     */
    List<AutoStepPO> listByCaseId(@Param("caseId") Integer caseId);

//    /**
//     * 按条件查询总数
//     *
//     * @param type     类型
//     * @param name     名字
//     * @param ownerId  所属人id
//     * @param isPublic 是否公用
//     * @return 总数
//     */
//    int count(@Param("type") Integer type,
//              @Param("name") String name,
//              @Param("ownerId") String ownerId,
//              @Param("isPublic") Boolean isPublic);

}
