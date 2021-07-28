package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.web.vo.TestCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.TestCaseVO;
import com.luoys.upgrade.toolservice.dao.TestCaseMapper;
import com.luoys.upgrade.toolservice.service.transform.TransformTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {

    @Autowired
    private TestCaseMapper testCaseMapper;


    /**
     * 新增单个用例
     * @param testCaseVO 用例对象
     * @return 新增成功为true，失败为false
     */
    public Boolean create(TestCaseVO testCaseVO) {
        if (testCaseVO.getOwnerId() == KeywordEnum.DEFAULT_USER.getCode()) {
            testCaseVO.setOwnerName(KeywordEnum.DEFAULT_USER.getDescription());
        } else {
            //todo 查用户名
        }
        int result = testCaseMapper.insert(TransformTestCase.transformVO2PO(testCaseVO));
        return result == 1;
    }

    /**
     * 逻辑删除单个用例
     * @param testCaseId 用例主键id
     * @return 删除成功为true，失败为false
     */
    public Boolean remove(Integer testCaseId) {
        int result = testCaseMapper.delete(testCaseId);
        return result == 1;
    }

    /**
     * 更新单个用例
     * @param testCaseVO 用例对象
     * @return 更新成功为true，失败为false
     */
    public Boolean update(TestCaseVO testCaseVO) {
        int result = testCaseMapper.update(TransformTestCase.transformVO2PO(testCaseVO));
        return result == 1;
    }

    /**
     * 查询用例列表
     * @param userId 用户id
     * @param isOnlyOwner 是否只查自己
     * @param status 用例状态
     * @param name 用例名称
     * @param pageIndex 页码
     * @return 用例列表
     */
    public List<TestCaseSimpleVO> query(String userId, Boolean isOnlyOwner, Integer status, String name, Integer pageIndex) {
        if (!isOnlyOwner) {
            userId = null;
        }
        return TransformTestCase.transformPO2SimpleVO(testCaseMapper.list(userId, status, name, pageIndex-1));
    }

    /**
     * 查询用例详情
     * @param testCaseId 用例主键id
     * @return 用例对象
     */
    public TestCaseVO queryDetail(Integer testCaseId) {
        return TransformTestCase.transformPO2VO(testCaseMapper.selectById(testCaseId));
    }

}
