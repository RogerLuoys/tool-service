package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.dao.AutoSuiteMapper;
import com.luoys.upgrade.toolservice.dao.SuiteCaseRelationMapper;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoSuite;
import com.luoys.upgrade.toolservice.service.transform.TransformSuiteCaseRelation;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteVO;
import com.luoys.upgrade.toolservice.web.vo.SuiteCaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@Slf4j
@Service
public class SuiteService {

    @Value("${tool.node.isMain}")
    private String test;

    @Autowired
    private AutoSuiteMapper autoSuiteMapper;

    @Autowired
    private SuiteCaseRelationMapper suiteCaseRelationMapper;

    @Autowired
    private CaseService caseService;

    /**
     * 新增单个测试集
     * @param autoSuiteVO 测试集对象
     * @return 成功为true，失败为false
     */
    public Boolean create(AutoSuiteVO autoSuiteVO) {
        if (StringUtil.isBlank(autoSuiteVO.getName())) {
            log.error("--->测试集名字必填：{}", autoSuiteVO);
            return false;
        }
        autoSuiteVO.setSuiteId(NumberSender.createSuiteId());
        int result = autoSuiteMapper.insert(TransformAutoSuite.transformVO2PO(autoSuiteVO));
        return result == 1;
    }

    /**
     * 快速新增测试集，必填项自动填入默认值
     * @param autoSuiteVO 测试集对象
     * @return 成功为true，失败为false
     */
    public Boolean quickCreate(AutoSuiteVO autoSuiteVO) {
        if (StringUtil.isBlank(autoSuiteVO.getName())) {
            log.error("--->测试集名字必填：{}", autoSuiteVO);
            return false;
        }
        autoSuiteVO.setSuiteId(NumberSender.createSuiteId());
        int result = autoSuiteMapper.insert(TransformAutoSuite.transformVO2PO(autoSuiteVO));
        return result == 1;
    }

    /**
     * 新增测试集关联的用例
     * @param suiteCaseVO 用例对象
     * @return 成功为true，失败为false
     */
    public Boolean createRelatedCase(SuiteCaseVO suiteCaseVO) {
        if (suiteCaseVO.getSequence() == null) {
            suiteCaseVO.setSequence(KeywordEnum.DEFAULT_CASE_SEQUENCE.getCode());
        }
        int result = suiteCaseRelationMapper.insert(TransformSuiteCaseRelation.transformVO2SimplePO(suiteCaseVO));
        return result == 1;
    }

    /**
     * 删除单个测试集
     * @param suiteId 测试集业务id
     * @return 成功为true，失败为false
     */
    public Boolean remove(String suiteId) {
        // 删除关联的用例
        suiteCaseRelationMapper.removeBySuiteId(suiteId);
        // 删除测试集
        int result = autoSuiteMapper.remove(suiteId);
        return result == 1;
    }

    /**
     * 删除测试集关联的用例
     * @param suiteCaseVO 用例对象
     * @return 成功为true，失败为false
     */
    public Boolean removeRelatedCase(SuiteCaseVO suiteCaseVO) {
        int result = suiteCaseRelationMapper.remove(TransformSuiteCaseRelation.transformVO2SimplePO(suiteCaseVO));
        return result == 1;
    }

    /**
     * 更新单个测试集的基本信息
     * @param autoSuiteVO 测试集对象
     * @return 成功为true，失败为false
     */
    public Boolean update(AutoSuiteVO autoSuiteVO) {
        int result = autoSuiteMapper.update(TransformAutoSuite.transformVO2PO(autoSuiteVO));
        return result == 1;
    }

    /**
     * 更新测试集关联的用例
     * @param suiteCaseVO 用例对象
     * @return 成功为true，失败为false
     */
    public Boolean updateRelatedCase(SuiteCaseVO suiteCaseVO) {
        int result = suiteCaseRelationMapper.update(TransformSuiteCaseRelation.transformVO2SimplePO(suiteCaseVO));
        return result == 1;
    }

    /**
     * 查询测试集列表
     * @param name 名字，可空
     * @param pageIndex 页码
     * @return 测试集列表
     */
    public List<AutoSuiteSimpleVO> query(String name, String ownerId, Integer pageIndex) {
        return TransformAutoSuite.transformPO2SimpleVO(autoSuiteMapper.list(name, ownerId, pageIndex-1));
    }

    /**
     * 查询测试集详情
     * @param suiteId 测试集业务id
     * @return 测试集对象
     */
    public AutoSuiteVO queryDetail(String suiteId) {
        // 查询基本信息
        AutoSuiteVO autoSuiteVO = TransformAutoSuite.transformPO2VO(autoSuiteMapper.selectByUUID(suiteId));
        // 查询用例列表
        List<SuiteCaseVO> caseList = TransformSuiteCaseRelation.transformPO2SimpleVO(suiteCaseRelationMapper.listCaseBySuiteId(suiteId));
        autoSuiteVO.setCaseList(caseList);
        return autoSuiteVO;
    }

    /**
     * 执行测试集（异步模式）
     * @param autoSuiteVO -
     * @return -
     */
    public Boolean useAsync(AutoSuiteVO autoSuiteVO) throws RejectedExecutionException {
        if (autoSuiteVO == null || autoSuiteVO.getCaseList() == null || autoSuiteVO.getCaseList().size() == 0) {
            log.error("--->执行测试集入参异常：{}", autoSuiteVO);
            return false;
        }
        // 先将上次执行结果清零
        autoSuiteMapper.updateResult(autoSuiteVO.getSuiteId(), 0, 0);
        List<SuiteCaseVO> caseList = autoSuiteVO.getCaseList();
        Map<Integer, List<SuiteCaseVO>> casesMap = orderAndSort(caseList);
        List<SuiteCaseVO> uiCaseList = casesMap.get(AutoCaseTypeEnum.UI_CASE.getCode());
        List<SuiteCaseVO> apiCaseList = casesMap.get(AutoCaseTypeEnum.INTERFACE_CASE.getCode());
        if (apiCaseList.size() != 0) {
            ThreadPoolUtil.execute(new Runnable() {
                @Override
                public void run() {
                    Map<String, Integer> result = execute(apiCaseList);
                    autoSuiteMapper.updateResult(autoSuiteVO.getSuiteId(), result.get("passed"), result.get("failed"));
                }
            });
        }
        if (uiCaseList.size() != 0) {
            ThreadPoolUtil.executeUI(new Runnable() {
                @Override
                public void run() {
                    Map<String, Integer> result = execute(uiCaseList);
                    autoSuiteMapper.updateResult(autoSuiteVO.getSuiteId(), result.get("passed"), result.get("failed"));
                }
            });
        }
//        try {
//
//        } catch (Exception e) {
//            log.error("--->执行测试集异常", e);
//            return false;
//        }
        return true;
    }

    /**
     * 按执行顺序排序，并将接口用例与UI用例区分开
     * @param caseList -
     * @return 包含两条数据的Map对象
     */
    private Map<Integer, List<SuiteCaseVO>> orderAndSort(List<SuiteCaseVO> caseList) {
        quickOrder(caseList, 0, caseList.size());
        Map<Integer, List<SuiteCaseVO>> casesMap = new HashMap<>();
        List<SuiteCaseVO> uiCaseList = new ArrayList<>();
        List<SuiteCaseVO> apiCaseList = new ArrayList<>();
        for (SuiteCaseVO suiteCaseVO : caseList) {
            if (suiteCaseVO.getAutoCase().getType().equals(AutoCaseTypeEnum.INTERFACE_CASE.getCode())) {
                apiCaseList.add(suiteCaseVO);
            } else if (suiteCaseVO.getAutoCase().getType().equals(AutoCaseTypeEnum.UI_CASE.getCode())) {
                uiCaseList.add(suiteCaseVO);
            } else {
                log.error("--->测试集中的未知用例类型：caseId={}", suiteCaseVO.getAutoCase().getCaseId());
            }
        }
        casesMap.put(AutoCaseTypeEnum.UI_CASE.getCode(), uiCaseList);
        casesMap.put(AutoCaseTypeEnum.INTERFACE_CASE.getCode(), apiCaseList);
        return casesMap;
    }

    /**
     * 快速排序
     * @param targetList 需要排序的列表
     * @param start 从0开始
     * @param end 列表长度
     */
    private void quickOrder(List<SuiteCaseVO> targetList, int start, int end) {
        int low = start;
        int high = end;
        SuiteCaseVO flag = targetList.get(start);
        while (low < high) {
            while (low <= high && targetList.get(high).getSequence() >= flag.getSequence()) {
                high--;
            }
            if (low < high) {
                targetList.set(low, targetList.get(high));
                low++;
            }
            while (low < high && targetList.get(low).getSequence() < flag.getSequence()) {
                low++;
            }
            if (low < high) {
                targetList.set(high, targetList.get(low));
                high--;
            }
        }
        targetList.set(low, flag);
        quickOrder(targetList, 0, low - 1);
        quickOrder(targetList, low + 1, targetList.size());
    }

    private Map<String, Integer> execute(List<SuiteCaseVO> caseList) {
        int passed = 0;
        int failed = 0;
        for (SuiteCaseVO vo : caseList) {
            try {
                // 先通过caseId查到用例详情，再执行用例
                if (caseService.use(caseService.queryDetail(vo.getAutoCase().getCaseId()))) {
                    passed++;
                } else {
                    failed++;
                }
            } catch (Exception e) {
                log.error("--->批量执行用例异常：caseId={}", vo.getAutoCase().getCaseId(), e);
                failed++;
            }
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

}
