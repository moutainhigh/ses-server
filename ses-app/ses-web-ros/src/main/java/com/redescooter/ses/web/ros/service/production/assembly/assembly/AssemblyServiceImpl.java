package com.redescooter.ses.web.ros.service.production.assembly.assembly;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.PaymentTypeEnums;
import com.redescooter.ses.api.common.enums.production.ProductionTypeEnums;
import com.redescooter.ses.api.common.enums.production.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PayStatusEnums;
import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.production.AssemblyServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBOrder;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrder;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyBOrderService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderPaymentService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.production.assembly.AssemblyService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.PaymentItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.ProductionPartsEnter;
import com.redescooter.ses.web.ros.vo.production.StagingPaymentEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyListEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyResult;
import com.redescooter.ses.web.ros.vo.production.assembly.SaveAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.SetPaymentAssemblyEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:AssemblyServiceImpl
 * @description: AssemblyServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:04
 */
@Service
public class AssemblyServiceImpl implements AssemblyService {

    @Autowired
    private AssemblyServiceMapper assemblyServiceMapper;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeFactoryService opeFactoryService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpePartsProductBService opePartsProductBService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeAssemblyOrderPaymentService opeAssemblyOrderPaymentService;

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAssemblyBOrderService opeAssemblyOrderBService;

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByTypes(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResult = assemblyServiceMapper.countByTypes(enter);

        Map<String, Integer> result = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(countByStatusResult)) {
            countByStatusResult.forEach(item -> {
                result.put(item.getStatus(), item.getTotalCount());
            });
        }
        for (AssemblyStatusEnums item : AssemblyStatusEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), 0);
            }
        }
        return result;
    }

    /**
     * 状态列表
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> statusList(GeneralEnter enter) {
        Map<String, String> result = Maps.newHashMap();
        for (AssemblyStatusEnums item : AssemblyStatusEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), item.getCode());
            }
        }
        return result;
    }

    /**
     * 付款方式
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> paymentTypeList(GeneralEnter enter) {
        Map<String, String> result = Maps.newHashMap();
        for (PaymentTypeEnums item : PaymentTypeEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), item.getCode());
            }
        }
        return result;
    }

    /**
     * 查询可组装的商品列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveAssemblyProductResult> queryAssemblyProduct(SaveAssemblyProductEnter enter) {
        List<SaveAssemblyProductResult> result = Lists.newArrayList();

        //获取 产品bom规则
        Map<Long, List<OpePartsProductB>> productMap = Maps.newHashMap();

        List<OpePartsProduct> opePartsProduct = new ArrayList<>();
        //获取库存
        List<OpeStock> opeStockList = new ArrayList<>();
        if (buildProductMap(productMap, opePartsProduct, opeStockList)) {
            return new ArrayList<>();
        }
        //可进行组装的车辆Id
        List<Long> productIds = Lists.newArrayList();

        //减库存
        if (StringUtils.isNotEmpty(enter.getProductList())) {
            List<ProductionPartsEnter> productList = new ArrayList<>();
            try {
                productList = JSON.parseArray(enter.getProductList(), ProductionPartsEnter.class);
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }

            productList.forEach(product -> {
                if (!productMap.containsKey(product.getId())) {
                    return;
                }
                for (OpePartsProductB item : productMap.get(product.getId())) {
                    opeStockList.forEach(stock -> {
                        if (stock.getMaterielProductId().equals(item.getPartsId())) {
                            stock.setAvailableTotal(stock.getAvailableTotal() - item.getPartsQty() * product.getQty());
                        }
                    });
                }
            });
        }

        //筛选出可组装的产品
        for (Map.Entry<Long, List<OpePartsProductB>> e : productMap.entrySet()) {
            Long k = e.getKey();
            //如果有任何 配件不满足库存要求 终止 该产品的查询
            int total = 0;
            flag:
            for (OpePartsProductB item : e.getValue()) {
                for (OpeStock stock : opeStockList) {
                    if (!item.getPartsProductId().equals(k)) {
                        break flag;
                    }
                    if (item.getPartsId().equals(stock.getMaterielProductId()) && stock.getAvailableTotal() < item.getPartsQty()) {
                        break flag;
                    }
                    if (item.getPartsId().equals(stock.getMaterielProductId()) && stock.getAvailableTotal() > item.getPartsQty()) {
                        total++;
                    }
                }
            }
            if (total == e.getValue().size()) {
                productIds.add(k);
            }
        }

        // 确认可组装的产品的最大值
        Map<Long, Integer> canAssembledMap = Maps.newHashMap();
        for (Map.Entry<Long, List<OpePartsProductB>> entry : productMap.entrySet()) {
            Long key = entry.getKey();
            List<OpePartsProductB> value = entry.getValue();
            Integer maxTotal = 0;
            int total = 0;
            flag:
            if (productIds.contains(key)) {
                for (OpePartsProductB item : value) {
                    for (OpeStock stock : opeStockList) {
                        if (item.getPartsId().equals(stock.getMaterielProductId()) && item.getPartsQty() * maxTotal++ > stock.getAvailableTotal()) {
                            break flag;
                        }
                        if (item.getPartsId().equals(stock.getMaterielProductId()) && item.getPartsQty() * maxTotal++ < stock.getAvailableTotal()) {
                            total++;
                        }
                    }
                }
            }
            if (total == value.size()) {
                canAssembledMap.put(key, maxTotal);
            }
        }


        opePartsProduct.forEach(item -> {
            SaveAssemblyProductResult productResult = null;
            if (productMap.containsKey(item.getId())) {
                productResult = SaveAssemblyProductResult.builder()
                        .id(item.getId())
                        .productN(item.getProductNumber())
                        .enName(item.getEnName())
                        .cnName(item.getCnName())
                        .stocks(0)
                        .build();
                if (canAssembledMap.containsKey(item.getId())) {
                    productResult.setStocks(canAssembledMap.get(item.getId()));
                }
            }
            result.add(productResult);
        });
        return result;
    }

    private Boolean buildProductMap(Map<Long, List<OpePartsProductB>> productMap, List<OpePartsProduct> opePartsProduct, List<OpeStock> opeStockList) {
        //查询所有组装商品
        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_DR, 0);
        opePartsProduct.addAll(opePartsProductService.list(opePartsProductQueryWrapper));

        if (CollectionUtils.isEmpty(opePartsProduct)) {
            return true;
        }
        List<Long> productIdsList = Lists.newArrayList();
        opePartsProduct.forEach(item -> {
            productIdsList.add(item.getId());
        });

        //查询整车组装配方
        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_DR, 0);
        opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_PRODUCT_ID, productIdsList);
        List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);

        if (CollectionUtils.isEmpty(partsProductBList)) {
            return true;
        }

        opePartsProduct.forEach(scooter -> {
            List<OpePartsProductB> productList = Lists.newArrayList();
            partsProductBList.forEach(item -> {
                if (scooter.getId().equals(item.getPartsProductId())) {
                    productList.add(item);
                }
            });
            if (CollectionUtils.isNotEmpty(productList)) {
                productMap.put(scooter.getId(), productList);
            }
        });

        //查询仓库信息
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, 0);
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockList.addAll(opeStockService.list(opeStockQueryWrapper));
        if (CollectionUtils.isEmpty(opeStockList)) {
            return true;
        }
        return false;
    }

    /**
     * 保存组装单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveAssembly(SaveAssemblyEnter enter) {
        return null;
    }

    /**
     * 工厂列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<FactoryCommonResult> factoryList(GeneralEnter enter) {
        List<FactoryCommonResult> result = new ArrayList<>();
        QueryWrapper<OpeFactory> opeFactoryQueryWrapper = new QueryWrapper();
        opeFactoryQueryWrapper.eq(OpeFactory.COL_DR, 0);
        List<OpeFactory> opeFactoryList = opeFactoryService.list(opeFactoryQueryWrapper);
        opeFactoryList.forEach(item -> {
            result.add(FactoryCommonResult.builder()
                    .id(item.getId())
                    .factoryName(item.getFactoryName())
                    .contactFullName(item.getContactFullName())
                    .contactEmail(item.getContactEmail())
                    .contactPhone(item.getContactPhone())
                    .contactPhoneCode(item.getContactPhoneCountryCode())
                    .build());
        });
        return result;
    }

    /**
     * 收件人列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ConsigneeResult> consigneeList(GeneralEnter enter) {
        List<ConsigneeResult> consigneeResultlist = new ArrayList<>();
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, 0);
        opeSysUserProfileQueryWrapper.ne(OpeSysUserProfile.COL_SYS_USER_ID, Constant.ADMINUSERID);
        List<OpeSysUserProfile> opeSysUserProfileList = opeSysUserProfileService.list(opeSysUserProfileQueryWrapper);
        opeSysUserProfileList.forEach(item -> {
            consigneeResultlist.add(ConsigneeResult.builder()
                    .id(item.getSysUserId())
                    .firstName(item.getFirstName())
                    .lastName(item.getLastName())
                    .phoneCountryCode(item.getCountryCode())
                    .phone(item.getTelNumber())
                    .build());
        });
        return consigneeResultlist;
    }

    /**
     * 组装列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AssemblyResult> list(AssemblyListEnter enter) {
        //对type 进行拆分 组装statusList
        List<String> statusList = Lists.newArrayList();
        if (StringUtils.equals(enter.getType(), ProductionTypeEnums.TODO.getValue())) {
            for (AssemblyStatusEnums item : AssemblyStatusEnums.values()) {
                statusList.add(item.getValue());
            }
            statusList.remove(AssemblyStatusEnums.CANCELLED.getValue());
            statusList.remove(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        }
        if (StringUtils.equals(enter.getType(), ProductionTypeEnums.TODO.getValue())) {
            statusList.add(AssemblyStatusEnums.CANCELLED.getValue());
            statusList.add(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        }

        int count = assemblyServiceMapper.assemblyListCount(enter, statusList);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<AssemblyResult> assemblyResultList = assemblyServiceMapper.assemblyList(enter, statusList);
        if (CollectionUtils.isEmpty(assemblyResultList)) {
            return PageResult.createZeroRowResult(enter);
        }

        List<Long> assemblyIds = Lists.newArrayList();
        assemblyResultList.forEach(item -> {
            assemblyIds.add(item.getId());
        });

        //获取支付信息
        List<PaymentItemDetailResult> opeAssemblyOrderPaymentList = assemblyServiceMapper.paymentItemDetailListByAssIds(assemblyIds);

        for (AssemblyResult item : assemblyResultList) {
            //月结信息 赋值
            if (StringUtils.equals(item.getPaymentType(), PaymentTypeEnums.MONTHLY_PAY.getValue())) {
                for (PaymentItemDetailResult payment : opeAssemblyOrderPaymentList) {
                    if (item.getId().equals(payment.getBizId())) {
                        item.setStatementDate(payment.getEstimatedPaymentDate());
                        item.setDays(payment.getDayNum());
                    }
                }

            } else {
                //分期信息赋值
                int payTotal = 0;
                int unpayTotal = 0;
                List<PaymentItemDetailResult> paymentList = Lists.newArrayList();
                for (PaymentItemDetailResult payment : opeAssemblyOrderPaymentList) {
                    if (item.getId().equals(payment.getBizId())) {
                        if (StringUtils.equals(payment.getStatus(), PayStatusEnums.UNPAID.getValue())) {
                            unpayTotal++;
                        }
                        if (StringUtils.equals(payment.getStatus(), PayStatusEnums.PAID.getValue())) {
                            payTotal++;
                        }
                        paymentList.add(payment);
                    }
                }
                item.setStagTotal(payTotal + unpayTotal);
                item.setPaidstagNum(payTotal);
                item.setPaymentItemDetailResultList(paymentList);
            }
        }

        return PageResult.create(enter, count, assemblyResultList);
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public AssemblyResult detail(IdEnter enter) {
        //组装单校验
        checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.detail(enter);
    }


    /**
     * 组装单节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<CommonNodeResult> assemblyNode(IdEnter enter) {
        checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.assemblyNode(enter);
    }

    /**
     * 组装单信息导出
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult export(IdEnter enter) {
        return null;
    }

    /**
     * 设置支付信息
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult setPaymentAssembly(SetPaymentAssemblyEnter enter) {
        //付款信息转换
        List<StagingPaymentEnter> paymentList = null;
        try {
            if (StringUtils.equals(PaymentTypeEnums.STAGING.getValue(), enter.getPaymentType())) {
                paymentList = JSONArray.parseArray(enter.getPaymentInfoList(), StagingPaymentEnter.class);
            }
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        //组装单校验
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.PENDING.getValue());

        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_DR, 0);
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ASSEMBLY_ID, opeAssemblyOrder.getId());
        List<OpeAssemblyBOrder> assemblyBOrderList = opeAssemblyOrderBService.list(opeAssemblyBOrderQueryWrapper);


        return null;
    }

    /**
     * 质检记录
     *
     * @param enter
     * @return
     */
    @Override
    public List<AssemblyQcResult> assemblyQcTrces(AssemblyQcEnter enter) {
        return null;
    }

    /**
     * 取消组装单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult cancle(IdEnter enter) {
        return null;
    }

    /**
     * 开始备料
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult startPrepare(IdEnter enter) {
        return null;
    }

    /**
     * 开始组装
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult startAssembly(IdEnter enter) {
        return null;
    }

    /**
     * 开始质检
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult startQc(IdEnter enter) {
        return null;
    }

    /**
     * Qc 完成
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult completeQc(IdEnter enter) {
        return null;
    }

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult inWh(IdEnter enter) {
        return null;
    }

    /**
     * 保存节点
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveNode(SaveNodeEnter enter) {
        return null;
    }

    /**
     * 组装单校验
     *
     * @param id
     * @param status
     * @return
     */
    private OpeAssemblyOrder checkAssembly(Long id, String status) {
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(id);
        if (opeAssemblyOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSEMBLY_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.isNotEmpty(status)) {
            if (StringUtils.equals(status, opeAssemblyOrder.getStatus())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
            }
        }
        return opeAssemblyOrder;
    }
}
