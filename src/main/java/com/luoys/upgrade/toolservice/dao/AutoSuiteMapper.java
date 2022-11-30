package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自动化套件dao层接口 updated
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
     * @param suiteId 主键id
     * @return 删除成功为1
     */
    int remove(@Param("suiteId") Integer suiteId);

    /**
     * 更新数据
     *
     * @param autoSuitePO 表对应的pojo对象
     * @return 成功为1
     */
    int update(AutoSuitePO autoSuitePO);

    /**
     * 更新套件执行结果
     *
     * @param suiteId 主键id
     * @param passed  成功数
     * @param failed  失败数
     * @return 成功为1
     */
    int updateResult(@Param("suiteId") Integer suiteId,
                     @Param("passed") Integer passed,
                     @Param("failed") Integer failed);

    /**
     * 更新套件状态
     *
     * @param suiteId 业务id
     * @param status  状态
     * @return 成功为1
     */
    int updateStatus(@Param("suiteId") Integer suiteId,
                     @Param("status") Integer status);

    /**
     * 更新套件关联的用例总数
     *
     * @param suiteId 主键id
     * @param total   关联的用例总数
     * @return 成功为1
     */
    int updateTotal(@Param("suiteId") Integer suiteId,
                    @Param("total") Integer total);

//    /**
//     * 同步套件执行状态
//     *
//     * @param suiteId        主键id
//     * @param isApiCompleted 套件中的api用例是否执行完成
//     * @param isUiCompleted  套件中的ui用例是否执行完成
//     * @return 成功为1
//     */
//    int updateExecuteStatus(@Param("suiteId") Integer suiteId,
//                            @Param("isApiCompleted") Boolean isApiCompleted,
//                            @Param("isUiCompleted") Boolean isUiCompleted);

    /**
     * 根据id查询详情
     *
     * @param suiteId 业务id(即主键id)
     * @return 表对应的pojo对象
     */
    AutoSuitePO selectByUUID(@Param("suiteId") Integer suiteId);

    /**
     * 按条件分页查询，默认10条数据一页
     *
     * @param name       名字
     * @param ownerId    所属人id
     * @param startIndex 页码，从0开始
     * @return 对象列表
     */
    List<AutoSuitePO> list(@Param("name") String name,
                           @Param("ownerId") Integer ownerId,
                           @Param("startIndex") Integer startIndex);

    /**
     * 按条件查询总条数
     *
     * @param name    名字
     * @param ownerId 所属人id
     * @return 总数
     */
    int count(@Param("name") String name,
              @Param("ownerId") Integer ownerId);

}
