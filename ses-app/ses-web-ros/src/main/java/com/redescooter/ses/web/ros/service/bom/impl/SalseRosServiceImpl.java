package com.redescooter.ses.web.ros.service.bom.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.enums.bom.SalseTabEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.bom.SalseRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheet;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheetHistory;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetService;
import com.redescooter.ses.web.ros.service.bom.SalseRosService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.ProductGetTypeResult;
import com.redescooter.ses.web.ros.vo.bom.sales.ProductListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.ProductListResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SalesServiceResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SubentryProductResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @Version???1.3
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

    @DubboReference
    private IdAppService idAppService;

    /**
     * @param enter
     * @desc: ??????????????????
     * @param: enter
     * @retrn: map
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public Map<String, Integer> commonCountStatus(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        // ????????????
        map.put(SalseTabEnums.SALES_PRODUCTS.getCode(), salseRosServiceMapper.productCount(enter));
        // ??????????????????
        map.put(SalseTabEnums.AFTER_SALES_PRODUCTS.getCode(), salseRosServiceMapper.afterProductCount(enter));
        // ??????????????????,???????????????
        map.put(SalseTabEnums.VALUE_ADDED_PRODUCTS.getCode(), 1);
        return map;
    }

    @Override
    public List<ProductGetTypeResult> getProductType(GeneralEnter enter) {
        List<ProductGetTypeResult> list = new ArrayList<>();

        ProductGetTypeResult type1 = new ProductGetTypeResult();
        type1.setKey(BomCommonTypeEnums.ACCESSORY.getCode());
        type1.setValue(Integer.valueOf(BomCommonTypeEnums.ACCESSORY.getValue()));


        ProductGetTypeResult type2 = new ProductGetTypeResult();
        type2.setKey(BomCommonTypeEnums.BATTERY.getCode());
        type2.setValue(Integer.valueOf(BomCommonTypeEnums.BATTERY.getValue()));


        ProductGetTypeResult type3 = new ProductGetTypeResult();
        type3.setKey(BomCommonTypeEnums.SCOOTER.getCode());
        type3.setValue(Integer.valueOf(BomCommonTypeEnums.SCOOTER.getValue()));

        list.add(type1);
        list.add(type2);
        list.add(type3);
        return list;
    }

    /**
     * @param enter
     * @desc: ????????????
     * @param: enter
     * @retrn: ProductListResult
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ProductListResult> productList(ProductListEnter enter) {
      if (NumberUtil.notNullAndGtFifty(enter.getKeyword())){
        return PageResult.createZeroRowResult(enter);
      }
        int count = salseRosServiceMapper.productListCount(enter);
        if (NumberUtil.eqZero(count)) {
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
        opeRegionalPriceSheetQueryWrapper.in(OpeRegionalPriceSheet.COL_PARTS_PRODUCT_ID, assIdList);
        List<OpeRegionalPriceSheet> opeRegionalPriceSheetList = opeRegionalPriceSheetService.list(opeRegionalPriceSheetQueryWrapper);
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetList)) {
            results.forEach(result -> {
                opeRegionalPriceSheetList.forEach(item -> {
                    if (result.getId().equals(item.getPartsProductId()) && item.getCurrencyUnit().equals(CurrencyUnitEnums.EN.getValue())) {
                        result.setProductEnPrice(item.getSalesPrice().toString());
                        result.setProductEnUnit(item.getCurrencyUnit());
                    }
                    if (result.getId().equals(item.getPartsProductId()) && item.getCurrencyUnit().equals(CurrencyUnitEnums.FR.getValue())) {
                        result.setProductFrPrice(item.getSalesPrice().toString());
                        result.setProductFrUnit(item.getCurrencyUnit());
                    }
                    // ????????????????????????????????????????????????
                    if (result.getId().equals(item.getPartsProductId()) && StringManaConstant.entityIsNotNull(result.getRefuseTime()) && DateUtil.timeComolete(result.getRefuseTime(),
                            item.getUpdatedTime()) > 0) {
                        result.setRefuseTime(item.getUpdatedTime());
                    }
                });
            });
        }

        return PageResult.create(enter, count, results);
    }

    @Override
    public List<SubentryProductResult> items(IdEnter enter) {
        List<SubentryProductResult> results = salseRosServiceMapper.productItems(enter);
        return results;
    }

    @Override
    public List<ProductGetTypeResult> getAfterProductType(GeneralEnter enter) {

        List<ProductGetTypeResult> list = new ArrayList<>();

        ProductGetTypeResult type1 = new ProductGetTypeResult();
        type1.setKey(BomCommonTypeEnums.PARTS.getCode());
        type1.setValue(Integer.valueOf(BomCommonTypeEnums.PARTS.getValue()));

        ProductGetTypeResult type2 = new ProductGetTypeResult();
        type2.setKey(BomCommonTypeEnums.COMBINATION.getCode());
        type2.setValue(Integer.valueOf(BomCommonTypeEnums.COMBINATION.getValue()));

        list.add(type1);
        list.add(type2);

        return list;
    }

    /**
     * @param enter
     * @desc: ??????????????????
     * @param: enter
     * @retrn: ProductListResult
     * @auther: alex
     * @date: 2020/2/25 18:08
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<ProductListResult> afterSaleList(ProductListEnter enter) {
        int count = salseRosServiceMapper.afterSaleListCount(enter);
        if (NumberUtil.eqZero(count)) {
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
        opeRegionalPriceSheetQueryWrapper.in(OpeRegionalPriceSheet.COL_PARTS_PRODUCT_ID, assIdList);
        List<OpeRegionalPriceSheet> opeRegionalPriceSheetList = opeRegionalPriceSheetService.list(opeRegionalPriceSheetQueryWrapper);

        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetList)) {
            results.forEach(result -> {
                opeRegionalPriceSheetList.forEach(item -> {
                    if (result.getId().equals(item.getPartsProductId()) && item.getCurrencyUnit().equals(CurrencyUnitEnums.EN.getValue())) {
                        result.setProductEnPrice(item.getSalesPrice().toString());
                        result.setProductEnUnit(item.getCurrencyUnit());
                    }
                    if (result.getId().equals(item.getPartsProductId()) && item.getCurrencyUnit().equals(CurrencyUnitEnums.FR.getValue())) {

                        result.setProductFrPrice(item.getSalesPrice().toString());
                        result.setProductFrUnit(item.getCurrencyUnit());
                    }
                    // ????????????????????????????????????????????????
                    if (result.getId().equals(item.getPartsProductId()) && StringManaConstant.entityIsNotNull(result.getRefuseTime()) && DateUtil.timeComolete(result.getRefuseTime(),
                        item.getUpdatedTime()) > 0) {
                        result.setRefuseTime(item.getUpdatedTime());
                    }
                });
            });
        }
        return PageResult.create(enter, count, results);
    }

    /**
     * @param enter
     * @desc: ??????????????????
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
        SalesServiceResult serviceResult = SalesServiceResult.builder()
                .id(1L)
                .service("License")
                .desc("????????????")
                .productFrPrice(BigDecimal.ONE)
                .productFrUnit(CurrencyUnitEnums.FR.getValue())
                .productEnPrice(BigDecimal.ONE)
                .productEnUnit(CurrencyUnitEnums.EN.getValue())
                .refuseTime(new Date())
                .build();
        // ??????????????????
        IdEnter idEnter = new IdEnter();
        BeanUtils.copyProperties(enter, idEnter);
        idEnter.setId(1L);
        SccPriceResult sccPriceResult = priceDetail(idEnter);
        if (StringManaConstant.entityIsNotNull(sccPriceResult)) {
            serviceResult.setProductFrPrice(sccPriceResult.getProductFrPrice());
            serviceResult.setProductFrUnit(CurrencyUnitEnums.FR.getValue());
            serviceResult.setProductEnPrice(sccPriceResult.getProductEnPrice());
            serviceResult.setProductEnUnit(CurrencyUnitEnums.EN.getValue());
        }
        result.add(serviceResult);
        return PageResult.create(enter, count, result);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<String> productTypeList(GeneralEnter enter) {
        List<String> result = new ArrayList<>();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            if (!SesStringUtils.equals(item.getCode(), BomCommonTypeEnums.PARTS.getCode())) {
                result.add(item.getCode());
            }
        }
        return result;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult editSalesProductPrice(SccPriceEnter enter) {
        // ????????????Id ????????? ??????????????????????????????
        List<OpeRegionalPriceSheet> regionalPriceSheetList = salseRosServiceMapper.productPriceList(enter);

        List<OpeRegionalPriceSheet> opeRegionalPriceList = new ArrayList<>();

        List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList = new ArrayList<>();

        if (CollectionUtils.isEmpty(regionalPriceSheetList)) {
            // ???????????????
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

            // ????????????
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.FR.getValue()));
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, enRegionalPriceSheet.getId(),
                CurrencyUnitEnums.EN.getValue()));

        } else {
            // ????????????
            regionalPriceSheetList.forEach(item -> {
                if (SesStringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.FR.getValue())) {
                    // ????????????????????????????????? ???????????????????????? ????????????
                    if (!item.getSalesPrice().setScale(2, BigDecimal.ROUND_HALF_UP).equals(new BigDecimal(enter.getProductFrPrice()).setScale(2, BigDecimal.ROUND_HALF_UP))) {
                        opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.FR.getValue()));
                        // ????????????
                        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.FR.getValue()));
                    }
                }
                if (SesStringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.EN.getValue())) {
                    // ????????????????????????????????? ???????????????????????? ????????????
                    if (!item.getSalesPrice().setScale(2, BigDecimal.ROUND_HALF_UP).equals(new BigDecimal(enter.getProductEnPrice()).setScale(2, BigDecimal.ROUND_HALF_UP))) {
                        opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.EN.getValue()));
                        // ????????????
                        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.EN.getValue()));
                    }
                }
            });
        }
        //????????????
        if (CollectionUtils.isNotEmpty(opeRegionalPriceList)) {
            opeRegionalPriceSheetService.saveOrUpdateBatch(opeRegionalPriceList);
        }
        //????????????
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetHistoryList)) {
            opeRegionalPriceSheetHistoryService.saveOrUpdateBatch(opeRegionalPriceSheetHistoryList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult editAfterProductPrice(SccPriceEnter enter) {
        // ????????????Id ????????? ??????????????????????????????
        List<OpeRegionalPriceSheet> regionalPriceSheetList = salseRosServiceMapper.productPriceList(enter);

        List<OpeRegionalPriceSheet> opeRegionalPriceList = new ArrayList<>();

        List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList = new ArrayList<>();

        if (CollectionUtils.isEmpty(regionalPriceSheetList)) {
            // ???????????????
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

            // ????????????
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.FR.getValue()));
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.EN.getValue()));

        } else {
            // ????????????
            regionalPriceSheetList.forEach(item -> {
                if (SesStringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.FR.getValue())) {
                    // ????????????????????????????????? ???????????????????????? ????????????
                    if (!item.getSalesPrice().setScale(2, BigDecimal.ROUND_HALF_UP).equals(new BigDecimal(enter.getProductFrPrice()).setScale(2, BigDecimal.ROUND_HALF_UP))) {
                        opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.FR.getValue()));
                        // ????????????
                        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.FR.getValue()));
                    }
                }
                if (SesStringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.EN.getValue())) {
                    // ????????????????????????????????? ???????????????????????? ????????????
                    if (!item.getSalesPrice().setScale(2, BigDecimal.ROUND_HALF_UP).equals(new BigDecimal(enter.getProductEnPrice()).setScale(2, BigDecimal.ROUND_HALF_UP))) {
                        opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.EN.getValue()));
                        // ????????????
                        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.EN.getValue()));
                    }
                }
            });
        }
        //????????????
        if (CollectionUtils.isNotEmpty(opeRegionalPriceList)) {
            opeRegionalPriceSheetService.saveOrUpdateBatch(opeRegionalPriceList);
        }
        //????????????
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetHistoryList)) {
            opeRegionalPriceSheetHistoryService.saveOrUpdateBatch(opeRegionalPriceSheetHistoryList);
        }
        return null;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult editServiceProductPrice(SccPriceEnter enter) {
        // ????????????Id ????????? ??????????????????????????????
        List<OpeRegionalPriceSheet> regionalPriceSheetList = salseRosServiceMapper.productPriceList(enter);

        List<OpeRegionalPriceSheet> opeRegionalPriceList = new ArrayList<>();

        List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList = new ArrayList<>();

        if (CollectionUtils.isEmpty(regionalPriceSheetList)) {
            // ???????????????
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

            // ????????????
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.FR.getValue()));
            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, frRegionalPriceSheet.getId(), CurrencyUnitEnums.EN.getValue()));

        } else {
            // ????????????
            regionalPriceSheetList.forEach(item -> {
                if (SesStringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.FR.getValue())) {
                    // ????????????????????????????????? ???????????????????????? ????????????
                    if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductFrPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                        opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.FR.getValue()));
                        // ????????????
                        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.FR.getValue()));
                    }
                } else {
                    if (SesStringUtils.equals(item.getCurrencyUnit(), CurrencyUnitEnums.EN.getValue())) {
                        // ????????????????????????????????? ???????????????????????? ????????????
                        if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductEnPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                            opeRegionalPriceList.add(buildOpeRegionalPriceSheetSingle(item, enter, CurrencyUnitEnums.EN.getValue()));
                            // ????????????
                            opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistory(enter, item.getId(), CurrencyUnitEnums.EN.getValue()));
                        }
                    }
                }
            });
        }
        //????????????
        if (CollectionUtils.isNotEmpty(opeRegionalPriceList)) {
            opeRegionalPriceSheetService.saveOrUpdateBatch(opeRegionalPriceList);
        }
        //????????????
        if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetHistoryList)) {
            opeRegionalPriceSheetHistoryService.saveOrUpdateBatch(opeRegionalPriceSheetHistoryList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
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
        opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_PARTS_PRODUCT_ID, enter.getId());
        List<OpeRegionalPriceSheet> regionalPriceSheetList = opeRegionalPriceSheetService.list(opeRegionalPriceSheetQueryWrapper);
        if (CollectionUtils.isNotEmpty(regionalPriceSheetList)) {
            regionalPriceSheetList.forEach(item -> {
                if (item.getCurrencyUnit().equals(CurrencyUnitEnums.EN.getValue())) {
                    result.setProductEnPrice(item.getSalesPrice());
                    result.setProductEnUnit(item.getCurrencyUnit());
                } else {
                    result.setProductFrPrice(item.getSalesPrice());
                    result.setProductFrUnit(item.getCurrencyUnit());
                }
                // ????????????????????????????????????????????????
                if (result.getId().equals(item.getPartsProductId()) && StringManaConstant.entityIsNotNull(result.getRefuseTime()) && DateUtil.timeComolete(result.getRefuseTime(), item.getUpdatedTime()) > 0) {
                    result.setRefuseTime(item.getUpdatedTime());
                }
            });
        }
        return result;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<SccPriceResult> priceList(ProductPriceHistroyListEnter enter) {
        int count = salseRosServiceMapper.sccPriceHistroyCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SccPriceResult> results = salseRosServiceMapper.sccPriceHistroyList(enter);

        results.forEach(item -> {
            if (StringManaConstant.entityIsNotNull(item.getProductEnPrice())) {
                item.setProductEnUnit(CurrencyUnitEnums.EN.getValue());
            }
            if (StringManaConstant.entityIsNotNull(item.getProductFrPrice())) {
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

        opeRegionalPriceSheetHistory.setPartsProductId(enter.getId());
        opeRegionalPriceSheetHistory.setPriceType("0");

        opeRegionalPriceSheetHistory.setRegionalPriceSheetId(regionalPriceSheetId);
        if (SesStringUtils.equals(CurrencyUnitEnums.FR.getValue(), unit)) {
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
        if (StringManaConstant.entityIsNull(opeRegionalPrice)) {
            opeRegionalPrice = new OpeRegionalPriceSheet();
        }
        opeRegionalPrice.setDr(0);
        opeRegionalPrice.setUserId(enter.getUserId());
        opeRegionalPrice.setTenantId(0L);
        opeRegionalPrice.setStatus(String.valueOf(BomStatusEnums.NORMAL.getValue()));
        opeRegionalPrice.setPartsProductId(enter.getId());
        opeRegionalPrice.setEffectiveTime(new Date());
        opeRegionalPrice.setValidFlag("1");
        opeRegionalPrice.setPriceType("1");

        if (SesStringUtils.equals(CurrencyUnitEnums.FR.getValue(), unit)) {
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
