package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 测试资源dao层接口
 *
 * @author luoys
 */
@Repository
public interface ResourceMapper {

    /**
     * 插入数据
     *
     * @param resourcePO 表对应的pojo对象
     * @return 插入成功为1
     */
    int insert(ResourcePO resourcePO);

    /**
     * 逻辑删除数据
     *
     * @param resourceId 业务id
     * @return 删除成功为1
     */
    int remove(@Param("resourceId") String resourceId);

    /**
     * 更新数据
     *
     * @param resourcePO 表对应的pojo对象
     * @return 成功为1
     */
    int update(ResourcePO resourcePO);

    /**
     * 根据业务id查询详情
     *
     * @param resourceId 业务id
     * @return 表对应的pojo对象
     */
    ResourcePO selectByUUID(@Param("resourceId") String resourceId);

    /**
     * 按条件分页查询，默认10条数据一页
     *
     * @param type       类型
     * @param name       名字
     * @param ownerId    所属人id
     * @param startIndex 页码，从0开始
     * @return 对象列表
     */
    List<ResourcePO> list(@Param("type") Integer type,
                          @Param("name") String name,
                          @Param("ownerId") String ownerId,
                          @Param("startIndex") Integer startIndex);

    /**
     * 按条件查询总数
     *
     * @param type    类型
     * @param name    名字
     * @param ownerId 所属人id
     * @return 总数
     */
    Integer count(@Param("type") Integer type,
                  @Param("name") String name,
                  @Param("ownerId") String ownerId);

}
