package com.redescooter.ses.mobile.rps.service.scooterqc.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyEventEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductQcTemplateBMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductQcTemplateMapper;
import com.redescooter.ses.mobile.rps.dao.scooterqc.ScooterQcServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterqcService;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ScooterqcServiceImpl implements ScooterqcService {

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
    private OpeProductQcTemplateBMapper opeProductQcTemplateBMapper;

    @Autowired
    private OpeProductQcTemplateMapper opeProductQcTemplateMapper;

    @Autowired
    private ScooterQcServiceMapper scooterQcServiceMapper;

    @Autowired
    private OpeAssembiyOrderTraceService opeAssembiyOrderTraceService;

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
            opeAssemblyOrderQueryWrapper.ne(OpeAssemblyOrder.COL_LAVE_WAIT_QC_TOTAL, 0);
            List<OpeAssemblyOrder> opeAssemblyOrderList = opeAssemblyOrderService.list(opeAssemblyOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyOrderList)) {
                for (OpeAssemblyOrder opeAssemblyOrder : opeAssemblyOrderList) {
                    scooterQcOneResultList.add(
                            scooterQcOneResult = ScooterQcOneResult.builder()
                                    .id(opeAssemblyOrder.getId())
                                    .scooterNum(opeAssemblyOrder.getAssemblyNumber())
                                    .checkTime(opeAssemblyOrder.getCreatedTime())
                                    .waitInWHName(opeAssemblyOrder.getWaitAssemblyTotal())
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
            // opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_PRODUCT_ID, enter.getPartId());
            //通过组装单子表id查找
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ASSEMBLY_ID, enter.getId());
            List<OpeAssemblyBOrder> opeAssemblyBOrderList = opeAssemblyBOrderService.list(opeAssemblyBOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyBOrderList)) {
                for (OpeAssemblyBOrder opeAssemblyBOrder : opeAssemblyBOrderList) {
                    scooterQcPartResultList.add(
                            scooterQcPartResult = ScooterQcPartResult.builder()
                                    .scooterBId(opeAssemblyBOrder.getId())
                                    .partId(opeAssemblyBOrder.getProductId())
                                    .partNum(opeAssemblyBOrder.getLaveWaitQcQty())
                                    .partStr(opeAssemblyBOrder.getProductNumber())
                                    .partName(opeAssemblyBOrder.getEnName())
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
    public PageResult<ScooterQcItemResult> scooterQcItem(ScooterQcIdItemEnter enter) {
        //先查询组组装单是否存在
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_PRODUCT_ID, enter.getPartId());
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            //抛组装子单为空异常
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        //判断组装单是否存在对应的组装单子表
        int count = scooterQcServiceMapper.scooterQcItemCount();
        ScooterQcItemOptionResult scooterQcItemOptionResult = null;
        ScooterQcItemResult scooterQcItemResult = null;
        List<ScooterQcItemResult> scooterQcItemResultList = new ArrayList<>();
        List<ScooterQcItemOptionResult> scooterQcItemOptionResultList = new ArrayList<>();


        //判断是否有组装单数据
        if (count == 0) {
            //组装单子表为空，直接返回空的子表显示页面
            return PageResult.createZeroRowResult(enter);
        } else {
            //质检模板表
            QueryWrapper<OpeProductQcTemplate> opeProductQcTemplateQueryWrapper = new QueryWrapper<>();
            //根据产品id查询到对应的质检模板
            opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID, enter.getPartId());
            OpeProductQcTemplate opeProductQcTemplate = opeProductQcTemplateService.getOne(opeProductQcTemplateQueryWrapper);
            //判断详细质检模板是否为空
            if (StringUtils.isEmpty(opeProductQcTemplate)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.OPE_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_TEMPLATE_IS_EMPTY.getMessage());
            }
            //质检模板子表
            QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
            //获取到对应的质检子模板
            opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, opeProductQcTemplate.getId());
            opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PASS_FLAG, 1);
            OpeProductQcTemplateB opeProductQcTemplateB = opeProductQcTemplateBService.getOne(opeProductQcTemplateBQueryWrapper);
            //判断详细质检信息是否为空
            if (StringUtils.isEmpty(opeProductQcTemplateB)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.OPE_TEMPLATE_B_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_TEMPLATE_B_IS_EMPTY.getMessage());
            }

            if (!StringUtils.isEmpty(opeProductQcTemplate)) {
                //获取模板数据库中的质检项集合
                opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, opeProductQcTemplate.getId());
                List<OpeProductQcTemplateB> templateBIdList = opeProductQcTemplateBService.list(opeProductQcTemplateBQueryWrapper);
                if (!CollectionUtils.isEmpty(templateBIdList)) {
                    for (OpeProductQcTemplateB templateB : templateBIdList) {
                        scooterQcItemOptionResultList.add(scooterQcItemOptionResult = ScooterQcItemOptionResult.builder()
                                .scooterBId(enter.getId())
                                .partId(enter.getPartId())
                                .id(templateB.getProductQcTemplateId())
                                .qcName(opeProductQcTemplate.getQcItemName())
                                .optionNum(templateB.getResultsSequence())
                                .build());
                    }
                    //构建质检详情页vo(需要修改)
                    scooterQcItemResult = ScooterQcItemResult.builder()
                            .id(opeAssemblyBOrder.getId())
                            .qcTemplateId(opeProductQcTemplate.getId())
                            .scooterQcItemOptionResultList(scooterQcItemOptionResultList)
                            .build();
                } else {
                    //没有质检模板
                    throw new SesMobileRpsException(ExceptionCodeEnums.OPE_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_TEMPLATE_IS_EMPTY.getMessage());
                }
            } else {
                //没有质检项目
                throw new SesMobileRpsException(ExceptionCodeEnums.OPE_OPTION_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_OPTION_IS_EMPTY.getMessage());
            }


            scooterQcItemResultList.add(scooterQcItemResult);
            return PageResult.create(enter, count, scooterQcItemResultList);
        }
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
        ScooterQcResidueNumResult scooterQcItemOptionResult = null;
        List<ScooterQcItemOptionEnter> qcItemOptionEnterList = JSON.parseArray(enter.getScooterQcItemOptionEnter(), ScooterQcItemOptionEnter.class);


        QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
        QueryWrapper<OpeProductQcTemplate> opeProductQcTemplateQueryWrapper = new QueryWrapper<>();
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
        QueryWrapper<OpeAssemblyQcItem> opeAssemblyQcItemQueryWrapper = new QueryWrapper<>();
        QueryWrapper<OpeAssemblyQcTrace> opeAssemblyQcTraceQueryWrapper = new QueryWrapper<>();


        OpeProductQcTemplateB opeProductQcTemplateB = null;
        OpeProductQcTemplate opeProductQcTemplate = null;
        OpeAssemblyBOrder opeAssemblyBOrder = null;
        OpeAssemblyOrder opeAssemblyOrder = null;
        OpeAssemblyQcItem opeAssemblyQcItem = null;
        OpeAssemblyQcTrace opeAssemblyQcTrace = null;
        OpeAssemblyBQc opeAssemblyBQc = null;
        OpeAssembiyOrderTrace opeAssembiyOrderTrace = null;
        List<OpeAssemblyQcItem> opeAssemblyQcItemList = new ArrayList<>();
        List<OpeProductQcTemplateB> opeProductQcTemplateBList = new ArrayList<>();


        //判断质检项集合是否为空
        if (!CollectionUtils.isEmpty(qcItemOptionEnterList)) {
            for (ScooterQcItemOptionEnter scooterQcItemOptionEnter : qcItemOptionEnterList) {
                opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID, enter.getPartId());
                opeProductQcTemplate = opeProductQcTemplateService.getOne(opeProductQcTemplateQueryWrapper);
                //判断详细质检信息是否为空
                if (StringUtils.isEmpty(opeProductQcTemplate)) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.OPE_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_TEMPLATE_IS_EMPTY.getMessage());
                }
                opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, opeProductQcTemplate.getId());
                opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_ID, scooterQcItemOptionEnter.getQcResultId());
                opeProductQcTemplateB = opeProductQcTemplateBService.getOne(opeProductQcTemplateBQueryWrapper);
                //判断详细质检信息是否为空
                if (StringUtils.isEmpty(opeProductQcTemplateB)) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.OPE_TEMPLATE_B_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_TEMPLATE_B_IS_EMPTY.getMessage());
                }

                //组装单子单
                opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
                opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_PRODUCT_ID, enter.getPartId());
                opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);

                if (StringUtils.isEmpty(opeAssemblyBOrder)) {
                    //抛组装子单为空异常
                    throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
                }

                //输入的待质检部件数错误
                if (opeAssemblyBOrder.getLaveWaitQcQty() != enter.getPartNum()) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_PART_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_PART_NUM_ERROR.getMessage());
                }

                //获取组装单
                opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, opeAssemblyBOrder.getAssemblyId());
                opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);

                    if (!opeProductQcTemplateB.getPassFlag()) {
                        //如果有质检失败的，直接返回质检失败
                        OpeAssemblyBQc opeAssemblyBQcTemp = OpeAssemblyBQc.builder()
                                .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_B_QC))
                                .dr(0)
                                .assemblyBId(opeProductQcTemplateB.getId())
                                .batchNo(UUID.randomUUID().toString().replace("-", "").substring(8))
                                .userId(enter.getUserId())
                                .qualityInspectorId(enter.getUserId())
                                .tenantId(enter.getTenantId())
                                .createdTime(new Date())
                                .status(AssemblyStatusEnums.QC.getMessage())
                                .qualityInspectionTime(new Date())
                                .createdBy(enter.getUserId())
                                .productId(enter.getPartId())
                                .qualityInspectorId(enter.getUserId())
                                .totalQualityInspected(enter.getPartNum())
                                .passCount(0)
                                .failCount(enter.getPartNum())
                                .build();

                        //根据对应的组装单id和产品id查询到对应的待质检数，直接返回原待质检数
                        scooterQcItemOptionResult = ScooterQcResidueNumResult.builder()
                                .result(Boolean.FALSE)
                                .residueNum(opeAssemblyOrder.getLaveWaitQcTotal())
                                .build();
                        return scooterQcItemOptionResult;
                    }

                    if ((opeAssemblyBOrder.getLaveWaitQcQty() - enter.getPartNum()) < 0) {
                        //待质检总数异常
                        throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
                    }

                    //质检Item
//                opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_PRODUCT_ID, opeAssemblyBOrder.getProductId());
//                opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_ASSEMBLY_B_ID, opeAssemblyBOrder.getId());
//                opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_ASSEMBLY_ID, opeAssemblyBOrder.getAssemblyId());
//                opeAssemblyQcItem = opeAssemblyQcItemService.getOne(opeAssemblyQcItemQueryWrapper);

                    opeAssemblyQcItemList.add(opeAssemblyQcItem = OpeAssemblyQcItem.builder()
                            .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_QC_ITEM))
                            .assemblyBId(opeAssemblyBOrder.getId())
                            .assemblyId(opeAssemblyOrder.getId())
                            .dr(0)
                            .qcResult(AssemblyStatusEnums.QC_PASSED.getValue())
                            .serialNum(UUID.randomUUID().toString().replace("-", "").substring(8))
                            .productId(opeAssemblyBOrder.getProductId())
                            .qcResult(opeProductQcTemplateB.getQcResult())
                            .revision(1)
                            .createdTime(new Date())
                            .build());

                    //质检Trace
//                opeAssemblyQcTraceQueryWrapper.eq(OpeAssemblyQcTrace.COL_OPE_ASSEMBLY_B_ID, opeAssemblyBOrder.getId());
//                opeAssemblyQcTraceQueryWrapper.eq(OpeAssemblyQcTrace.COL_PRODUCT_QC_TEMPLATE_ID, opeAssemblyQcItem.getId());
//                opeAssemblyQcTrace = opeAssemblyQcTraceService.getOne(opeAssemblyQcTraceQueryWrapper);
                    opeAssemblyQcTrace = OpeAssemblyQcTrace.builder()
                            .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_QC_TRACE))
                            .productQcTemplateBId(opeProductQcTemplateB.getId())
                            .productQcTemplateId(opeProductQcTemplate.getId())
                            .opeAssemblyBId(opeAssemblyBOrder.getId())
                            .assemblyQcItemId(opeAssemblyQcItem.getId())
                            .revision(1)
                            .dr(0)
                            .picture(scooterQcItemOptionEnter.getImageUrl())
                            .createdTime(new Date())
                            .build();
                    opeAssemblyQcTraceService.save(opeAssemblyQcTrace);
                    scooterQcItemOptionResult = ScooterQcResidueNumResult.builder()
                            .result(Boolean.TRUE)
                            .residueNum(opeAssemblyBOrder.getLaveWaitQcQty())
                            .build();

                    //质检成功的
                    opeAssemblyBQc = OpeAssemblyBQc.builder()
                            .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_B_QC))
                            .dr(0)
                            .assemblyBId(opeAssemblyBOrder.getId())
                            .batchNo(UUID.randomUUID().toString().replace("-", "").substring(8))
                            .createdTime(new Date())
                            .tenantId(enter.getUserId())
                            .userId(enter.getUserId())
                            .status(AssemblyStatusEnums.QC_PASSED.getValue())
                            .createdBy(enter.getUserId())
                            .productId(enter.getPartId())
                            .qualityInspectorId(enter.getUserId())
                            .totalQualityInspected(enter.getPartNum())
                            .passCount(enter.getPartNum())
                            .failCount(0)
                            .build();

                    //批量插入质检结果批次表id
                    Long tempId = opeAssemblyBQc.getId();
                    opeAssemblyQcItemList.forEach(opeAssemblyQcItemOne -> {
                        opeAssemblyQcItemOne.setAssemblyBQcId(tempId);
                    });
                    opeAssemblyQcItemService.saveBatch(opeAssemblyQcItemList);


                    //把质检成功的产品对应的组装单和组装单子单的待质检数量进行修改
                    //修改组装单的总待质检数量
                    opeAssemblyOrder.setLaveWaitQcTotal(opeAssemblyOrder.getLaveWaitQcTotal() - enter.getPartNum());
                    //修改组装单子单的待质检数
                    opeAssemblyBOrder.setLaveWaitQcQty((opeAssemblyBOrder.getLaveWaitQcQty() - enter.getPartNum()));
                if (opeAssemblyOrder.getLaveWaitQcTotal()<0||opeAssemblyBOrder.getLaveWaitQcQty()<0) {
                    //抛组装子单为空异常
                    throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
                }

                    //修改组装单状态
                    boolean flag = false;
                    if (opeAssemblyOrder.getLaveWaitQcTotal() == 0) {
                        opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());
                        flag = !flag;
                    } else {
                        opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC.getValue());
                    }
                    if (opeAssemblyBOrder.getLaveWaitQcQty() == 0) {
                        opeAssemblyBOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());
                    } else {
                        opeAssemblyBOrder.setStatus(AssemblyStatusEnums.QC.getValue());
                    }

                    //if ((opeAssemblyBOrder.getLaveWaitQcQty() == 0)) {}



                    opeAssembiyOrderTrace = OpeAssembiyOrderTrace.builder()
                            .id(idAppService.getId(SequenceName.OPE_ASSEMBIY_ORDER_TRACE))
                            .userId(enter.getUserId())
                            .dr(0)
                            .userId(enter.getUserId())
                            .event(flag ? AssemblyEventEnums.QC_PASSED.getValue() : AssemblyEventEnums.QC.getValue())
                            .status(flag ? AssemblyEventEnums.QC_PASSED.getValue() : AssemblyEventEnums.QC.getValue())
                            .eventTime(new Date())
                            .createdBy(enter.getUserId())
                            .updatedBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedTime(new Date())
                            .opeAssembiyOrderId(opeAssemblyOrder.getId())
                            .build();
                    //保存组装单主表状态节点
                    opeAssembiyOrderTraceService.save(opeAssembiyOrderTrace);
                    opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
                    //返回结果集
                    scooterQcItemOptionResult = ScooterQcResidueNumResult.builder()
                            .result(Boolean.TRUE)
                            .residueNum(opeAssemblyOrder.getLaveWaitQcTotal())
                            .build();


                    //修改组装单子单状态
//            if (opeAssemblyBOrder.getLaveWaitQcQty() == 0) {
//                opeAssemblyBOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());
//            } else {
//                opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC.getValue());
//            }
                    //修改组装单子单的状态
                    opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
                    //保存质检批次记录
                    opeAssemblyBQcService.save(opeAssemblyBQc);
                    opeAssemblyOrderService.updateById(opeAssemblyOrder);


            }
        } else {
            //质检项目为空
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_OPTION_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_OPTION_IS_EMPTY.getMessage());
        }
        //通过质检，则返回成功的质检状态
        return scooterQcItemOptionResult;
    }


}
