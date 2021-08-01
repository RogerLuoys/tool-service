package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.web.vo.TestCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.TestCaseVO;
import com.luoys.upgrade.toolservice.dao.po.TestCasePO;

import java.util.ArrayList;
import java.util.List;

public class TransformTestCase {

    public static TestCaseSimpleVO transformPO2SimpleVO(TestCasePO po) {
        if (po == null) {
            return null;
        }
        TestCaseSimpleVO vo = new TestCaseSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setTitle(po.getTitle());
        vo.setTestCaseId(po.getId());
        vo.setStatus(po.getStatus());
        return vo;
    }

    public static List<TestCaseSimpleVO> transformPO2SimpleVO(List<TestCasePO> poList) {
        List<TestCaseSimpleVO> voList = new ArrayList<>();
        for (TestCasePO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static TestCaseVO transformPO2VO(TestCasePO po) {
        if (po == null) {
            return null;
        }
        TestCaseVO vo = new TestCaseVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setTestCaseId(po.getId());
        vo.setOwnerName(po.getOwnerName());
        vo.setTitle(po.getTitle());
        vo.setStatus(po.getStatus());
//        vo.setPreStepList(Transform.toParam(po.getPreDetail()));
//        vo.setMainStepList(Transform.toParam(po.getMainDetail()));
//        vo.setAfterStepList(Transform.toParam(po.getAfterDetail()));
        return vo;
    }

    public static TestCasePO transformVO2PO(TestCaseVO vo) {
        if (vo == null) {
            return null;
        }
        TestCasePO po = new TestCasePO();
        po.setDescription(vo.getDescription());
        po.setOwnerId(vo.getOwnerId());
        po.setId(vo.getTestCaseId());
        po.setOwnerName(vo.getOwnerName());
        po.setTitle(vo.getTitle());
        po.setStatus(vo.getStatus());
//        po.setPreDetail(Transform.toParam(vo.getPreStepList()));
//        po.setMainDetail(Transform.toParam(vo.getMainStepList()));
//        po.setAfterDetail(Transform.toParam(vo.getAfterStepList()));
        return po;
    }

}
