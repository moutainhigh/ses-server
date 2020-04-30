package com.redescooter.ses.mobile.rps.service.scooterqc.impl;

import com.alibaba.fastjson.JSON;
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
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterQcService;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScooterQcServiceImpl implements ScooterQcService {

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
        ScooterQcOneResult scooterQcOneResult = null;
        List<ScooterQcOneResult> scooterQcOneResultList = new ArrayList<>();

        //opeAssemblyOrderService对应的数据库表为空的时候直接返回
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
            opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_STATUS, AssemblyStatusEnums.QC.getValue());
            opeAssemblyOrderQueryWrapper.isNotNull(OpeAssemblyOrder.COL_ID);
            //待质检项不为0
            opeAssemblyOrderQueryWrapper.gt(OpeAssemblyOrder.COL_LAVE_WAIT_QC_TOTAL, 0);
            List<OpeAssemblyOrder> opeAssemblyOrderList = opeAssemblyOrderService.list(opeAssemblyOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyOrderList)) {
                for (OpeAssemblyOrder opeAssemblyOrder : opeAssemblyOrderList) {
                    scooterQcOneResultList.add(
                            scooterQcOneResult = ScooterQcOneResult.builder()
                                    .id(opeAssemblyOrder.getId())
                                    .assemblyNum(opeAssemblyOrder.getAssemblyNumber())
                                    .checkTime(opeAssemblyOrder.getCreatedTime())
                                    .waitQcNum(opeAssemblyOrder.getLaveWaitQcTotal())
                                    .build());
                }
            } else {
                return PageResult.createZeroRowResult(enter);
            }
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
        //opeAssemblyBOrderService对应的数据库表为空的时候直接返回

        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
            //通过组装单子表id查找
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ASSEMBLY_ID, enter.getId());
            List<OpeAssemblyBOrder> opeAssemblyBOrderList = opeAssemblyBOrderService.list(opeAssemblyBOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyBOrderList)) {
                for (OpeAssemblyBOrder opeAssemblyBOrder : opeAssemblyBOrderList) {
                    scooterQcPartResultList.add(
                            scooterQcPartResult = ScooterQcPartResult.builder()
                                    .assemblyBId(opeAssemblyBOrder.getId())
                                    .productId(opeAssemblyBOrder.getProductId())
                                    .productNum(opeAssemblyBOrder.getLaveWaitQcQty())
                                    .productStr(opeAssemblyBOrder.getProductNumber())
                                    .productName(opeAssemblyBOrder.getEnName())
                                    .build());
                }
                ;
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
    public ScooterQcItemListResult scooterQcItem(ScooterQcIdItemEnter enter) {
        //先查询组组装单是否存在
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            //抛组装子单为空异常
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        //判断组装单是否存在对应的组装单子表
        int count = scooterQcServiceMapper.scooterQcItemCount();
        //质检结果信息
        ScooterQcItemOptionResult scooterQcItemOptionResult = null;
        //单挑质检项
        ScooterQcItemResult scooterQcItemResult = null;
        //页面显示质检项列表
        List<ScooterQcItemResult> scooterQcItemResults = new ArrayList<>();
        //质检选项集合
        List<ScooterQcItemOptionResult> scooterQcItemOptionResultList = new ArrayList<>();
        //返回结果集
        ScooterQcItemListResult scooterQcItemListResultList = new ScooterQcItemListResult();

        //判断是否有组装单数据
        if (count == 0) {
            //组装单子表为空，直接返回空的子表显示页面
            return null;
        } else {
            //质检模板表
            QueryWrapper<OpeProductQcTemplate> opeProductQcTemplateQueryWrapper = new QueryWrapper<>();
            //根据产品id查询到对应的质检模板
            opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID, opeAssemblyBOrder.getProductId());
            List<OpeProductQcTemplate> opeProductQcTemplateList = opeProductQcTemplateService.list(opeProductQcTemplateQueryWrapper);

            if (!CollectionUtils.isEmpty(opeProductQcTemplateList)) {
                for (OpeProductQcTemplate opeProductQcTemplate : opeProductQcTemplateList) {

                    //判断详细质检模板是否为空
                    if (StringUtils.isEmpty(opeProductQcTemplate)) {
                        throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
                    }

                    //获取模板数据库中的质检项集合
                    QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
                    opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, opeProductQcTemplate.getId());
                    List<OpeProductQcTemplateB> templateBIdList = opeProductQcTemplateBService.list(opeProductQcTemplateBQueryWrapper);
                    if (!CollectionUtils.isEmpty(templateBIdList)) {
                        for (OpeProductQcTemplateB templateB : templateBIdList) {
                            scooterQcItemOptionResultList.add(scooterQcItemOptionResult = ScooterQcItemOptionResult.builder()
                                    .id(templateB.getId())
                                    .qcResult(templateB.getQcResult())//质检结果
                                    .uploadFlag(templateB.getUploadFlag())
                                    .optionNum(templateB.getResultsSequence())
                                    .build());
                        }
                        //构建质检详情页vo
                        scooterQcItemResult = ScooterQcItemResult.builder()
                                .id(opeAssemblyBOrder.getId())
                                .qcTemplateId(opeProductQcTemplate.getId())
                                .scooterQcItemOptionResultList(scooterQcItemOptionResultList)
                                .build();
                        scooterQcItemResults.add(scooterQcItemResult);
                    } else {
                        //没有质检项目
                        throw new SesMobileRpsException(ExceptionCodeEnums.QC_OPTION_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_OPTION_IS_EMPTY.getMessage());
                    }
                    //返回结果集
                    scooterQcItemListResultList.setScooterQcItemResultList(scooterQcItemResults);
                }
            } else {
                //没有质检模板
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
            }
        }
        return scooterQcItemListResultList;
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
        //返回的结果集
        ScooterQcResidueNumResult scooterQcItemOptionResult = null;
        List<ScooterQcItemOptionEnter> qcItemOptionEnterList = JSON.parseArray(enter.getScooterQcItemOptionEnter(), ScooterQcItemOptionEnter.class);
        //传入的质检信息为空
        if (CollectionUtils.isEmpty(qcItemOptionEnterList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        //判断质检项集合是否为空
        if (!CollectionUtils.isEmpty(qcItemOptionEnterList)) {
            //组装单子单
            QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
            OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);

            //抛组装子单为空异常
            if (StringUtils.isEmpty(opeAssemblyBOrder)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
            }

            //获取组装单
            QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
            opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, opeAssemblyBOrder.getAssemblyId());
            OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);

            //抛组装单为空异常
            if (StringUtils.isEmpty(opeAssemblyOrder)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
            }

            //待质检总数异常
            if ((opeAssemblyBOrder.getLaveWaitQcQty() - 1 < 0)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
            }

            //订单详情集合
            List<OpeAssemblyQcTrace> opeAssemblyQcTraceList = new ArrayList<>();

            //判断是否有质检项质检失败
            boolean qcOptionFlag = true;

            //本次质检的批次号
            IdEnter idEnter=new IdEnter();
            BeanUtils.copyProperties(enter,idEnter);
            idEnter.setId(opeAssemblyOrder.getId());
            String batchNum = bussinessNumberService.assemblyBatchNo(idEnter);

            //查询质检详情中时间最近的一条质检记录
            QueryWrapper<OpeAssemblyQcItem> opeAssemblyQcItemQueryWrapper = new QueryWrapper<>();
            opeAssemblyQcItemQueryWrapper.isNotNull(OpeAssemblyQcItem.COL_BATCH_NO);

            List<OpeAssemblyQcItem> opeAssemblyQcItems = opeAssemblyQcItemService.list(opeAssemblyQcItemQueryWrapper);
            OpeAssemblyQcItem assemblyQcItem = null;

            if (!CollectionUtils.isEmpty(opeAssemblyQcItems)) {
                List<OpeAssemblyQcItem> assemblyQcItems = opeAssemblyQcItems.stream().sorted(Comparator.comparing(OpeAssemblyQcItem::getCreatedTime).reversed()).collect(Collectors.toList());
                //最近的一次质检项目
                if (DateUtils.isSameDay(assemblyQcItems.get(0).getCreatedTime(), new Date())) {
                    assemblyQcItem = assemblyQcItems.get(0);
                }
            }

            for (ScooterQcItemOptionEnter scooterQcItemOptionEnter : qcItemOptionEnterList) {
                //获取质检模板
                QueryWrapper<OpeProductQcTemplate> opeProductQcTemplateQueryWrapper = new QueryWrapper<>();
                opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID, opeAssemblyBOrder.getProductId());
                OpeProductQcTemplate opeProductQcTemplate = opeProductQcTemplateService.getOne(opeProductQcTemplateQueryWrapper);

                //判断详细质检信息是否为空
                if (StringUtils.isEmpty(opeProductQcTemplate)) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
                }

                //查询质检模板结果项
                QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
                opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_ID, scooterQcItemOptionEnter.getQcResultId());
                opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, opeProductQcTemplate.getId());
                OpeProductQcTemplateB opeProductQcTemplateB = opeProductQcTemplateBService.getOne(opeProductQcTemplateBQueryWrapper);

                //判断详细质检信息是否为空
                if (StringUtils.isEmpty(opeProductQcTemplateB)) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
                }

                if (!opeProductQcTemplateB.getPassFlag()) {
                    //如果有质检失败的质检项，本次质检则失败
                    qcOptionFlag = false;
                }


                //质检Trace
                OpeAssemblyQcTrace opeAssemblyQcTrace = null;
                opeAssemblyQcTraceList.add(opeAssemblyQcTrace = OpeAssemblyQcTrace.builder()
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

            //质检批次记录
            OpeAssemblyLotTrace opeAssemblyLotTrace = null;
            if (!StringUtils.isEmpty(assemblyQcItem)) {
                QueryWrapper<OpeAssemblyLotTrace> opeAssemblyLotTraceQueryWrapper = new QueryWrapper<>();
                opeAssemblyLotTraceQueryWrapper.eq(OpeAssemblyLotTrace.COL_BATCH_NO, assemblyQcItem.getBatchNo());
                opeAssemblyLotTrace = opeAssemblyLotTraceService.getOne(opeAssemblyLotTraceQueryWrapper);
                if (!StringUtils.isEmpty(opeAssemblyLotTrace)) {
                    opeAssemblyLotTrace.setPassCount(qcOptionFlag ? opeAssemblyLotTrace.getPassCount() + 1 : opeAssemblyLotTrace.getPassCount());
                    opeAssemblyLotTrace.setFailCount(qcOptionFlag ? opeAssemblyLotTrace.getFailCount() : opeAssemblyLotTrace.getFailCount() + 1);
                    opeAssemblyLotTrace.setTotalQualityInspected(opeAssemblyLotTrace.getTotalQualityInspected() + 1);
                    opeAssemblyLotTrace.setUpdatedBy(enter.getUserId());
                    opeAssemblyLotTrace.setUpdatedTime(new Date());
                }
            } else {
                //本次质检的质检批次记录
                opeAssemblyLotTrace = OpeAssemblyLotTrace.builder()
                        .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_LOT_TRACE))
                        .dr(0)
                        .batchNo(StringUtils.isEmpty(assemblyQcItem) ? batchNum : assemblyQcItem.getBatchNo())
                        .assemblyId(opeAssemblyOrder.getId())
                        .tenantId(enter.getTenantId())
                        .qualityInspectorId(enter.getUserId())
                        .userId(enter.getUserId())
                        .createdTime(new Date())
                        .createdBy(enter.getUserId())
                        .updatedTime(new Date())
                        .updatedBy(enter.getUserId())
                        .totalQualityInspected(1)  //需要质检总数
                        .passCount(qcOptionFlag ? 1 : 0)
                        .failCount(qcOptionFlag ? 0 : 1)
                        .build();
            }

            //查找对应的质检记录是否存在
            OpeAssemblyBQc opeAssemblyBQc = null;
            QueryWrapper<OpeAssemblyBQc> opeAssemblyBQcQueryWrapper = new QueryWrapper<>();
            opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_ASSEMBLY_B_ID, enter.getId());
            opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_BATCH_NO, assemblyQcItem.getBatchNo());
            opeAssemblyBQc = opeAssemblyBQcService.getOne(opeAssemblyBQcQueryWrapper);

            if (StringUtils.isEmpty(opeAssemblyBQc)) {
                //质检记录
                opeAssemblyBQc = OpeAssemblyBQc.builder()
                        .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_B_QC))
                        .dr(0)
                        .batchNo(StringUtils.isEmpty(assemblyQcItem) ? batchNum : assemblyQcItem.getBatchNo())
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
                        .totalQualityInspected(1)  //需要质检总数
                        .passCount(qcOptionFlag ? 1 : 0)
                        .failCount(0)
                        .build();
            } else {
                opeAssemblyBQc.setStatus(qcOptionFlag ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue());
                opeAssemblyBQc.setPassCount(qcOptionFlag ? opeAssemblyBQc.getPassCount() + 1 : opeAssemblyBQc.getPassCount());
                opeAssemblyBQc.setTotalQualityInspected(opeAssemblyBQc.getTotalQualityInspected() + 1);
            }

            //质检Item
            OpeAssemblyQcItem opeAssemblyQcItem = OpeAssemblyQcItem.builder()
                    .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_QC_ITEM))
                    .dr(0)
                    .serialNum(batchNum)
                    .assemblyBId(opeAssemblyBOrder.getId())
                    .assemblyId(opeAssemblyOrder.getId())
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .assemblyBQcId(opeAssemblyBQc.getId())
                    .assemblyLotId(opeAssemblyLotTrace.getId())
                    .productId(opeAssemblyBOrder.getProductId())
                    .qcResult(qcOptionFlag ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue())
                    .revision(0)
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
                    .batchNo(qcOptionFlag ? (StringUtils.isEmpty(assemblyQcItem) ? batchNum : assemblyQcItem.getBatchNo()) : null)  //批次号
                    .build();

            //质检结果批量插入批次表id和质检记录id
            for (OpeAssemblyQcTrace opeAssemblyQcTrace : opeAssemblyQcTraceList) {
                opeAssemblyQcTrace.setAssemblyQcItemId(opeAssemblyQcItem.getId());
            }

            //修改组装单和子单状态标记
            boolean orderFlag = false;

            //把质检成功的产品对应的组装单和组装单子单的待质检数量进行修改
            //修改组装单的总待质检数量
            if ((!StringUtils.isEmpty(opeAssemblyOrder.getLaveWaitQcTotal())) && (!StringUtils.isEmpty(opeAssemblyBOrder.getLaveWaitQcQty()))) {
                if (qcOptionFlag) {
                    //修改组装单子单的待质检数
                    opeAssemblyBOrder.setLaveWaitQcQty(opeAssemblyBOrder.getLaveWaitQcQty() - 1);
                    //修改组装单的待质检数
                    opeAssemblyOrder.setLaveWaitQcTotal(opeAssemblyOrder.getLaveWaitQcTotal() - 1);
                }
                if (opeAssemblyOrder.getLaveWaitQcTotal() < 0 || opeAssemblyBOrder.getLaveWaitQcQty() < 0) {
                    //待质检总数错误
                    throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
                }

                //判断组装单和子单节点是否发生改变
                if (opeAssemblyOrder.getLaveWaitQcTotal() == 0) {
                    opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());

                    //订单状态改变，产生节点表
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


            //保存每个质检项的质检Trace集合
            opeAssemblyQcTraceService.saveBatch(opeAssemblyQcTraceList);
            //保存质检批次记录
            opeAssemblyLotTraceService.saveOrUpdate(opeAssemblyLotTrace);
            //保存质检详情记录
            opeAssemblyQcItemService.save(opeAssemblyQcItem);
            //修改组装单子单的状态
            opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
            //保存质检批次记录
            opeAssemblyBQcService.saveOrUpdate(opeAssemblyBQc);
            //保存组装单
            opeAssemblyOrderService.updateById(opeAssemblyOrder);

            //返回结果集
            scooterQcItemOptionResult = ScooterQcResidueNumResult.builder()
                    .result(orderFlag ? Boolean.TRUE : Boolean.FALSE)
                    .residueNum(opeAssemblyBOrder.getLaveWaitQcQty())
                    .build();

        } else {
            //质检项目为空
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_OPTION_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_OPTION_IS_EMPTY.getMessage());
        }
        //通过质检，则返回成功的质检状态
        return scooterQcItemOptionResult;
    }


}
