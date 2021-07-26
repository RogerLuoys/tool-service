package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.TestCaseMapper;
import com.luoys.upgrade.toolservice.dao.TestSuiteMapper;
import com.luoys.upgrade.toolservice.service.transform.TransformTestSuite;
import com.luoys.upgrade.toolservice.web.vo.TestSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.TestSuiteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiteService {

    @Autowired
    private TestSuiteMapper testSuiteMapper;


    public Boolean create(TestSuiteVO testSuiteVO) {
        int result = testSuiteMapper.insert(TransformTestSuite.transformVO2PO(testSuiteVO));
        return result == 1;
    }

    public Boolean remove(Integer testSuiteId) {
        int result = testSuiteMapper.delete(testSuiteId);
        return result == 1;
    }

    public Boolean update(TestSuiteVO testSuiteVO) {
        int result = testSuiteMapper.update(TransformTestSuite.transformVO2PO(testSuiteVO));
        return result == 1;
    }

    public List<TestSuiteSimpleVO> query(String name, Integer pageIndex) {
        return TransformTestSuite.transformPO2SimpleVO(testSuiteMapper.list(name, pageIndex-1));
    }

    public TestSuiteVO queryDetail(Integer testSuiteId) {
        return TransformTestSuite.transformPO2VO(testSuiteMapper.selectById(testSuiteId));
    }

}
