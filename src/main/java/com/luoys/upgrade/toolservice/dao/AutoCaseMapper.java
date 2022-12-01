package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自动化用例dao层接口 update
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
     * @param caseId 主键id
     * @return 删除成功为1
     */
    int remove(@Param("caseId") Integer caseId);

    /**
     * 更新数据
     *
     * @param autoCasePO 表对应的pojo对象
     * @return 成功为1
     */
    int update(AutoCasePO autoCasePO);

    /**
     * 更新用例状态
     *
     * @param caseId 用例主键id
     * @param status 用例业务状态
     * @return 成功为1
     */
    int updateStatus(@Param("caseId") Integer caseId,
                     @Param("status") Integer status);

    /**
     * 根据id查询详情
     *
     * @param caseId 主键id
     * @return 表对应的pojo对象
     */
    AutoCasePO selectById(@Param("caseId") Integer caseId);

    /**
     * 按条件分页查询，默认10条数据一页
     *
     * @param status     状态
     * @param name       名字
     * @param startIndex 页码，从0开始
     * @return 对象列表
     */
    List<AutoCasePO> list(@Param("projectId") Integer projectId,
                          @Param("supperCaseId") Integer supperCaseId,
                          @Param("status") Integer status,
                          @Param("name") String name,
                          @Param("startIndex") Integer startIndex);

    /**
     * 按条件查询总数
     *
     * @param status  状态
     * @param name    名字
     * @return 总数
     */
    int count(@Param("projectId") Integer projectId,
              @Param("supperCaseId") Integer supperCaseId,
              @Param("status") Integer status,
              @Param("name") String name);

}
