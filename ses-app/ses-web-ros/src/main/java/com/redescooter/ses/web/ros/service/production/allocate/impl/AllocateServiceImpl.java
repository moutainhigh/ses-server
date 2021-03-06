package com.redescooter.ses.web.ros.service.production.allocate.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.ProductionTypeEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderEventEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.production.AllocateServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocate;
import com.redescooter.ses.web.ros.dm.OpeAllocateB;
import com.redescooter.ses.web.ros.dm.OpeAllocateTrace;
import com.redescooter.ses.web.ros.dm.OpeParts;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeAllocateBService;
import com.redescooter.ses.web.ros.service.base.OpeAllocateService;
import com.redescooter.ses.web.ros.service.base.OpeAllocateTraceService;
import com.redescooter.ses.web.ros.service.base.OpePartsService;
import com.redescooter.ses.web.ros.service.base.OpeStockBillService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.production.allocate.AllocateService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.ProductPartsListEnter;
import com.redescooter.ses.web.ros.vo.production.ProductPartsResult;
import com.redescooter.ses.web.ros.vo.production.ProductionPartsEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderNodeResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderPartResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderResult;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAllocateEnter;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @ClassName:AllocateServiceImpl
 * @description: AllocateServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2020/03/19 15:10
 */
@Service
public class AllocateServiceImpl implements AllocateService {

    @Autowired
    private AllocateServiceMapper allocateServiceMapper;

    @Autowired
    private OpeAllocateService opeAllocateService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockService opeStockService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private OpeAllocateTraceService opeAllocateTraceService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpeAllocateBService opeAllocateBService;

    @Autowired
    private OpePartsService opePartsService;

    /**
     * ????????????
     *
     * @return
     */
    @Override
    public Map<String, Integer> countByType(GeneralEnter enter) {

        List<CountByStatusResult> countByStatusResultList = allocateServiceMapper.countByType(enter);

        Map<String, Integer> map = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(countByStatusResultList)) {
            countByStatusResultList.forEach(item -> {
                map.put(item.getStatus(), item.getTotalCount());
            });
        }

        for (ProductionTypeEnums item : ProductionTypeEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> statusList(GeneralEnter enter) {
        Map<String, String> map = Maps.newHashMap();
        for (AllocateOrderStatusEnums item : AllocateOrderStatusEnums.values()) {
            map.put(item.getValue(), item.getCode());
        }
        return map;
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AllocateOrderResult> list(AllocateOrderEnter enter) {
        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return PageResult.createZeroRowResult(enter);
        }
        List<String> statusList = new ArrayList();
        if (StringUtils.equals(ProductionTypeEnums.TODO.getValue(), enter.getClassType())) {
            for (AllocateOrderStatusEnums item : AllocateOrderStatusEnums.values()) {
                statusList.add(item.getValue());
            }
            statusList.remove(AllocateOrderStatusEnums.CANCELLED.getValue());
            statusList.remove(AllocateOrderStatusEnums.INPRODUCTIONWH.getValue());
        } else {
            statusList.add(AllocateOrderStatusEnums.CANCELLED.getValue());
            statusList.add(AllocateOrderStatusEnums.INPRODUCTIONWH.getValue());
        }

        int count = allocateServiceMapper.allocateListCount(enter, statusList);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AllocateOrderResult> allocateOrderResultList = allocateServiceMapper.allocateList(enter, statusList);
        return PageResult.create(enter, count, allocateOrderResultList);
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    public AllocateOrderResult detail(IdEnter enter) {
        AllocateOrderResult detail = allocateServiceMapper.detail(enter);
        if (StringManaConstant.entityIsNull(detail)) {
            throw new SesWebRosException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }
        return detail;
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<AllocateOrderNodeResult> allocateOrderNode(IdEnter enter) {
        checkAllocate(enter.getId(), null);
        List<AllocateOrderNodeResult> allocateOrderNodeList = allocateServiceMapper.allocateOrderNodeList(enter);
        if (CollectionUtils.isEmpty(allocateOrderNodeList)) {
            return Lists.newArrayList();
        }
        return allocateOrderNodeList;
    }

    /**
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<AllocateOrderPartResult> allocateOrderDetailPartsList(IdEnter enter) {
        checkAllocate(enter.getId(), null);

        List<AllocateOrderPartResult> allocateOrderPartResultList = allocateServiceMapper.allocateOrderDetailPartsList(enter);
        if (CollectionUtils.isEmpty(allocateOrderPartResultList)) {
            return new ArrayList<>();
        }
        return allocateOrderPartResultList;
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startPrepare(IdEnter enter) {
        OpeAllocate opeAllocate = checkAllocate(enter.getId(), AllocateOrderStatusEnums.PENDING.getValue());

        opeAllocate.setStatus(AllocateOrderStatusEnums.PREPARE.getValue());
        opeAllocate.setUpdatedBy(enter.getUserId());
        opeAllocate.setUpdatedTime(new Date());
        opeAllocateService.updateById(opeAllocate);

        //??????
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAllocate.getId());
        saveNodeEnter.setStatus(AllocateOrderStatusEnums.PREPARE.getValue());
        saveNodeEnter.setEvent(AllocateOrderEventEnums.INPROGRESS.getValue());
        saveNodeEnter.setMemo(null);
        this.saveAllocateNode(saveNodeEnter);
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
    public GeneralResult startAllocate(IdEnter enter) {
        OpeAllocate opeAllocate = checkAllocate(enter.getId(), AllocateOrderStatusEnums.PREPARE.getValue());

        opeAllocate.setStatus(AllocateOrderStatusEnums.ALLOCATE.getValue());
        opeAllocate.setUpdatedBy(enter.getUserId());
        opeAllocate.setUpdatedTime(new Date());
        opeAllocateService.updateById(opeAllocate);

        //??????
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAllocate.getId());
        saveNodeEnter.setStatus(AllocateOrderStatusEnums.ALLOCATE.getValue());
        saveNodeEnter.setEvent(AllocateOrderEventEnums.ALLOCATE.getValue());
        saveNodeEnter.setMemo(null);
        this.saveAllocateNode(saveNodeEnter);
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
    public GeneralResult cancelAllocate(IdEnter enter) {
        List<OpeStock> updateOpeStock = Lists.newArrayList();

        List<OpeStockBill> updateOpeStockBillList = Lists.newArrayList();

        OpeAllocate opeAllocate = checkAllocate(enter.getId(), AllocateOrderStatusEnums.PENDING.getValue());

        opeAllocate.setStatus(AllocateOrderStatusEnums.CANCELLED.getValue());
        opeAllocate.setUpdatedBy(enter.getUserId());
        opeAllocate.setUpdatedTime(new Date());
        opeAllocateService.updateById(opeAllocate);

        //?????? ?????????
        QueryWrapper<OpeStockBill> opeStockBillQueryWrapper = new QueryWrapper<>();
        opeStockBillQueryWrapper.eq(OpeStockBill.COL_DR, Constant.DR_FALSE);
        opeStockBillQueryWrapper.eq(OpeStockBill.COL_SOURCE_ID, opeAllocate.getId());

        List<OpeStockBill> stockBillList = opeStockBillService.list(opeStockBillQueryWrapper);

        if (StringManaConstant.entityIsNull(stockBillList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getCode(), ExceptionCodeEnums.STOCK_BILL_NOT_IS_EXIST.getMessage());
        }
        List<Long> stockIds = Lists.newArrayList();
        //?????????????????????
        stockBillList.forEach(item -> {
            stockIds.add(item.getStockId());
            item.setStatus(StockBillStatusEnums.ABNORMAL.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
            updateOpeStockBillList.add(item);
        });

        //????????????
        List<OpeStock> opeStockList = new ArrayList<OpeStock>(opeStockService.listByIds(stockIds));

        if (CollectionUtils.isEmpty(opeStockList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        opeStockList.forEach(item -> {
            stockBillList.forEach(bill -> {
                if (item.getId().equals(bill.getStockId())) {
                    item.setAvailableTotal(item.getAvailableTotal() + bill.getTotal());
                    item.setOutTotal(item.getOutTotal() - bill.getTotal());
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                    updateOpeStock.add(item);
                }
            });
        });

        //???????????????
        opeStockService.updateBatchById(opeStockList);
        //?????? ???????????????
        opeStockBillService.updateBatchById(stockBillList);

        //??????
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAllocate.getId());
        saveNodeEnter.setStatus(AllocateOrderStatusEnums.CANCELLED.getValue());
        saveNodeEnter.setEvent(AllocateOrderEventEnums.CANCELLED.getValue());
        saveNodeEnter.setMemo(null);
        this.saveAllocateNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductPartsResult> allocatePartsList(ProductPartsListEnter enter) {
        //??????????????????Id
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, Constant.DR_FALSE);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        if (StringUtils.isNotEmpty(enter.getProductType())) {
            enter.setProductType(BomCommonTypeEnums.getCodeByValue(enter.getProductType()));
        }

        List<ProductPartsResult> result = allocateServiceMapper.allocatePartsList(enter, opeWhse.getId());
        if (CollectionUtils.isEmpty(result)) {
            return Lists.newArrayList();
        }
        result.forEach(item -> {
            item.setType(BomCommonTypeEnums.getValueByCode(item.getType()));
        });
        return result;
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult inWhAllocate(IdEnter enter) {
        //?????????
        List<OpeStockBill> opeStockBillList = Lists.newArrayList();
        //??????
        List<OpeStock> saveStockList = Lists.newArrayList();

        OpeAllocate opeAllocate = checkAllocate(enter.getId(), AllocateOrderStatusEnums.ALLOCATE.getValue());

        opeAllocate.setStatus(AllocateOrderStatusEnums.INPRODUCTIONWH.getValue());
        opeAllocate.setUpdatedBy(enter.getUserId());
        opeAllocate.setUpdatedTime(new Date());
        opeAllocateService.updateById(opeAllocate);
        //?????? ????????????
        buildStockDateSingle(enter, opeStockBillList, saveStockList, opeAllocate);

        //todo ??????????????? ??????

        //??????
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAllocate.getId());
        saveNodeEnter.setStatus(AllocateOrderStatusEnums.INPRODUCTIONWH.getValue());
        saveNodeEnter.setEvent(AllocateOrderEventEnums.INPRODUCTIONWH.getValue());
        saveNodeEnter.setMemo(null);
        this.saveAllocateNode(saveNodeEnter);

        //?????????
        opeStockBillService.saveBatch(opeStockBillList);
        // ??????
        opeStockService.saveOrUpdateBatch(saveStockList);

        return new GeneralResult(enter.getRequestId());
    }

    private void buildStockDateSingle(IdEnter enter, List<OpeStockBill> opeStockBillList, List<OpeStock> saveStockList, OpeAllocate opeAllocate) {
        //???????????????
        //??????????????? ??????
        QueryWrapper<OpeAllocateB> opeAllocateBQueryWrapper = new QueryWrapper<>();
        opeAllocateBQueryWrapper.eq(OpeAllocateB.COL_DR, Constant.DR_FALSE);
        opeAllocateBQueryWrapper.eq(OpeAllocateB.COL_ALLOCATE_ID, opeAllocate.getId());
        List<OpeAllocateB> opeAllocateBList = opeAllocateBService.list(opeAllocateBQueryWrapper);


        //?????? ??????????????????
        List<Long> partIds = new ArrayList<>();
        opeAllocateBList.forEach(item -> {
            partIds.add(item.getPartId());
        });

        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, Constant.DR_FALSE);
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        // ????????????
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, Constant.DR_FALSE);
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, partIds);
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);

        for (OpeAllocateB item : opeAllocateBList) {
            Boolean stockExist = Boolean.FALSE;
            for (OpeStock stock : opeStockList) {
                if (item.getPartId().equals(stock.getMaterielProductId())) {
                    //?????????
                    opeStockBillList.add(buildStockBillSingle(enter, opeAllocate.getId(), item.getTotal(), stock.getId(), InOutWhEnums.IN.getValue()));
                    stockExist = Boolean.TRUE;

                    //??????
                    stock.setIntTotal(stock.getIntTotal() + item.getTotal());
                    stock.setAvailableTotal(stock.getAvailableTotal() + item.getTotal());
                    stock.setUpdatedBy(enter.getUserId());
                    stock.setUpdatedTime(new Date());
                    saveStockList.add(stock);
                }
            }
            if (!stockExist) {
                //????????????
                saveStockList.add(OpeStock.builder()
                        .id(idAppService.getId(SequenceName.OPE_STOCK))
                        .dr(0)
                        .userId(enter.getUserId())
                        .tenantId(0L)
                        .whseId(opeWhse.getId())
                        .intTotal(item.getTotal())
                        .outTotal(0)
                        .availableTotal(item.getTotal())
                        .wornTotal(0)
                        .materielProductId(item.getPartId())
                        .revision(0)
                        .createdBy(enter.getUserId())
                        .createdTime(new Date())
                        .updatedBy(enter.getUserId())
                        .updatedTime(new Date())
                        .build());
            }
        }

        //??????????????????
        Collection<OpeParts> opePartList = opePartsService.listByIds(partIds);
        if (opePartList.isEmpty()) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        opePartList.forEach(item -> {
            saveStockList.forEach(stock -> {
                if (item.getId().equals(stock.getMaterielProductId())) {
                    stock.setMaterielProductType(item.getPartsType());
                    stock.setMaterielProductName(item.getCnName());
                }
            });
        });
    }

    /**
     * ???????????????
     *
     * @param saveAllocateEnter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveAllocate(SaveAllocateEnter saveAllocateEnter) {
        //SaveAllocateEnter??????????????????
        SaveAllocateEnter enter = SesStringUtils.objStringTrim(saveAllocateEnter);
        //???????????????????????????
        List<ProductionPartsEnter> productionPartsEnterList = null;
        // ???????????????
        List<OpeStockBill> saveStockBill = Lists.newArrayList();
        // ???????????????
        List<OpeAllocateB> opeAllocateBList = Lists.newArrayList();
        //???????????????
        OpeAllocateTrace opeAllocateTrace = null;
        //??????????????????
        List<OpeStock> opeStockList = null;

        try {
            productionPartsEnterList = JSONArray.parseArray(enter.getPartList(), ProductionPartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        Long allocateId = idAppService.getId(SequenceName.OPE_ALLOCATE);

        List<Long> partIds = Lists.newArrayList();
        productionPartsEnterList.forEach(item -> {
            partIds.add(item.getId());
        });

        //??????????????????
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, Constant.DR_FALSE);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        //????????????
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.in(OpeStock.COL_ID, partIds);
        opeStockQueryWrapper.eq(OpeStock.COL_DR, Constant.DR_FALSE);
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockList = opeStockService.list(opeStockQueryWrapper);

        //???????????? ?????? ?????????
        Integer totalCount = 0;
        totalCount = buildStockDateSingle(enter, productionPartsEnterList, saveStockBill, opeStockList, allocateId, totalCount, opeAllocateBList);

        //????????????
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(allocateId);
        saveNodeEnter.setStatus(AllocateOrderStatusEnums.PENDING.getValue());
        saveNodeEnter.setEvent(AllocateOrderEventEnums.PENDING.getValue());
        saveNodeEnter.setMemo(null);
        this.saveAllocateNode(saveNodeEnter);

        //???????????????
        OpeAllocate opeAllocate = buildOpeAllocateSingle(enter, allocateId, totalCount);
        //????????? ??????
        opeStockBillService.saveBatch(saveStockBill);
        //????????????
        opeStockService.updateBatchById(opeStockList);
        //???????????????
        opeAllocateService.save(opeAllocate);
        //?????????????????????
        opeAllocateBService.saveBatch(opeAllocateBList);
        return new GeneralResult(enter.getRequestId());
    }

    private Integer buildStockDateSingle(SaveAllocateEnter enter, List<ProductionPartsEnter> productionPartsEnterList, List<OpeStockBill> saveStockBill, List<OpeStock> opeStockList, Long allocateId
            , Integer totalCount, List<OpeAllocateB> opeAllocateBList) {
        if (CollectionUtils.isNotEmpty(opeStockList)) {
            for (ProductionPartsEnter part : productionPartsEnterList) {
                Boolean stockExist = Boolean.FALSE;
                for (OpeStock item : opeStockList) {
                    if (part.getId().equals(item.getId())) {
                        stockExist = Boolean.TRUE;
                        //???????????? ????????????
                        if (part.getQty() > item.getAvailableTotal()) {
                            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                        }
                        //???????????????
                        saveStockBill.add(buildStockBillSingle(enter, allocateId, part.getQty(), item.getId(), InOutWhEnums.OUT.getValue()));
                        totalCount += part.getQty();
                        item.setAvailableTotal(item.getAvailableTotal() - part.getQty());
                        item.setOutTotal(item.getOutTotal() + part.getQty());
                        item.setUpdatedBy(enter.getUserId());
                        item.setUpdatedTime(new Date());

                        opeAllocateBList.add(buildOpeAllocateBSingle(enter, allocateId, part, item));
                    }
                    if (stockExist) {
                        break;
                    }
                }
                //????????????????????????
                if (!stockExist) {
                    throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
                }
            }
        } else {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        return totalCount;
    }

    private OpeAllocateB buildOpeAllocateBSingle(SaveAllocateEnter enter, Long allocateId, ProductionPartsEnter part, OpeStock item) {
        return OpeAllocateB.builder()
                .id(idAppService.getId(SequenceName.OPE_ALLOCATE_B))
                .dr(0)
                .userId(enter.getUserId())
                .tenantId(0L)
                .allocateId(allocateId)
                .partId(item.getMaterielProductId())
                .materielProductType(BomCommonTypeEnums.PARTS.getValue())
                .materielProductId(0L)
                .materielProductType(null)
                .total(part.getQty())
                .pendingStorageQty(part.getQty())
                .preparationWaitQty(part.getQty())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .build();
    }

    private OpeAllocate buildOpeAllocateSingle(SaveAllocateEnter enter, Long allocateId, int totalCount) {
        return OpeAllocate.builder()
                .id(allocateId)
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .allocateNum("RED" + RandomUtil.randomDouble(999999, 10000000))
                .status(AllocateOrderStatusEnums.PENDING.getValue())
                .count(totalCount)
                .preparationWaitTotal(totalCount)
                .pendingStorageTotal(totalCount)
                .consigneeId(enter.getConsigneeId())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    private OpeStockBill buildStockBillSingle(GeneralEnter enter, Long allocateId, int qty, Long stockId, String description) {
        return OpeStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .stockId(stockId)
                .direction(description)
                .status(StockBillStatusEnums.NORMAL.getValue())
                .sourceId(allocateId)
                .total(qty)
                .sourceType(SourceTypeEnums.ALLOCATE.getValue())
                .principalId(enter.getUserId())
                .operatineTime(new Date())
                .revision(0)
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
     * @return
     */
    @Override
    public List<ConsigneeResult> consigneeList(GeneralEnter enter) {
        List<ConsigneeResult> consigneeResultlist = new ArrayList<>();
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, Constant.DR_FALSE);
        opeSysUserProfileQueryWrapper.ne(OpeSysUserProfile.COL_SYS_USER_ID, Constant.ADMINUSERID);
        List<OpeSysUserProfile> opeSysUserProfileList = opeSysUserProfileService.list(opeSysUserProfileQueryWrapper);
        opeSysUserProfileList.forEach(item -> {
            consigneeResultlist.add(ConsigneeResult.builder()
                    .id(item.getSysUserId())
                    .firstName(item.getFirstName())
                    .lastName(item.getLastName())
                    .phoneCountryCode(item.getCountryCode())
                    .phone(item.getTelNumber())
                    .email(item.getEmail())
                    .build());
        });
        return consigneeResultlist;
    }

    /**
     * ????????????
     *
     * @return
     */
    @Override
    public GeneralResult saveAllocateNode(SaveNodeEnter enter) {
        opeAllocateTraceService.save(OpeAllocateTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .allocateId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createBy(enter.getUserId())
                .createTime(new Date())
                .updateBy(enter.getUserId())
                .updateTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }

    private OpeAllocate checkAllocate(Long id, String status) {
        OpeAllocate opeAllocate = opeAllocateService.getById(id);
        if (StringManaConstant.entityIsNull(opeAllocate)) {
            throw new SesWebRosException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.isNotEmpty(status)) {
            if (!StringUtils.equals(status, opeAllocate.getStatus())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
            }
        }
        return opeAllocate;
    }
}
