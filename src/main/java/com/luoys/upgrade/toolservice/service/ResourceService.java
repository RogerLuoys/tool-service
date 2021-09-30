package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.dao.ResourceMapper;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformResource;
import com.luoys.upgrade.toolservice.web.vo.ResourceSimpleVO;
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
        resourceVO.setResourceId(NumberSender.createResourceId());
        if (resourceVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode().toString())) {
            resourceVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
        } else {
            String userName = userMapper.selectByUUId(resourceVO.getOwnerId()).getUserName();
            resourceVO.setOwnerName(userName);
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
    public Boolean remove(String resourceId) {
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

    /**
     * 更换使用者
     *
     * @param resourceVO 资源对象
     * @return 成功为true，失败为false
     */
    public Boolean updateUser(ResourceVO resourceVO) {
        int result = resourceMapper.updateUser(resourceVO.getResourceId(), resourceVO.getUserId(), resourceVO.getUserName());
        return result == 1;
    }

    /**
     * 查询资源列表
     *
     * @param type      类型
     * @param name      名字
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 资源列表
     */
    public List<ResourceSimpleVO> query(Integer type, String name, String userId, Integer pageIndex) {
        int startIndex = (pageIndex - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode();
        return TransformResource.transformPO2SimpleVO(resourceMapper.list(type, name, userId, startIndex));
    }

    /**
     * 查询资源总数
     *
     * @param type   类型
     * @param name   名字
     * @param userId 用户id
     * @return 资源列表
     */
    public Integer count(Integer type, String name, String userId) {
        return resourceMapper.count(type, name, userId);
    }

    /**
     * 查询资源详情
     *
     * @param resourceId 资源业务id
     * @return 资源对象
     */
    public ResourceVO queryDetail(String resourceId) {
        return TransformResource.transformPO2VO(resourceMapper.selectByUUID(resourceId));
    }

}
