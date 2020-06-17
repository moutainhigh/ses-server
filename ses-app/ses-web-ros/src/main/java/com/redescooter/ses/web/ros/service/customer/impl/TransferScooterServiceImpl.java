package com.redescooter.ses.web.ros.service.customer.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountResult;
import com.redescooter.ses.api.hub.service.corporate.CorporateScooterService;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.vo.HubSaveScooterEnter;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.CustomerServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeStockBillService;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.customer.TransferScooterService;
import com.redescooter.ses.web.ros.vo.customer.*;
import com.redescooter.ses.web.ros.vo.transferscooter.ChooseScooterListResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
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
    private CustomerServiceMapper customerServiceMapper;

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private OpeStockProdProductService opeStockProdProductService;

    @Reference
    private ScooterService scooterService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpeStockService opeStockService;

    @Reference
    private AccountBaseService accountBaseService;

    @Reference
    private CorporateScooterService corporateScooterService;

    @Reference
    private CusotmerScooterService cusotmerScooterService;

    @Reference
    private IdAppService idAppService;


    /**
     * 车辆用户分配信息
     *
     * @param enter
     */
    @Override
    public PageResult<ScooterCustomerResult> scooterCustomerResult(PageEnter enter) {
        int count = customerServiceMapper.scooterCustomerCount(enter);
        if (count == 0) {
            PageResult.createZeroRowResult(enter);
        }
        List<ScooterCustomerResult> scooterCustomerResults = customerServiceMapper.scooterCustomerResult(enter);

        // 查询是否有库存
        List<OpeStockProdProduct> opeStockProdProductList = opeStockProdProductService.list(new LambdaQueryWrapper<OpeStockProdProduct>().eq(OpeStockProdProduct::getStatus,
                StockProductPartStatusEnums.AVAILABLE.getValue()).eq(OpeStockProdProduct::getDr,0));
        if (CollectionUtils.isEmpty(opeStockProdProductList)){
            scooterCustomerResults.forEach(item->{
                item.setHasProductStock(Boolean.FALSE);
            });
        }else {
            scooterCustomerResults.forEach(item->{
                item.setHasProductStock(Boolean.TRUE);
            });
        }

        return PageResult.create(enter, count, scooterCustomerResults);
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //查询分配整车列表
     * @Date 2020/4/24 17:01
     * @Param [enter]
     */
    @Override
    public ChooseScooterListResult chooseScooterList(IdEnter enter) {
        OpeCustomer opeCustomer = opeCustomerService.getById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        int allocateScooterQty = opeCustomer.getScooterQuantity() - opeCustomer.getAssignationScooterQty();

        //查询可分配的整车列表
        QueryWrapper<OpeStockProdProduct> opeStockProdProductQueryWrapper = new QueryWrapper<>();
        opeStockProdProductQueryWrapper.eq(OpeStockProdProduct.COL_DR, 0);
        opeStockProdProductQueryWrapper.eq(OpeStockProdProduct.COL_STATUS, StockProductPartStatusEnums.AVAILABLE.getValue());
        List<OpeStockProdProduct> opeStockProdProductList = opeStockProdProductService.list(opeStockProdProductQueryWrapper);

        List<ChooseScooterResult> resultList = Lists.newArrayList();
        //封装反参
        if (CollectionUtils.isNotEmpty(opeStockProdProductList)) {
            opeStockProdProductList.forEach(item -> {
                resultList.add(
                        ChooseScooterResult.builder()
                                .id(item.getId())
                                .batchNum(item.getLot())
                                .serilNum(item.getSerialNumber())
                                .build()
                );
            });
        }

        return ChooseScooterListResult.builder()
                .allocateScooterQty(CollectionUtils.isEmpty(opeStockProdProductList) == true ? 0 : (allocateScooterQty > opeStockProdProductList.size() ? opeStockProdProductList.size() :
                        allocateScooterQty))
                .chooseScooterResultList(resultList)
                .build();
    }

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
        //解析车牌号 合适 AA-001-AA
        for (TransferScooterListEnter transferScooterListEnter : transferScooterListEnterList) {
            if (StringUtils.isEmpty(transferScooterListEnter.getNumberPlate())) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            StringBuilder numberPlate = new StringBuilder();
            numberPlate
                    .append(transferScooterListEnter.getNumberPlate().substring(0, 2)).
                    append("-").
                    append(transferScooterListEnter.getNumberPlate().substring(2,transferScooterListEnter.getNumberPlate().length() - 2))
                    .append("-")
                    .append(transferScooterListEnter.getNumberPlate().substring(transferScooterListEnter.getNumberPlate().length() - 2));
            transferScooterListEnter.setNumberPlate(numberPlate.toString());
        }


        //验证客户 状态
        OpeCustomer opeCustomer = opeCustomerService.getById(enter.getId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getCode(),
                    ExceptionCodeEnums.CONVERT_TO_FORMAL_CUSTOMER_FIRST.getMessage());
        }

        //验证前端输入的车辆数量
        if (opeCustomer.getAssignationScooterQty() != null && opeCustomer.getAssignationScooterQty() != 0) {
            if ((opeCustomer.getScooterQuantity()-opeCustomer.getAssignationScooterQty()) < transferScooterListEnterList.size()) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_TRANSFERSCOOTER_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.CUSTOMER_TRANSFERSCOOTER_QTY_IS_WRONG.getMessage());
            }
        }
        //验证可以已分配的车辆数量
        if (opeCustomer.getAssignationScooterQty() != null && opeCustomer.getAssignationScooterQty().equals(0)) {
            if (opeCustomer.getScooterQuantity() < transferScooterListEnterList.size()) {
                throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_TRANSFERSCOOTER_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.CUSTOMER_TRANSFERSCOOTER_QTY_IS_WRONG.getMessage());
            }
        }

        //客户采购的车辆 已完成分配数量 无需分配
        if (opeCustomer.getAssignationScooterQty().equals(opeCustomer.getScooterQuantity())) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_IS_NOT_NEED_ALLOCATION_SCOOTER.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_NEED_ALLOCATION_SCOOTER.getMessage());
        }

        //校验车牌号是否已经存在
        Set<String> scooterPlates = new HashSet<>();
        transferScooterListEnterList.forEach(item -> {
            if (scooterPlates.contains(item.getNumberPlate())) {
                throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_PLATES_NOT_REPEATABLE.getCode(), ExceptionCodeEnums.SCOOTER_PLATES_NOT_REPEATABLE.getMessage());
            }
            scooterPlates.add(item.getNumberPlate());
        });
        //查询车辆基本信息
        List<BaseScooterResult> baseScooterResultList = scooterService.scooterInforByPlates(new ArrayList<>(scooterPlates));
        if (CollectionUtils.isNotEmpty(baseScooterResultList)) {
            throw new SesWebRosException(ExceptionCodeEnums.SCOOTER_IS_ALREADY_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_ALREADY_EXIST.getMessage());
        }

        //验证库存信息
        Collection<OpeStockProdProduct> opeStockProdProductList =
                opeStockProdProductService.listByIds(transferScooterListEnterList.stream().map(TransferScooterListEnter::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(opeStockProdProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        if (opeStockProdProductList.size() != transferScooterListEnterList.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        //修改状态
        opeStockProdProductList.forEach(item -> {
            if (!item.getStatus().equals(StockProductPartStatusEnums.AVAILABLE.getValue())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
            }
            item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });

        //查询库存
        Collection<OpeStock> opeStockList =
                opeStockService.listByIds(opeStockProdProductList.stream().map(OpeStockProdProduct::getStockId).collect(Collectors.toList()));
        if(CollectionUtils.isEmpty(opeStockList)){
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
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
            saveStockBillList.add(buildStockBillSingle(enter, key, value));
        });
        //库存更新
        opeStockList.forEach(item -> {
            if (stockBillMap.get(item.getId()) > item.getAvailableTotal()) {
                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(),
                        ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
            }
            item.setOutTotal(item.getOutTotal() + stockBillMap.get(item.getId()));
            item.setAvailableTotal(item.getAvailableTotal() - stockBillMap.get(item.getId()));
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });

        // 维护客户表中 客户的车辆分配数量的字段
        if (opeCustomer.getScooterQuantity() != null && opeCustomer.getScooterQuantity() != 0) {
            opeCustomer.setAssignationScooterQty(opeCustomer.getAssignationScooterQty() + Long.valueOf(stockBillMap.values().stream().count()).intValue());
        } else {
            opeCustomer.setAssignationScooterQty(Long.valueOf(stockBillMap.values().stream().count()).intValue());
        }
        opeCustomer.setUpdatedBy(enter.getUserId());
        opeCustomer.setUpdatedTime(new Date());
        opeCustomerService.updateById(opeCustomer);

        //查询客户的账号信息
        QueryAccountResult queryAccountResult = accountBaseService.customerAccountDeatil(opeCustomer.getEmail());

        //车辆信息保存Scooter 库
        List<BaseScooterEnter> savBaseScooterEnterList = Lists.newArrayList();
        transferScooterListEnterList.forEach(item -> {
            BaseScooterEnter baseScooterEnter = buildScooter(item, opeStockProdProductList);
            BeanUtils.copyProperties(enter, baseScooterEnter);
            baseScooterEnter.setId(item.getId());
            savBaseScooterEnterList.add(baseScooterEnter);
        });

        //客户和车辆产生绑定关系
        List<HubSaveScooterEnter> saveScooterEnterList = new ArrayList<>();


        //将数据存储到相对应的 corporate、cumstomer 数据库
        if (StringUtils.equals(opeCustomer.getCustomerType(), CustomerTypeEnum.ENTERPRISE.getValue())) {
            transferScooterListEnterList.forEach(item -> {
                HubSaveScooterEnter scooterEnter = buildHubSaveScooterEnterSingle(enter, item);
                BeanUtils.copyProperties(enter, scooterEnter);
                scooterEnter.setUserId(queryAccountResult.getId());
                scooterEnter.setTenantId(queryAccountResult.getTenantId());
                scooterEnter.setScooterId(item.getId());
                saveScooterEnterList.add(scooterEnter);
            });
            corporateScooterService.saveScooter(saveScooterEnterList);
        }
        //将数据 保存到 customer、corporate 数据库
        if (StringUtils.equals(opeCustomer.getCustomerType(), CustomerTypeEnum.PERSONAL.getValue())) {
            transferScooterListEnterList.forEach(item -> {
                HubSaveScooterEnter scooterEnter = buildHubSaveScooterEnterSingle(enter, item);
                BeanUtils.copyProperties(enter, scooterEnter);
                scooterEnter.setUserId(queryAccountResult.getId());
                scooterEnter.setTenantId(queryAccountResult.getTenantId());
                scooterEnter.setScooterId(item.getId());
                saveScooterEnterList.add(scooterEnter);
            });
            cusotmerScooterService.saveScooter(saveScooterEnterList);
        }

        //将数据存储到Scooter库
        scooterService.saveScooter(savBaseScooterEnterList);

        //出库单据保存
        opeStockBillService.saveBatch(saveStockBillList);

        //更新库存
        opeStockService.updateBatchById(opeStockList);

        //更新库存条目
        opeStockProdProductService.updateBatchById(opeStockProdProductList);

        return new GeneralResult(enter.getRequestId());
    }

    private HubSaveScooterEnter buildHubSaveScooterEnterSingle(TransferScooterEnter enter, TransferScooterListEnter item) {
        HubSaveScooterEnter scooterEnter = HubSaveScooterEnter.builder()
                .scooterId(item.getId())
                .model(ScooterModelEnums.SCOOTER_125_CC.getValue())
                .longitude(Constant.LONGITUDE)
                .latitude(Constant.LATITUDE)
                .licensePlate(item.getNumberPlate())
                .licensePlatePicture(null)
                .status(ScooterStatusEnums.AVAILABLE.getValue())
                .build();
        BeanUtils.copyProperties(enter, scooterEnter);
        return scooterEnter;
    }

    private OpeStockBill buildStockBillSingle(TransferScooterEnter enter, Long key, Integer value) {
        return OpeStockBill.builder()
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
                .build();
    }

    private BaseScooterEnter buildScooter(TransferScooterListEnter item, Collection<OpeStockProdProduct> opeStockProdProductList) {
        OpeStockProdProduct opeStockProdProduct = null;
        for (OpeStockProdProduct product : opeStockProdProductList) {
            if (item.getId().equals(product.getId())) {
                opeStockProdProduct = product;
            }
        }
        return BaseScooterEnter.builder()
                .id(item.getId())
                .scooterNo(opeStockProdProduct.getSerialNumber())
                .status(ScooterLockStatusEnums.LOCK.getValue())
                .availableStatus(ScooterStatusEnums.AVAILABLE.getValue())
                .boxStatus(ScooterLockStatusEnums.LOCK.getValue())
                .model(ScooterModelEnums.SCOOTER_125_CC.getValue())
                .licensePlate(item.getNumberPlate())
                .licensePlatePicture(null)
                .licensePlateTime(new Date())
                .scooterIdPicture(null)
                .insurance(null)
                .insureTime(new Date())
                .revision(0)
                .scooterEcuId(0L)
                .longitule(BigDecimal.ZERO)
                .latitude(BigDecimal.ZERO)
                .battery(100)
                .build();
    }
}
