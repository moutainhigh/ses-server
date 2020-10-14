package com.redescooter.ses.web.ros.service.bom.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.bom.SupplierChaimRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPartPriceHistory;
import com.redescooter.ses.web.ros.dm.OpeProductionPartPriceSheet;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartPriceHistoryService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartPriceSheetService;
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
import org.apache.commons.lang3.StringUtils;
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
    private OpeProductionPartPriceSheetService opeProductionPartPriceSheetService;

    @Autowired
    private OpeProductionPartPriceHistoryService opeProductionPartPriceHistoryService;


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
        return PageResult.create(enter, count, supplierChaimRosServiceMapper.supplierChaimList(enter));
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
        OpeProductionPartPriceSheet opePriceSheet =
            buildOpeProductionPartPriceSheetSingle(enter, opeProductionParts.getPartsType());
        //查询是否存在报价
        QueryWrapper<OpeProductionPartPriceSheet> opePriceSheetQueryWrapper = new QueryWrapper<>();
        opePriceSheetQueryWrapper.eq(OpeProductionPartPriceSheet.COL_DR, 0);
        opePriceSheetQueryWrapper.eq(OpeProductionPartPriceSheet.COL_PRODUCTION_ID, enter.getId());
        opePriceSheetQueryWrapper.eq(OpeProductionPartPriceSheet.COL_CREATED_BY, enter.getUserId());
        opePriceSheetQueryWrapper.last("limit 1");
        OpeProductionPartPriceSheet queryOpePriceSheet =
            opeProductionPartPriceSheetService.getOne(opePriceSheetQueryWrapper);
        if (queryOpePriceSheet == null) {
            // 第一次修改直接保存
            if (SesStringUtils.isBlank(enter.getProductFrUnit())) {
                throw new SesWebRosException(ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getCode(), ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getMessage());
            }
            opePriceSheet.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PART_PRICE_SHEET));
            opePriceSheet.setCreatedBy(enter.getUserId());
            opePriceSheet.setCreatedTime(new Date());
        } else {
            opePriceSheet.setId(queryOpePriceSheet.getId());
            opePriceSheet.setCreatedBy(queryOpePriceSheet.getCreatedBy());
            opePriceSheet.setCreatedTime(queryOpePriceSheet.getCreatedTime());
        }
        if (queryOpePriceSheet != null) {
            if (queryOpePriceSheet.getPrice().equals(new BigDecimal(enter.getProductFrPrice()))) {
                return new GeneralResult(enter.getRequestId());
            }
        }
        opeProductionPartPriceSheetService.saveOrUpdate(opePriceSheet);
        //生成日志
        OpeProductionPartPriceHistory opeProductionPartPriceHistory = buildopeProductionPartPriceHistoryServiceSingle(
            enter, opePriceSheet.getId(), opePriceSheet.getCurrencyUnit(), opeProductionParts.getPartsType());
        opeProductionPartPriceHistoryService.save(opeProductionPartPriceHistory);
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
        QueryWrapper<OpeProductionPartPriceSheet> opePriceSheetQueryWrapper = new QueryWrapper<>();
        opePriceSheetQueryWrapper.eq(OpeProductionPartPriceSheet.COL_DR, 0);
        opePriceSheetQueryWrapper.eq(OpeProductionPartPriceSheet.COL_PRODUCTION_ID, enter.getId());
        opePriceSheetQueryWrapper.eq(OpeProductionPartPriceSheet.COL_CREATED_BY, enter.getUserId());
        opePriceSheetQueryWrapper.last("limit 1");
        OpeProductionPartPriceSheet queryOpePriceSheet =
            opeProductionPartPriceSheetService.getOne(opePriceSheetQueryWrapper);
        if (queryOpePriceSheet == null) {
            return new SccPriceResult();
        }
        return SccPriceResult.builder()
            .id(queryOpePriceSheet.getProductionId())
                .productEnPrice(null)
                .productEnUnit(null)
            .productFrPrice(queryOpePriceSheet.getPrice()).productFrUnit(queryOpePriceSheet.getCurrencyUnit())
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
            if (!StringUtils.equals(item.getValue(),CurrencyUnitEnums.CN.getValue())){
                priceUnitResult = new PriceUnitResult();
                priceUnitResult.setCode(item.getCode());
                priceUnitResult.setVlue(item.getValue());
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

    private OpeProductionPartPriceSheet buildOpeProductionPartPriceSheetSingle(EditProductPriceEnter enter,
        Integer partsType) {
        OpeProductionPartPriceSheet saveProductionPartPriceSheet = new OpeProductionPartPriceSheet();
        saveProductionPartPriceSheet.setDr(0);
        saveProductionPartPriceSheet.setTenantId(0L);
        saveProductionPartPriceSheet.setStatus(BomStatusEnums.NORMAL.getValue());
        saveProductionPartPriceSheet.setPrice(new BigDecimal(enter.getProductFrPrice()));
        saveProductionPartPriceSheet
            .setCurrencyType(SesStringUtils.isBlank(CurrencyUnitEnums.checkValue(enter.getProductFrUnit())) == true
                ? CurrencyUnitEnums.FR.getCode()
                :
                CurrencyUnitEnums.getEnumByValue(enter.getProductFrUnit()).getCode());
        saveProductionPartPriceSheet.setCurrencyUnit(enter.getProductFrUnit());
        saveProductionPartPriceSheet.setStandardCurrency(CurrencyUnitEnums.CN.getValue());
        saveProductionPartPriceSheet.setExchangeRate("0");
        saveProductionPartPriceSheet.setProductionId(enter.getId());
        saveProductionPartPriceSheet.setProductionType(partsType);
        saveProductionPartPriceSheet.setRevision(0);
        saveProductionPartPriceSheet.setUpdatedBy(enter.getUserId());
        saveProductionPartPriceSheet.setUpdatedTime(new Date());
        return saveProductionPartPriceSheet;
    }

    private OpeProductionPartPriceHistory buildopeProductionPartPriceHistoryServiceSingle(EditProductPriceEnter enter,
        Long id, String unit, Integer partsType) {
        OpeProductionPartPriceHistory opeProductionPartPriceHistory = new OpeProductionPartPriceHistory();
        opeProductionPartPriceHistory.setId(idAppService.getId(SequenceName.OPE_PRODUCTION_PART_PRICE_HISTORY));
        opeProductionPartPriceHistory.setDr(0);
        opeProductionPartPriceHistory.setTenantId(0L);
        opeProductionPartPriceHistory.setPrice(new BigDecimal(enter.getProductFrPrice()));
        opeProductionPartPriceHistory.setPriceSheetId(id);
        opeProductionPartPriceHistory.setCurrencyType(SesStringUtils.isBlank(CurrencyUnitEnums.checkCode(unit)) == true
            ? CurrencyUnitEnums.FR.getCode() :
                CurrencyUnitEnums.checkCode(unit));
        opeProductionPartPriceHistory.setCurrencyUnit(unit);
        opeProductionPartPriceHistory.setStandardCurrency(CurrencyUnitEnums.CN.getValue());
        opeProductionPartPriceHistory.setExchangeRate("0");
        opeProductionPartPriceHistory.setProductionId(enter.getId());
        opeProductionPartPriceHistory.setProductionType(partsType);
        opeProductionPartPriceHistory.setRevision(0);
        opeProductionPartPriceHistory.setUpdatedBy(enter.getUserId());
        opeProductionPartPriceHistory.setUpdatedTime(new Date());
        opeProductionPartPriceHistory.setCreatedBy(enter.getUserId());
        opeProductionPartPriceHistory.setCreatedTime(new Date());
        return opeProductionPartPriceHistory;
    }
}
