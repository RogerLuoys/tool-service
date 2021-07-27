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


    /**
     * 新增单个测试集
     * @param testSuiteVO 测试集对象
     * @return 成功为true，失败为false
     */
    public Boolean create(TestSuiteVO testSuiteVO) {
        int result = testSuiteMapper.insert(TransformTestSuite.transformVO2PO(testSuiteVO));
        return result == 1;
    }

    /**
     * 删除单个测试集
     * @param testSuiteId 测试集主键id
     * @return 成功为true，失败为false
     */
    public Boolean remove(Integer testSuiteId) {
        int result = testSuiteMapper.delete(testSuiteId);
        return result == 1;
    }

    /**
     * 更新单个测试集
     * @param testSuiteVO 测试集对象
     * @return 成功为true，失败为false
     */
    public Boolean update(TestSuiteVO testSuiteVO) {
        int result = testSuiteMapper.update(TransformTestSuite.transformVO2PO(testSuiteVO));
        return result == 1;
    }

    /**
     * 查询测试集列表
     * @param name 名字，可空
     * @param pageIndex 页码
     * @return 测试集列表
     */
    public List<TestSuiteSimpleVO> query(String name, Integer pageIndex) {
        return TransformTestSuite.transformPO2SimpleVO(testSuiteMapper.list(name, pageIndex-1));
    }

    /**
     * 查询测试集详情
     * @param testSuiteId 测试集主键id
     * @return 测试集对象
     */
    public TestSuiteVO queryDetail(Integer testSuiteId) {
        return TransformTestSuite.transformPO2VO(testSuiteMapper.selectById(testSuiteId));
    }

}
