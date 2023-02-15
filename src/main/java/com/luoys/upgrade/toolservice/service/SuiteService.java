package com.luoys.upgrade.toolservice.service;

import com.google.common.collect.Lists;
import com.luoys.upgrade.toolservice.dao.*;
import com.luoys.upgrade.toolservice.dao.po.*;
import com.luoys.upgrade.toolservice.service.automation.AutoExecutor;
import com.luoys.upgrade.toolservice.service.common.HttpUtil;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.dto.SlaveDTO;
import com.luoys.upgrade.toolservice.service.dto.SuiteDTO;
import com.luoys.upgrade.toolservice.service.enums.*;
import com.luoys.upgrade.toolservice.service.transform.*;
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
    private ResourceSuiteRelationMapper resourceSuiteRelationMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private CaseService caseService;

    /**
     * 新增单个套件
     *
     * @param autoSuiteVO 套件对象
     * @return 返回id
     */
    public Integer create(AutoSuiteVO autoSuiteVO) {
        if (StringUtil.isBlank(autoSuiteVO.getName())) {
            log.error("--->套件名字必填：{}", autoSuiteVO);
            return -1;
        }
//        if (autoSuiteVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode().toString())) {
//            autoSuiteVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
//        } else {
//            String userName = userMapper.selectById(autoSuiteVO.getOwnerId()).getUsername();
//            autoSuiteVO.setOwnerName(userName);
//        }
//        autoSuiteVO.setSuiteId(NumberSender.createSuiteId());
        AutoSuitePO autoSuitePO = TransformAutoSuite.transformVO2PO(autoSuiteVO);
        autoSuiteMapper.insert(autoSuitePO);
        return autoSuitePO.getId();
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
        autoSuiteVO.setSlaveType(AutoSuiteSlaveTypeEnum.ANY_SLAVE.getCode());
        AutoSuitePO autoSuitePO = TransformAutoSuite.transformVO2PO(autoSuiteVO);
        autoSuiteMapper.insert(autoSuitePO);
        return autoSuitePO.getId();
    }

    /**
     * 为套件指定执行机器
     *
     * @param suiteId -
     * @return 返回id
     */
    public Integer createRelateSlave(Integer suiteId, Integer resourceId) {
        ResourceSuiteRelationPO resourceSuiteRelationPO = new ResourceSuiteRelationPO();
        resourceSuiteRelationPO.setSuiteId(suiteId);
        resourceSuiteRelationPO.setResourceId(resourceId);
        resourceSuiteRelationPO.setType(ResourceSuiteTypeEnum.SUITE_SLAVE.getCode());
        resourceSuiteRelationMapper.insert(resourceSuiteRelationPO);
        return resourceSuiteRelationPO.getId();
    }

    /**
     * 套件关联单个用例
     *
     * @param suiteCaseVO 用例对象
     * @return 返回id
     */
    public Integer createRelatedCase(SuiteCaseVO suiteCaseVO) {
        if (suiteCaseVO.getSequence() == null) {
            suiteCaseVO.setSequence(DefaultEnum.DEFAULT_CASE_SEQUENCE.getCode());
        }
        if (suiteCaseVO.getStatus() == null) {
            suiteCaseVO.setStatus(AutoCaseStatusEnum.PLANNING.getCode());
        }
        SuiteCaseRelationPO suiteCaseRelationPO = TransformSuiteCaseRelation.transformVO2SimplePO(suiteCaseVO);
        suiteCaseRelationMapper.insert(suiteCaseRelationPO);
        // 更新关联的总用例数
        if (suiteCaseRelationPO.getId() != null) {
            int total = suiteCaseRelationMapper.countBySuiteId(suiteCaseVO.getSuiteId(), null);
            autoSuiteMapper.updateTotal(suiteCaseVO.getSuiteId(), total);
        }
        return suiteCaseRelationPO.getId();
    }

    /**
     * 套件关联批量用例
     *
     * @param suiteId 套件业务id
     * @param userId  用例所属人
     * @param name    用例名称
     * @return 返回关联数量
     */
    public Integer createRelatedCase(Integer suiteId, Integer userId, String name) {
        List<SuiteCaseRelationPO> relateCase = new ArrayList<>();
        for (AutoCaseSimpleVO vo : queryAll(suiteId, userId, name)) {
            SuiteCaseRelationPO po = new SuiteCaseRelationPO();
            po.setCaseId(vo.getCaseId());
            po.setSequence(DefaultEnum.DEFAULT_CASE_SEQUENCE.getCode());
            po.setStatus(AutoCaseStatusEnum.PLANNING.getCode());
            po.setSuiteId(suiteId);
            relateCase.add(po);
        }
        if (relateCase.size() == 0) {
            log.error("--->关联用例时，列表不能为空：suiteId={}", suiteId);
            return 0;
        }
        int result = suiteCaseRelationMapper.batchInsert(relateCase);
        // 更新关联的总用例数
        if (result > 0) {
            int total = suiteCaseRelationMapper.countBySuiteId(suiteId, null);
            autoSuiteMapper.updateTotal(suiteId, total);
        }
        return result;
    }

    /**
     * 删除单个套件
     *
     * @param suiteId 套件业务id
     * @return 成功为1
     */
    public Integer remove(Integer suiteId) {
        // 删除关联的用例
        suiteCaseRelationMapper.removeBySuiteId(suiteId);
        // 删除套件
        return autoSuiteMapper.remove(suiteId);
    }

    /**
     * 删除套件关联的用例
     *
     * @param suiteId 套件id
     * @param caseId  用例id
     * @return 成功为删除数量
     */
    public Integer removeRelatedCase(Integer suiteId, Integer caseId) {
        int result = suiteCaseRelationMapper.remove(suiteId, caseId);
        // 删除后更新总条数
        if (result > 0) {
            int total = suiteCaseRelationMapper.countBySuiteId(suiteId, null);
            autoSuiteMapper.updateTotal(suiteId, total);
        }
        return result;
    }

    /**
     * 删除套件关联的用例
     *
     * @param suiteId 套件id
     * @param resourceId  用例id
     * @return 成功1
     */
    public Integer removeRelatedSlave(Integer suiteId, Integer resourceId) {
        return resourceSuiteRelationMapper.remove(resourceId, suiteId, 1);
    }

    /**
     * 更新单个套件的基本信息
     *
     * @param autoSuiteVO 套件对象
     * @return 成功为1
     */
    public Integer update(AutoSuiteVO autoSuiteVO) {
        AutoSuitePO autoSuitePO = TransformAutoSuite.transformVO2PO(autoSuiteVO);
        return autoSuiteMapper.update(autoSuitePO);
    }

    /**
     * 更新套件关联的用例
     *
     * @param suiteCaseVO 用例对象
     * @return 成功为1
     */
    public Integer updateRelatedCase(SuiteCaseVO suiteCaseVO) {
        SuiteCaseRelationPO suiteCaseRelationPO = TransformSuiteCaseRelation.transformVO2SimplePO(suiteCaseVO);
        return suiteCaseRelationMapper.update(suiteCaseRelationPO);
    }

    /**
     * 重置测试套件的执行状态
     *
     * @param suiteId 套件id
     * @return 重置成功为1
     */
    public Integer reset(Integer suiteId) {
        return autoSuiteMapper.updateStatus(suiteId, AutoSuiteStatusEnum.SUITE_FREE.getCode());
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

//    /**
//     * 查询套件详情，
//     * 分页查询套件关联的用例
//     *
//     * @param suiteId    套件业务id
//     * @param startIndex 套件业务id
//     * @return 套件对象
//     */
//    public AutoSuiteVO queryDetail(Integer suiteId, Integer startIndex) {
//        return queryDetail(suiteId, startIndex, null);
//    }

    /**
     * 查询套件详情
     * 只查询到了用例基本信息，用例关联的步骤详情未查询
     *
     * @param suiteId 套件业务id
     * @return 套件对象
     */
    public AutoSuiteVO queryDetail(Integer suiteId) {
        // 查询套件基本信息
        AutoSuiteVO autoSuiteVO = TransformAutoSuite.transformPO2VO(autoSuiteMapper.selectById(suiteId));
        // 查询套件指定的机器
        List<ResourceSuiteRelationPO> resourceSuiteRelationPOList = resourceSuiteRelationMapper.selectBySuiteId(autoSuiteVO.getSuiteId(), ResourceSuiteTypeEnum.SUITE_SLAVE.getCode());
        autoSuiteVO.setSlaveList(TransformResourceSuiteRelation.transformPO2DTO(resourceSuiteRelationPOList));
        // 查询套件关联的用例列表
        QueryVO queryVO = new QueryVO();
        queryVO.setSuiteId(suiteId);
        queryVO.setPageIndex(1);
        autoSuiteVO.setRelatedCase(this.queryRelateCase(queryVO));
        return autoSuiteVO;
    }

    /**
     * 根据条件查找套件关联的用例
     * @param queryVO -
     * @return 用例列表
     */
    public PageInfo<SuiteCaseVO> queryRelateCase(QueryVO queryVO) {
        // 查询套件关联的用例列表
        List<SuiteCaseVO> caseList = TransformSuiteCaseRelation.transformPO2SimpleVO(
                suiteCaseRelationMapper.listCaseBySuiteId(queryVO.getSuiteId(), queryVO.getPageIndex() == null ? null : ((queryVO.getPageIndex() - 1) * 10), queryVO.getName()));
        PageInfo<SuiteCaseVO> pageInfo = new PageInfo<>();
        pageInfo.setList(caseList);
        pageInfo.setTotal(suiteCaseRelationMapper.countBySuiteId(queryVO.getSuiteId(), null));
        return pageInfo;
    }

    private List<SuiteCaseVO> getCaseBeExecuted(Integer suiteId, Boolean retry) {
        // 查询套件关联的所有用例
        List<SuiteCaseVO> caseList = TransformSuiteCaseRelation.transformPO2SimpleVO(
                suiteCaseRelationMapper.listCaseBySuiteId(suiteId, null, null));
        if (caseList.size() == 0) {
            log.error("--->套件内未找到关联的用例：suiteId={}", suiteId);
            return caseList;
        }
        if (retry != null && retry) {
            // 如果是批量手动重试，则去掉已成功的用例
            caseList = caseList.stream().filter(suiteCaseVO -> !suiteCaseVO.getStatus().equals(AutoCaseStatusEnum.SUCCESS.getCode())).collect(Collectors.toList());
            if (caseList.size() == 0) {
                log.info("--->套件内所有用例已执行成功，无需重试：suiteId={}", suiteId);
                return caseList;
            }
        } else {
            // 如果非重试，则将套件上次的所有执行结果重置
            autoSuiteMapper.updateResult(suiteId, 0, 0);
            suiteCaseRelationMapper.resetStatusBySuiteId(suiteId);
        }
        // 用例按sequence正序
        caseList = caseList.stream().sorted(Comparator.comparing(SuiteCaseVO::getSequence)).collect(Collectors.toList());
        return caseList;
    }

    /**
     * 执行套件中的批量用例（异步模式）
     *
     * @param suiteId 套件业务id
     * @param retry   重试，true只执行不通过部分用例，false或null执行全部
     * @return true只代表唤起执行操作成功
     */
    public Boolean executeByLocal(Integer suiteId, Boolean retry) throws RejectedExecutionException {
        List<SuiteCaseVO> caseList = this.getCaseBeExecuted(suiteId, retry);
        autoSuiteMapper.updateStatus(suiteId, AutoSuiteStatusEnum.SUITE_RUNNING.getCode());
        // 使用线程池执行用例，并更新结果
        ThreadPoolUtil.executeAuto(() -> {
            try {
                // 执行所有用例
                execute(caseList);
            } finally {
                // 更新套件状态
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
     * 执行套件中的批量用例（异步模式）
     *
     * @param autoSuiteVO 套件业务id
     * @return true只代表唤起执行操作成功
     */
    public Boolean executeBySchedule(AutoSuiteVO autoSuiteVO) throws RejectedExecutionException {
        // 查询要执行的所有用例
        List<SuiteCaseVO> caseList = this.getCaseBeExecuted(autoSuiteVO.getSuiteId(), autoSuiteVO.getRetry());
        if (caseList.size() == 0) {
            return false;
        }
        SuiteDTO suiteDTO = TransformAutoSuite.transformVO2DTO(autoSuiteVO);
        // 开始调度
        if (autoSuiteVO.getSlaveType().equals(AutoSuiteSlaveTypeEnum.ASSIGN_SLAVE.getCode())) {
            // 指定机器
            List<SlaveDTO> slaveList = autoSuiteVO.getSlaveList();
            if (slaveList == null || slaveList.size() == 0) {
                return false;
            }
            List<List<SuiteCaseVO>> runList = Lists.partition(caseList, slaveList.size());
            // 将要执行的用例分批放入不同的机器中执行
            for (int i = 0; i < runList.size(); i++) {
                suiteDTO.setRelatedCase(runList.get(i));
                suiteDTO.setResourceId(slaveList.get(i).getResourceId());
                log.info("--->调用http接口执行套件：url={}", slaveList.get(i).getUrl());
                HttpUtil.scheduleRun(slaveList.get(i).getUrl(), suiteDTO);
            }
        } else if (autoSuiteVO.getSlaveType().equals(AutoSuiteSlaveTypeEnum.ANY_SLAVE.getCode())) {
            // 随机机器
            List<ResourceVO> resourceVOList = TransformResource.transformPO2VO(resourceMapper.listPublic(autoSuiteVO.getProjectId()));
            for (ResourceVO resourceVO : resourceVOList) {
                suiteDTO.setRelatedCase(caseList);
                suiteDTO.setResourceId(resourceVO.getResourceId());
                log.info("--->调用http接口执行套件：url={}", resourceVO.getSlave().getUrl());
                Boolean result = HttpUtil.scheduleRun(resourceVO.getSlave().getUrl(), suiteDTO);
                if (result) {
                    return true;
                }
            }
            autoSuiteMapper.updateStatus(autoSuiteVO.getSuiteId(), AutoSuiteStatusEnum.SUITE_PENDING.getCode());
            return false;
        }
        return true;
    }

    /**
     * 执行套件中的批量用例（异步模式）
     *
     * @param suiteDTO 套件业务id
     * @return true只代表唤起执行操作成功
     */
    public Boolean scheduleRun(SuiteDTO suiteDTO) throws RejectedExecutionException {
        List<SuiteCaseVO> caseList = suiteDTO.getRelatedCase();
        // 使用线程池执行用例，并更新结果
        ThreadPoolUtil.executeAuto(() -> {
            try {
                log.info("--->套件开始执行: suiteId={}", suiteDTO.getSuiteId());
                // 把套件状态更新为执行中
                autoSuiteMapper.updateStatus(suiteDTO.getSuiteId(), AutoSuiteStatusEnum.SUITE_RUNNING.getCode());
                ResourceSuiteRelationPO resourceSuiteRelationPO = new ResourceSuiteRelationPO();
                resourceSuiteRelationPO.setResourceId(suiteDTO.getResourceId());
                resourceSuiteRelationPO.setSuiteId(suiteDTO.getSuiteId());
                resourceSuiteRelationPO.setType(ResourceSuiteTypeEnum.SLAVE_USAGE.getCode());
                // 新增机器使用记录
                resourceSuiteRelationMapper.insert(resourceSuiteRelationPO);
                // 执行所有用例，后更新结果
                execute(caseList);
            } finally {
                // 先删除机器使用记录
                resourceSuiteRelationMapper.remove(suiteDTO.getResourceId(), suiteDTO.getSuiteId(), ResourceSuiteTypeEnum.SLAVE_USAGE.getCode());
                if (resourceSuiteRelationMapper.selectBySuiteId(suiteDTO.getSuiteId(), ResourceSuiteTypeEnum.SLAVE_USAGE.getCode()).size() == 0) {
                    // 套件没有在机器中执行的记录，说明全部执行完成
                    autoSuiteMapper.updateStatus(suiteDTO.getSuiteId(), AutoSuiteStatusEnum.SUITE_FREE.getCode());
                }
                // 全量更新套件执行结果
                autoSuiteMapper.updateResult(suiteDTO.getSuiteId(),
                        suiteCaseRelationMapper.countBySuiteId(suiteDTO.getSuiteId(), AutoCaseStatusEnum.SUCCESS.getCode()),
                        suiteCaseRelationMapper.countBySuiteId(suiteDTO.getSuiteId(), AutoCaseStatusEnum.FAIL.getCode()));
                log.info("--->套件执行结束: suiteId={}", suiteDTO.getSuiteId());
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
    public Boolean executeBySingle(SuiteCaseVO suiteCaseVO) throws RejectedExecutionException {
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

    /**
     * 批量执行用例，并更新套件中的用例状态
     *
     * @param caseList 用例列表，列表对象中需要有caseId和suiteId
     * @return 用例执行结果已写入形参
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
