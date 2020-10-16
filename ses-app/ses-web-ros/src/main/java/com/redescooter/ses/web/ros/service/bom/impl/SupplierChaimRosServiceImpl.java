package com.redescooter.ses.web.ros.service.bom.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.enums.product.ProductPriceTypeEnums;
import com.redescooter.ses.api.common.enums.product.SaleProductPriceTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.bom.SupplierChaimRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeProductPrice;
import com.redescooter.ses.web.ros.dm.OpeProductPriceHistory;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetService;
import com.redescooter.ses.web.ros.service.base.OpeProductPriceHistoryService;
import com.redescooter.ses.web.ros.service.base.OpeProductPriceService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetService;
import com.redescooter.ses.web.ros.service.bom.SupplierChaimRosService;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.PriceUnitResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.EditProductPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.ProductPriceChartResult;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.SupplierChaimListEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.SupplierChaimListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:SupplierChaimRosServiceImpl
 * @description: SupplierChaimRosServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/28 10:08
 */
@Service
@Slf4j
public class SupplierChaimRosServiceImpl implements SupplierChaimRosService {

    @Autowired
    private SupplierChaimRosServiceMapper supplierChaimRosServiceMapper;

    @Autowired
    private OpePriceSheetService opePriceSheetService;

    @Autowired
    private OpePriceSheetHistoryService opePriceSheetHistoryService;

    @Autowired
    private OpeRegionalPriceSheetService opeRegionalPriceSheetService;

    @Autowired
    private OpeRegionalPriceSheetHistoryService opeRegionalPriceSheetHistoryService;

    @Autowired
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeProductPriceService opeProductPriceService;

    @Autowired
    private OpeProductPriceHistoryService opeProductPriceHistoryService;


    @Reference
    private IdAppService idAppService;

    /**
     * @param enter
     * @desc: 类型统计
     * @param: enter
     * @retrn: map
     * @auther: alex
     * @date: 2020/2/25 14:41
     * @Version: Ros 1.2
     */
    @Override
    public Map<String, Integer> countByPartType(GeneralEnter enter) {
        List<CountByStatusResult> countByPartType = supplierChaimRosServiceMapper.countByPartType(enter);
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByPartType) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (BomCommonTypeEnums type : BomCommonTypeEnums.values()) {
            if (!map.containsKey(type.getValue())) {
                map.put(type.getValue(), 0);
            }
        }
        map.remove(BomCommonTypeEnums.SCOOTER.getValue());
        map.remove(BomCommonTypeEnums.COMBINATION.getValue());
        return map;
    }

    /**
     * @param enter
     * @desc: 供应链列表
     * @param: enter
     * @retrn: SupplierChaimListResult
     * @auther: alex
     * @date: 2020/2/25 15:13
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<SupplierChaimListResult> supplierChaimList(SupplierChaimListEnter enter) {
      if (enter.getKeyword()!=null && enter.getKeyword().length()>50){
        return PageResult.createZeroRowResult(enter);
      }
        int count = supplierChaimRosServiceMapper.supplierChaimListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<SupplierChaimListResult> supplierChaimListResultList =
            supplierChaimRosServiceMapper.supplierChaimList(enter);

        supplierChaimListResultList.forEach(item -> {
            // 产品类型
            item.setType(BomCommonTypeEnums.getEnumsByValue(item.getTypeId()).getCode());
            // 转换货币单位
            item.setUnit(item.getUnitId());
        });
        return PageResult.create(enter, count, supplierChaimListResultList);
    }

    /**
     * @param enter
     * @desc: 修改报价
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/25 15:42
     * @Version: Ros 1.2
     */
    @Transactional
    @Override
    public GeneralResult editProductPrice(EditProductPriceEnter enter) {
        OpeProductionParts opeProductionParts = opeProductionPartsService.getById(enter.getId());
        if (opeProductionParts == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        //部品价格保存 保存价格日志
        OpeProductPrice opeProductPrice =
            buildOpeProductPriceSingle(enter, SaleProductPriceTypeEnums.PURCHASE_PRICE.getValue());
        //查询是否存在报价
        QueryWrapper<OpeProductPrice> opePriceSheetQueryWrapper = new QueryWrapper<>();
        opePriceSheetQueryWrapper.eq(OpeProductPrice.COL_DR, 0);
        opePriceSheetQueryWrapper.eq(OpeProductPrice.COL_PRODUCT_ID, enter.getId());
        opePriceSheetQueryWrapper.eq(OpeProductPrice.COL_PRODUCT_PRICE_TYPE,
            SaleProductPriceTypeEnums.PURCHASE_PRICE.getValue());
        opePriceSheetQueryWrapper.last("limit 1");
        OpeProductPrice queryOpePriceSheet = opeProductPriceService.getOne(opePriceSheetQueryWrapper);
        if (queryOpePriceSheet == null) {
            // 第一次修改直接保存
            if (SesStringUtils.isBlank(enter.getProductFrUnit())) {
                throw new SesWebRosException(ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getCode(), ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getMessage());
            }
            opeProductPrice.setId(idAppService.getId(SequenceName.OPE_PRODUCT_PRICE));
            opeProductPrice.setCreatedBy(enter.getUserId());
            opeProductPrice.setCreatedTime(new Date());
        } else {
            opeProductPrice.setId(queryOpePriceSheet.getId());
            opeProductPrice.setCreatedBy(queryOpePriceSheet.getCreatedBy());
            opeProductPrice.setCreatedTime(queryOpePriceSheet.getCreatedTime());
        }
        if (queryOpePriceSheet != null) {
            if (queryOpePriceSheet.getPrice().equals(new BigDecimal(enter.getProductFrPrice()))) {
                return new GeneralResult(enter.getRequestId());
            }
        }
        opeProductPriceService.saveOrUpdate(opeProductPrice);
        //生成日志
        OpeProductPriceHistory opeProductionPartPriceHistory =
            buildOpeProductPriceHistorySingle(enter, opeProductPrice.getId(), opeProductPrice.getCurrencyType());
        opeProductPriceHistoryService.save(opeProductionPartPriceHistory);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 产品报价详情
     *
     * @param enter
     * @return
     */
    @Override
    public SccPriceResult productPriceDetail(IdEnter enter) {
        // 供应链 产品报价
        QueryWrapper<OpeProductPrice> opePriceSheetQueryWrapper = new QueryWrapper<>();
        opePriceSheetQueryWrapper.eq(OpeProductPrice.COL_DR, 0);
        opePriceSheetQueryWrapper.eq(OpeProductPrice.COL_PRODUCT_ID, enter.getId());
        opePriceSheetQueryWrapper.eq(OpeProductPrice.COL_PRODUCT_PRICE_TYPE,
            SaleProductPriceTypeEnums.PURCHASE_PRICE.getValue());
        opePriceSheetQueryWrapper.eq(OpeProductPrice.COL_CREATED_BY, enter.getUserId());
        opePriceSheetQueryWrapper.last("limit 1");
        OpeProductPrice queryOpePriceSheet = opeProductPriceService.getOne(opePriceSheetQueryWrapper);
        if (queryOpePriceSheet == null) {
            return new SccPriceResult();
        }
        return SccPriceResult.builder()
            .id(queryOpePriceSheet.getProductId())
                .productEnPrice(null)
                .productEnUnit(null)
            .productEnUnitId(0).productFrPrice(queryOpePriceSheet.getPrice())
            .productFrUnitId(queryOpePriceSheet.getCurrencyType())
            .productFrUnit(
                CurrencyUnitEnums.getEnumByValue(String.valueOf(queryOpePriceSheet.getCurrencyType())).getName())
            .refuseTime(queryOpePriceSheet.getUpdatedTime())
                .build();
    }

    /**
     * 货币单位
     *
     * @param enter
     * @return
     */
    @Override
    public List<PriceUnitResult> currencyUnit(GeneralEnter enter) {
        List<PriceUnitResult> list = new ArrayList<>();
        PriceUnitResult priceUnitResult = null;
        for (CurrencyUnitEnums item : CurrencyUnitEnums.values()) {
            if (!item.getValue().equals(CurrencyUnitEnums.CN.getValue())) {
                priceUnitResult = new PriceUnitResult();
                priceUnitResult.setCode(item.getCode());
                priceUnitResult.setVlue(String.valueOf(item.getValue()));
                priceUnitResult.setUnit(item.getName());
                list.add(priceUnitResult);
            }
        }
        return list;
    }

    /**
     * @param enter
     * @desc: 产品价格列表
     * @paam: enter
     * @retrn: ProductPriceResult
     * @auther: alex
     * @date: 2020/2/25 15:31
     * @Version: Ros 1.2
     */
    @Override
    public PageResult<SccPriceResult> productPriceHistroyList(ProductPriceHistroyListEnter enter) {
        // 供应链模块
        int count = supplierChaimRosServiceMapper.scPriceHistroyCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, supplierChaimRosServiceMapper.scPriceHistroyList(enter));
    }

    /**
     * @param enter
     * @desc: 价格图表
     * @param: enter
     * @retrn: ProductPriceResult
     * @auther: alex
     * @date: 2020/2/25 15:37
     * @Version: Ros 1.2
     */
    @Override
    public ProductPriceChartResult productPriceHistroyChart(IdEnter enter) {
        ProductPriceChartResult result = supplierChaimRosServiceMapper.productPriceHistroyChart(enter);
        List<SccPriceResult> sccPriceResultList = supplierChaimRosServiceMapper.productPriceHistroyChartList(enter);
        result.setPriceResultList(sccPriceResultList);
        return result;
    }

    private OpeProductPrice buildOpeProductPriceSingle(EditProductPriceEnter enter, Integer partsType) {
        OpeProductPrice saveOpeProductPrice = new OpeProductPrice();
        saveOpeProductPrice.setDr(0);
        saveOpeProductPrice.setTenantId(0L);
        saveOpeProductPrice.setStatus(BomStatusEnums.NORMAL.getValue());
        saveOpeProductPrice.setPrice(new BigDecimal(enter.getProductFrPrice()));
        saveOpeProductPrice.setCurrencyType(CurrencyUnitEnums.checkValue(enter.getProductFrUnit()) == null
            ? Integer.valueOf(CurrencyUnitEnums.FR.getValue())
                :
                Integer.valueOf(CurrencyUnitEnums.getEnumByValue(enter.getProductFrUnit()).getValue()));
        saveOpeProductPrice.setStandardCurrency(Integer.valueOf(CurrencyUnitEnums.CN.getValue()));
        saveOpeProductPrice.setExchangeRate("0");
        saveOpeProductPrice.setProductId(enter.getId());
        saveOpeProductPrice.setProductPriceType(partsType);
        saveOpeProductPrice.setPriceType(ProductPriceTypeEnums.PURCHASE_PRICE.getValue());
        saveOpeProductPrice.setRevision(0);
        saveOpeProductPrice.setUpdatedBy(enter.getUserId());
        saveOpeProductPrice.setUpdatedTime(new Date());
        return saveOpeProductPrice;
    }

    private OpeProductPriceHistory buildOpeProductPriceHistorySingle(EditProductPriceEnter enter, Long id,
        Integer unit) {
        OpeProductPriceHistory opeProductPriceHistory = new OpeProductPriceHistory();
        opeProductPriceHistory.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PART_PRICE_HISTORY));
        opeProductPriceHistory.setDr(0);
        opeProductPriceHistory.setTenantId(0L);
        opeProductPriceHistory.setPrice(new BigDecimal(enter.getProductFrPrice()));
        opeProductPriceHistory.setProductPriceId(id);
        opeProductPriceHistory.setCurrencyType(CurrencyUnitEnums.checkValue(String.valueOf(unit)) == null
            ? Integer.valueOf(CurrencyUnitEnums.FR.getValue()) : unit);
        opeProductPriceHistory.setStandardCurrency(Integer.valueOf(CurrencyUnitEnums.CN.getValue()));
        opeProductPriceHistory.setPriceType(ProductPriceTypeEnums.PURCHASE_PRICE.getValue());
        opeProductPriceHistory.setExchangeRate("0");
        opeProductPriceHistory.setProductId(enter.getId());
        opeProductPriceHistory.setProductPriceType(SaleProductPriceTypeEnums.PURCHASE_PRICE.getValue());
        opeProductPriceHistory.setRevision(0);
        opeProductPriceHistory.setUpdatedBy(enter.getUserId());
        opeProductPriceHistory.setUpdatedTime(new Date());
        opeProductPriceHistory.setCreatedBy(enter.getUserId());
        opeProductPriceHistory.setCreatedTime(new Date());
        return opeProductPriceHistory;
    }
}
