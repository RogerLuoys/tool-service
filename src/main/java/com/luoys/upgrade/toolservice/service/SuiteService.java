package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.AutoSuiteMapper;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoSuite;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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
        int result = autoSuiteMapper.insert(TransformAutoSuite.transformVO2PO(autoSuiteVO));
        return result == 1;
    }

    /**
     * 删除单个测试集
     * @param suiteId 测试集主键id
     * @return 成功为true，失败为false
     */
    public Boolean remove(Integer suiteId) {
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
     * @param suiteId 测试集主键id
     * @return 测试集对象
     */
    public AutoSuiteVO queryDetail(Integer suiteId) {
        return TransformAutoSuite.transformPO2VO(autoSuiteMapper.selectById(suiteId));
    }

    public void use(AutoSuiteVO autoSuiteVO) {
        List<CaseDTO> caseList = autoSuiteVO.getCaseList();
        order(caseList);
        for (CaseDTO caseDTO: caseList) {
            execute(caseDTO);
        }
    }

    public void order(List<CaseDTO> caseList) {
        //todo 排序
        quickOrder(caseList, 0, caseList.size());
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

    public Boolean execute(CaseDTO caseDTO) {
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
