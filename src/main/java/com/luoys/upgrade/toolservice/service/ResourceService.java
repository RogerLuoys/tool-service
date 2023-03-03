package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.dao.ResourceMapper;
import com.luoys.upgrade.toolservice.service.enums.DefaultEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformResource;
import com.luoys.upgrade.toolservice.web.vo.QueryVO;
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

    /**
     * 创建单个资源
     *
     * @param resourceVO 资源对象
     * @return 成功为true，失败为false
     */
    public Integer create(ResourceVO resourceVO) {
        ResourcePO resourcePO = TransformResource.transformVO2PO(resourceVO);
        resourceMapper.insert(resourcePO);
        return resourcePO.getId();
    }

    /**
     * 逻辑删除单个资源
     *
     * @param resourceId 资源业务id
     * @return 成功为1
     */
    public Integer remove(Integer resourceId) {
        return resourceMapper.remove(resourceId);
    }

    /**
     * 更新单个资源
     *
     * @param resourceVO 资源对象
     * @return 成功为1
     */
    public Integer update(ResourceVO resourceVO) {
        ResourcePO resourcePO = TransformResource.transformVO2PO(resourceVO);
        return resourceMapper.update(resourcePO);
    }

    /**
     * 查询资源列表
     *
     * @param queryVO      类型
     * @return 资源列表
     */
    public List<ResourceVO> query(QueryVO queryVO) {
        Integer startIndex = queryVO.getPageIndex() == null ? null : (queryVO.getPageIndex() - 1) * DefaultEnum.DEFAULT_PAGE_SIZE.getCode();
        if (queryVO.getPageIndex() != null) {
            startIndex = (queryVO.getPageIndex() - 1) * DefaultEnum.DEFAULT_PAGE_SIZE.getCode();
        }
        List<ResourcePO> resourcePOList = resourceMapper.list(queryVO.getType(), queryVO.getName(), queryVO.getUserId(), queryVO.getProjectId(), startIndex);
        return TransformResource.transformPO2VO(resourcePOList);
    }

    /**
     * 查询资源总数
     *
     * @param queryVO   类型
     * @return 资源列表
     */
    public Integer count(QueryVO queryVO) {
        return resourceMapper.count(queryVO.getType(), queryVO.getName(), queryVO.getUserId());
    }

    /**
     * 查询资源详情
     *
     * @param resourceId 资源业务id
     * @return 资源对象
     */
    public ResourceVO queryDetail(Integer resourceId) {
        ResourcePO resourcePO = resourceMapper.selectByID(resourceId);
        return TransformResource.transformPO2VO(resourcePO);
    }

}
