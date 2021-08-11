package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自动化用例dao层接口
 *
 * @author luoys
 */
@Repository
public interface AutoCaseMapper {

    /**
     * 插入数据
     *
     * @param autoCasePO 表对应的pojo对象
     * @return 插入成功为1
     */
    int insert(AutoCasePO autoCasePO);

    /**
     * 逻辑删除数据
     *
     * @param caseId id
     * @return 删除成功为1
     */
    int remove(@Param("caseId") String caseId);

    /**
     * 更新数据
     *
     * @param autoCasePO 表对应的pojo对象
     * @return 成功为1
     */
    int update(AutoCasePO autoCasePO);

    /**
     * 根据id查询详情
     *
     * @param caseId id
     * @return 表对应的pojo对象
     */
    AutoCasePO selectByUUID(@Param("caseId") String caseId);

    /**
     * 按条件分页查询，默认10条数据一页
     *
     * @param status     状态
     * @param name       名字
     * @param ownerId    所属人id
     * @param startIndex 页码，从0开始
     * @return
     */
    List<AutoCasePO> list(@Param("status") Integer status,
                          @Param("name") String name,
                          @Param("ownerId") String ownerId,
                          @Param("startIndex") Integer startIndex);

}
