package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.UserProjectRelationMapper;
import com.luoys.upgrade.toolservice.dao.po.UserProjectRelationPO;
import com.luoys.upgrade.toolservice.service.transform.TransformUserProjectRelation;
import com.luoys.upgrade.toolservice.web.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
