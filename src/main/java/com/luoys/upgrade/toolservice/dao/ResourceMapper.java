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
    int remove(@Param("resourceId") Integer resourceId);

    /**
     * 更新数据
     *
     * @param resourcePO 表对应的pojo对象
     * @return 成功为1
     */
    int update(ResourcePO resourcePO);

    /**
     * 更换使用者
     *
     * @param resourceId 业务id
     * @param userId 使用者id
     * @param userName 使用者昵称
     * @return 成功为1
     */
    int updateUser(@Param("resourceId") Integer resourceId,
                   @Param("userId") Integer userId,
                   @Param("userName") String userName);

    /**
     * 根据业务id查询详情
     *
     * @param resourceId 业务id
     * @return 表对应的pojo对象
     */
    ResourcePO selectByID(@Param("resourceId") Integer resourceId);


    /**
     * 根据名称查询详情
     *
     * @param name 业务id
     * @return 表对应的pojo对象
     */
    ResourcePO selectByName(@Param("name") String name);

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
                          @Param("ownerId") Integer ownerId,
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
                  @Param("ownerId") Integer ownerId);

}
