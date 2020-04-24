package com.redescooter.ses.web.ros.service.customer.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.wh.StockItemStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import com.redescooter.ses.web.ros.exception.ExceptionCode;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeStockBillService;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.customer.TransferScooterService;
import com.redescooter.ses.web.ros.vo.customer.TransferScooterEnter;
import com.redescooter.ses.web.ros.vo.customer.TransferScooterListEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:TransferScooterServiceImpl
 * @description: TransferScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:43
 */
@Service
public class TransferScooterServiceImpl implements TransferScooterService {

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private OpeStockProdProductService opeStockProdProductService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpeStockService opeStockService;

    @Reference
    private IdAppService idAppService;

    /**
     * 车辆分配
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult transferScooter(TransferScooterEnter enter) {

        List<OpeStockBill> saveStockBillList = Lists.newArrayList();

        //解析json 数据
        List<TransferScooterListEnter> transferScooterListEnterList = new ArrayList<>();
        try {
            transferScooterListEnterList.addAll(JSON.parseArray(enter.getProductListJson(), TransferScooterListEnter.class));
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(transferScooterListEnterList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        //验证客户 状态
        OpeCustomer opeCustomer = opeCustomerService.getById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getCode(), ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getMessage());
        }

        //验证库存信息
        Collection<OpeStockProdProduct> opeStockProdProductList =
                opeStockProdProductService.listByIds(transferScooterListEnterList.stream().map(TransferScooterListEnter::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(opeStockProdProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        if (opeStockProdProductList.size() != transferScooterListEnterList.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        Collection<OpeStock> opeStockList = opeStockService.listByIds(opeStockProdProductList.stream().map(OpeStockProdProduct::getStockId).collect(Collectors.toList()));


        //根据产品型号形成 出库单

        Map<Long, Integer> stockBillMap = new HashMap<>();
        opeStockProdProductList.forEach(item -> {
            if (stockBillMap.containsKey(item.getStockId())) {
                stockBillMap.put(item.getStockId(), stockBillMap.get(item.getStockId()) + 1);
            } else {
                stockBillMap.put(item.getStockId(), 1);
            }
        });

        stockBillMap.forEach((key, value) -> {
            //形成出库单
            saveStockBillList.add(OpeStockBill.builder()
                    .id(idAppService.getId(SequenceName.OPE_STOCK_PROD_PRODUCT))
                    .dr(0)
                    .userId(enter.getUserId())
                    .tenantId(enter.getTenantId())
                    .status(StockBillStatusEnums.NORMAL.getValue())
                    .stockId(key)
                    .direction(InOutWhEnums.OUT.getValue())
                    .sourceId(null)
                    .sourceType(SourceTypeEnums.SCOOTER_ALLOCATE.getValue())
                    .total(value)
                    .principalId(enter.getUserId())
                    .operatineTime(new Date())
                    .revision(0)
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .build());
        });
        //库存更新
        opeStockList.forEach(item -> {
            if (stockBillMap.get(item.getId()) > item.getAvailableTotal()) {
                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
            }
            item.setOutTotal(item.getOutTotal() + stockBillMap.get(item.getId()));
            item.setAvailableTotal(item.getAvailableTotal() - stockBillMap.get(item.getId()));
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });

        //出库单据保存
        opeStockBillService.saveBatch(saveStockBillList);

        //更新库存
        opeStockService.updateBatchById(opeStockList);

        //更新库存条目

        return null;
    }
}
