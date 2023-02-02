package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.po.*;
import com.luoys.upgrade.toolservice.service.automation.AutoExecutor;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.dao.AutoCaseMapper;
import com.luoys.upgrade.toolservice.dao.AutoSuiteMapper;
import com.luoys.upgrade.toolservice.dao.SuiteCaseRelationMapper;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseStatusEnum;
import com.luoys.upgrade.toolservice.service.enums.AutoSuiteStatusEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoCase;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoSuite;
import com.luoys.upgrade.toolservice.service.transform.TransformSuiteCaseRelation;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

/**
 * 套件服务，包含自动化套件相关的所有业务逻辑
 *
 * @author luoys
 */
@Slf4j
@Service
public class SuiteService {

    @Autowired
    private AutoSuiteMapper autoSuiteMapper;

    @Autowired
    private AutoCaseMapper autoCaseMapper;

    @Autowired
    private SuiteCaseRelationMapper suiteCaseRelationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CaseService caseService;

    /**
     * 新增单个套件
     *
     * @param autoSuiteVO 套件对象
     * @return 成功为true，失败为false
     */
    public Boolean create(AutoSuiteVO autoSuiteVO) {
        if (StringUtil.isBlank(autoSuiteVO.getName())) {
            log.error("--->套件名字必填：{}", autoSuiteVO);
            return false;
        }
        if (autoSuiteVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode().toString())) {
            autoSuiteVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
        } else {
            String userName = userMapper.selectById(autoSuiteVO.getOwnerId()).getUsername();
            autoSuiteVO.setOwnerName(userName);
        }
//        autoSuiteVO.setSuiteId(NumberSender.createSuiteId());
        int result = autoSuiteMapper.insert(TransformAutoSuite.transformVO2PO(autoSuiteVO));
        return result == 1;
    }

    /**
     * 快速新增套件，必填项自动填入默认值
     *
     * @param autoSuiteVO 套件对象
     * @return 成功为suiteId，失败为null
     */
    public Integer quickCreate(AutoSuiteVO autoSuiteVO) {
        if (StringUtil.isBlank(autoSuiteVO.getName())) {
            log.error("--->套件名字必填：{}", autoSuiteVO);
            return null;
        }
        autoSuiteVO.setStatus(AutoSuiteStatusEnum.SUITE_FREE.getCode());
        AutoSuitePO autoSuitePO = TransformAutoSuite.transformVO2PO(autoSuiteVO);
        autoSuiteMapper.insert(autoSuitePO);
        return autoSuitePO.getId();
    }

    /**
     * 套件关联单个用例
     *
     * @param suiteCaseVO 用例对象
     * @return 成功为true，失败为false
     */
    public Boolean createRelatedCase(SuiteCaseVO suiteCaseVO) {
        if (suiteCaseVO.getSequence() == null) {
            suiteCaseVO.setSequence(KeywordEnum.DEFAULT_CASE_SEQUENCE.getCode());
        }
        if (suiteCaseVO.getStatus() == null) {
            suiteCaseVO.setStatus(AutoCaseStatusEnum.PLANNING.getCode());
        }
        int result = suiteCaseRelationMapper.insert(TransformSuiteCaseRelation.transformVO2SimplePO(suiteCaseVO));
        // 更新关联的总用例数
        if (result == 1) {
            int total = suiteCaseRelationMapper.countBySuiteId(suiteCaseVO.getSuiteId(), null);
            autoSuiteMapper.updateTotal(suiteCaseVO.getSuiteId(), total);
        }
        return result == 1;
    }

    /**
     * 套件关联批量用例
     *
     * @param suiteId 套件业务id
     * @param userId  用例所属人
     * @param name    用例名称
     * @return 成功为true
     */
    public Boolean createRelatedCase(Integer suiteId, Integer userId, String name) {
        List<SuiteCaseRelationPO> relateCase = new ArrayList<>();
        for (AutoCaseSimpleVO vo : queryAll(suiteId, userId, name)) {
            SuiteCaseRelationPO po = new SuiteCaseRelationPO();
            po.setCaseId(vo.getCaseId());
            po.setSequence(KeywordEnum.DEFAULT_CASE_SEQUENCE.getCode());
            po.setStatus(AutoCaseStatusEnum.PLANNING.getCode());
            po.setSuiteId(suiteId);
            relateCase.add(po);
        }
        if (relateCase.size() == 0) {
            log.error("--->关联用例时，列表不能为空：suiteId={}", suiteId);
            return false;
        }
        int result = suiteCaseRelationMapper.batchInsert(relateCase);
        // 更新关联的总用例数
        if (result > 0) {
            int total = suiteCaseRelationMapper.countBySuiteId(suiteId, null);
            autoSuiteMapper.updateTotal(suiteId, total);
        }
        return result > 0;
    }

    /**
     * 删除单个套件
     *
     * @param suiteId 套件业务id
     * @return 成功为true，失败为false
     */
    public Boolean remove(Integer suiteId) {
        // 删除关联的用例
        suiteCaseRelationMapper.removeBySuiteId(suiteId);
        // 删除套件
        int result = autoSuiteMapper.remove(suiteId);
        return result == 1;
    }

    /**
     * 删除套件关联的用例
     *
     * @param suiteId 套件id
     * @param caseId  用例id
     * @return 成功为true，失败为false
     */
    public Boolean removeRelatedCase(Integer suiteId, Integer caseId) {
        int result = suiteCaseRelationMapper.remove(suiteId, caseId);
        // 删除后更新总条数
        if (result > 0) {
            int total = suiteCaseRelationMapper.countBySuiteId(suiteId, null);
            autoSuiteMapper.updateTotal(suiteId, total);
        }
        return result > 1;
    }

    /**
     * 更新单个套件的基本信息
     *
     * @param autoSuiteVO 套件对象
     * @return 成功为true，失败为false
     */
    public Boolean update(AutoSuiteVO autoSuiteVO) {
        int result = autoSuiteMapper.update(TransformAutoSuite.transformVO2PO(autoSuiteVO));
        return result == 1;
    }

    /**
     * 更新套件关联的用例
     *
     * @param suiteCaseVO 用例对象
     * @return 成功为true，失败为false
     */
    public Boolean updateRelatedCase(SuiteCaseVO suiteCaseVO) {
        int result = suiteCaseRelationMapper.update(TransformSuiteCaseRelation.transformVO2SimplePO(suiteCaseVO));
        return result == 1;
    }

    /**
     * 重置测试套件的执行状态
     *
     * @param suiteId 套件id
     * @return 重置成功为true
     */
    public Boolean reset(Integer suiteId) {
//        autoSuiteMapper.updateResult(suiteId, 0, 0);
        autoSuiteMapper.updateStatus(suiteId, AutoSuiteStatusEnum.SUITE_FREE.getCode());
//        suiteCaseRelationMapper.resetStatusBySuiteId(suiteId);
        return true;
    }

    /**
     * 查询套件列表
     *
     * @param queryVO  查询条件
     * @return 套件列表
     */
    public List<AutoSuiteSimpleVO> query(QueryVO queryVO) {
        AutoSuiteQueryPO autoSuiteQueryPO = TransformAutoSuite.transformVO2PO(queryVO);
        List<AutoSuitePO> autoSuitePOList = autoSuiteMapper.list(autoSuiteQueryPO);
        return TransformAutoSuite.transformPO2SimpleVO(autoSuitePOList);
    }

    /**
     * 查询套件总条数
     *
     * @param queryVO  查询条件
     * @return 套件总数
     */
    public Integer count(QueryVO queryVO) {
        AutoSuiteQueryPO autoSuiteQueryPO = TransformAutoSuite.transformVO2PO(queryVO);
        return autoSuiteMapper.count(autoSuiteQueryPO);
    }

    /**
     * 查询符合条件且尚未被套件添加过的所有用例
     *
     * @param suiteId 套件业务id
     * @param userId  用例所属人
     * @param name    用例名称
     * @return 用例列表
     */
    public List<AutoCaseSimpleVO> queryAll(Integer suiteId, Integer userId, String name) {
        AutoCaseQueryPO autoCaseQueryPO = new AutoCaseQueryPO();
        autoCaseQueryPO.setName(name);
        autoCaseQueryPO.setProjectId(1);//todo 要传
        List<AutoCasePO> allCase = autoCaseMapper.list(autoCaseQueryPO);
        List<SuiteCaseRelationPO> selectedCase = suiteCaseRelationMapper.listCaseBySuiteId(suiteId, null, null);
        List<AutoCasePO> selectableCase = new ArrayList<>();
        // 筛选出未添加过的用例
        boolean isExist;
        for (AutoCasePO po : allCase) {
            // 计划中的用例不添加
            if (po.getStatus().equals(AutoCaseStatusEnum.PLANNING.getCode())) {
                continue;
            }
            // 先判断用例是否已被关联，如果关联过，则标记为true
            isExist = false;
            for (SuiteCaseRelationPO selectedPO : selectedCase) {
                if (po.getId().equals(selectedPO.getCaseId())) {
                    isExist = true;
                    break;
                }
            }
            // 如果未关联，则加入可选择列表
            if (!isExist) {
                selectableCase.add(po);
            }
        }
        return TransformAutoCase.transformPO2SimpleVO(selectableCase);
    }

    /**
     * 查询套件详情，
     * 分页查询套件关联的用例
     *
     * @param suiteId    套件业务id
     * @param startIndex 套件业务id
     * @return 套件对象
     */
    public AutoSuiteVO queryDetail(Integer suiteId, Integer startIndex) {
        return queryDetail(suiteId, startIndex, null);
    }

    /**
     * 查询套件详情
     * 只查询到了用例基本信息，用例关联的步骤详情未查询
     *
     * @param suiteId 套件业务id
     * @return 套件对象
     */
    private AutoSuiteVO queryDetail(Integer suiteId, Integer startIndex, Boolean retry) {
        if (retry == null || !retry) {
            retry = null;
        }
        // 查询基本信息
        AutoSuiteVO autoSuiteVO = TransformAutoSuite.transformPO2VO(autoSuiteMapper.selectByUUID(suiteId));
        // 查询用例列表
        List<SuiteCaseVO> caseList = TransformSuiteCaseRelation.transformPO2SimpleVO(
                suiteCaseRelationMapper.listCaseBySuiteId(suiteId, startIndex == null ? null : ((startIndex - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode()), retry));
        PageInfo<SuiteCaseVO> pageInfo = new PageInfo<>();
        pageInfo.setList(caseList);
        pageInfo.setTotal(suiteCaseRelationMapper.countBySuiteId(suiteId, null));
        autoSuiteVO.setRelatedCase(pageInfo);
        return autoSuiteVO;
    }

    /**
     * 执行套件中的批量用例（异步模式）
     *
     * @param suiteId 套件业务id
     * @param retry   重试，true只执行不通过部分用例，false或null执行全部
     * @return true只代表唤起执行操作成功
     */
    public Boolean useAsync(Integer suiteId, Boolean retry) throws RejectedExecutionException {
        // 全量查询套件详情，如果重试则只查询其中未通过用例
        AutoSuiteVO autoSuiteVO = queryDetail(suiteId, null, retry);
        // 套件只能同时执行一个，如果状态已是执行中，则直接退出
        if (autoSuiteVO.getStatus().equals(AutoSuiteStatusEnum.SUITE_RUNNING.getCode())) {
            log.error("--->套件内正在执行中：suiteId={}", suiteId);
            return false;
        }
        // 获取关联的所有用例，并按sequence正序
        List<SuiteCaseVO> caseList = autoSuiteVO.getRelatedCase().getList().stream().sorted(Comparator.comparing(SuiteCaseVO::getSequence)).collect(Collectors.toList());
        if (caseList.size() == 0) {
            log.error("--->套件内未找到关联的用例：suiteId={}", suiteId);
            return false;
        }
        // 如果非重试，则将套件上次的所有执行结果重置
        if (!retry) {
            autoSuiteMapper.updateResult(suiteId, 0, 0);
            suiteCaseRelationMapper.resetStatusBySuiteId(suiteId);
        }
        autoSuiteMapper.updateStatus(suiteId, AutoSuiteStatusEnum.SUITE_RUNNING.getCode());
        // 使用线程池执行用例，并更新结果
        ThreadPoolUtil.executeAuto(() -> {
            try {
                // 执行所有用例，后更新结果
                execute(caseList);
            } finally {
                autoSuiteMapper.updateStatus(suiteId, AutoSuiteStatusEnum.SUITE_FREE.getCode());
                // 全量更新套件执行结果
                autoSuiteMapper.updateResult(suiteId,
                        suiteCaseRelationMapper.countBySuiteId(suiteId, AutoCaseStatusEnum.SUCCESS.getCode()),
                        suiteCaseRelationMapper.countBySuiteId(suiteId, AutoCaseStatusEnum.FAIL.getCode()));
            }
        });
        return true;
    }

    /**
     * 执行套件中的单个用例（异步模式）
     *
     * @param suiteCaseVO 套件关联用例对象，需要suiteId，caseId
     * @return true只代表唤起执行操作成功
     */
    public Boolean useAsync(SuiteCaseVO suiteCaseVO) throws RejectedExecutionException {
        if (suiteCaseVO.getSuiteId() == null || suiteCaseVO.getAutoCase().getCaseId() == null) {
            log.error("--->执行套件中单个用例时，缺少关键数据");
            return false;
        }
        List<SuiteCaseVO> caseList = new ArrayList<>();
        caseList.add(suiteCaseVO);
        // 使用线程池执行用例，并更新结果
        ThreadPoolUtil.executeAuto(() -> {
            // 执行所有用例，后更新结果
            execute(caseList);
            // 全量更新套件执行结果
            autoSuiteMapper.updateResult(suiteCaseVO.getSuiteId(),
                    suiteCaseRelationMapper.countBySuiteId(suiteCaseVO.getSuiteId(), AutoCaseStatusEnum.SUCCESS.getCode()),
                    suiteCaseRelationMapper.countBySuiteId(suiteCaseVO.getSuiteId(), AutoCaseStatusEnum.FAIL.getCode()));
        });
        return true;
    }

//    /**
//     * 按执行顺序排序，并将接口用例与UI用例区分开
//     *
//     * @param caseList 用例列表
//     * @return 包含两条数据的Map对象
//     */
//    private Map<Integer, List<SuiteCaseVO>> orderAndSort(List<SuiteCaseVO> caseList) {
////        quickOrder(caseList, 0, caseList.size() - 1);
//        Map<Integer, List<SuiteCaseVO>> casesMap = new HashMap<>();
//        List<SuiteCaseVO> uiCaseList = new ArrayList<>();
//        List<SuiteCaseVO> apiCaseList = new ArrayList<>();
//        for (SuiteCaseVO suiteCaseVO : caseList) {
//            if (suiteCaseVO.getAutoCase().getType().equals(AutoCaseTypeEnum.INTERFACE_CASE.getCode())) {
//                apiCaseList.add(suiteCaseVO);
//            } else if (suiteCaseVO.getAutoCase().getType().equals(AutoCaseTypeEnum.UI_CASE.getCode())) {
//                uiCaseList.add(suiteCaseVO);
//            } else {
//                log.error("--->套件中的未知用例类型：caseId={}", suiteCaseVO.getAutoCase().getCaseId());
//            }
//        }
//        casesMap.put(AutoCaseTypeEnum.UI_CASE.getCode(), uiCaseList);
//        casesMap.put(AutoCaseTypeEnum.INTERFACE_CASE.getCode(), apiCaseList);
//        return casesMap;
//    }

    /**
     * 快速排序
     *
     * @param targetList 需要排序的列表
     * @param start      从0开始
     * @param end        列表长度-1
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

    /**
     * 批量执行用例，并更新套件中的用例状态
     *
     * @param caseList 用例列表，列表对象中需要有caseId和suiteId
     * @return 返回所执行用例的成功数和失败数
     */
    private void execute(List<SuiteCaseVO> caseList) {
        boolean result = false;
        AutoExecutor executor = new AutoExecutor();
        for (SuiteCaseVO vo : caseList) {
            try {
                // 先通过caseId查出用例详情，再设置执行参数，最后执行用例
                AutoCaseVO autoCaseVO = caseService.queryDetail(vo.getAutoCase().getCaseId());
                CaseDTO caseDTO = TransformAutoCase.transformVO2DTO(autoCaseVO);
                result = executor.executeCase(caseDTO);
            } catch (Exception e) {
                log.error("--->批量执行用例异常：caseId={}", vo.getAutoCase().getCaseId(), e);
                result = false;
            } finally {
                // 更新套件中关联用例的执行状态
                suiteCaseRelationMapper.updateStatus(vo.getSuiteId(), vo.getCaseId(),
                        result ? AutoCaseStatusEnum.SUCCESS.getCode() : AutoCaseStatusEnum.FAIL.getCode());
            }
        }
        // 所有用例执行完成后，关闭资源
        executor.close();
    }
}
