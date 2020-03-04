package com.redescooter.ses.web.ros.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.BomTypeEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.SalseRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheet;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheetHistory;
import com.redescooter.ses.web.ros.service.SalseRosService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetService;
import com.redescooter.ses.web.ros.vo.bom.sales.ProductListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.ProductListResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SalesServiceResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:SalseRosServiceImpl
 * @description: SalseRosServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 13:04
 */
@Service
@Slf4j
public class SalseRosServiceImpl implements SalseRosService {

    @Autowired
    private SalseRosServiceMapper salseRosServiceMapper;

    @Autowired
    private OpeRegionalPriceSheetService opeRegionalPriceSheetService;

    @Autowired
    private OpeRegionalPriceSheetHistoryService opeRegionalPriceSheetHistoryService;

    @Reference
    private IdAppService idAppService;

    /**
     * @param enter
     * @desc: 服务类型统计
     * @param: enter
     * @retrn: map
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public Map<String, Integer> countByServiceType(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // 产品数量
        map.put("product", salseRosServiceMapper.productCount(enter));
        // 售后产品数量
        map.put("afterSale", salseRosServiceMapper.afterSaleCount(enter));
        // 增值服务数量
        map.put("service", 1);
        return map;
    }

    /**
     * @param enter
     * @desc: 产品列表
     * @param: enter
     * @retrn: ProductListResult
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ProductListResult> productList(ProductListEnter enter) {
        int count = salseRosServiceMapper.productListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ProductListResult> results = salseRosServiceMapper.productList(enter);
        List<Long> assIdList = new ArrayList<>();
        results.forEach(item -> {
            assIdList.add(item.getId());
        });
        QueryWrapper<OpeRegionalPriceSheet> opeRegionalPriceSheetQueryWrapper = new QueryWrapper<>();
        opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_DR, 0);
        opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_USER_ID, enter.getUserId());
        opeRegionalPriceSheetQueryWrapper.in(OpeRegionalPriceSheet.COL_ASSEMBLY_ID, assIdList);
        List<OpeRegionalPriceSheet> opeRegionalPriceSheetList = opeRegionalPriceSheetService.list(opeRegionalPriceSheetQueryWrapper);
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetList)) {
            results.forEach(result -> {
                opeRegionalPriceSheetList.forEach(item -> {
                    if (result.getId().equals(item.getAssemblyId()) && item.getCurrencyUnit().equals(CurrencyUnitEnums.EN.getValue())) {
                        result.setProductEnPrice(item.getSalesPrice().toString());
                        result.setProductEnUnit(item.getCurrencyUnit());
                    }
                    if (result.getId().equals(item.getAssemblyId()) && item.getCurrencyUnit().equals(CurrencyUnitEnums.FR.getValue())) {

                        result.setProductFrPrice(item.getSalesPrice().toString());
                        result.setProductFrUnit(item.getCurrencyUnit());
                    }
                    result.setRefuseTime(item.getUpdatedTime());
                });
            });
        }

        return PageResult.create(enter, count, results);
    }

    /**
     * @param enter
     * @desc: 售后产品列表
     * @param: enter
     * @retrn: ProductListResult
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ProductListResult> afterSaleList(ProductListEnter enter) {
        int count = salseRosServiceMapper.afterSaleListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<ProductListResult> results = salseRosServiceMapper.afterSaleList(enter);
        List<Long> assIdList = new ArrayList<>();
        results.forEach(item -> {
            assIdList.add(item.getId());
        });
        QueryWrapper<OpeRegionalPriceSheet> opeRegionalPriceSheetQueryWrapper = new QueryWrapper<>();
        opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_DR, 0);
        opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_USER_ID, enter.getUserId());
        opeRegionalPriceSheetQueryWrapper.in(OpeRegionalPriceSheet.COL_ASSEMBLY_ID, assIdList);
        List<OpeRegionalPriceSheet> opeRegionalPriceSheetList = opeRegionalPriceSheetService.list(opeRegionalPriceSheetQueryWrapper);
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetList)) {
            results.forEach(result -> {
                opeRegionalPriceSheetList.forEach(item -> {
                    if (result.getId().equals(item.getAssemblyId()) && item.getCurrencyUnit().equals(CurrencyUnitEnums.EN.getValue())) {
                        result.setProductEnPrice(item.getSalesPrice().toString());
                        result.setProductEnUnit(item.getCurrencyUnit());
                    }
                    if (result.getId().equals(item.getAssemblyId()) && item.getCurrencyUnit().equals(CurrencyUnitEnums.FR.getValue())) {

                        result.setProductFrPrice(item.getSalesPrice().toString());
                        result.setProductFrUnit(item.getCurrencyUnit());
                    }
                });
            });
        }
        return PageResult.create(enter, count, results);
    }

    /**
     * @param enter
     * @desc: 销售产品描述
     * @param: enter
     * @retrn: SalesServiceResult
     * @auther: alex
     * @date: 2020/3/3 17:41
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<SalesServiceResult> salesServiceList(PageEnter enter) {
        int count = 1;
        List<SalesServiceResult> result = new ArrayList<>();
        result.add(SalesServiceResult.builder()
                .id(1000000L)
                .service("License")
                .desc("车辆上牌")
                .productFrPrice(BigDecimal.ONE)
                .productFrUnit(CurrencyUnitEnums.FR.getValue())
                .productEnPrice(BigDecimal.ONE)
                .productEnUnit(CurrencyUnitEnums.EN.getValue())
                .refuseTime(new Date())
                .build());

        return PageResult.create(enter, count, result);
    }

    /**
     * 产品类型
     *
     * @param enter
     * @return
     */
    @Override
    public List<String> productTypeList(GeneralEnter enter) {
        List<String> result = new ArrayList<>();
        for (BomTypeEnums item : BomTypeEnums.values()) {
            if (!StringUtils.equals(item.getCode(), BomTypeEnums.PARTS.getCode())) {
                result.add(item.getCode());
            }
        }
        return result;
    }

    /**
     * 销售产品报价
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult editSalesProductPrice(SccPriceEnter enter) {
        // 查询当前Id 的报价 确定是否为第一次插入
        List<OpeRegionalPriceSheet> regionalPriceSheetList = salseRosServiceMapper.productPriceList(enter);

        List<OpeRegionalPriceSheet> opeRegionalPriceList = new ArrayList<>();

        List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList = new ArrayList<>();

        if (CollectionUtils.isEmpty(regionalPriceSheetList)) {
            // 第一次保存
            OpeRegionalPriceSheet frRegionalPriceSheet = buildOpeRegionalPriceSheetSingle(null, enter, CurrencyUnitEnums.FR.getValue());
            frRegionalPriceSheet.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET));
            frRegionalPriceSheet.setCreatedBy(enter.getUserId());
            frRegionalPriceSheet.setCreatedTime(new Date());
            opeRegionalPriceList.add(frRegionalPriceSheet);

            OpeRegionalPriceSheet enRegionalPriceSheet = buildOpeRegionalPriceSheetSingle(null, enter, CurrencyUnitEnums.EN.getValue());
            enRegionalPriceSheet.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET));
            enRegionalPriceSheet.setCreatedBy(enter.getUserId());
            enRegionalPriceSheet.setCreatedTime(new Date());
            opeRegionalPriceList.add(enRegionalPriceSheet);

            // 保存记录
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.FR.getValue()));
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.EN.getValue()));

        } else {
            // 修改报价
            regionalPriceSheetList.forEach(item -> {
                if (StringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.FR.getValue())) {
                    // 对价格不相等的进行更新 价格相等时不进行 任何操作
                    if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductFrPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                        opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.FR.getValue()));
                        // 生成节点
                        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.FR.getValue()));
                    }
                } else {
                    if (StringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.EN.getValue())) {
                        // 对价格不相等的进行更新 价格相等时不进行 任何操作
                        if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductEnPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                            opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.EN.getValue()));
                            // 生成节点
                            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.EN.getValue()));
                        }
                    }
                }
            });
        }
        //价格保存
        if (CollectionUtils.isNotEmpty(opeRegionalPriceList)) {
            opeRegionalPriceSheetService.saveOrUpdateBatch(opeRegionalPriceList);
        }
        //节点保存
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetHistoryList)) {
            opeRegionalPriceSheetHistoryService.saveOrUpdateBatch(opeRegionalPriceSheetHistoryList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 销售产品报价
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult editAfterProductPrice(SccPriceEnter enter) {
        // 查询当前Id 的报价 确定是否为第一次插入
        List<OpeRegionalPriceSheet> regionalPriceSheetList = salseRosServiceMapper.productPriceList(enter);

        List<OpeRegionalPriceSheet> opeRegionalPriceList = new ArrayList<>();

        List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList = new ArrayList<>();

        if (CollectionUtils.isEmpty(regionalPriceSheetList)) {
            // 第一次保存
            OpeRegionalPriceSheet frRegionalPriceSheet = buildOpeRegionalPriceSheetSingle(null, enter, CurrencyUnitEnums.FR.getValue());
            frRegionalPriceSheet.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET));
            frRegionalPriceSheet.setCreatedBy(enter.getUserId());
            frRegionalPriceSheet.setCreatedTime(new Date());
            opeRegionalPriceList.add(frRegionalPriceSheet);

            OpeRegionalPriceSheet enRegionalPriceSheet = buildOpeRegionalPriceSheetSingle(null, enter, CurrencyUnitEnums.EN.getValue());
            enRegionalPriceSheet.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET));
            enRegionalPriceSheet.setCreatedBy(enter.getUserId());
            enRegionalPriceSheet.setCreatedTime(new Date());
            opeRegionalPriceList.add(enRegionalPriceSheet);

            // 保存记录
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.FR.getValue()));
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.EN.getValue()));

        } else {
            // 修改报价
            regionalPriceSheetList.forEach(item -> {
                if (StringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.FR.getValue())) {
                    // 对价格不相等的进行更新 价格相等时不进行 任何操作
                    if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductFrPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                        opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.FR.getValue()));
                        // 生成节点
                        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.FR.getValue()));
                    }
                } else {
                    if (StringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.EN.getValue())) {
                        // 对价格不相等的进行更新 价格相等时不进行 任何操作
                        if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductEnPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                            opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.EN.getValue()));
                            // 生成节点
                            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.EN.getValue()));
                        }
                    }
                }
            });
        }
        //价格保存
        if (CollectionUtils.isNotEmpty(opeRegionalPriceList)) {
            opeRegionalPriceSheetService.saveOrUpdateBatch(opeRegionalPriceList);
        }
        //节点保存
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetHistoryList)) {
            opeRegionalPriceSheetHistoryService.saveOrUpdateBatch(opeRegionalPriceSheetHistoryList);
        }
        return null;
    }

    /**
     * 销售产品报价
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult editServiceProductPrice(SccPriceEnter enter) {
        // 查询当前Id 的报价 确定是否为第一次插入
        List<OpeRegionalPriceSheet> regionalPriceSheetList = salseRosServiceMapper.productPriceList(enter);

        List<OpeRegionalPriceSheet> opeRegionalPriceList = new ArrayList<>();

        List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList = new ArrayList<>();

        if (CollectionUtils.isEmpty(regionalPriceSheetList)) {
            // 第一次保存
            OpeRegionalPriceSheet frRegionalPriceSheet = buildOpeRegionalPriceSheetSingle(null, enter, CurrencyUnitEnums.FR.getValue());
            frRegionalPriceSheet.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET));
            frRegionalPriceSheet.setCreatedBy(enter.getUserId());
            frRegionalPriceSheet.setCreatedTime(new Date());
            opeRegionalPriceList.add(frRegionalPriceSheet);

            OpeRegionalPriceSheet enRegionalPriceSheet = buildOpeRegionalPriceSheetSingle(null, enter, CurrencyUnitEnums.EN.getValue());
            enRegionalPriceSheet.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET));
            enRegionalPriceSheet.setCreatedBy(enter.getUserId());
            enRegionalPriceSheet.setCreatedTime(new Date());
            opeRegionalPriceList.add(enRegionalPriceSheet);

            // 保存记录
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.FR.getValue()));
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.EN.getValue()));

        } else {
            // 修改报价
            regionalPriceSheetList.forEach(item -> {
                if (StringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.FR.getValue())) {
                    // 对价格不相等的进行更新 价格相等时不进行 任何操作
                    if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductFrPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                        opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.FR.getValue()));
                        // 生成节点
                        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.FR.getValue()));
                    }
                } else {
                    if (StringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.EN.getValue())) {
                        // 对价格不相等的进行更新 价格相等时不进行 任何操作
                        if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductEnPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                            opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.EN.getValue()));
                            // 生成节点
                            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.EN.getValue()));
                        }
                    }
                }
            });
        }
        //价格保存
        if (CollectionUtils.isNotEmpty(opeRegionalPriceList)) {
            opeRegionalPriceSheetService.saveOrUpdateBatch(opeRegionalPriceList);
        }
        //节点保存
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetHistoryList)) {
            opeRegionalPriceSheetHistoryService.saveOrUpdateBatch(opeRegionalPriceSheetHistoryList);
        }
        return null;
    }

    /**
     * 产品价格详情
     *
     * @param enter
     * @return
     */
    @Override
    public SccPriceResult priceDetail(IdEnter enter) {
        SccPriceResult result = new SccPriceResult();
        result.setId(enter.getId());
        QueryWrapper<OpeRegionalPriceSheet> opeRegionalPriceSheetQueryWrapper = new QueryWrapper<>();
        opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_USER_ID, enter.getUserId());
        opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_DR, 0);
        opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_ASSEMBLY_ID, enter.getId());
        List<OpeRegionalPriceSheet> regionalPriceSheetList = opeRegionalPriceSheetService.list(opeRegionalPriceSheetQueryWrapper);
        if (CollectionUtils.isNotEmpty(regionalPriceSheetList)) {
            regionalPriceSheetList.forEach(item -> {
                if (item.getCurrencyUnit().equals(CurrencyUnitEnums.EN.getValue())) {
                    result.setProductEnPrice(item.getSalesPrice().toString());
                    result.setProductEnUnit(item.getCurrencyUnit());
                } else {
                    result.setProductFrPrice(item.getSalesPrice().toString());
                    result.setProductFrUnit(item.getCurrencyUnit());
                }
            });
        }
        return result;
    }

    /**
     * 价格列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<SccPriceResult> priceList(ProductPriceHistroyListEnter enter) {
        int count = salseRosServiceMapper.sccPriceHistroyCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SccPriceResult> results = salseRosServiceMapper.sccPriceHistroyList(enter);

        results.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getProductEnPrice())) {
                item.setProductEnUnit(CurrencyUnitEnums.EN.getValue());
            }
            if (StringUtils.isNotEmpty(item.getProductFrPrice())) {
                item.setProductFrUnit(CurrencyUnitEnums.FR.getValue());
            }
        });
        return PageResult.create(enter, count, results);
    }


    private OpeRegionalPriceSheetHistory buildOpeRegionalPriceSheetHistory(SccPriceEnter enter, Long regionalPriceSheetId, String unit) {
        OpeRegionalPriceSheetHistory opeRegionalPriceSheetHistory = new OpeRegionalPriceSheetHistory();
        opeRegionalPriceSheetHistory.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET_HISTORY));
        opeRegionalPriceSheetHistory.setDr(0);
        opeRegionalPriceSheetHistory.setTenantId(0L);
        opeRegionalPriceSheetHistory.setUserId(enter.getUserId());
        opeRegionalPriceSheetHistory.setStatus(BomStatusEnums.NORMAL.getValue());

        opeRegionalPriceSheetHistory.setAssemblyId(enter.getId());
        opeRegionalPriceSheetHistory.setPartId(0L);
        opeRegionalPriceSheetHistory.setPriceType("0");

        opeRegionalPriceSheetHistory.setRegionalPriceSheetId(regionalPriceSheetId);
        if (StringUtils.equals(CurrencyUnitEnums.FR.getValue(), unit)) {
            opeRegionalPriceSheetHistory.setSalesPrice(new BigDecimal(enter.getProductFrPrice()));
            opeRegionalPriceSheetHistory.setCurrencyType(CurrencyUnitEnums.FR.getCode());
            opeRegionalPriceSheetHistory.setCurrencyUnit(unit);
        } else {
            opeRegionalPriceSheetHistory.setSalesPrice(new BigDecimal(enter.getProductEnPrice()));
            opeRegionalPriceSheetHistory.setCurrencyType(CurrencyUnitEnums.EN.getCode());
            opeRegionalPriceSheetHistory.setCurrencyUnit(unit);
        }
        opeRegionalPriceSheetHistory.setStandardCurrency(CurrencyUnitEnums.CN.getCode());
        opeRegionalPriceSheetHistory.setExchangeRate("0");
        opeRegionalPriceSheetHistory.setCountryCode(enter.getCountry());
        opeRegionalPriceSheetHistory.setCountryCity("0");
        opeRegionalPriceSheetHistory.setCountryLanguage(enter.getLanguage());
        opeRegionalPriceSheetHistory.setRevision(0);
        opeRegionalPriceSheetHistory.setCreatedBy(enter.getUserId());
        opeRegionalPriceSheetHistory.setCreatedTime(new Date());
        opeRegionalPriceSheetHistory.setUpdatedBy(enter.getUserId());
        opeRegionalPriceSheetHistory.setUpdatedTime(new Date());

        return opeRegionalPriceSheetHistory;
    }

    private OpeRegionalPriceSheet buildOpeRegionalPriceSheetSingle(OpeRegionalPriceSheet opeRegionalPrice, SccPriceEnter enter, String unit) {
        if (opeRegionalPrice == null) {
            opeRegionalPrice = new OpeRegionalPriceSheet();
        }
        opeRegionalPrice.setDr(0);
        opeRegionalPrice.setUserId(enter.getUserId());
        opeRegionalPrice.setTenantId(0L);
        opeRegionalPrice.setStatus(BomStatusEnums.NORMAL.getValue());
        opeRegionalPrice.setPartId(0L);
        opeRegionalPrice.setAssemblyId(enter.getId());
        opeRegionalPrice.setEffectiveTime(new Date());
        opeRegionalPrice.setValidFlag("1");
        opeRegionalPrice.setPriceType("1");

        if (StringUtils.equals(CurrencyUnitEnums.FR.getValue(), unit)) {
            opeRegionalPrice.setSalesPrice(new BigDecimal(enter.getProductFrPrice()));
            opeRegionalPrice.setCurrencyType(CurrencyUnitEnums.FR.getCode());
            opeRegionalPrice.setCurrencyUnit(unit);
        } else {
            opeRegionalPrice.setSalesPrice(new BigDecimal(enter.getProductEnPrice()));
            opeRegionalPrice.setSalesPrice(new BigDecimal(enter.getProductEnPrice()));
            opeRegionalPrice.setCurrencyType(CurrencyUnitEnums.EN.getCode());
            opeRegionalPrice.setCurrencyUnit(unit);
        }
        opeRegionalPrice.setStandardCurrency(CurrencyUnitEnums.CN.getCode());
        opeRegionalPrice.setExchangeRate("0");
        opeRegionalPrice.setCountryCode(enter.getCountry());
        opeRegionalPrice.setCountryCity("0");
        opeRegionalPrice.setCountryLanguage(enter.getLanguage());
        opeRegionalPrice.setRevision(0);
        opeRegionalPrice.setUpdatedBy(enter.getUserId());
        opeRegionalPrice.setUpdatedTime(new Date());
        return opeRegionalPrice;
    }
}
