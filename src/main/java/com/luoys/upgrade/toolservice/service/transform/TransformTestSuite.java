package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.TestSuitePO;
import com.luoys.upgrade.toolservice.web.vo.TestSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.TestSuiteVO;

import java.util.ArrayList;
import java.util.List;

public class TransformTestSuite {

    public static TestSuiteSimpleVO transformPO2SimpleVO(TestSuitePO po) {
        if (po == null) {
            return null;
        }
        TestSuiteSimpleVO vo = new TestSuiteSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setTitle(po.getTitle());
        vo.setFailed(po.getFailed());
        vo.setPassed(po.getPassed());
        vo.setTestSuiteId(po.getId());
        return vo;
    }

    public static List<TestSuiteSimpleVO> transformPO2SimpleVO(List<TestSuitePO> poList) {
        List<TestSuiteSimpleVO> voList = new ArrayList<>();
        for (TestSuitePO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static TestSuiteVO transformPO2VO(TestSuitePO po) {
        if (po == null) {
            return null;
        }
        TestSuiteVO vo = new TestSuiteVO();
        vo.setDescription(po.getDescription());
        vo.setTestSuiteId(po.getId());
        vo.setTitle(po.getTitle());
        vo.setFailed(po.getFailed());
        vo.setPassed(po.getPassed());
        vo.setConfigList(Transform.toParam(po.getConfigDetail()));
        vo.setCaseList(Transform.toParam(po.getCaseDetail()));
        return vo;
    }

    public static TestSuitePO transformVO2PO(TestSuiteVO vo) {
        if (vo == null) {
            return null;
        }
        TestSuitePO po = new TestSuitePO();
        po.setDescription(vo.getDescription());
        po.setId(vo.getTestSuiteId());
        po.setTitle(vo.getTitle());
        po.setFailed(vo.getFailed());
        po.setPassed(vo.getPassed());
        po.setConfigDetail(Transform.toParam(vo.getConfigList()));
        po.setCaseDetail(Transform.toParam(vo.getCaseList()));
        return po;
    }

}
