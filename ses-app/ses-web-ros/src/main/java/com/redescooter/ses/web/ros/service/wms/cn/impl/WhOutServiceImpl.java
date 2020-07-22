package com.redescooter.ses.web.ros.service.wms.cn.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.wms.ConsignMethodEnums;
import com.redescooter.ses.api.common.enums.wms.ConsignTypeEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutEventEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutStatusBEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutStatusEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutTypeEnums;
import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.wms.cn.WhOutServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrder;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrderB;
import com.redescooter.ses.web.ros.dm.OpeOutwhTrace;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import com.redescooter.ses.web.ros.dm.OpeStockPurchas;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCode;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderBService;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderService;
import com.redescooter.ses.web.ros.service.base.OpeOutwhTraceService;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockPurchasService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.wms.cn.WhOutService;
import com.redescooter.ses.web.ros.vo.wms.cn.SavePartProductEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.StartWhOutOrderEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutConsigneeResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutOrderListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutOrderListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutProductListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutProductListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutSaveEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.WhOutWhResult;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:WhOutServiceImpl
 * @description: WhOutServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 10:30
 */

@Log4j
@Service
public class WhOutServiceImpl implements WhOutService {

    @Autowired
    private WhOutServiceMapper whOutServiceMapper;

    @Autowired
    private OpeOutwhOrderService opeOutwhOrderService;

    @Autowired
    private OpeOutwhTraceService opeOutwhTraceService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeStockPurchasService opeStockPurchasService;

    @Autowired
    private OpeStockProdProductService opeStockProdProductService;

    @Autowired
    private OpeOutwhOrderBService opeOutwhOrderBService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Reference
    private IdAppService idAppService;


    /**
     * 出库单列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutOrderListResult> whOrderList(WhOutOrderListEnter enter) {
        Integer count = whOutServiceMapper.whOrderListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.whOrderList(enter));
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public WhOutDetailResult detail(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            //异常
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        return whOutServiceMapper.detail(enter);
    }

    /**
     * 订单节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<CommonNodeResult> nodeList(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        return whOutServiceMapper.nodeList(enter);
    }

    /**
     * 详情产品列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutDetailProductPartListResult> detailProductPartList(WhOutDetailProductPartListEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        int count = whOutServiceMapper.detailProductPartListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.detailProductPartList(enter));
    }

    /**
     * 取消出库单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult cancel(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PENDING.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //修改主订单
        opeOutwhOrder.setStatus(WhOutStatusEnums.CANCELLED.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //保存节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.CANCELLED.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.CANCELLED.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开始出库单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult start(StartWhOutOrderEnter enter) {
        if (ConsignTypeEnums.getEnumsByValue(enter.getConsignType()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CONSIGN_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.CONSIGN_TYPE_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(ConsignTypeEnums.getEnumsByValue(enter.getConsignType()).getValue(), ConsignTypeEnums.AIR_PARCEL.getValue())) {
            if (ConsignMethodEnums.getEnumByValue(enter.getAirParcelType()) == null) {
                throw new SesWebRosException(ExceptionCodeEnums.NO_LOAN.getCode(), ExceptionCodeEnums.NO_LOAN.getMessage());
            }
        }
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PREPARE_MATERIAL.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        //修改主订单
        opeOutwhOrder.setStatus(WhOutStatusEnums.OUT_WH.getValue());
        opeOutwhOrder.setConsignType(enter.getConsignType());
        opeOutwhOrder.setConsignMethod(StringUtils.equals(ConsignTypeEnums.getEnumsByValue(enter.getConsignType()).getValue(), ConsignTypeEnums.AIR_PARCEL.getValue())
                == true ? enter.getAirParcelType() : null);
        opeOutwhOrder.setConsignCompany(enter.getConsignCompany());
        opeOutwhOrder.setTrackingNum(enter.getTrackingN());
        opeOutwhOrder.setAnnex(enter.getAnnex());
        opeOutwhOrder.setLogisticsPrice(new BigDecimal(enter.getPrice()));
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //保存节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.OUT_WH.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.OUT_WH.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 确认备料
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult prepareMaterial(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PENDING.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        //库存锁定
        lockStock(Lists.newArrayList(enter.getId()));

        //修改主订单
        opeOutwhOrder.setStatus(WhOutStatusEnums.PREPARE_MATERIAL.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //保存节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.PREPARE_MATERIAL.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.PREPARE_MATERIAL.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 出库
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult outwh(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (opeOutwhOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PREPARE_MATERIAL.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        //子订单出库
        whOutServiceMapper.outwh(enter.getId());

        //修改主订单
        opeOutwhOrder.setStatus(WhOutStatusEnums.OUT_WH.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //保存节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.OUT_WH.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.OUT_WH.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
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
     * 保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(WhOutSaveEnter enter) {
        List<SavePartProductEnter> savePartProductEnterList = new ArrayList<>();
        try {
            savePartProductEnterList.addAll(JSON.parseArray(enter.getProductList(), SavePartProductEnter.class));
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(savePartProductEnterList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_PART_IS_EMPTY.getCode(), ExceptionCodeEnums.PRODUCT_PART_IS_EMPTY.getMessage());
        }

        //校验收货人
        OpeSysUserProfile opeSysUserProfile = opeSysUserProfileService.getOne(new LambdaQueryWrapper<OpeSysUserProfile>().eq(OpeSysUserProfile::getSysUserId, enter.getUserId()));
        if (opeSysUserProfile == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //校验物流方式
        if (ConsignTypeEnums.getEnumsByValue(enter.getConsignType()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CONSIGN_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.CONSIGN_TYPE_NOT_EXIST.getMessage());
        }
        //查询仓库是否存在
        OpeWhse gogalWh = opeWhseService.getById(enter.getGogalId());
        if (gogalWh == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        //库存校验
        List<OpeStock> opeStocks = new ArrayList<>(opeStockService.listByIds(savePartProductEnterList.stream().map(SavePartProductEnter::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opeStocks)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        //库存校验
        for (SavePartProductEnter item : savePartProductEnterList) {
            Boolean existStock = Boolean.FALSE;
            for (OpeStock stock : opeStocks) {
                if (item.getId().equals(stock.getId())) {
                    if (item.getQty() <= stock.getAvailableTotal()) {
                        existStock = Boolean.TRUE;
                        continue;
                    }
                    //库存不满足 抛出库存不足
                    throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                }
            }
            //无匹配库存 抛出暂无库存呢
            if (!existStock) {
                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
            }
        }

        //主订单新建
        OpeOutwhOrder opeOutwhOrder = buildOpeOutWhOrder(enter, savePartProductEnterList.stream().mapToInt(SavePartProductEnter::getQty).sum(), opeSysUserProfile);

        //子订单新建
        List<OpeOutwhOrderB> opeOutwhOrderBList = new ArrayList<>();
        savePartProductEnterList.forEach(item -> {
            Long productId = opeStocks.stream().filter(stock -> item.getId().equals(stock.getId())).findFirst().orElse(null).getMaterielProductId();
            String productType = opeStocks.stream().filter(stock -> item.getId().equals(stock.getId())).findFirst().orElse(null).getMaterielProductType();
            opeOutwhOrderBList.add(buildOpeOutWhSingle(enter, opeOutwhOrder.getId(), item, productId, productType));
        });

        //主订单保存
        opeOutwhOrderService.save(opeOutwhOrder);
        //子订单保存
        opeOutwhOrderBService.saveBatch(opeOutwhOrderBList);
        //订单节点保存
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.PENDING.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.PENDING.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 收件人集合
     *
     * @param enter
     * @return
     */
    @Override
    public List<WhOutConsigneeResult> consigneeList(GeneralEnter enter) {
        return whOutServiceMapper.consigneeList(enter);
    }

    /**
     * 仓库列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<WhOutWhResult> whList(GeneralEnter enter) {
        List<WhOutWhResult> results = new ArrayList<>();

        List<OpeWhse> whseList = opeWhseService.list(new LambdaQueryWrapper<OpeWhse>().in(OpeWhse::getType, WhseTypeEnums.FR_WHSE.getValue(), WhseTypeEnums.UK_WHSE.getValue()));
        if (CollectionUtils.isEmpty(whseList)) {
            return results;
        }
        whseList.forEach(item -> {
            results.add(WhOutWhResult.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .address(item.getAddress())
                    .build());
        });
        return results;
    }

    /**
     * 发货方式
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> consignType(GeneralEnter enter) {
        Map<String, String> resultMap = new HashMap<>();
        for (ConsignTypeEnums item : ConsignTypeEnums.values()) {
            resultMap.put(item.getCode(), item.getValue());
        }
        return resultMap;
    }

    /**
     * 委托方式
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> consignMethod(StringEnter enter) {
        if (!StringUtils.equals(ConsignTypeEnums.getEnumsByCode(enter.getSt()).getValue(), ConsignTypeEnums.AIR_PARCEL.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.NO_LOAN.getCode(), ExceptionCodeEnums.NO_LOAN.getMessage());
        }
        Map<String, String> resultMap = new HashMap<>();
        for (ConsignMethodEnums item : ConsignMethodEnums.values()) {
            resultMap.put(item.getCode(), item.getValue());
        }
        return resultMap;
    }

    /**
     * 订单状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> statusByCount(GeneralEnter enter) {
        Map<String, Integer> resultMap = new HashMap<>();

        //统计查询
        List<CountByStatusResult> statusResultList = whOutServiceMapper.statusByCount(enter);
        if (CollectionUtils.isNotEmpty(statusResultList)) {
            statusResultList.forEach(item -> {
                resultMap.put(item.getStatus(), item.getTotalCount());
            });
        }
        for (WhOutTypeEnums item : WhOutTypeEnums.values()) {
            if (!resultMap.containsKey(item.getValue())) {
                resultMap.put(item.getValue(), 0);
            }
        }
        return resultMap;
    }

    /**
     * 产品列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutProductListResult> productList(WhOutProductListEnter enter) {
        //采购仓库 成品仓库库存
        Integer count = whOutServiceMapper.productListCount(enter);
        if (count == null) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.productListList(enter));
    }

    /**
     * 保存订单节点
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveNode(SaveNodeEnter enter) {
        OpeOutwhTrace opeOutwhTrace = OpeOutwhTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_OUTWH_TRACE))
                .dr(0)
                .whOutOrderId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(enter.getMemo())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
        opeOutwhTraceService.save(opeOutwhTrace);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 出库单库存锁定
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public void lockStock(List<Long> ids) {

        //查询子订单信息
        List<OpeOutwhOrderB> outwhOrderBList = opeOutwhOrderBService.list(new LambdaQueryWrapper<OpeOutwhOrderB>().in(OpeOutwhOrderB::getOutwhOrderId, ids));
        if (CollectionUtils.isEmpty(outwhOrderBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }

        //库存锁定
        List<OpeStock> opeStocks = new ArrayList<>(opeStockService.listByIds(outwhOrderBList.stream().map(OpeOutwhOrderB::getStockId).collect(Collectors.toSet())));
        if (CollectionUtils.isEmpty(opeStocks)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }

        //库存总表锁定库存
        outwhOrderBList.forEach(item -> {
            for (OpeStock stock : opeStocks) {
                //库存不足
                if (stock.getAvailableTotal() < item.getTotalCount()) {
                    throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
                }
                //库存锁定
                if (item.getStockId().equals(stock.getId())) {
                    stock.setLockTotal(stock.getLockTotal() + item.getTotalCount());
                    stock.setAvailableTotal(stock.getAvailableTotal() - item.getTotalCount());
                    stock.setUpdatedTime(new Date());
                    break;
                }
            }
        });

        //部件子表锁定库存
        List<OpeStockPurchas> stockPurchasList = lockOpeStockPurchas(outwhOrderBList, opeStocks);
        //整车锁定
        List<OpeStockProdProduct> stockProdProductList = lockOpeStockProdProductsSingle(outwhOrderBList, opeStocks);

        //库存总表更新
        opeStockService.updateBatch(opeStocks);
        //子表更新
        opeStockPurchasService.updateBatch(stockPurchasList);
        opeStockProdProductService.updateBatch(stockProdProductList);
    }

    /**
     * 部件锁定
     *
     * @param outwhOrderBList
     * @param opeStocks
     * @return
     */
    private List<OpeStockPurchas> lockOpeStockPurchas(List<OpeOutwhOrderB> outwhOrderBList, List<OpeStock> opeStocks) {
        //库存部件Id
        List<Long> opeStockPurchasIds =
                opeStocks.stream().filter(item -> {
                    if (!StringUtils.equals(item.getMaterielProductType(), BomCommonTypeEnums.SCOOTER.getValue()) || !StringUtils.equals(item.getMaterielProductType(),
                            BomCommonTypeEnums.COMBINATION.getValue())) {
                        return true;
                    }
                    return false;
                }).map(OpeStock::getId).collect(Collectors.toList());

        //查询部件子表
        List<OpeStockPurchas> stockPurchasList = opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>().eq(OpeStockPurchas::getStatus,
                StockProductPartStatusEnums.AVAILABLE.getValue()).in(OpeStockPurchas::getStockId, opeStockPurchasIds).orderByDesc(OpeStockPurchas::getCreatedTime));
        if (CollectionUtils.isEmpty(stockPurchasList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        for (int i = 0; i < outwhOrderBList.size(); i++) {
            OpeOutwhOrderB opeOutwhOrderB = outwhOrderBList.get(i);

            for (int j = 0; j < stockPurchasList.size(); j++) {
                OpeStockPurchas opeStockPurchas = stockPurchasList.get(j);

                if (opeOutwhOrderB.getStockId().equals(opeStockPurchas.getStockId())) {

                    opeStockPurchas.setBindOrderId(opeOutwhOrderB.getOutwhOrderId());
                    opeStockPurchas.setSourceType(SourceTypeEnums.WH_OUT.getValue());
                    opeStockPurchas.setStatus(StockProductPartStatusEnums.LOCKING.getValue());
                    opeStockPurchas.setUpdatedTime(new Date());
                    break;
                }
            }
        }
        return stockPurchasList;
    }

    /**
     * 整车锁定
     *
     * @param outwhOrderBList
     * @param opeStocks
     * @return
     */
    private List<OpeStockProdProduct> lockOpeStockProdProductsSingle(List<OpeOutwhOrderB> outwhOrderBList, List<OpeStock> opeStocks) {
        //库存整车Id
        List<Long> opeStockProductIds =
                opeStocks.stream().filter(item -> StringUtils.equals(item.getMaterielProductType(), BomCommonTypeEnums.SCOOTER.getValue())).map(OpeStock::getId).collect(Collectors.toList());
        //查询整车子表
        List<OpeStockProdProduct> stockProdProductList = opeStockProdProductService.list(new LambdaQueryWrapper<OpeStockProdProduct>().eq(OpeStockProdProduct::getStatus,
                StockProductPartStatusEnums.AVAILABLE.getValue()).in(OpeStockProdProduct::getStockId, opeStockProductIds).orderByDesc(OpeStockProdProduct::getCreatedTime));

        if (CollectionUtils.isEmpty(stockProdProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        for (int i = 0; i < outwhOrderBList.size(); i++) {
            OpeOutwhOrderB opeOutwhOrderB = outwhOrderBList.get(i);

            for (int j = 0; j < stockProdProductList.size(); j++) {
                OpeStockProdProduct opeStockProdProduct = stockProdProductList.get(j);

                if (opeOutwhOrderB.getStockId().equals(opeStockProdProduct.getStockId())) {

                    opeStockProdProduct.setBindOrderId(opeOutwhOrderB.getOutwhOrderId());
                    opeStockProdProduct.setSourceType(SourceTypeEnums.WH_OUT.getValue());
                    opeStockProdProduct.setStatus(StockProductPartStatusEnums.LOCKING.getValue());
                    opeStockProdProduct.setUpdatedTime(new Date());
                    break;
                }
            }
        }
        return stockProdProductList;
    }

    /**
     * 主订单新建
     *
     * @param enter
     * @param totalCount
     * @param opeSysUserProfile
     * @return
     */
    private OpeOutwhOrder buildOpeOutWhOrder(WhOutSaveEnter enter, int totalCount, OpeSysUserProfile opeSysUserProfile) {
        return OpeOutwhOrder.builder()
                .id(idAppService.getId(SequenceName.OPE_OUTWH_ORDER))
                .dr(0)
                .orderNo(RandomUtil.randomStringUpper(8))
                .inWhId(enter.getGogalId())
                .address(null)
                .consigneeId(enter.getConsigneeId())
                .consigneePhone(opeSysUserProfile.getTelNumber())
                .consigneeEmail(opeSysUserProfile.getEmail())
                .countryCode(opeSysUserProfile.getCountryCode())
                .notifyFirstName(enter.getNotifyFirstName())
                .notifyLastName(enter.getNotifyLastName())
                .consignType(enter.getConsignType())
                .status(WhOutStatusEnums.PENDING.getValue())
                .productCount(totalCount)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    /**
     * 子订单新建
     *
     * @param enter
     * @param orderId
     * @param item
     * @param productId
     * @param productType
     * @return
     */
    private OpeOutwhOrderB buildOpeOutWhSingle(WhOutSaveEnter enter, Long orderId, SavePartProductEnter item, Long productId, String productType) {
        return OpeOutwhOrderB
                .builder()
                .id(idAppService.getId(SequenceName.OPE_OUTWH_ORDER_B))
                .outwhOrderId(orderId)
                .stockId(item.getId())
                .partProductId(productId)
                .productType(productType)
                .totalCount(item.getQty())
                .lastOutCount(item.getQty())
                .status(WhOutStatusBEnums.UNPREPARED.getValue())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }
}
