package com.redescooter.ses.web.ros.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.BomTypeEnums;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.enums.bom.PriceTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductPriceTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.SupplierChaimRosServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePriceSheet;
import com.redescooter.ses.web.ros.dm.OpePriceSheetHistory;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheet;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheetHistory;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.SupplierChaimRosService;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetHistoryService;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetService;
import com.redescooter.ses.web.ros.vo.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.supplierChaim.EditProductPriceEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.ProductPriceChartResult;
import com.redescooter.ses.web.ros.vo.supplierChaim.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.SccPriceEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.SupplierChaimListEnter;
import com.redescooter.ses.web.ros.vo.supplierChaim.SupplierChaimListResult;
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
        for (BomTypeEnums type : BomTypeEnums.values()) {
            if (!map.containsKey(type.getCode())) {
                map.put(type.getCode(), 0);
            }
        }
        map.remove(BomTypeEnums.SCOOTER.getCode());
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

        if (!enter.getServiceType()) {
            QueryWrapper<OpePriceSheet> opePriceSheetQueryWrapper = new QueryWrapper<>();
            opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_DR, 0);
            opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_PARTS_ID, enter.getId());
            opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_USER_ID, enter.getUserId());
            OpePriceSheet queryOpePriceSheet = opePriceSheetService.getOne(opePriceSheetQueryWrapper);
            if (queryOpePriceSheet == null) {
                // 第一次修改直接保存
                if (StringUtils.isBlank(enter.getProductFrUnit())) {
                    throw new SesWebRosException(ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getCode(), ExceptionCodeEnums.CURRENCY_UNIT_IS_EMPTY.getMessage());
                }
                if (StringUtils.isBlank(CurrencyUnitEnums.checkValue(enter.getProductFrUnit()))) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
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
                if (queryOpePriceSheet.getPrice().subtract(new BigDecimal(enter.getProductFrPrice())).intValue() == BigDecimal.ZERO.intValue()) {
                    return new GeneralResult(enter.getRequestId());
                }
            }
            opePriceSheetService.saveOrUpdate(opePriceSheet);
            //生成日志
            OpePriceSheetHistory opePriceSheetHistory = buildOpePriceSheetHistorySingle(enter, opePriceSheet.getId(), opePriceSheet.getCurrencyUnit());
            opePriceSheetHistoryService.save(opePriceSheetHistory);
        } else {
            // 货币单位校验
            checkUnit(enter);
            // 价格校验 确定是否为第一次插入
            QueryWrapper<OpeRegionalPriceSheet> opeRegionalPriceSheetQueryWrapper = new QueryWrapper<>();
            opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_DR, 0);
            opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_USER_ID, enter.getUserId());
            if (StringUtils.equals(enter.getPriceType(), ProductPriceTypeEnums.SCOOTER.getCode())) {
                opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_ASSEMBLY_ID, enter.getId());
                opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_PRICE_TYPE, PriceTypeEnums.COMBINATION.getValue());
            } else {
                opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_PART_ID, enter.getId());
                opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_PRICE_TYPE, PriceTypeEnums.COMBINATION.getValue());
            }
            List<OpeRegionalPriceSheet> regionalPriceSheetList = opeRegionalPriceSheetService.list(opeRegionalPriceSheetQueryWrapper);

            List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList = new ArrayList<>();
            List<OpeRegionalPriceSheet> saveOrUpdateOpeRegionalPriceSheetList = new ArrayList<>();
            // 第一次保存
            if (CollectionUtils.isEmpty(regionalPriceSheetList)) {
                savePrice(enter, regionalPriceSheetList, opeRegionalPriceSheetHistoryList, saveOrUpdateOpeRegionalPriceSheetList);
            } else {
                // 修改报价
                regionalPriceSheetList.forEach(item -> {
                    buildPrice(enter, regionalPriceSheetList, opeRegionalPriceSheetHistoryList, item, saveOrUpdateOpeRegionalPriceSheetList);
                });
            }
            // 价格保存
            if (CollectionUtils.isNotEmpty(saveOrUpdateOpeRegionalPriceSheetList)) {
                opeRegionalPriceSheetService.saveOrUpdateBatch(saveOrUpdateOpeRegionalPriceSheetList);
            }
            // 节点保存
            if (CollectionUtils.isNotEmpty(opeRegionalPriceSheetHistoryList)) {
                opeRegionalPriceSheetHistoryService.saveBatch(opeRegionalPriceSheetHistoryList);
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    private void savePrice(EditProductPriceEnter enter, List<OpeRegionalPriceSheet> regionalPriceSheetList, List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList,
                           List<OpeRegionalPriceSheet> saveList) {
        // 法国报价
        OpeRegionalPriceSheet frRegionalPriceSheet = buildOpeRegionalPriceSheet(new OpeRegionalPriceSheet(), enter, enter.getProductFrUnit());
        frRegionalPriceSheet.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET));
        frRegionalPriceSheet.setCreatedBy(enter.getUserId());
        frRegionalPriceSheet.setCreatedTime(new Date());

        // 英国报价
        OpeRegionalPriceSheet enRegionalPriceSheet = buildOpeRegionalPriceSheet(new OpeRegionalPriceSheet(), enter, enter.getProductEnUnit());
        enRegionalPriceSheet.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET));
        enRegionalPriceSheet.setCreatedBy(enter.getUserId());
        enRegionalPriceSheet.setCreatedTime(new Date());

        // 生成历史节点
        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistroy(enter, frRegionalPriceSheet.getId(), enter.getProductFrUnit()));
        opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistroy(enter, enRegionalPriceSheet.getId(), enter.getProductEnUnit()));

        // 放到更新集合中
        saveList.add(enRegionalPriceSheet);
        saveList.add(frRegionalPriceSheet);
    }

    private void checkUnit(EditProductPriceEnter enter) {
        // 报价参数校验
        if (StringUtils.isBlank(enter.getProductEnPrice())) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_PRICE_IS_EMPTY.getCode(), ExceptionCodeEnums.PRODUCT_PRICE_IS_EMPTY.getMessage());
        }
        // 报价单位过滤
        if (StringUtils.isBlank(enter.getProductEnUnit()) ||
                StringUtils.isBlank(enter.getProductFrUnit()) ||
                StringUtils.isBlank(CurrencyUnitEnums.checkValue(enter.getProductEnUnit())) ||
                StringUtils.isBlank(CurrencyUnitEnums.checkValue(enter.getProductFrUnit()))) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        // 价格类型
        if (StringUtils.isBlank(ProductPriceTypeEnums.checkCode(enter.getPriceType()))) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
    }

    private void buildPrice(EditProductPriceEnter enter, List<OpeRegionalPriceSheet> regionalPriceSheetList, List<OpeRegionalPriceSheetHistory> opeRegionalPriceSheetHistoryList,
                            OpeRegionalPriceSheet item, List<OpeRegionalPriceSheet> saveList) {
        // 报价修改
        if (StringUtils.equals(item.getCurrencyUnit(), enter.getProductFrUnit())) {
            // 对价格不相等的进行更新 价格相等时不进行 任何操作
            if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductFrPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                OpeRegionalPriceSheet regionalPriceSheet = buildOpeRegionalPriceSheet(item, enter, enter.getProductFrUnit());
                // 生成节点
                opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistroy(enter, regionalPriceSheet.getId(), enter.getProductFrUnit()));
                saveList.add(regionalPriceSheet);
            }
        } else {
            if (StringUtils.equals(item.getCurrencyUnit(), enter.getProductEnUnit())) {
                // 对价格不相等的进行更新 价格相等时不进行 任何操作
                if (item.getSalesPrice().subtract(new BigDecimal(enter.getProductEnPrice())).intValue() != BigDecimal.ZERO.intValue()) {
                    OpeRegionalPriceSheet regionalPriceSheet = buildOpeRegionalPriceSheet(item, enter, enter.getProductEnUnit());
                    // 生成节点
                    opeRegionalPriceSheetHistoryList.add(buildOpeRegionalPriceSheetHistroy(enter, regionalPriceSheet.getId(), enter.getProductEnUnit()));
                    saveList.add(regionalPriceSheet);
                }
            }
        }
    }

    /**
     * 产品报价详情
     *
     * @param enter
     * @return
     */
    @Override
    public SccPriceResult productPriceDetail(SccPriceEnter enter) {
        // 价格类型
        if (StringUtils.isBlank(ProductPriceTypeEnums.checkValue(enter.getPriceType()))) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        SccPriceResult result = null;
        if (!enter.getServiceType()) {
            // 供应链 产品报价
            QueryWrapper<OpePriceSheet> opePriceSheetQueryWrapper = new QueryWrapper<>();
            opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_PARTS_ID, enter.getId());
            opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_DR, 0);
            opePriceSheetQueryWrapper.eq(OpePriceSheet.COL_USER_ID, enter.getUserId());
            OpePriceSheet opePriceSheet = opePriceSheetService.getOne(opePriceSheetQueryWrapper);
            if (opePriceSheet == null) {
                return new SccPriceResult();
            }
            result = SccPriceResult.builder()
                    .id(opePriceSheet.getPartsId())
                    .productEnPrice(null)
                    .productEnUnit(null)
                    .productFrPrice(opePriceSheet.getPrice().toString())
                    .productFrUnit(opePriceSheet.getCurrencyUnit())
                    .refuseTime(opePriceSheet.getUpdatedTime())
                    .build();
        } else {
            // 销售产品报价
            QueryWrapper<OpeRegionalPriceSheet> opeRegionalPriceSheetQueryWrapper = new QueryWrapper<>();
            opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_DR, 0);
            opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_USER_ID, enter.getUserId());
            if (StringUtils.equals(enter.getPriceType(), ProductPriceTypeEnums.SCOOTER.getCode())) {
                opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_ASSEMBLY_ID, enter.getId());
                opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_PRICE_TYPE, PriceTypeEnums.COMBINATION.getValue());
            } else {
                opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_PART_ID, enter.getId());
                opeRegionalPriceSheetQueryWrapper.eq(OpeRegionalPriceSheet.COL_PRICE_TYPE, PriceTypeEnums.COMBINATION.getValue());
            }
            List<OpeRegionalPriceSheet> regionalPriceSheetList = opeRegionalPriceSheetService.list(opeRegionalPriceSheetQueryWrapper);
            if (CollectionUtils.isEmpty(regionalPriceSheetList)) {
                return new SccPriceResult();
            }
            for (OpeRegionalPriceSheet item : regionalPriceSheetList) {
                result.setId(StringUtils.equals(enter.getPriceType(), ProductPriceTypeEnums.SCOOTER.getCode()) == true ? item.getAssemblyId() : item.getPartId());

                if (StringUtils.equals(CurrencyUnitEnums.FR.getValue(), item.getCurrencyUnit())) {
                    result.setProductFrPrice(item.getSalesPrice().toString());
                    result.setProductFrUnit(item.getCurrencyUnit());
                } else {
                    result.setProductEnPrice(item.getSalesPrice().toString());
                    result.setProductEnUnit(item.getCurrencyUnit());
                }
                result.setRefuseTime(item.getUpdatedTime());
            }
        }
        return result;
    }

    /**
     * 货币单位
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> currencyUnit(GeneralEnter enter) {
        Map<String, String> result = new HashMap<>();
        for (CurrencyUnitEnums item : CurrencyUnitEnums.values()) {
            result.put(item.getValue(), item.getCode());
        }
        result.remove(CurrencyUnitEnums.CN.getValue());
        return result;
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
        PageResult result = null;
        if (!enter.getServiceType()) {
            // 供应链模块
            int count = supplierChaimRosServiceMapper.scPriceHistroyCount(enter);
            if (count == 0) {
                result = PageResult.createZeroRowResult(enter);
            }
            result = PageResult.create(enter, count, supplierChaimRosServiceMapper.scPriceHistroyList(enter));
        } else {
            // 销售模块
            int count = supplierChaimRosServiceMapper.sccPriceHistroyCount(enter);
            if (count == 0) {
                result = PageResult.createZeroRowResult(enter);
            }
            result = PageResult.create(enter, count, supplierChaimRosServiceMapper.sccPriceHistroyList(enter));
        }

        return result;
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
        saveOpePriceSheet.setCurrencyType(StringUtils.isBlank(CurrencyUnitEnums.checkValue(enter.getProductFrUnit())) == true ? CurrencyUnitEnums.FR.getCode() :
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
        opePriceSheetHistory.setStatus(BomStatusEnums.NORMAL.getValue());
        opePriceSheetHistory.setPrice(new BigDecimal(enter.getProductFrPrice()));
        opePriceSheetHistory.setPriceSheetId(id);
        opePriceSheetHistory.setCurrencyType(StringUtils.isBlank(CurrencyUnitEnums.checkCode(unit)) == true ? CurrencyUnitEnums.FR.getCode() :
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

    private OpeRegionalPriceSheetHistory buildOpeRegionalPriceSheetHistroy(EditProductPriceEnter enter, Long regionalPriceSheetId, String unit) {
        OpeRegionalPriceSheetHistory opeRegionalPriceSheetHistory = new OpeRegionalPriceSheetHistory();
        opeRegionalPriceSheetHistory.setId(idAppService.getId(SequenceName.OPE_REGIONAL_PRICE_SHEET_HISTORY));
        opeRegionalPriceSheetHistory.setDr(0);
        opeRegionalPriceSheetHistory.setTenantId(0L);
        opeRegionalPriceSheetHistory.setUserId(enter.getUserId());
        opeRegionalPriceSheetHistory.setStatus(BomStatusEnums.NORMAL.getValue());
        if (StringUtils.equals(enter.getPriceType(), ProductPriceTypeEnums.SCOOTER.getCode())) {
            opeRegionalPriceSheetHistory.setAssemblyId(enter.getId());
            opeRegionalPriceSheetHistory.setPartId(0L);
            opeRegionalPriceSheetHistory.setPriceType(PriceTypeEnums.COMBINATION.getValue());
        } else {
            opeRegionalPriceSheetHistory.setAssemblyId(0L);
            opeRegionalPriceSheetHistory.setPartId(enter.getId());
            opeRegionalPriceSheetHistory.setPriceType(PriceTypeEnums.PART.getValue());
        }
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

    private OpeRegionalPriceSheet buildOpeRegionalPriceSheet(OpeRegionalPriceSheet opeRegionalPriceSheet, EditProductPriceEnter enter, String unit) {
        opeRegionalPriceSheet.setDr(0);
        opeRegionalPriceSheet.setTenantId(0L);
        opeRegionalPriceSheet.setUserId(enter.getUserId());
        opeRegionalPriceSheet.setStatus(BomStatusEnums.NORMAL.getValue());
        if (StringUtils.equals(enter.getPriceType(), ProductPriceTypeEnums.SCOOTER.getCode())) {
            opeRegionalPriceSheet.setAssemblyId(enter.getId());
            opeRegionalPriceSheet.setPartId(0L);
            opeRegionalPriceSheet.setPriceType(PriceTypeEnums.COMBINATION.getValue());
        } else {
            opeRegionalPriceSheet.setAssemblyId(0L);
            opeRegionalPriceSheet.setPartId(enter.getId());
            opeRegionalPriceSheet.setPriceType(PriceTypeEnums.PART.getValue());
        }
        opeRegionalPriceSheet.setEffectiveTime(new Date());
        opeRegionalPriceSheet.setValidFlag("1");
        if (StringUtils.equals(CurrencyUnitEnums.FR.getValue(), unit)) {
            opeRegionalPriceSheet.setSalesPrice(new BigDecimal(enter.getProductFrPrice()));
            opeRegionalPriceSheet.setCurrencyType(CurrencyUnitEnums.FR.getCode());
            opeRegionalPriceSheet.setCurrencyUnit(unit);
        } else {
            opeRegionalPriceSheet.setSalesPrice(new BigDecimal(enter.getProductEnPrice()));
            opeRegionalPriceSheet.setSalesPrice(new BigDecimal(enter.getProductEnPrice()));
            opeRegionalPriceSheet.setCurrencyType(CurrencyUnitEnums.EN.getCode());
            opeRegionalPriceSheet.setCurrencyUnit(unit);
        }
        opeRegionalPriceSheet.setStandardCurrency(CurrencyUnitEnums.CN.getCode());
        opeRegionalPriceSheet.setExchangeRate("0");
        opeRegionalPriceSheet.setCountryCode(enter.getCountry());
        opeRegionalPriceSheet.setCountryCity("0");
        opeRegionalPriceSheet.setCountryLanguage(enter.getLanguage());
        opeRegionalPriceSheet.setRevision(0);
        opeRegionalPriceSheet.setUpdatedBy(enter.getUserId());
        opeRegionalPriceSheet.setUpdatedTime(new Date());
        return opeRegionalPriceSheet;
    }
}
