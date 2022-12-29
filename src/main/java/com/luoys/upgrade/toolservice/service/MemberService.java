package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.UserProjectRelationMapper;
import com.luoys.upgrade.toolservice.dao.po.UserProjectRelationPO;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformResource;
import com.luoys.upgrade.toolservice.service.transform.TransformUserProjectRelation;
import com.luoys.upgrade.toolservice.web.vo.MemberVO;
import com.luoys.upgrade.toolservice.web.vo.ResourceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目成员
 *
 * @author luoys
 */
@Slf4j
@Service
public class MemberService {

    @Autowired
    private UserProjectRelationMapper userProjectRelationMapper;

    /**
     * 邀请成员
     * @param memberVO 成员信息
     * @return 结果
     */
    public Integer invite(MemberVO memberVO) {
        UserProjectRelationPO userProjectRelationPO = TransformUserProjectRelation.transformVO2PO(memberVO);
        userProjectRelationMapper.insert(userProjectRelationPO);
        return userProjectRelationPO.getId();
    }

    /**
     * 逻辑删除单个资源
     */
    public Boolean remove(Integer userId, Integer projectId) {
        int result = userProjectRelationMapper.remove(userId, projectId);
        return result == 1;
    }

    /**
     * 更新成员角色
     *
     * @param memberVO 成员对象
     * @return 成功为true，失败为false
     */
    public Boolean update(MemberVO memberVO) {
        int result = userProjectRelationMapper.update(TransformUserProjectRelation.transformVO2PO(memberVO));
        return result == 1;
    }

    /**
     * 查询成员列表
     *
     * @param projectId   项目id
     * @param nickname   昵称
     * @return 总数
     */
    public List<MemberVO> query(Integer projectId, String nickname, Integer pageIndex) {
        int startIndex = (pageIndex - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode();
        List<UserProjectRelationPO> userProjectRelationPOList = userProjectRelationMapper.listMember(projectId, nickname, startIndex);
        return TransformUserProjectRelation.transformPO2VO(userProjectRelationPOList);
    }
    /**
     * 查询成员列表
     *
     * @param projectId   项目id
     * @param nickname   昵称
     * @return 总数
     */
    public Integer count(Integer projectId, String nickname) {
        return userProjectRelationMapper.count(projectId, nickname);
    }

}
