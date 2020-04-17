package com.redescooter.ses.mobile.rps.service.scooterqc.impl;

import java.util.ArrayList;

import java.util.*;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductQcTemplateBMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductQcTemplateMapper;
import com.redescooter.ses.mobile.rps.dao.scooterqc.ScooterQcMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterqcService;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ScooterqcServiceImpl implements ScooterqcService {

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAssemblyBOrderService opeAssemblyBOrderService;

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
    private ScooterQcMapper scooterQcMapper;


    /**
     * @Author kyle
     * @Description //查询整车质检列表并展示
     * @Date 2020/4/14 14:37
     * @Param [enter]
     **/
    @Override
    public PageResult<ScooterQcOneResult> scooterQcList(PageEnter enter) {
        int count = scooterQcMapper.scooterQcListCount();
        ScooterQcOneResult scooterQcOneResult = null;
        List<ScooterQcOneResult> scooterQcOneResultList = new ArrayList<>();
        //opeAssemblyOrderService对应的数据库表为空的时候直接返回
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
            opeAssemblyOrderQueryWrapper.isNotNull(OpeAssemblyOrder.COL_ID);
            //待质检项不为0
            opeAssemblyOrderQueryWrapper.ne(OpeAssemblyOrder.COL_LAVE_WAIT_QC_TOTAL,0);
            List<OpeAssemblyOrder> opeAssemblyOrderList = opeAssemblyOrderService.list(opeAssemblyOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyOrderList)) {
                for (OpeAssemblyOrder opeAssemblyOrder : opeAssemblyOrderList) {
                    scooterQcOneResultList.add(
                            scooterQcOneResult = ScooterQcOneResult.builder()
                                    .scooterId(opeAssemblyOrder.getId())
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
    @Override
    public PageResult<ScooterQcPartResult> partListById(ScooterQcIdEnter enter) {

        int count = scooterQcMapper.partListByIdCount();
        ScooterQcPartResult scooterQcPartResult = null;
        List<ScooterQcPartResult> scooterQcPartResultList = new ArrayList<>();
        //opeAssemblyBOrderService对应的数据库表为空的时候直接返回
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_PRODUCT_ID, enter.getPartId());
            //通过组装单子表id查找
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getScooterBId());
            List<OpeAssemblyBOrder> opeAssemblyBOrderList = opeAssemblyBOrderService.list(opeAssemblyBOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyBOrderList)) {
                for (OpeAssemblyBOrder opeAssemblyBOrder : opeAssemblyBOrderList) {
                    scooterQcPartResultList.add(
                            scooterQcPartResult = ScooterQcPartResult.builder()
                                    .id(opeAssemblyBOrder.getId())
                                    .scooterBId(opeAssemblyBOrder.getAssemblyId())
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
    @Override
    public PageResult<ScooterQcItemResult> scooterQcItem(ScooterQcIdEnter enter) {
        //先查询组组装单是否存在
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getScooterBId());
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_PRODUCT_ID, enter.getPartId());
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            //抛组装子单为空异常
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        //判断组装单是否存在对应的组装单子表
        int count = scooterQcMapper.scooterQcItemCount();
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
            //质检模板子表
            QueryWrapper<OpeProductQcTemplateB> opeProductQcTemplateBQueryWrapper = new QueryWrapper<>();
            //获取到对应的质检子模板
            opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, opeProductQcTemplate.getId());
            OpeProductQcTemplateB opeProductQcTemplateB = opeProductQcTemplateBService.getOne(opeProductQcTemplateBQueryWrapper);

            if (!StringUtils.isEmpty(opeProductQcTemplate)) {
                //获取模板数据库中的质检项集合
                opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, opeProductQcTemplate.getId());
                List<OpeProductQcTemplateB> templateBIdList = opeProductQcTemplateBService.list(opeProductQcTemplateBQueryWrapper);
                if (!CollectionUtils.isEmpty(templateBIdList)) {
                    for (OpeProductQcTemplateB templateB : templateBIdList) {
                        scooterQcItemOptionResultList.add(scooterQcItemOptionResult = ScooterQcItemOptionResult.builder()
                                .scooterBId(enter.getScooterBId())
                                .partId(enter.getPartId())
                                .qcId(templateB.getProductQcTemplateId())
                                .qcName(opeProductQcTemplate.getQcItemName())
                                //.qcStatus(templateB.getQcResult())
                                //.uploadFlag(templateB.getUploadFlag()?Boolean.TRUE:Boolean.FALSE)
                                .optionNum(templateB.getResultsSequence())
                                .build());
                    }
                    //构建质检详情页vo(需要修改)
                    scooterQcItemResult = ScooterQcItemResult.builder()
                            .scooterBId(opeAssemblyBOrder.getId())
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

        //判断质检项集合是否为空
        if (!CollectionUtils.isEmpty(qcItemOptionEnterList)) {
            for (ScooterQcItemOptionEnter scooterQcItemOptionEnter : qcItemOptionEnterList) {
                opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_PRODUCT_QC_TEMPLATE_ID, scooterQcItemOptionEnter.getQcId());
                opeProductQcTemplateBQueryWrapper.eq(OpeProductQcTemplateB.COL_ID, scooterQcItemOptionEnter.getQcResultId());
                opeProductQcTemplateB = opeProductQcTemplateBService.getOne(opeProductQcTemplateBQueryWrapper);

                opeProductQcTemplateQueryWrapper.eq(OpeProductQcTemplate.COL_PRODUCT_ID, enter.getPartId());
                opeProductQcTemplate = opeProductQcTemplateService.getOne(opeProductQcTemplateQueryWrapper);
                //组装单子单
                opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getScooterBId());
                opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);

                //获取组装单
                opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, opeAssemblyBOrder.getAssemblyId());
                opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);
                if (!opeProductQcTemplateB.getPassFlag()) {
                    //没有质检成功的
                    //根据对应的组装单id和产品id查询到对应的待质检数，直接返回原待质检数
                    return scooterQcItemOptionResult = ScooterQcResidueNumResult.builder()
                            .result("质检失败")
                            .residueNum(opeAssemblyBOrder.getLaveWaitQcQty())
                            .build();
                } else {
                    //质检成功的
                    //把质检成功的产品对应的组装单和组装单子单的待质检数量进行修改
                    if ((opeAssemblyBOrder.getLaveWaitQcQty() - enter.getPartNum()) < 0) {
                        //待质检总数异常
                        throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_QC_NUM_ERROR.getMessage());
                    }
                    if ((opeAssemblyBOrder.getLaveWaitQcQty() - enter.getPartNum()) == 0) {
                        //修改组装单子表待质检数为0
                        opeAssemblyBOrder.setLaveWaitQcQty(0);
                        //修改组装单的总待质检数量
                        opeAssemblyOrder.setLaveWaitQcTotal(opeAssemblyOrder.getLaveWaitQcTotal() - enter.getPartNum());
                        opeAssemblyOrderService.updateById(opeAssemblyOrder);
                        return scooterQcItemOptionResult = ScooterQcResidueNumResult.builder()
                                .result("质检成功")
                                .residueNum(0)
                                .build();

                    }
                    opeAssemblyBOrder.setLaveWaitQcQty((opeAssemblyBOrder.getLaveWaitQcQty() - enter.getPartNum()));
                    opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
                    //质检Item
                    opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_PRODUCT_ID,opeAssemblyBOrder.getProductId());
                    opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_ASSEMBLY_B_ID,opeAssemblyBOrder.getId());
                    opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_ASSEMBLY_ID,opeAssemblyBOrder.getAssemblyId());
                    opeAssemblyQcItem = opeAssemblyQcItemService.getOne(opeAssemblyQcItemQueryWrapper);

                    opeAssemblyQcItem = OpeAssemblyQcItem.builder()
                            .assemblyBId(opeAssemblyBOrder.getId())
                            .assemblyId(opeAssemblyOrder.getId())
                            .dr(0)
                            .productId(opeAssemblyBOrder.getProductId())
                            .qcResult(opeProductQcTemplateB.getQcResult())
                            .revision(1)
                            .createdTime(new Date())
                            .build();
                    opeAssemblyQcItemService.updateById(opeAssemblyQcItem);

                    //质检Trace
                    opeAssemblyQcTraceQueryWrapper.eq(OpeAssemblyQcTrace.COL_OPE_ASSEMBLY_B_ID,opeAssemblyBOrder.getId());
                    opeAssemblyQcTraceQueryWrapper.eq(OpeAssemblyQcTrace.COL_PRODUCT_QC_TEMPLATE_ID,opeAssemblyQcItem.getId());

                    opeAssemblyQcTrace = opeAssemblyQcTraceService.getOne(opeAssemblyQcTraceQueryWrapper);
                    opeAssemblyQcTrace = OpeAssemblyQcTrace.builder()
                            .productQcTemplateBId(opeProductQcTemplateB.getId())
                            .productQcTemplateId(opeProductQcTemplate.getId())
                            .opeAssemblyBId(opeAssemblyBOrder.getId())
                            .assemblyQcItemId(opeAssemblyQcItem.getId())
                            .revision(1)
                            .dr(0)
                            .picture(scooterQcItemOptionEnter.getImageUrl())
                            .createdTime(new Date())
                            .build();
                    opeAssemblyQcTraceService.updateById(opeAssemblyQcTrace);
                    scooterQcItemOptionResult = ScooterQcResidueNumResult.builder()
                            .result("质检成功")
                            .residueNum(opeAssemblyBOrder.getLaveWaitQcQty())
                            .build();
                }

            }
        } else {
            //质检项目为空
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_OPTION_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_OPTION_IS_EMPTY.getMessage());
        }
        //通过质检，则返回成功的质检状态
        return scooterQcItemOptionResult;
    }


}
