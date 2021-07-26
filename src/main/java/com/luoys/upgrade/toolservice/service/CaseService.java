package com.luoys.upgrade.toolservice.service;

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


    public Boolean create(TestCaseVO testCaseVO) {
        int result = testCaseMapper.insert(TransformTestCase.transformVO2PO(testCaseVO));
        return result == 1;
    }

    public Boolean remove(Integer testCaseId) {
        int result = testCaseMapper.delete(testCaseId);
        return result == 1;
    }

    public Boolean update(TestCaseVO testCaseVO) {
        int result = testCaseMapper.update(TransformTestCase.transformVO2PO(testCaseVO));
        return result == 1;
    }

    public List<TestCaseSimpleVO> query(String userId, Boolean isOnlyOwner, Integer status, String name, Integer pageIndex) {
        if (!isOnlyOwner) {
            userId = null;
        }
        return TransformTestCase.transformPO2SimpleVO(testCaseMapper.list(userId, status, name, pageIndex-1));
    }

    public TestCaseVO queryDetail(Integer testCaseId) {
        return TransformTestCase.transformPO2VO(testCaseMapper.selectById(testCaseId));
    }

}
