package com.redescooter.ses.web.ros.service.wms.cn.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.wms.ConsignMethodEnums;
import com.redescooter.ses.api.common.enums.wms.ConsignTypeEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutClassTypeEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutEventEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutStatusBEnums;
import com.redescooter.ses.api.common.enums.wms.WhOutStatusEnums;
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
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.wms.cn.WhOutServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeFrStock;
import com.redescooter.ses.web.ros.dm.OpeFrStockBill;
import com.redescooter.ses.web.ros.dm.OpeFrStockProduct;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrder;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrderB;
import com.redescooter.ses.web.ros.dm.OpeOutwhTrace;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import com.redescooter.ses.web.ros.dm.OpeStockPurchas;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeFrStockBillService;
import com.redescooter.ses.web.ros.service.base.OpeFrStockProductService;
import com.redescooter.ses.web.ros.service.base.OpeFrStockService;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderBService;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderService;
import com.redescooter.ses.web.ros.service.base.OpeOutwhTraceService;
import com.redescooter.ses.web.ros.service.base.OpeStockBillService;
import com.redescooter.ses.web.ros.service.base.OpeStockProdProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockPurchasService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.wms.cn.WhOutService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @Version???1.3
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

    @Autowired
    private OpeFrStockService opeFrStockService;

    @Autowired
    private OpeFrStockProductService opeFrStockProductService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpeFrStockBillService opeFrStockBillService;

    @DubboReference
    private IdAppService idAppService;


    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutOrderListResult> whOrderList(WhOutOrderListEnter enter) {
        List<String> statusList = new ArrayList<>();
        if (StringUtils.equals(enter.getClassType(), WhOutClassTypeEnums.TODO.getValue())) {
            statusList.add(WhOutStatusEnums.PENDING.getValue());
            statusList.add(WhOutStatusEnums.PREPARE_MATERIAL.getValue());
            statusList.add(WhOutStatusEnums.OUT_WH.getValue());
            statusList.add(WhOutStatusEnums.START_PREPARE_MATERIAL.getValue());
        } else {
            statusList.add(WhOutStatusEnums.CANCELLED.getValue());
            statusList.add(WhOutStatusEnums.IN_WH.getValue());
        }

        Integer count = whOutServiceMapper.whOrderListCount(enter, statusList);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.whOrderList(enter, statusList));
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    public WhOutDetailResult detail(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutwhOrder)) {
            //??????
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        return whOutServiceMapper.detail(enter);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<CommonNodeResult> nodeList(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutwhOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        return whOutServiceMapper.nodeList(enter);
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutDetailProductPartListResult> detailProductPartList(WhOutDetailProductPartListEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutwhOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        int count = whOutServiceMapper.detailProductPartListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.detailProductPartList(enter));
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult cancel(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutwhOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PENDING.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //???????????????
        opeOutwhOrder.setStatus(WhOutStatusEnums.CANCELLED.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //????????????
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
     * ???????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult start(StartWhOutOrderEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutwhOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PENDING.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        if (StringUtils.equals(opeOutwhOrder.getConsignType(), ConsignTypeEnums.AIR_PARCEL.getValue())) {
            if (StringManaConstant.entityIsNull(ConsignMethodEnums.getEnumByValue(enter.getAirParcelType()))) {
                throw new SesWebRosException(ExceptionCodeEnums.NO_LOAN.getCode(), ExceptionCodeEnums.NO_LOAN.getMessage());
            }
        }

        //???????????????
        opeOutwhOrder.setStatus(WhOutStatusEnums.START_PREPARE_MATERIAL.getValue());
        opeOutwhOrder.setConsignType(opeOutwhOrder.getConsignType());
        opeOutwhOrder.setConsignMethod(StringUtils.equals(opeOutwhOrder.getConsignType(), ConsignTypeEnums.AIR_PARCEL.getValue()) == true ? enter.getAirParcelType() : null);
        opeOutwhOrder.setConsignCompany(enter.getConsignCompany());
        opeOutwhOrder.setTrackingNum(enter.getTrackingN());
        opeOutwhOrder.setAnnex(enter.getAnnex());
        opeOutwhOrder.setLogisticsPrice(new BigDecimal(enter.getPrice()));
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //????????????
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.START_PREPARE_MATERIAL.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.START_PREPARE_MATERIAL.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult prepareMaterial(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutwhOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.START_PREPARE_MATERIAL.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        //????????????
        lockStock(Lists.newArrayList(enter.getId()));

        //???????????????
        opeOutwhOrder.setStatus(WhOutStatusEnums.PREPARE_MATERIAL.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //????????????
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
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult outwh(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutwhOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.PREPARE_MATERIAL.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //???????????????
        List<OpeOutwhOrderB> orderBList = opeOutwhOrderBService.list(new LambdaQueryWrapper<OpeOutwhOrderB>().eq(OpeOutwhOrderB::getOutwhOrderId, opeOutwhOrder.getId()));
        if (CollectionUtils.isEmpty(orderBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }

        //?????? ??????
        List<OpeStock> opeStocks = new ArrayList<>(opeStockService.listByIds(orderBList.stream().map(OpeOutwhOrderB::getStockId).collect(Collectors.toList())));

        if (CollectionUtils.isEmpty(opeStocks)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }

        //??????????????????
        orderBList.forEach(item -> {
            opeStocks.forEach(stock -> {
                if (item.getStockId().equals(stock.getId())) {
                    stock.setLockTotal(stock.getLockTotal() - item.getTotalCount());
                    stock.setOutTotal(stock.getOutTotal() + item.getTotalCount());
                    stock.setUpdatedTime(new Date());
                    stock.setUpdatedBy(enter.getUserId());
                }
            });
        });

        //???????????????
        outOrderStock(enter, orderBList);

        //????????????
        opeStockService.updateBatch(opeStocks);
        //???????????????
        opeOutwhOrder.setStatus(WhOutStatusEnums.OUT_WH.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //????????????
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
     * ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult inWh(IdEnter enter) {
        OpeOutwhOrder opeOutwhOrder = opeOutwhOrderService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeOutwhOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(WhOutStatusEnums.OUT_WH.getValue(), opeOutwhOrder.getStatus())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }

        //???????????????
        List<OpeFrStockBill> opeStockBillList = new ArrayList<>();
        //????????????
        List<OpeFrStock> opeFrStockList = new ArrayList<>();
        //????????????
        List<OpeFrStockProduct> opeFrStockProductList = new ArrayList<>();

        //??????????????????
        buildProductItem(enter, opeStockBillList, opeFrStockList, opeFrStockProductList);

        if (CollectionUtils.isNotEmpty(opeStockBillList)) {
            opeFrStockBillService.saveOrUpdateBatch(opeStockBillList);
        }
        if (CollectionUtils.isNotEmpty(opeFrStockList)) {
            // ????????????
            opeFrStockService.saveOrUpdateBatch(opeFrStockList);
        }
        if (CollectionUtils.isNotEmpty(opeFrStockProductList)) {
            // ???????????????
            opeFrStockProductService.saveOrUpdateBatch(opeFrStockProductList);
        }

        //???????????????
        opeOutwhOrder.setStatus(WhOutStatusEnums.IN_WH.getValue());
        opeOutwhOrder.setUpdatedBy(enter.getUserId());
        opeOutwhOrder.setUpdatedTime(new Date());
        opeOutwhOrderService.updateById(opeOutwhOrder);
        //????????????
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeOutwhOrder.getId());
        saveNodeEnter.setStatus(WhOutStatusEnums.IN_WH.getValue());
        saveNodeEnter.setEvent(WhOutEventEnums.IN_WH.getValue());
        saveNodeEnter.setMemo(null);
        saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
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

        //???????????????
        OpeSysUserProfile opeSysUserProfile = opeSysUserProfileService.getOne(new LambdaQueryWrapper<OpeSysUserProfile>().eq(OpeSysUserProfile::getSysUserId, enter.getUserId()));
        if (StringManaConstant.entityIsNull(opeSysUserProfile)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //??????????????????
        if (StringManaConstant.entityIsNull(ConsignTypeEnums.getEnumsByValue(enter.getConsignType()))) {
            throw new SesWebRosException(ExceptionCodeEnums.CONSIGN_TYPE_NOT_EXIST.getCode(), ExceptionCodeEnums.CONSIGN_TYPE_NOT_EXIST.getMessage());
        }
        //????????????????????????
        OpeWhse gogalWh = opeWhseService.getById(enter.getGogalId());
        if (StringManaConstant.entityIsNull(gogalWh)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        //????????????
        List<OpeStock> opeStocks = new ArrayList<>(opeStockService.listByIds(savePartProductEnterList.stream().map(SavePartProductEnter::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opeStocks)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        //????????????
        for (SavePartProductEnter item : savePartProductEnterList) {
            Boolean existStock = Boolean.FALSE;
            for (OpeStock stock : opeStocks) {
                if (item.getId().equals(stock.getId())) {
                    if (item.getQty() <= stock.getAvailableTotal()) {
                        existStock = Boolean.TRUE;
                        continue;
                    }
                    //??????????????? ??????????????????
                    throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                }
            }
            //??????????????? ?????????????????????
            if (!existStock) {
                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
            }
        }

        //???????????????
        OpeOutwhOrder opeOutwhOrder = buildOpeOutWhOrder(enter, savePartProductEnterList.stream().mapToInt(SavePartProductEnter::getQty).sum(), opeSysUserProfile);

        //???????????????
        List<OpeOutwhOrderB> opeOutwhOrderBList = new ArrayList<>();
        savePartProductEnterList.forEach(item -> {
            Long productId = opeStocks.stream().filter(stock -> item.getId().equals(stock.getId())).findFirst().orElse(null).getMaterielProductId();
            String productType = opeStocks.stream().filter(stock -> item.getId().equals(stock.getId())).findFirst().orElse(null).getMaterielProductType();
            opeOutwhOrderBList.add(buildOpeOutWhSingle(enter, opeOutwhOrder.getId(), item, productId, productType));
        });

        //???????????????
        opeOutwhOrderService.save(opeOutwhOrder);
        //???????????????
        opeOutwhOrderBService.saveBatch(opeOutwhOrderBList);
        //??????????????????
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
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<WhOutConsigneeResult> consigneeList(GeneralEnter enter) {
        return whOutServiceMapper.consigneeList(enter);
    }

    /**
     * ????????????
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
     * ????????????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> consignMethod(StringEnter enter) {
        if (!StringUtils.equals(ConsignTypeEnums.getEnumsByCode(enter.getKeyword()).getValue(), ConsignTypeEnums.AIR_PARCEL.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.NO_LOAN.getCode(), ExceptionCodeEnums.NO_LOAN.getMessage());
        }
        Map<String, String> resultMap = new HashMap<>();
        for (ConsignMethodEnums item : ConsignMethodEnums.values()) {
            resultMap.put(item.getCode(), item.getValue());
        }
        return resultMap;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> statusByCount(GeneralEnter enter) {
        Map<String, Integer> resultMap = new HashMap<>();

        //????????????
        List<CountByStatusResult> statusResultList = whOutServiceMapper.statusByCount(enter);
        if (CollectionUtils.isNotEmpty(statusResultList)) {
            statusResultList.forEach(item -> {
                //????????????????????????????????????
                if (resultMap.containsKey(item.getStatus())) {
                    resultMap.put(item.getStatus(), resultMap.get(item.getStatus()) + item.getTotalCount());
                } else {
                    resultMap.put(item.getStatus(), item.getTotalCount());
                }
            });

        }
        for (WhOutClassTypeEnums item : WhOutClassTypeEnums.values()) {
            if (!resultMap.containsKey(item.getValue())) {
                resultMap.put(item.getValue(), 0);
            }
        }
        return resultMap;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutProductListResult> productList(WhOutProductListEnter enter) {
        //???????????? ????????????
        List<OpeWhse> opeWhseList = opeWhseService.list(new LambdaQueryWrapper<OpeWhse>().in(OpeWhse::getType, WhseTypeEnums.ASSEMBLY.getValue(), WhseTypeEnums.PURCHAS.getValue()));
        if (StringManaConstant.entityIsNull(opeWhseList)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        //???????????? ??????????????????
        Integer count = whOutServiceMapper.productListCount(enter, opeWhseList);
        if (StringManaConstant.entityIsNull(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, whOutServiceMapper.productListList(enter, opeWhseList));
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
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
     * ?????????????????????
     *
     * @param ids
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void lockStock(List<Long> ids) {

        //?????????????????????
        List<OpeOutwhOrderB> outwhOrderBList = opeOutwhOrderBService.list(new LambdaQueryWrapper<OpeOutwhOrderB>().in(OpeOutwhOrderB::getOutwhOrderId, ids));
        if (CollectionUtils.isEmpty(outwhOrderBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getCode(), ExceptionCodeEnums.WH_OUT_ORDER_NOT_EXIST.getMessage());
        }

        //????????????
        List<OpeStock> opeStocks = new ArrayList<>(opeStockService.listByIds(outwhOrderBList.stream().map(OpeOutwhOrderB::getStockId).collect(Collectors.toSet())));
        if (CollectionUtils.isEmpty(opeStocks)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }

        //????????????????????????
        outwhOrderBList.forEach(item -> {
            Boolean stockExist = Boolean.FALSE;
            for (OpeStock stock : opeStocks) {
                //????????????
                if (item.getStockId().equals(stock.getId())) {
                    //????????????
                    if (stock.getAvailableTotal() < item.getTotalCount()) {
                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
                    }
                    stockExist = Boolean.TRUE;
                    stock.setLockTotal(stock.getLockTotal() + item.getTotalCount());
                    stock.setAvailableTotal(stock.getAvailableTotal() - item.getTotalCount());
                    stock.setUpdatedTime(new Date());
                    break;
                }
            }
            if (!stockExist) {
                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
            }
        });

        if (outwhOrderBList.stream().filter(item -> StringUtils.equals(item.getProductType(), BomCommonTypeEnums.SCOOTER.getValue())).findFirst().orElse(null) != null) {
            //????????????
            List<OpeStockProdProduct> stockProdProductList = lockOpeStockProdProductsSingle(outwhOrderBList, opeStocks);
            opeStockProdProductService.updateBatch(stockProdProductList);
        } else {
            //????????????????????????
            List<OpeStockPurchas> stockPurchasList = lockOpeStockPurchas(outwhOrderBList, opeStocks);
            //????????????
            opeStockPurchasService.updateBatch(stockPurchasList);
        }

        //??????????????????
        opeStockService.updateBatch(opeStocks);
    }

    /**
     * ????????????
     *
     * @param outwhOrderBList
     * @param opeStocks
     * @return
     */
    private List<OpeStockPurchas> lockOpeStockPurchas(List<OpeOutwhOrderB> outwhOrderBList, List<OpeStock> opeStocks) {
        //????????????Id
        List<Long> opeStockPurchasIds =
                opeStocks.stream().filter(item -> {
                    if (!StringUtils.equals(item.getMaterielProductType(), BomCommonTypeEnums.SCOOTER.getValue()) || !StringUtils.equals(item.getMaterielProductType(),
                            BomCommonTypeEnums.COMBINATION.getValue())) {
                        return true;
                    }
                    return false;
                }).map(OpeStock::getId).collect(Collectors.toList());

        //??????????????????
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

                    if (StringUtils.isEmpty(opeStockPurchas.getSerialNumber())) {
                        if (opeStockPurchas.getAvailableQty() < opeOutwhOrderB.getTotalCount()) {
                            opeStockPurchas.setAvailableQty(0);
                            opeStockPurchas.setBindOrderId(opeOutwhOrderB.getId());
                            opeStockPurchas.setSourceType(SourceTypeEnums.WH_OUT.getValue());
                            opeStockPurchas.setStatus(StockProductPartStatusEnums.LOCKING.getValue());
                            opeStockPurchas.setUpdatedTime(new Date());
                            opeOutwhOrderB.setLastOutCount(opeOutwhOrderB.getTotalCount() - opeStockPurchas.getAvailableQty());
                            continue;
                        } else {
                            opeStockPurchas.setAvailableQty(opeStockPurchas.getAvailableQty() - opeOutwhOrderB.getLastOutCount());
                        }
                    } else {
                        opeStockPurchas.setAvailableQty(0);
                        opeStockPurchas.setBindOrderId(opeOutwhOrderB.getId());
                        opeStockPurchas.setSourceType(SourceTypeEnums.WH_OUT.getValue());
                        opeStockPurchas.setStatus(StockProductPartStatusEnums.LOCKING.getValue());
                        opeStockPurchas.setUpdatedTime(new Date());
                    }

                    break;
                }
            }
        }
        return stockPurchasList;
    }

    /**
     * ????????????
     *
     * @param outwhOrderBList
     * @param opeStocks
     * @return
     */
    private List<OpeStockProdProduct> lockOpeStockProdProductsSingle(List<OpeOutwhOrderB> outwhOrderBList, List<OpeStock> opeStocks) {
        //????????????Id
        List<Long> opeStockProductIds =
                opeStocks.stream().filter(item -> StringUtils.equals(item.getMaterielProductType(), BomCommonTypeEnums.SCOOTER.getValue())).map(OpeStock::getId).collect(Collectors.toList());
        //??????????????????
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

                    opeStockProdProduct.setBindOrderId(opeOutwhOrderB.getId());
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
     * ???????????????
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
     * ???????????????
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
                .dr(0)
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

    /**
     * ???????????????
     *
     * @param enter
     * @param orderBList
     */
    private void outOrderStock(IdEnter enter, List<OpeOutwhOrderB> orderBList) {
        List<OpeStockBill> opeStockBillList = new ArrayList<>();

        //????????????
        List<OpeOutwhOrderB> scooterOpeOutWhOrderList =
                orderBList.stream().filter(item -> StringUtils.equals(item.getProductType(), BomCommonTypeEnums.SCOOTER.getValue())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(scooterOpeOutWhOrderList)) {
            //??????????????????
            List<OpeStockProdProduct> stockProdProductList = opeStockProdProductService.list(new LambdaQueryWrapper<OpeStockProdProduct>()
                    .in(OpeStockProdProduct::getBindOrderId, scooterOpeOutWhOrderList.stream().map(OpeOutwhOrderB::getId).collect(Collectors.toList())));
            if (CollectionUtils.isEmpty(stockProdProductList)) {
                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
            }

            scooterOpeOutWhOrderList.forEach(item -> {
                OpeStockBill opeStockBill = OpeStockBill.builder()
                        .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                        .dr(0)
                        .stockId(item.getStockId())
                        .direction(InOutWhEnums.OUT.getValue())
                        .sourceId(item.getOutwhOrderId())
                        .status(StockBillStatusEnums.NORMAL.getValue())
                        .total(item.getTotalCount())
                        .sourceType(SourceTypeEnums.WH_OUT.getValue())
                        .operatineTime(new Date())
                        .revision(0)
                        .createdTime(new Date())
                        .createdBy(enter.getUserId())
                        .updatedBy(enter.getUserId())
                        .updatedTime(new Date())
                        .build();

                for (OpeStockProdProduct scooter : stockProdProductList) {
                    if (item.getId().equals(scooter.getBindOrderId())) {
                        scooter.setAvailableQty(0);
                        scooter.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                        scooter.setOutStockTime(new Date());
                        scooter.setOutStockBillId(opeStockBill.getId());
                        scooter.setUpdatedBy(enter.getUserId());
                        scooter.setUpdatedTime(new Date());
                    }
                    break;
                }
                opeStockBillList.add(opeStockBill);
            });
            opeStockProdProductService.updateBatch(stockProdProductList);
        }


        List<OpeOutwhOrderB> partOutWhOrderList = orderBList.stream().filter(item -> !StringUtils.equals(item.getProductType(), BomCommonTypeEnums.SCOOTER.getValue())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(partOutWhOrderList)) {

            List<OpeStockPurchas> stockPurchasList = opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>().in(OpeStockPurchas::getBindOrderId,
                    partOutWhOrderList.stream().map(OpeOutwhOrderB::getId).collect(Collectors.toList())));

            partOutWhOrderList.forEach(item -> {
                OpeStockBill opeStockBill = OpeStockBill.builder()
                        .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                        .dr(0)
                        .stockId(item.getStockId())
                        .direction(InOutWhEnums.OUT.getValue())
                        .sourceId(item.getOutwhOrderId())
                        .status(StockBillStatusEnums.NORMAL.getValue())
                        .total(item.getTotalCount())
                        .sourceType(SourceTypeEnums.WH_OUT.getValue())
                        .operatineTime(new Date())
                        .revision(0)
                        .createdTime(new Date())
                        .createdBy(enter.getUserId())
                        .updatedBy(enter.getUserId())
                        .updatedTime(new Date())
                        .build();


                // todo ????????????Id???????????? ?????????????????? ??????Bug????????? ????????????????????? ??????
                if (CollectionUtils.isNotEmpty(stockPurchasList)) {
                    int count = item.getTotalCount();
                    for (OpeStockPurchas part : stockPurchasList) {
                        if (item.getId().equals(part.getBindOrderId())) {

                            if (count >= part.getAvailableQty()) {
                                count -= part.getAvailableQty();

                                part.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                                part.setOutPrincipalId(enter.getUserId());
                                part.setOutStockTime(new Date());
                                part.setOutStockBillId(opeStockBill.getId());
                                part.setUpdatedBy(enter.getUserId());
                                part.setUpdatedTime(new Date());
                                part.setAvailableQty(0);
                                if (NumberUtil.eqZero(count)) {
                                    break;
                                }
                            }
                            if (count < part.getAvailableQty()) {
                                part.setAvailableQty(part.getAvailableQty() - count);
                                part.setUpdatedBy(enter.getUserId());
                                part.setUpdatedTime(new Date());
                            }

                            if (NumberUtil.eqZero(count)) {
                                break;
                            }
                        }
                        break;
                    }
                }

                opeStockBillList.add(opeStockBill);
            });
            if (CollectionUtils.isNotEmpty(stockPurchasList)) {
                opeStockPurchasService.updateBatch(stockPurchasList);
            }
        }
        //???????????????
        opeStockBillService.batchInsert(opeStockBillList);
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @param opeStockBillList
     * @param opeFrStockList
     * @param opeFrStockProductList
     */
    private void buildProductItem(IdEnter enter, List<OpeFrStockBill> opeStockBillList, List<OpeFrStock> opeFrStockList, List<OpeFrStockProduct> opeFrStockProductList) {
        //??????????????????
        OpeWhse opeWhse = opeWhseService.getOne(new LambdaQueryWrapper<OpeWhse>().eq(OpeWhse::getType, WhseTypeEnums.FR_WHSE.getValue()));
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        //???????????????
        List<OpeOutwhOrderB> opeOutwhOrderBList = opeOutwhOrderBService.list(new LambdaQueryWrapper<OpeOutwhOrderB>().eq(OpeOutwhOrderB::getOutwhOrderId, enter.getId()));
        if (CollectionUtils.isEmpty(opeOutwhOrderBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }


        //????????????
        List<OpeFrStock> frStockList = opeFrStockService.list(new LambdaQueryWrapper<OpeFrStock>()
                .in(OpeFrStock::getMaterielProductId, opeOutwhOrderBList.stream().map(OpeOutwhOrderB::getPartProductId).collect(Collectors.toList())));

        //????????????????????????
        Collection<OpeStock> opeStocks = opeStockService.listByIds(opeOutwhOrderBList.stream().map(OpeOutwhOrderB::getStockId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(opeStocks)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }

        List<OpeOutwhOrderB> scooterOutWhOrderList =
                opeOutwhOrderBList.stream().filter(item -> StringUtils.equals(item.getProductType(), BomCommonTypeEnums.SCOOTER.getValue())).collect(Collectors.toList());

        List<OpeOutwhOrderB> partOutWhOrderList =
                opeOutwhOrderBList.stream().filter(item -> !StringUtils.equals(item.getProductType(), BomCommonTypeEnums.SCOOTER.getValue())).collect(Collectors.toList());

        List<OpeStockPurchas> opeStockPartList = null;
        if (CollectionUtils.isNotEmpty(partOutWhOrderList)) {
            opeStockPartList = opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>()
                    .in(OpeStockPurchas::getBindOrderId, opeOutwhOrderBList.stream().map(OpeOutwhOrderB::getId).collect(Collectors.toList())));
        }
        List<OpeStockProdProduct> opeStockScooterList = null;
        if (CollectionUtils.isNotEmpty(scooterOutWhOrderList)) {
            opeStockScooterList = opeStockProdProductService.list(new LambdaQueryWrapper<OpeStockProdProduct>().in(OpeStockProdProduct::getBindOrderId,
                    opeOutwhOrderBList.stream().map(OpeOutwhOrderB::getId).collect(Collectors.toList())));
        }

        if (CollectionUtils.isEmpty(frStockList)) {

            for (OpeOutwhOrderB opeOutwhOrderB : opeOutwhOrderBList) {
                String productName = opeStocks.stream().filter(stock -> stock.getId().equals(opeOutwhOrderB.getStockId())).findAny().map(OpeStock::getMaterielProductName).orElse(null);

                OpeFrStock opeFrStock = OpeFrStock.builder()
                        .id(idAppService.getId(SequenceName.OPE_FR_STOCK))
                        .dr(0)
                        .whseId(opeWhse.getId())
                        .intTotal(opeOutwhOrderB.getTotalCount())
                        .outTotal(0)
                        .availableTotal(opeOutwhOrderB.getTotalCount())
                        .wornTotal(0)
                        .lockTotal(0)
                        .materielProductId(opeOutwhOrderB.getPartProductId())
                        .materielProductType(opeOutwhOrderB.getProductType())
                        .materielProductName(productName)
                        .revision(0)
                        .updatedBy(enter.getUserId())
                        .updatedTime(new Date())
                        .createdBy(enter.getUserId())
                        .createdTime(new Date())
                        .build();


                //???????????????
                OpeFrStockBill opeFrStockBill = buildOpeFrStockBill(enter, opeOutwhOrderB, opeFrStock.getId());
                opeStockBillList.add(opeFrStockBill);
                //???????????????
                opeFrStockList.add(opeFrStock);
                if (CollectionUtils.isNotEmpty(partOutWhOrderList)) {
                    opeStockPartList.forEach(item -> {
                        opeFrStockProductList.add(buildFrStockProduct(enter,
                                opeOutwhOrderB.getProductType(),
                                opeFrStock.getId(),
                                item.getPartId(),
                                item.getLot(),
                                item.getSerialNumber(),
                                item.getPartNumber(),
                                opeFrStockBill.getStockId(),
                                opeOutwhOrderB.getTotalCount()));
                    });
                    break;
                }
                if (CollectionUtils.isNotEmpty(scooterOutWhOrderList)) {
                    opeStockScooterList.forEach(item -> {
                        opeFrStockProductList.add(buildFrStockProduct(enter,
                                opeOutwhOrderB.getProductType(),
                                opeFrStock.getId(),
                                item.getProductId(),
                                item.getLot(),
                                item.getSerialNumber(),
                                item.getProductNumber(),
                                opeFrStockBill.getId(),
                                opeOutwhOrderB.getTotalCount()));
                    });
                    break;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(frStockList)) {
            for (OpeOutwhOrderB opeOutwhOrderB : opeOutwhOrderBList) {
                for (OpeFrStock stock : frStockList) {
                    if (opeOutwhOrderB.getPartProductId().equals(stock.getMaterielProductId()) && StringUtils.equals(opeOutwhOrderB.getProductType(), stock.getMaterielProductType())) {
                        stock.setIntTotal(stock.getIntTotal() + opeOutwhOrderB.getTotalCount());
                        stock.setAvailableTotal(stock.getAvailableTotal() + opeOutwhOrderB.getTotalCount());
                        stock.setUpdatedBy(enter.getUserId());
                        stock.setUpdatedTime(new Date());

                        //???????????????
                        OpeFrStockBill opeFrStockBill = buildOpeFrStockBill(enter, opeOutwhOrderB, stock.getId());

                        if (CollectionUtils.isNotEmpty(partOutWhOrderList)) {
                            opeStockPartList.forEach(stockPart -> {
                                opeFrStockProductList.add(buildFrStockProduct(enter,
                                        opeOutwhOrderB.getProductType(),
                                        stock.getId(),
                                        stockPart.getPartId(),
                                        stockPart.getLot(),
                                        stockPart.getSerialNumber(),
                                        stockPart.getPartNumber(),
                                        opeFrStockBill.getStockId(),
                                        opeOutwhOrderB.getTotalCount()));
                            });
                            break;
                        }
                        if (CollectionUtils.isNotEmpty(scooterOutWhOrderList)) {
                            opeStockScooterList.forEach(stockScooter -> {
                                opeFrStockProductList.add(buildFrStockProduct(enter,
                                        opeOutwhOrderB.getProductType(),
                                        stock.getId(),
                                        stockScooter.getProductId(),
                                        stockScooter.getLot(),
                                        stockScooter.getSerialNumber(),
                                        stockScooter.getProductNumber(),
                                        opeFrStockBill.getId(),
                                        opeOutwhOrderB.getTotalCount()));
                            });
                            break;
                        }
                        opeStockBillList.add(opeFrStockBill);
                        opeFrStockList.add(stock);
                    }
                }
            }
        }
    }

    private OpeFrStockProduct buildFrStockProduct(IdEnter enter, String productType, Long stockId, Long productId, String lot, String serialNumber, String productNumber, Long id,
                                                  int inwhCount) {
        return OpeFrStockProduct
                .builder()
                .id(idAppService.getId(SequenceName.OPE_FR_STOCK_PRODUCT))
                .dr(0)
                .status(StockProductPartStatusEnums.AVAILABLE.getValue())
                .stockId(stockId)
                .productId(productId)
                .lot(lot)
                .serialNumber(serialNumber)
                .productNumber(productNumber)
                .productType(productType)
                .inStockBillId(id)
                .principalId(enter.getUserId())
                .inWhQty(org.springframework.util.StringUtils.isEmpty(serialNumber) == true ? inwhCount : 1)
                .inStockTime(new Date())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    private OpeFrStockBill buildOpeFrStockBill(IdEnter enter, OpeOutwhOrderB item, Long stockId) {
        return OpeFrStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .stockId(stockId)
                .direction(InOutWhEnums.IN.getValue())
                .sourceId(item.getId())
                .sourceType(SourceTypeEnums.WH_OUT.getValue())
                .status(StockBillStatusEnums.ABNORMAL.getValue())
                .total(item.getTotalCount())
                .principalId(enter.getUserId())
                .operatineTime(new Date())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedTime(new Date())
                .updatedBy(enter.getUserId())
                .build();
    }

}
