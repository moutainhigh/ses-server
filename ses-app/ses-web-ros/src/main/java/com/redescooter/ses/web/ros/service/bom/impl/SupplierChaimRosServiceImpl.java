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
import com.redescooter.ses.web.ros.dm.OpePriceSheet;
import com.redescooter.ses.web.ros.dm.OpePriceSheetHistory;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.bom.SupplierChaimRosService;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetService;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.PriceUnitResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.EditProductPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.ProductPriceChartResult;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.SupplierChaimListEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.SupplierChaimListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.apache.dubbo.config.annotation.Service;

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
            if (!map.containsKey(type.getCode())) {
                map.put(type.getCode(), 0);
            }
        }
        map.remove(BomCommonTypeEnums.SCOOTER.getCode());
        map.remove(BomCommonTypeEnums.COMBINATION.getCode());
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
        //部品价格保存 保存价格日志
        OpePriceSheet opePriceSheet = buildOpePriceSheetSingle(enter);
        //查询是否存在报价
        QueryWrapper<OpePriceSheet> opePriceSheetQueryWrapper = new QueryWrapper<>();
        opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_DR, 0);
        opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_PARTS_ID, enter.getId());
        opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_USER_ID, enter.getUserId());
        OpePriceSheet queryOpePriceSheet = opePriceSheetService.getOne(opePriceSheetQueryWrapper);
        if (queryOpePriceSheet == null) {
            // 第一次修改直接保存
            if (SesStringUtils.isBlank(enter.getProductFrUnit())) {
                throw new SesWebRosException(ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getCode(), ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getMessage());
            }
            opePriceSheet.setId(idAppService.getId(SequenceName.OPE_PRICE_SHEET));
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
        opePriceSheetService.saveOrUpdate(opePriceSheet);
        //生成日志
        OpePriceSheetHistory opePriceSheetHistory = buildOpePriceSheetHistorySingle(enter, opePriceSheet.getId(), opePriceSheet.getCurrencyUnit());
        opePriceSheetHistoryService.save(opePriceSheetHistory);
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
        QueryWrapper<OpePriceSheet> opePriceSheetQueryWrapper = new QueryWrapper<>();
        opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_PARTS_ID, enter.getId());
        opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_DR, 0);
        opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_USER_ID, enter.getUserId());
        OpePriceSheet opePriceSheet = opePriceSheetService.getOne(opePriceSheetQueryWrapper);
        if (opePriceSheet == null) {
            return new SccPriceResult();
        }
        return SccPriceResult.builder()
                .id(opePriceSheet.getPartsId())
                .productEnPrice(null)
                .productEnUnit(null)
                .productFrPrice(opePriceSheet.getPrice())
                .productFrUnit(opePriceSheet.getCurrencyUnit())
                .refuseTime(opePriceSheet.getUpdatedTime())
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
            priceUnitResult = new PriceUnitResult();
            priceUnitResult.setCode(item.getCode());
            priceUnitResult.setVlue(item.getValue());
            priceUnitResult.setUnit(item.getName());
            list.add(priceUnitResult);
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

    private OpePriceSheet buildOpePriceSheetSingle(EditProductPriceEnter enter) {
        OpePriceSheet saveOpePriceSheet = new OpePriceSheet();
        saveOpePriceSheet.setDr(0);
        saveOpePriceSheet.setTenantId(0L);
        saveOpePriceSheet.setUserId(enter.getUserId());
        saveOpePriceSheet.setStatus(BomStatusEnums.NORMAL.getValue());
        saveOpePriceSheet.setPrice(new BigDecimal(enter.getProductFrPrice()));
        saveOpePriceSheet.setCurrencyType(SesStringUtils.isBlank(CurrencyUnitEnums.checkValue(enter.getProductFrUnit())) == true ? CurrencyUnitEnums.FR.getCode() :
                CurrencyUnitEnums.getEnumByValue(enter.getProductFrUnit()).getCode());
        saveOpePriceSheet.setCurrencyUnit(enter.getProductFrUnit());
        saveOpePriceSheet.setStandardCurrency(CurrencyUnitEnums.CN.getValue());
        saveOpePriceSheet.setExchangeRate("0");
        saveOpePriceSheet.setPartsId(enter.getId());
        saveOpePriceSheet.setRevision(0);
        saveOpePriceSheet.setUpdatedBy(enter.getUserId());
        saveOpePriceSheet.setUpdatedTime(new Date());
        return saveOpePriceSheet;
    }

    private OpePriceSheetHistory buildOpePriceSheetHistorySingle(EditProductPriceEnter enter, Long id, String unit) {
        OpePriceSheetHistory opePriceSheetHistory = new OpePriceSheetHistory();
        opePriceSheetHistory.setId(idAppService.getId(SequenceName.OPE_PRICE_SHEET_HISTORY));
        opePriceSheetHistory.setDr(0);
        opePriceSheetHistory.setTenantId(0L);
        opePriceSheetHistory.setUserId(enter.getUserId());
        opePriceSheetHistory.setPrice(new BigDecimal(enter.getProductFrPrice()));
        opePriceSheetHistory.setPriceSheetId(id);
        opePriceSheetHistory.setCurrencyType(SesStringUtils.isBlank(CurrencyUnitEnums.checkCode(unit)) == true ? CurrencyUnitEnums.FR.getCode() :
                CurrencyUnitEnums.checkCode(unit));
        opePriceSheetHistory.setCurrencyUnit(unit);
        opePriceSheetHistory.setStandardCurrency(CurrencyUnitEnums.CN.getValue());
        opePriceSheetHistory.setExchangeRate("0");
        opePriceSheetHistory.setPartsId(enter.getId());
        opePriceSheetHistory.setRevision(0);
        opePriceSheetHistory.setUpdatedBy(enter.getUserId());
        opePriceSheetHistory.setUpdatedTime(new Date());
        opePriceSheetHistory.setCreatedBy(enter.getUserId());
        opePriceSheetHistory.setCreatedTime(new Date());
        return opePriceSheetHistory;
    }
}
