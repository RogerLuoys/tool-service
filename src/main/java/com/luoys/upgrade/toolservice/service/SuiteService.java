package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.dao.AutoSuiteMapper;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoSuite;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SuiteService {

    @Value("${tool.node.isMain}")
    private String test;

    @Autowired
    private AutoSuiteMapper autoSuiteMapper;

    @Autowired
    private CaseService caseService;

    /**
     * 新增单个测试集
     * @param autoSuiteVO 测试集对象
     * @return 成功为true，失败为false
     */
    public Boolean create(AutoSuiteVO autoSuiteVO) {
        autoSuiteVO.setSuiteId(NumberSender.createSuiteId());
        int result = autoSuiteMapper.insert(TransformAutoSuite.transformVO2PO(autoSuiteVO));
        return result == 1;
    }

    /**
     * 删除单个测试集
     * @param suiteId 测试集业务id
     * @return 成功为true，失败为false
     */
    public Boolean remove(String suiteId) {
        int result = autoSuiteMapper.remove(suiteId);
        return result == 1;
    }

    /**
     * 更新单个测试集
     * @param autoSuiteVO 测试集对象
     * @return 成功为true，失败为false
     */
    public Boolean update(AutoSuiteVO autoSuiteVO) {
        int result = autoSuiteMapper.update(TransformAutoSuite.transformVO2PO(autoSuiteVO));
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
        return TransformAutoSuite.transformPO2VO(autoSuiteMapper.selectByUUID(suiteId));
    }

    public Boolean useAsync(AutoSuiteVO autoSuiteVO) {
        List<CaseDTO> caseList = autoSuiteVO.getCaseList();
        Map<Integer, List<CaseDTO>> casesMap = orderAndSort(caseList);
        List<CaseDTO> uiCaseList = casesMap.get(AutoCaseTypeEnum.UI_CASE.getCode());
        List<CaseDTO> apiCaseList = casesMap.get(AutoCaseTypeEnum.INTERFACE_CASE.getCode());
        try {
            if (apiCaseList.size() != 0) {
                ThreadPoolUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        execute(apiCaseList);
                    }
                });
            }
            if (uiCaseList.size() != 0) {
                ThreadPoolUtil.executeUI(new Runnable() {
                    @Override
                    public void run() {
                        execute(uiCaseList);
                    }
                });
            }
        } catch (Exception e) {
            log.error("--->执行测试集异常", e);
            return false;
        }
        return true;
    }

//    public void use(AutoSuiteVO autoSuiteVO) {
//        List<CaseDTO> caseList = autoSuiteVO.getCaseList();
//        order(caseList);
//        for (CaseDTO caseDTO: caseList) {
//            execute(caseDTO);
//        }
//    }

    public Map<Integer, List<CaseDTO>> orderAndSort(List<CaseDTO> caseList) {
        quickOrder(caseList, 0, caseList.size());
        Map<Integer, List<CaseDTO>> casesMap = new HashMap<>();
        List<CaseDTO> uiCaseList = new ArrayList<>();
        List<CaseDTO> apiCaseList = new ArrayList<>();
        for (CaseDTO caseDTO : caseList) {
            if (caseDTO.getType().equals(AutoCaseTypeEnum.INTERFACE_CASE.getCode())) {
                apiCaseList.add(caseDTO);
            } else if (caseDTO.getType().equals(AutoCaseTypeEnum.UI_CASE.getCode())) {
                uiCaseList.add(caseDTO);
            } else {
                log.error("--->测试集中的未知用例类型：caseId={}", caseDTO.getCaseId());
            }
        }
        casesMap.put(AutoCaseTypeEnum.UI_CASE.getCode(), uiCaseList);
        casesMap.put(AutoCaseTypeEnum.INTERFACE_CASE.getCode(), apiCaseList);
        return casesMap;
    }

    /**
     * 快速排序
     * @param targetList
     * @param start
     * @param end
     */
    private void quickOrder(List<CaseDTO> targetList, int start, int end) {
        int low = start;
        int high = end;
        CaseDTO flag = targetList.get(start);
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

    private Boolean execute(List<CaseDTO> caseList) {
        return false;
    }

    private Boolean execute(CaseDTO caseDTO) {
        Boolean result;
        try {
            result = caseService.use(caseService.queryDetail(caseDTO.getCaseId()));
        } catch (Exception e) {
            log.error("---->执行用例异常：caseId={}", caseDTO.getCaseId());
            result = false;
        }
        return result;
    }

}
