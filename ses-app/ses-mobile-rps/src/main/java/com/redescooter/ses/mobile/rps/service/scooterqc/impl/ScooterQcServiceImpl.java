package com.redescooter.ses.mobile.rps.service.scooterqc.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyEventEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.scooterqc.ScooterQcServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.BussinessNumberService;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterQcService;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScooterQcServiceImpl implements ScooterQcService {

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAssemblyBOrderService opeAssemblyBOrderService;

    @Autowired
    private OpeAssemblyBQcService opeAssemblyBQcService;

    @Autowired
    private OpeAssemblyQcItemService opeAssemblyQcItemService;

    @Autowired
    private OpeAssemblyQcTraceService opeAssemblyQcTraceService;

    @Autowired
    private OpeProductQcTemplateBService opeProductQcTemplateBService;

    @Autowired
    private OpeProductQcTemplateService opeProductQcTemplateService;

    @Autowired
    private ScooterQcServiceMapper scooterQcServiceMapper;

    @Autowired
    private ReceiptTraceService receiptTraceService;

    @Autowired
    private OpeAssemblyLotTraceService opeAssemblyLotTraceService;

    @Autowired
    private BussinessNumberService bussinessNumberService;

    @Reference
    private IdAppService idAppService;

    /**
     * @Author kyle
     * @Description //查询整车质检列表并展示
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @Transactional
    @Override
    public PageResult<ScooterQcOneResult> scooterQcList(PageEnter enter) {
        int count = scooterQcServiceMapper.scooterQcListCount();
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ScooterQcOneResult> scooterQcOneResultList = new ArrayList<>();

        // opeAssemblyOrderService对应的数据库表为空的时候直接返回
        QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_STATUS, AssemblyStatusEnums.QC.getValue());
        opeAssemblyOrderQueryWrapper.isNotNull(OpeAssemblyOrder.COL_ID);
        // 待质检项不为0
        opeAssemblyOrderQueryWrapper.gt(OpeAssemblyOrder.COL_LAVE_WAIT_QC_TOTAL, 0);
        List<OpeAssemblyOrder> opeAssemblyOrderList = opeAssemblyOrderService.list(opeAssemblyOrderQueryWrapper);
        if (CollectionUtils.isEmpty(opeAssemblyOrderList)) {
            return PageResult.createZeroRowResult(enter);
        }
        for (OpeAssemblyOrder opeAssemblyOrder : opeAssemblyOrderList) {
            scooterQcOneResultList.add(ScooterQcOneResult.builder()
                    .id(opeAssemblyOrder.getId())
                    .assemblyNum(opeAssemblyOrder.getAssemblyNumber())
                    .checkTime(opeAssemblyOrder.getCreatedTime())
                    .waitQcNum(opeAssemblyOrder.getLaveWaitQcTotal())
                    .build());
        }
        return PageResult.create(enter, count, scooterQcOneResultList);
    }

    /**
     * @Author kyle
     * @Description //根据组装单id查询到对应的部件详情列表
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @Transactional
    @Override
    public PageResult<ScooterQcPartResult> partListById(ScooterQcIdEnter enter) {
        int count = scooterQcServiceMapper.partListByIdCount();
        ScooterQcPartResult scooterQcPartResult = null;
        List<ScooterQcPartResult> scooterQcPartResultList = new ArrayList<>();
        // opeAssemblyBOrderService对应的数据库表为空的时候直接返回

        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
            // 通过组装单子表id查找
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ASSEMBLY_ID, enter.getId());
            List<OpeAssemblyBOrder> opeAssemblyBOrderList =
                    opeAssemblyBOrderService.list(opeAssemblyBOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyBOrderList)) {
                for (OpeAssemblyBOrder opeAssemblyBOrder : opeAssemblyBOrderList) {
                    scooterQcPartResultList.add(scooterQcPartResult = ScooterQcPartResult.builder()
                            .assemblyBId(opeAssemblyBOrder.getId()).productId(opeAssemblyBOrder.getProductId())
                            .productNum(opeAssemblyBOrder.getLaveWaitQcQty())
                            .productStr(opeAssemblyBOrder.getProductNumber()).productName(opeAssemblyBOrder.getEnName())
                            .build());
                }
            } else {
                return PageResult.createZeroRowResult(enter);
            }
        }
        return PageResult.create(enter, count, scooterQcPartResultList);
    }

    /**
     * @Author kyle
     * @Description //根据组装单id和部件id查询到对应的具体部件质检详情列表
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @Transactional
    @Override
    public List<ScooterQcItemResult> scooterQcItem(ScooterQcIdItemEnter enter) {
        // 先查询组组装单是否存在
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);
        // 抛组装子单为空异常
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }
        // 查询质检项
        List<OpeProductQcTemplate> productQcTemplateList =
                opeProductQcTemplateService.list(new LambdaQueryWrapper<OpeProductQcTemplate>()
                        .eq(OpeProductQcTemplate::getProductId, opeAssemblyBOrder.getProductId()));
        if (CollectionUtils.isEmpty(productQcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_QC_TEMPLETE.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_NOT_QC_TEMPLETE.getMessage());
        }
        // 查询结果集
        List<OpeProductQcTemplateB> productQcTemplateBList = opeProductQcTemplateBService
                .list(new LambdaQueryWrapper<OpeProductQcTemplateB>().in(OpeProductQcTemplateB::getProductQcTemplateId,
                        productQcTemplateList.stream().map(OpeProductQcTemplate::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(productQcTemplateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_QC_RESULT.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_NOT_QC_RESULT.getMessage());
        }

        // 封装数据返回
        List<ScooterQcItemResult> resultList = new ArrayList<>();
        productQcTemplateList.forEach(item -> {
            List<ScooterQcItemOptionResult> qcResult = new ArrayList<>();
            productQcTemplateBList.forEach(qc -> {
                if (item.getId().equals(qc.getProductQcTemplateId())) {
                    qcResult.add(ScooterQcItemOptionResult.builder().id(qc.getId()).optionNum(qc.getResultsSequence())
                            .qcResult(qc.getQcResult()).uploadFlag(qc.getUploadFlag()).build());
                }
            });
            resultList.add(ScooterQcItemResult.builder().id(opeAssemblyBOrder.getId()).qcName(item.getQcItemName())
                    .qcTemplateId(item.getId()).scooterQcItemOptionResultList(qcResult).build());
        });
        return resultList;
    }

    /**
     * @Author kyle
     * @Description //提交部件质检选项详情列表修改
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @Transactional
    @Override
    public ScooterQcResidueNumResult setScooterQcItem(ScooterQcItemEnter enter) {
        // 返回的结果集
        List<ScooterQcItemOptionEnter> qcItemOptionEnterList = null;
        try {
            qcItemOptionEnterList =
                    JSON.parseArray(enter.getScooterQcItemOptionEnter(), ScooterQcItemOptionEnter.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),
                    ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        if (CollectionUtils.isEmpty(qcItemOptionEnterList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),
                    ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        // 组装单子单
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);

        // 抛组装子单为空异常
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        // 获取组装单
        QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, opeAssemblyBOrder.getAssemblyId());
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);

        // 抛组装单为空异常
        if (StringUtils.isEmpty(opeAssemblyOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(),
                    ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }

        //主订单状态判断
        if (!org.apache.commons.lang3.StringUtils.equals(opeAssemblyOrder.getStatus(),
                AssemblyStatusEnums.QC.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(),
                    ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        //质检数量判断
        if (opeAssemblyBOrder.getLaveWaitQcQty() == 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());
        }

        // 待质检总数异常
        if ((opeAssemblyBOrder.getLaveWaitQcQty() - 1 < 0)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(),
                    ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
        }

        // 质检记录集合
        List<OpeAssemblyQcTrace> opeAssemblyQcTraceList = new ArrayList<>();

        // 判断是否有质检项质检失败
        boolean qcOptionFlag = true;

        // 本次质检的批次号
        IdEnter idEnter = new IdEnter();
        BeanUtils.copyProperties(enter, idEnter);
        idEnter.setId(opeAssemblyOrder.getId());
        String batchNum = bussinessNumberService.assemblyBatchNo(idEnter);

        // 本次质检的序列号
        idEnter.setId(opeAssemblyBOrder.getId());
        String serialNum = bussinessNumberService.productSerialN(idEnter);

        for (ScooterQcItemOptionEnter scooterQcItemOptionEnter : qcItemOptionEnterList) {
            // 获取质检模板
            QueryWrapper<OpeProductQcTemplate> opeProductQcTemplateQueryWrapper = new QueryWrapper<>();
            opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID,
                    opeAssemblyBOrder.getProductId());
            OpeProductQcTemplate opeProductQcTemplate =
                    opeProductQcTemplateService.getOne(opeProductQcTemplateQueryWrapper);

            // 判断详细质检信息是否为空
            if (StringUtils.isEmpty(opeProductQcTemplate)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
            }

            // 查询质检模板结果项
            QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
            opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_ID,
                    scooterQcItemOptionEnter.getQcResultId());
            opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID,
                    opeProductQcTemplate.getId());
            OpeProductQcTemplateB opeProductQcTemplateB =
                    opeProductQcTemplateBService.getOne(opeProductQcTemplateBQueryWrapper);

            // 判断详细质检信息是否为空
            if (StringUtils.isEmpty(opeProductQcTemplateB)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
            }

            if (!opeProductQcTemplateB.getPassFlag()) {
                // 如果有质检失败的质检项，本次质检则失败
                qcOptionFlag = false;
            }

            // 质检Trace
            opeAssemblyQcTraceList.add(OpeAssemblyQcTrace.builder()
                    .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_QC_TRACE))
                    .dr(0)
                    .productQcTemplateBId(opeProductQcTemplateB.getId())
                    .productQcTemplateBName(opeProductQcTemplateB.getQcResult())
                    .productQcTemplateId(opeProductQcTemplate.getId())
                    .productQcTemplateName(opeProductQcTemplate.getQcItemName())
                    .opeAssemblyBId(opeAssemblyBOrder.getId())
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .revision(0)
                    .picture(scooterQcItemOptionEnter.getImageUrl())
                    .createdTime(new Date())
                    .build());
        }

        // 质检批次记录
        OpeAssemblyLotTrace opeAssemblyLotTrace = null;
        QueryWrapper<OpeAssemblyLotTrace> opeAssemblyLotTraceQueryWrapper = new QueryWrapper<>();
        opeAssemblyLotTraceQueryWrapper.eq(OpeAssemblyLotTrace.COL_BATCH_NO, batchNum);
        opeAssemblyLotTrace = opeAssemblyLotTraceService.getOne(opeAssemblyLotTraceQueryWrapper);
        if (!StringUtils.isEmpty(opeAssemblyLotTrace)) {
            opeAssemblyLotTrace.setPassCount(
                    qcOptionFlag ? opeAssemblyLotTrace.getPassCount() + 1 : opeAssemblyLotTrace.getPassCount());
            opeAssemblyLotTrace.setFailCount(
                    qcOptionFlag ? opeAssemblyLotTrace.getFailCount() : opeAssemblyLotTrace.getFailCount() + 1);
            opeAssemblyLotTrace.setTotalQualityInspected(opeAssemblyLotTrace.getTotalQualityInspected() + 1);
            opeAssemblyLotTrace.setUpdatedBy(enter.getUserId());
            opeAssemblyLotTrace.setUpdatedTime(new Date());

        } else {
            // 本次质检的质检批次记录
            opeAssemblyLotTrace =
                    OpeAssemblyLotTrace.builder()
                            .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_LOT_TRACE))
                            .dr(0)
                            .batchNo(batchNum)
                            .assemblyId(opeAssemblyOrder.getId())
                            .tenantId(enter.getTenantId())
                            .qualityInspectorId(enter.getUserId())
                            .userId(enter.getUserId())
                            .createdTime(new Date())
                            .createdBy(enter.getUserId())
                            .updatedTime(new Date())
                            .updatedBy(enter.getUserId())
                            .totalQualityInspected(1) // 需要质检总数
                            .passCount(qcOptionFlag ? 1 : 0)
                            .failCount(qcOptionFlag ? 0 : 1)
                            .build();
        }

        // 查找对应的质检记录是否存在
        OpeAssemblyBQc opeAssemblyBQc = null;
        QueryWrapper<OpeAssemblyBQc> opeAssemblyBQcQueryWrapper = new QueryWrapper<>();
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_ASSEMBLY_B_ID, enter.getId());
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_BATCH_NO, batchNum);
        opeAssemblyBQc = opeAssemblyBQcService.getOne(opeAssemblyBQcQueryWrapper);

        if (StringUtils.isEmpty(opeAssemblyBQc)) {
            // 质检记录
            opeAssemblyBQc = OpeAssemblyBQc.builder()
                    .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_B_QC))
                    .dr(0)
                    .batchNo(batchNum)
                    .assemblyBId(opeAssemblyBOrder.getId())
                    .tenantId(enter.getTenantId())
                    .userId(enter.getUserId())
                    .qualityInspectionTime(new Date())
                    .createdTime(new Date())
                    .createdBy(enter.getUserId())
                    .updatedTime(new Date())
                    .updatedBy(enter.getUserId())
                    .productId(opeAssemblyBOrder.getProductId())
                    .status(qcOptionFlag ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue())
                    .qualityInspectorId(enter.getUserId())
                    .totalQualityInspected(1)
                    .passCount(qcOptionFlag ? 1 : 0)
                    .failCount(0)
                    .build();
        } else {
            opeAssemblyBQc.setStatus(qcOptionFlag ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue());
            opeAssemblyBQc
                    .setPassCount(qcOptionFlag ? opeAssemblyBQc.getPassCount() + 1 : opeAssemblyBQc.getPassCount());
            opeAssemblyBQc.setTotalQualityInspected(opeAssemblyBQc.getTotalQualityInspected() + 1);
        }

        // 质检Item
        OpeAssemblyQcItem opeAssemblyQcItem = OpeAssemblyQcItem.builder()
                .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_QC_ITEM))
                .dr(0)
                .serialNum(serialNum)
                .assemblyBId(opeAssemblyBOrder.getId())
                .assemblyId(opeAssemblyOrder.getId())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .assemblyBQcId(opeAssemblyBQc.getId())
                .assemblyLotId(qcOptionFlag ?opeAssemblyLotTrace.getId():null)
                .productId(opeAssemblyBOrder.getProductId())
                .qcResult(qcOptionFlag ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .batchNo(batchNum)
                .build();

        // 质检结果批量插入批次表id和质检记录id
        for (OpeAssemblyQcTrace opeAssemblyQcTrace : opeAssemblyQcTraceList) {
            opeAssemblyQcTrace.setAssemblyQcItemId(opeAssemblyQcItem.getId());
        }

        // 修改组装单和子单状态标记
        boolean orderFlag = false;

        // 把质检成功的产品对应的组装单和组装单子单的待质检数量进行修改
        // 修改组装单的总待质检数量
        if ((opeAssemblyOrder.getLaveWaitQcTotal() != null) && (opeAssemblyBOrder.getLaveWaitQcQty() != null)) {
            if (qcOptionFlag) {
                // 修改组装单子单的待质检数
                opeAssemblyBOrder.setLaveWaitQcQty(opeAssemblyBOrder.getLaveWaitQcQty() - 1);
                // 修改组装单的待质检数
                opeAssemblyOrder.setLaveWaitQcTotal(opeAssemblyOrder.getLaveWaitQcTotal() - 1);
            }
            if (opeAssemblyOrder.getLaveWaitQcTotal() < 0 || opeAssemblyBOrder.getLaveWaitQcQty() < 0) {
                // 待质检总数错误
                throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(),
                        ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
            }

            // 判断组装单和子单节点是否发生改变
            if (opeAssemblyOrder.getLaveWaitQcTotal() == 0) {
                opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());

                // 订单状态改变，产生节点表
                SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
                BeanUtils.copyProperties(enter, saveNodeEnter);
                saveNodeEnter.setId(opeAssemblyOrder.getId());
                saveNodeEnter.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());
                saveNodeEnter.setEvent(AssemblyEventEnums.QC_PASSED.getValue());
                saveNodeEnter.setMemo(null);
                receiptTraceService.saveAssemblyNode(saveNodeEnter);

            } else {
                opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC.getValue());
            }
            if (opeAssemblyBOrder.getLaveWaitQcQty() == 0) {
                opeAssemblyBOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());
                orderFlag = true;
            } else {
                opeAssemblyBOrder.setStatus(AssemblyStatusEnums.QC.getValue());
            }
        } else {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(),
                    ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
        }

        // 保存每个质检项的质检Trace集合
        opeAssemblyQcTraceService.saveBatch(opeAssemblyQcTraceList);
        // 保存质检批次记录
        opeAssemblyLotTraceService.saveOrUpdate(opeAssemblyLotTrace);
        // 保存质检详情记录
        opeAssemblyQcItemService.save(opeAssemblyQcItem);
        // 修改组装单子单的状态
        opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
        // 保存质检批次记录
        opeAssemblyBQcService.saveOrUpdate(opeAssemblyBQc);
        // 保存组装单
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        // 返回结果集
        return ScooterQcResidueNumResult.builder().laveQcTotal(opeAssemblyOrder.getLaveWaitQcTotal()).result(orderFlag ? Boolean.TRUE : Boolean.FALSE).build();
    }

    /**
     * 检查车辆是否已经质检过
     *
     * @param enter
     * @return
     */
    @Override
    public CheckScooterSerilaNResult checkScooterSerilaN(CheckScooterSerilaNEnter enter) {
        OpePartsProduct opePartsProduct = opePartsProductService.getById(enter.getProductId());
        if (opePartsProduct == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }
        OpeAssemblyQcItem opeAssemblyQcItem = opeAssemblyQcItemService.getOne(new LambdaQueryWrapper<OpeAssemblyQcItem>()
                .eq(OpeAssemblyQcItem::getSerialNum, enter.getSerialN())
                .eq(OpeAssemblyQcItem::getQcResult, QcStatusEnums.PASS.getValue())
        );
        return CheckScooterSerilaNResult.builder().whetherQc(opeAssemblyQcItem == null ? Boolean.TRUE:Boolean.FALSE).build();
    }
}
