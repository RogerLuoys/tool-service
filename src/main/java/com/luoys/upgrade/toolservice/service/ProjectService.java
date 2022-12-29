package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.ProjectMapper;
import com.luoys.upgrade.toolservice.dao.po.ProjectPO;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformProject;
import com.luoys.upgrade.toolservice.web.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目
 *
 * @author luoys
 */
@Slf4j
@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 邀请成员
     * @param projectVO 成员信息
     * @return 结果
     */
    public Integer create(ProjectVO projectVO) {
        ProjectPO projectPO = TransformProject.transformVO2PO(projectVO);
        projectMapper.insert(projectPO);
        return projectPO.getId();
    }

    /**
     * 逻辑删除单个资源
     */
    public Boolean remove(Integer projectId) {
        int result = projectMapper.remove(projectId);
        return result == 1;
    }

    /**
     * 更新成员角色
     *
     * @param projectVO 成员对象
     * @return 成功为true，失败为false
     */
    public Boolean update(ProjectVO projectVO) {
        int result = projectMapper.update(TransformProject.transformVO2PO(projectVO));
        return result == 1;
    }

    /**
     * 查询成员列表
     *
     * @param name   项目id
     * @param pageIndex   页码
     * @return 总数
     */
    public List<ProjectVO> query(String name, Integer pageIndex) {
        int startIndex = (pageIndex - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode();
        List<ProjectPO> projectPOList = projectMapper.list(name, startIndex);
        return TransformProject.transformPO2VO(projectPOList);
    }
    /**
     * 查询成员列表
     *
     * @param name   昵称
     * @return 总数
     */
    public Integer count(String name) {
        return projectMapper.count(name);
    }

}
