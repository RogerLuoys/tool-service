package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.dao.ResourceMapper;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformResource;
import com.luoys.upgrade.toolservice.web.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源服务，包含资源相关的所有业务逻辑
 *
 * @author luoys
 */
@Service
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 创建单个资源
     *
     * @param resourceVO 资源对象
     * @return 成功为true，失败为false
     */
    public Boolean create(ResourceVO resourceVO) {
//        resourceVO.setResourceId(NumberSender.createResourceId());
        if (resourceVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode())) {
            resourceVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
        } else {
            String username = userMapper.selectById(resourceVO.getOwnerId()).getUsername();
            resourceVO.setOwnerName(username);
        }
        int result = resourceMapper.insert(TransformResource.transformVO2PO(resourceVO));
        return result == 1;
    }

    /**
     * 逻辑删除单个资源
     *
     * @param resourceId 资源业务id
     * @return 成功为true，失败为false
     */
    public Boolean remove(Integer resourceId) {
        int result = resourceMapper.remove(resourceId);
        return result == 1;
    }

    /**
     * 更新单个资源
     *
     * @param resourceVO 资源对象
     * @return 成功为true，失败为false
     */
    public Boolean update(ResourceVO resourceVO) {
        int result = resourceMapper.update(TransformResource.transformVO2PO(resourceVO));
        return result == 1;
    }

//    /**
//     * 更换使用者
//     *
//     * @param resourceVO 资源对象
//     * @return 成功为true，失败为false
//     */
//    public Boolean updateUser(ResourceVO resourceVO) {
//        int result = resourceMapper.updateUser(resourceVO.getResourceId(), resourceVO.getUserId(), resourceVO.getUserName());
//        return result == 1;
//    }

    /**
     * 查询资源列表
     *
     * @param type      类型
     * @param name      名字
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 资源列表
     */
    public List<ResourceVO> query(Integer type, String name, Integer userId, Integer pageIndex) {
        Integer startIndex = null;
        if (pageIndex != null) {
            startIndex = (pageIndex - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode();
        }
        List<ResourcePO> resourcePOList = resourceMapper.list(type, name, userId, startIndex);
        return TransformResource.transformPO2VO(resourcePOList);
    }

    /**
     * 查询资源总数
     *
     * @param type   类型
     * @param name   名字
     * @param userId 用户id
     * @return 资源列表
     */
    public Integer count(Integer type, String name, Integer userId) {
        return resourceMapper.count(type, name, userId);
    }

    /**
     * 查询资源详情
     *
     * @param resourceId 资源业务id
     * @return 资源对象
     */
    public ResourceVO queryDetail(Integer resourceId) {
        return TransformResource.transformPO2VO(resourceMapper.selectByID(resourceId));
    }

//    /**
//     * 查询资源详情，查询数据库连接信息时，需要通过名称查
//     *
//     * @param name 资源的名称
//     * @return 资源对象
//     */
//    public ResourceVO queryDetailByName(String name) {
//        return TransformResource.transformPO2VO(resourceMapper.selectByName(name));
//    }

}
