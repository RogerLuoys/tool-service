package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.dao.po.ToolPO;
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
     * 逻辑删除数据
     *
     * @param stepId 业务id
     * @return 删除成功为1
     */
    int remove(@Param("stepId") String stepId);

    /**
     * 更新数据
     *
     * @param autoStepPO 表对应的pojo对象
     * @return 成功为1
     */
    int update(AutoStepPO autoStepPO);

    int updateResult(@Param("stepId") String stepId,
                     @Param("actualResult") String actualResult);

    /**
     * 根据id查询详情
     *
     * @param stepId 业务id
     * @return 表对应的pojo对象
     */
    AutoStepPO selectByUUID(@Param("stepId") String stepId);

    /**
     * 按条件分页查询，默认10条数据一页
     *
     * @param type       类型
     * @param name       名字
     * @param ownerId    所属人id
     * @param isPublic   是否公用
     * @param startIndex 页码，从0开始
     * @return
     */
    List<AutoStepPO> list(@Param("type") Integer type,
                          @Param("name") String name,
                          @Param("ownerId") String ownerId,
                          @Param("isPublic") Boolean isPublic,
                          @Param("startIndex") Integer startIndex);

}
