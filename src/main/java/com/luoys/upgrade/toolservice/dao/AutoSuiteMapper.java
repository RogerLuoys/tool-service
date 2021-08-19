package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;
import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自动化测试集dao层接口
 *
 * @author luoys
 */
@Repository
public interface AutoSuiteMapper {

    /**
     * 插入数据
     *
     * @param autoSuitePO 表对应的pojo对象
     * @return 插入成功为1
     */
    int insert(AutoSuitePO autoSuitePO);

    /**
     * 逻辑删除数据
     *
     * @param suiteId 业务id
     * @return 删除成功为1
     */
    int remove(@Param("suiteId") String suiteId);

    /**
     * 更新数据
     *
     * @param autoSuitePO 表对应的pojo对象
     * @return 成功为1
     */
    int update(AutoSuitePO autoSuitePO);

    /**
     * 更新测试集执行结果
     *
     * @param suiteId 业务id
     * @param passed 成功数
     * @param failed 失败数
     * @return 成功为1
     */
    int updateResult(@Param("suiteId") String suiteId,
                     @Param("passed") Integer passed,
                     @Param("failed") Integer failed);

    /**
     * 更新测试集状态
     *
     * @param suiteId 业务id
     * @param status 状态
     * @return 成功为1
     */
    int updateStatus(@Param("suiteId") String suiteId,
                     @Param("status") Integer status);

    /**
     * 同步测试集执行状态
     *
     * @param suiteId 业务id
     * @param isApiCompleted 测试集中的api用例是否执行完成
     * @param isUiCompleted 测试集中的ui用例是否执行完成
     * @return 成功为1
     */
    int updateExecuteStatus(@Param("suiteId") String suiteId,
                     @Param("isApiCompleted") Boolean isApiCompleted,
                     @Param("isUiCompleted") Boolean isUiCompleted);

    /**
     * 根据id查询详情
     *
     * @param suiteId 业务id
     * @return 表对应的pojo对象
     */
    AutoSuitePO selectByUUID(@Param("suiteId") String suiteId);

    /**
     * 按条件分页查询，默认10条数据一页
     *
     * @param name       名字
     * @param ownerId    所属人id
     * @param startIndex 页码，从0开始
     * @return
     */
    List<AutoSuitePO> list(@Param("name") String name,
                           @Param("ownerId") String ownerId,
                           @Param("startIndex") Integer startIndex);

}
