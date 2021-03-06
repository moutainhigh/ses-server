package com.redescooter.ses.mobile.rps.service.scooterqc.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyEventEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.scooterqc.ScooterQcServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBOrder;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBQc;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyLotTrace;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcItem;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcTrace;
import com.redescooter.ses.mobile.rps.dm.OpePartsProduct;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssembly;
import com.redescooter.ses.mobile.rps.dm.OpeProductQcTemplate;
import com.redescooter.ses.mobile.rps.dm.OpeProductQcTemplateB;
import com.redescooter.ses.mobile.rps.dm.OpeStock;
import com.redescooter.ses.mobile.rps.dm.OpeWhse;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.BussinessNumberService;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyBOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyBQcService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyLotTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyQcItemService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyQcTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsProductService;
import com.redescooter.ses.mobile.rps.service.base.OpeProductAssemblyService;
import com.redescooter.ses.mobile.rps.service.base.OpeProductQcTemplateBService;
import com.redescooter.ses.mobile.rps.service.base.OpeProductQcTemplateService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockService;
import com.redescooter.ses.mobile.rps.service.base.OpeWhseService;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterQcService;
import com.redescooter.ses.mobile.rps.vo.scooterqc.CheckScooterSerilaNEnter;
import com.redescooter.ses.mobile.rps.vo.scooterqc.CheckScooterSerilaNResult;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcIdEnter;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcIdItemEnter;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemEnter;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemOptionEnter;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemOptionResult;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemResult;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcOneResult;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcPartResult;
import com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcResidueNumResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeProductAssemblyService opeProductAssemblyService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * @Author kyle
     * @Description //?????????????????????????????????
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public PageResult<ScooterQcOneResult> scooterQcList(PageEnter enter) {
        int count = scooterQcServiceMapper.scooterQcListCount();
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ScooterQcOneResult> scooterQcOneResultList = new ArrayList<>();

        // opeAssemblyOrderService????????????????????????????????????????????????
        QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_STATUS, AssemblyStatusEnums.QC.getValue());
        opeAssemblyOrderQueryWrapper.isNotNull(OpeAssemblyOrder.COL_ID);
        // ??????????????????0
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
     * @Description //???????????????id????????????????????????????????????
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public PageResult<ScooterQcPartResult> partListById(ScooterQcIdEnter enter) {
        int count = scooterQcServiceMapper.partListByIdCount();
        ScooterQcPartResult scooterQcPartResult = null;
        List<ScooterQcPartResult> scooterQcPartResultList = new ArrayList<>();
        // opeAssemblyBOrderService????????????????????????????????????????????????

        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
            // ?????????????????????id??????
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ASSEMBLY_ID, enter.getId());
            List<OpeAssemblyBOrder> opeAssemblyBOrderList =
                    opeAssemblyBOrderService.list(opeAssemblyBOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyBOrderList)) {
                for (OpeAssemblyBOrder opeAssemblyBOrder : opeAssemblyBOrderList) {
                    scooterQcPartResultList.add(ScooterQcPartResult.builder()
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
     * @Description //???????????????id?????????id????????????????????????????????????????????????
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public List<ScooterQcItemResult> scooterQcItem(ScooterQcIdItemEnter enter) {
        // ?????????????????????????????????
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);
        // ???????????????????????????
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }
        // ???????????????
        List<OpeProductQcTemplate> productQcTemplateList =
                opeProductQcTemplateService.list(new LambdaQueryWrapper<OpeProductQcTemplate>()
                        .eq(OpeProductQcTemplate::getProductId, opeAssemblyBOrder.getProductId()));
        if (CollectionUtils.isEmpty(productQcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_QC_TEMPLETE.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_NOT_QC_TEMPLETE.getMessage());
        }
        // ???????????????
        List<OpeProductQcTemplateB> productQcTemplateBList = opeProductQcTemplateBService
                .list(new LambdaQueryWrapper<OpeProductQcTemplateB>().in(OpeProductQcTemplateB::getProductQcTemplateId,
                        productQcTemplateList.stream().map(OpeProductQcTemplate::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(productQcTemplateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_QC_RESULT.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_NOT_QC_RESULT.getMessage());
        }

        // ??????????????????
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
     * @Description //??????????????????????????????????????????
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public ScooterQcResidueNumResult setScooterQcItem(ScooterQcItemEnter enter) {
        // ??????????????????
        List<ScooterQcItemOptionEnter> qcItemOptionEnterList = null;
        try {
            qcItemOptionEnterList = JSON.parseArray(enter.getScooterQcItemOptionEnter(), ScooterQcItemOptionEnter.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        if (CollectionUtils.isEmpty(qcItemOptionEnterList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        //??????????????????
//        OpeProductAssembly opeProductAssembly = opeProductAssemblyService.getOne(new LambdaQueryWrapper<OpeProductAssembly>().eq(OpeProductAssembly::getProductSerialNum, enter.getProductSerialNum
//        ()));
//        if (opeProductAssembly == null) {
//            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getMessage());
//        }

        // ???????????????
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);

        // ???????????????????????????
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        // ???????????????
        QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, opeAssemblyBOrder.getAssemblyId());
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);

        // ????????????????????????
        if (StringUtils.isEmpty(opeAssemblyOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }

        //?????????????????????
        if (!org.apache.commons.lang3.StringUtils.equals(opeAssemblyOrder.getStatus(), AssemblyStatusEnums.QC.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        //??????????????????
        if (opeAssemblyBOrder.getLaveWaitQcQty() == 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_NEED_QC.getMessage());
        }

        // ?????????????????????
        if ((opeAssemblyBOrder.getLaveWaitQcQty() - 1 < 0)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
        }

        // ??????????????????
        List<OpeAssemblyQcTrace> opeAssemblyQcTraceList = new ArrayList<>();

        // ????????????????????????????????????
        boolean qcOptionFlag = true;

        // ????????????????????????
        IdEnter idEnter = new IdEnter();
        BeanUtils.copyProperties(enter, idEnter);
        idEnter.setId(opeAssemblyOrder.getId());
        String batchNum = bussinessNumberService.assemblyBatchNo(idEnter);


        //todo bill??????????????? ??????????????? ???????????????????????????
        String serialNum = null;

        //?????????????????????????????????
        List<OpeProductAssembly> opeProductAssemblyList = opeProductAssemblyService.list(new LambdaQueryWrapper<OpeProductAssembly>().eq(OpeProductAssembly::getAssemblyBId, enter.getId()));
        if (CollectionUtils.isEmpty(opeProductAssemblyList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_ASSEMBLY_TRACE_IS_NOT_EXIST.getMessage());
        }
        //?????????????????????????????????
        List<OpeAssemblyQcItem> opeAssemblyQcItemList = opeAssemblyQcItemService.list(new LambdaQueryWrapper<OpeAssemblyQcItem>().eq(OpeAssemblyQcItem::getAssemblyBId, enter.getId()));
        List<String> assemblySerialNum = opeProductAssemblyList.stream().map(OpeProductAssembly::getProductSerialNum).collect(Collectors.toList());
        List<String> assemblyQcSerialNum = opeAssemblyQcItemList.stream().map(OpeAssemblyQcItem::getSerialNum).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(opeProductAssemblyList)) {
            for (String item : assemblyQcSerialNum) {
                assemblySerialNum.remove(item);
            }
        }
        serialNum = assemblySerialNum.get(0);


        for (ScooterQcItemOptionEnter scooterQcItemOptionEnter : qcItemOptionEnterList) {
            // ??????????????????
            QueryWrapper<OpeProductQcTemplate> opeProductQcTemplateQueryWrapper = new QueryWrapper<>();
            opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID, opeAssemblyBOrder.getProductId());
            OpeProductQcTemplate opeProductQcTemplate = opeProductQcTemplateService.getOne(opeProductQcTemplateQueryWrapper);

            // ????????????????????????????????????
            if (StringUtils.isEmpty(opeProductQcTemplate)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
            }

            // ???????????????????????????
            QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
            opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_ID, scooterQcItemOptionEnter.getQcResultId());
            opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, opeProductQcTemplate.getId());
            OpeProductQcTemplateB opeProductQcTemplateB = opeProductQcTemplateBService.getOne(opeProductQcTemplateBQueryWrapper);

            // ????????????????????????????????????
            if (StringUtils.isEmpty(opeProductQcTemplateB)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
            }

            if (!opeProductQcTemplateB.getPassFlag()) {
                // ?????????????????????????????????????????????????????????
                qcOptionFlag = false;
            }

            // ??????Trace
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

        // ??????????????????
        OpeAssemblyLotTrace opeAssemblyLotTrace = null;
        QueryWrapper<OpeAssemblyLotTrace> opeAssemblyLotTraceQueryWrapper = new QueryWrapper<>();
        opeAssemblyLotTraceQueryWrapper.eq(OpeAssemblyLotTrace.COL_BATCH_NO, batchNum);
        opeAssemblyLotTrace = opeAssemblyLotTraceService.getOne(opeAssemblyLotTraceQueryWrapper);
        if (!StringUtils.isEmpty(opeAssemblyLotTrace)) {
            opeAssemblyLotTrace.setPassCount(qcOptionFlag ? opeAssemblyLotTrace.getPassCount() + 1 : opeAssemblyLotTrace.getPassCount());
            opeAssemblyLotTrace.setFailCount(qcOptionFlag ? opeAssemblyLotTrace.getFailCount() : opeAssemblyLotTrace.getFailCount() + 1);
            opeAssemblyLotTrace.setTotalQualityInspected(opeAssemblyLotTrace.getTotalQualityInspected() + 1);
            opeAssemblyLotTrace.setUpdatedBy(enter.getUserId());
            opeAssemblyLotTrace.setUpdatedTime(new Date());

        } else {
            // ?????????????????????????????????
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
                            .totalQualityInspected(1) // ??????????????????
                            .passCount(qcOptionFlag ? 1 : 0)
                            .failCount(qcOptionFlag ? 0 : 1)
                            .build();
        }

        // ???????????????????????????????????????
        OpeAssemblyBQc opeAssemblyBQc = null;
        QueryWrapper<OpeAssemblyBQc> opeAssemblyBQcQueryWrapper = new QueryWrapper<>();
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_ASSEMBLY_B_ID, enter.getId());
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_BATCH_NO, batchNum);
        opeAssemblyBQc = opeAssemblyBQcService.getOne(opeAssemblyBQcQueryWrapper);

        if (StringUtils.isEmpty(opeAssemblyBQc)) {
            // ????????????
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
            opeAssemblyBQc.setPassCount(qcOptionFlag ? opeAssemblyBQc.getPassCount() + 1 : opeAssemblyBQc.getPassCount());
            opeAssemblyBQc.setTotalQualityInspected(opeAssemblyBQc.getTotalQualityInspected() + 1);
        }

        // ??????Item
        OpeAssemblyQcItem opeAssemblyQcItem = OpeAssemblyQcItem.builder()
                .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_QC_ITEM))
                .dr(0)
                .serialNum(serialNum)
                .assemblyBId(opeAssemblyBOrder.getId())
                .assemblyId(opeAssemblyOrder.getId())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .assemblyBQcId(opeAssemblyBQc.getId())
                .assemblyLotId(qcOptionFlag ? opeAssemblyLotTrace.getId() : null)
                .productId(opeAssemblyBOrder.getProductId())
                .qcResult(qcOptionFlag ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .batchNo(batchNum)
                .build();

        // ?????????????????????????????????id???????????????id
        for (OpeAssemblyQcTrace opeAssemblyQcTrace : opeAssemblyQcTraceList) {
            opeAssemblyQcTrace.setAssemblyQcItemId(opeAssemblyQcItem.getId());
        }

        // ????????????????????????????????????
        boolean orderFlag = false;

        // ??????????????????????????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????
        if ((opeAssemblyOrder.getLaveWaitQcTotal() != null) && (opeAssemblyBOrder.getLaveWaitQcQty() != null)) {
            if (qcOptionFlag) {
                // ????????????????????????????????????
                opeAssemblyBOrder.setLaveWaitQcQty(opeAssemblyBOrder.getLaveWaitQcQty() - 1);
                // ??????????????????????????????
                opeAssemblyOrder.setLaveWaitQcTotal(opeAssemblyOrder.getLaveWaitQcTotal() - 1);

                //???????????????
                toBeStoredFillingPoint(opeAssemblyBOrder.getProductId(), opeAssemblyBOrder.getAssemblyQty());
            }
            if (opeAssemblyOrder.getLaveWaitQcTotal() < 0 || opeAssemblyBOrder.getLaveWaitQcQty() < 0) {
                // ?????????????????????
                throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
            }

            // ????????????????????????????????????????????????
            if (opeAssemblyOrder.getLaveWaitQcTotal() == 0) {
                opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());

                // ????????????????????????????????????
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
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
        }

        // ??????????????????????????????Trace??????
        opeAssemblyQcTraceService.saveBatch(opeAssemblyQcTraceList);
        // ????????????????????????
        opeAssemblyLotTraceService.saveOrUpdate(opeAssemblyLotTrace);
        // ????????????????????????
        opeAssemblyQcItemService.save(opeAssemblyQcItem);
        // ??????????????????????????????
        opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
        // ????????????????????????
        opeAssemblyBQcService.saveOrUpdate(opeAssemblyBQc);
        // ???????????????
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        // ???????????????
        return ScooterQcResidueNumResult.builder().laveQcTotal(opeAssemblyOrder.getLaveWaitQcTotal()).result(qcOptionFlag ? Boolean.TRUE : Boolean.FALSE).build();
    }

    /**
     * ?????????????????????????????????
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
        return CheckScooterSerilaNResult.builder().whetherQc(opeAssemblyQcItem == null ? Boolean.TRUE : Boolean.FALSE).build();
    }


    /**
     * ???????????????
     *
     * @param productId
     */
    private void toBeStoredFillingPoint(Long productId, int qty) {
        //????????????
        OpeWhse whse = opeWhseService.getOne(new LambdaQueryWrapper<OpeWhse>().eq(OpeWhse::getType, WhseTypeEnums.ASSEMBLY.getValue()));
        if (whse == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        OpePartsProduct opePartsProduct = opePartsProductService.getById(productId);
        if (opePartsProduct == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        //???????????????????????????
        OpeStock opeStock = opeStockService.getOne(new LambdaQueryWrapper<OpeStock>().eq(OpeStock::getWhseId, whse.getId())
                .eq(OpeStock::getMaterielProductId, productId)
                .eq(OpeStock::getMaterielProductType, BomCommonTypeEnums.SCOOTER.getValue()));

        if (opeStock == null) {
            opeStock = buildStock(whse, opePartsProduct, 1);
        }else {
            //????????????
            opeStock.setWaitStoredTotal(opeStock.getWaitStoredTotal() + 1);
            opeStock.setUpdatedTime(new Date());
        }
        //????????????
        opeStockService.saveOrUpdate(opeStock);
    }

    /**
     * ?????? stock ??????
     *
     * @param whse
     * @param product
     * @param qty
     * @return
     */
    private OpeStock buildStock(OpeWhse whse, OpePartsProduct product, int qty) {
        OpeStock opeStock = OpeStock.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK))
                .dr(0)
                .userId(0L)
                .tenantId(0L)
                .whseId(whse.getId())
                .intTotal(0)
                .availableTotal(0)
                .outTotal(0)
                .wornTotal(0)
                .lockTotal(0)
                .waitProductTotal(0)
                .waitStoredTotal(qty)
                .materielProductId(product.getId())
                .materielProductName(product.getProductNumber())
                .materielProductType(BomCommonTypeEnums.SCOOTER.getValue())
                .revision(0)
                .updatedBy(0L)
                .updatedTime(new Date())
                .createdBy(0L)
                .createdTime(new Date())
                .build();
        return opeStock;
    }
}
