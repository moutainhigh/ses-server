package com.redescooter.ses.mobile.rps.service.prowaitinwh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyEventEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.prowaitinwh.ProWaitInWHServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.prowaitinwh.ProWaitInWHService;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.ProWaitInWHIdEnter;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.ProWaitInWHInfoResult;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.ProWaitInWHItemResult;
import com.redescooter.ses.mobile.rps.vo.prowaitinwh.ProWaitInWHLOneResult;
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

/**
 * @ClassNameProWaitInWHServiceImpl
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:52
 * @Version V1.0
 **/
@Service
public class ProWaitInWHServiceImpl implements ProWaitInWHService {


    @Autowired
    private ProWaitInWHServiceMapper proWaitInWHServiceMapper;

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAssemblyBOrderService opeAssemblyBOrderService;

    @Autowired
    private OpeAssemblyQcItemService opeAssemblyQcItemService;

    @Autowired
    private OpeAssemblyBQcService opeAssemblyBQcService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private ReceiptTraceService receiptTraceService;

    @Reference
    private IdAppService idAppService;


    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、查询生产仓库待入库列表
     * @Date 2020/4/14 17:51
     * @Param [enter]
     */
    @Transactional
    @Override
    public PageResult<ProWaitInWHLOneResult> proWaitInWHList(PageEnter enter) {
        int count = proWaitInWHServiceMapper.proWaitInWHListCount();
        List<ProWaitInWHLOneResult> proWaitInWHLOneResults = new ArrayList<>();
        ProWaitInWHLOneResult proWaitInWHLOneResult = null;

        //opeAssemblyOrderService对应的数据库表为空的时候直接返回
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
            //待入库数量大于0
            opeAssemblyOrderQueryWrapper.ge(OpeAssemblyOrder.COL_IN_WAIT_WH_TOTAL, 0);
            opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_STATUS, AssemblyStatusEnums.QC_PASSED.getValue());
            List<OpeAssemblyOrder> opeAssemblyOrderList = opeAssemblyOrderService.list(opeAssemblyOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyOrderList)) {
                for (OpeAssemblyOrder opeAssemblyOrder : opeAssemblyOrderList) {
                    proWaitInWHLOneResults.add(
                            proWaitInWHLOneResult = ProWaitInWHLOneResult.builder()
                                    .scooterId(opeAssemblyOrder.getId())
                                    .waitInWHNum(opeAssemblyOrder.getInWaitWhTotal())
                                    .waitInWHStr(opeAssemblyOrder.getAssemblyNumber())
                                    .inWHTListTime(new Date())
                                    .build());
                }
            } else {
                return PageResult.createZeroRowResult(enter);
            }
        }
        return PageResult.create(enter, count, proWaitInWHLOneResults);
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、根据组装单id查询对应的待入库商品部件详情列表
     * @Date 2020/4/14 17:49
     * @Param [enter]
     */
    @Transactional
    @Override
    public PageResult<ProWaitInWHItemResult> proWaitWHItemList(ProWaitInWHIdEnter enter) {
        int count = proWaitInWHServiceMapper.proWaitWHItemListCount();
        ProWaitInWHItemResult proWaitInWHItemResult = null;
        List<ProWaitInWHItemResult> proWaitWHItemListResult = new ArrayList<>();

        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        } else {
            QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
            opeAssemblyBOrderQueryWrapper.ge(OpeAssemblyBOrder.COL_IN_WAIT_WH_QTY, 0);
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_PRODUCT_ID, enter.getPartId());
            //通过组装单子表id查找
            opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getScooterBId());
            List<OpeAssemblyBOrder> opeAssemblyBOrderList = opeAssemblyBOrderService.list(opeAssemblyBOrderQueryWrapper);
            if (!CollectionUtils.isEmpty(opeAssemblyBOrderList)) {
                for (OpeAssemblyBOrder opeAssemblyBOrder : opeAssemblyBOrderList) {
                    proWaitWHItemListResult.add(
                            proWaitInWHItemResult = ProWaitInWHItemResult.builder()
                                    .id(opeAssemblyBOrder.getId())
                                    .scooterBId(opeAssemblyBOrder.getAssemblyId())
                                    .partId(opeAssemblyBOrder.getProductId())
                                    .partNum(opeAssemblyBOrder.getInWaitWhQty())
                                    .partStr(opeAssemblyBOrder.getProductNumber())
                                    .partName(opeAssemblyBOrder.getEnName())
                                    .build());
                }
            } else {
                return PageResult.createZeroRowResult(enter);
            }
        }
        return PageResult.create(enter, count, proWaitWHItemListResult);
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、根据具体的部品id查询生产仓库待入库详情
     * @Date 2020/4/14 17:50
     * @Param [enter]
     */
    @Transactional
    @Override
    public ProWaitInWHInfoResult proWaitInWHInfoOut(IdEnter enter) {
        return null;
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、提交生产仓库入库信息
     * @Date 2020/4/14 17:52
     * @Param [enter]
     */
    @Transactional
    @Override
    public ProWaitInWHInfoResult proWaitInWHInfoIn(ProWaitInWHIdEnter enter) {
        //返回结果集
        ProWaitInWHInfoResult proWaitInWHInfoResult = null;

        //查询对应的质检记录
        QueryWrapper<OpeAssemblyBQc> opeAssemblyBQcQueryWrapper = new QueryWrapper<>();
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_PRODUCT_ID, enter.getPartId());
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_ASSEMBLY_B_ID, enter.getScooterBId());
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_STATUS, AssemblyStatusEnums.QC_PASSED.getValue());
        OpeAssemblyBQc opeAssemblyBQc = opeAssemblyBQcService.getOne(opeAssemblyBQcQueryWrapper);

        //抛质检记录为空异常
        OpeAssemblyQcItem opeAssemblyQcItem = null;
        if (StringUtils.isEmpty(opeAssemblyBQc)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_INFO_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_INFO_IS_EMPTY.getMessage());
        } else {
            //通过质检项查询质检结果id
            QueryWrapper<OpeAssemblyQcItem> opeAssemblyQcItemQueryWrapper = new QueryWrapper<>();
            opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_PRODUCT_ID, enter.getPartId());
            opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_ASSEMBLY_B_ID, enter.getScooterBId());
            opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_ASSEMBLY_B_QC_ID, opeAssemblyBQc.getId());
            opeAssemblyQcItem = opeAssemblyQcItemService.getOne(opeAssemblyQcItemQueryWrapper);
        }

        //获取组装单子单
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getScooterBId());
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_PRODUCT_ID, enter.getPartId());
        opeAssemblyBOrderQueryWrapper.gt(OpeAssemblyBOrder.COL_IN_WAIT_WH_QTY, 0);
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);

        //抛组装子单为空异常
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        //如果待入库产品的数量为0，抛异常
        if (StringUtils.isEmpty(opeAssemblyBOrder.getInWaitWhQty())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_IS_EMPTY.getMessage());
        }

        //获取组装单
        QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, opeAssemblyBOrder.getAssemblyId());
        opeAssemblyOrderQueryWrapper.gt(OpeAssemblyOrder.COL_IN_WAIT_WH_TOTAL, 0);//部件待入库数不能为空
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);

        //组装单不存在
        if (StringUtils.isEmpty(opeAssemblyOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_ORDER_IS_EMPTY.getMessage());
        }

        //查找仓库类型
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ASSEMBLY.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);

        //根据仓库id查询库存表
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, enter.getPartId());
        OpeStock opeStock = opeStockService.getOne(opeStockQueryWrapper);

        //查询仓库中是否有该产品，有就数量叠加，没有就新增
        if (!StringUtils.isEmpty(opeStock)) {
            //入库总数+1
            opeStock.setIntTotal(opeStock.getIntTotal() + 1);
            //剩余库存数+1
            opeStock.setIntTotal(opeStock.getAvailableTotal() + 1);
            opeStockService.updateById(opeStock);
        } else {
            opeStock = OpeStock.builder()
                    .id(idAppService.getId(SequenceName.OPE_STOCK))
                    .dr(0)
                    .intTotal(1)
                    .whseId(opeWhse.getId())
                    .intTotal(1)
                    .revision(1)
                    .userId(enter.getUserId())
                    .tenantId(enter.getTenantId())
                    .materielProductName(opeAssemblyBOrder.getEnName())
                    .materielProductId(enter.getPartId())
                    .availableTotal(1)
                    .updatedTime(new Date())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .createdBy(enter.getUserId())
                    .build();
            opeStockService.save(opeStock);
        }

        //更新一条生产入库信息
        OpeStockBill opeStockBill = OpeStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .stockId(opeStock.getId())
                .direction(InOutWhEnums.IN.getValue())
                .status(StockBillStatusEnums.NORMAL.getValue())
                .total(1)
                .userId(enter.getUserId())
                .tenantId(enter.getTenantId())
                .sourceId(opeAssemblyOrder.getId())
                .principalId(enter.getUserId())
                .sourceType(WhseTypeEnums.ASSEMBLY.getValue())
                .operatineTime(new Date())
                .revision(1)
                .createdTime(new Date())
                .updatedTime(new Date())
                .operatineTime(new Date())
                .createdBy(enter.getUserId())
                .updatedBy(enter.getUserId())
                .build();
        opeStockBillService.save(opeStockBill);

        //修改组装单和子单状态标记
        boolean orderFlag = false;

        //把质检成功的产品对应的组装单和组装单子单的待入库数量进行修改
        //修改组装单的总待入库数量

        if ((!StringUtils.isEmpty(opeAssemblyOrder.getInWaitWhTotal())) && (!StringUtils.isEmpty(opeAssemblyBOrder.getInWaitWhQty()))) {
            //修改组装单子单的待入库数
            opeAssemblyBOrder.setInWaitWhQty(opeAssemblyBOrder.getInWaitWhQty() - 1);
            //修改组装单的待入库数
            opeAssemblyOrder.setInWaitWhTotal(opeAssemblyOrder.getInWaitWhTotal() - 1);
            if (opeAssemblyOrder.getInWaitWhTotal() == 0) {
                //组装单入库完成
                opeAssemblyOrder.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
                orderFlag = true;
            }
            if (opeAssemblyBOrder.getInWaitWhQty() == 0) {
                //组装单入库完成
                opeAssemblyBOrder.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
            }
            if (opeAssemblyOrder.getInWaitWhTotal() < 0 || opeAssemblyBOrder.getInWaitWhQty() < 0) {
                //待入库总数错误
                throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
            }
        } else {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
        }
        //修改订单状态该表的节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAssemblyOrder.getId());
        saveNodeEnter.setStatus(orderFlag ? AssemblyStatusEnums.IN_PRODUCTION_WH.getValue() : AssemblyStatusEnums.QC_PASSED.getValue());
        saveNodeEnter.setEvent(orderFlag ? AssemblyEventEnums.IN_PRODUCTION_WH.getValue() : AssemblyEventEnums.QC_PASSED.getValue());
        saveNodeEnter.setMemo(null);
        receiptTraceService.saveAssemblyNode(saveNodeEnter);

        //保存子单状态
        opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
        //保存组装单状态
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //返回入库成功结果集
        proWaitInWHInfoResult = ProWaitInWHInfoResult.builder()
                .partNum(opeAssemblyBOrder.getProductNumber())
                .batchNum(opeAssemblyBQc.getBatchNo())  //批次号
                .proTime(opeAssemblyOrder.getCreatedTime())
                .partName(opeAssemblyBOrder.getEnName())
                .residueNum(opeAssemblyBOrder.getInWaitWhQty())
                .Num(opeAssemblyQcItem.getSerialNum())  //序列号
                .build();

        return proWaitInWHInfoResult;
    }


}
