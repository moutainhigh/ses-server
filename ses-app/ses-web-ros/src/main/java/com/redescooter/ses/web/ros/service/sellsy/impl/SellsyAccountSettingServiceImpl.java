package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyAccountSettingService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SellsyAccountSettingServiceImpl implements SellsyAccountSettingService {

    @Autowired
    private SellsyService sellsyService;

    /**
     * 查询账户的货币单位
     * 
     * @return
     */
    @Override
    public List<SellsyCurrencyResult> queryCurrencyList() {

        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.AccountPrefs_GetCurrencies).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyCurrencyResult());
    }

    /**
     * 查询货币单位
     * 
     * @return
     */
    @Override
    public SellsyCurrencyResult queryCurrencyOne() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.AccountPrefs_GetCurrency).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        // 默认只有一种货币单位

        return JSON.parseObject(sellsyGeneralResult.getResult().toString(), SellsyCurrencyResult.class);
    }

    /**
     * 布局列表
     * 
     * @return
     */
    @Override
    public List<SellsyLayoutResult> queryDocLayoutList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetDocLayouts).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyLayoutResult());
    }

    /**
     * 获取支付日期
     * 
     * @return
     */
    @Override
    public List<SellsyPayDateResult> queryPayDateList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetPayDates).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyPayDateResult());
    }

    /**
     * 单据税据 类型 含税或者不含税
     */
    @Override
    public List<SellsyRateCategoryResult> queryRateCategoryList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetRateCategories).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyRateCategoryResult());
    }

    /**
     * 查询指定 税率
     * 
     * @param enter
     */
    @Override
    public SellsyRateCategoryResult queryateCategoryOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetRateCategory).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return JSON.parseObject(sellsyGeneralResult.getResult().toString(), SellsyRateCategoryResult.class);
    }

    /**
     * 查询翻译语言
     * 
     * @return
     */
    @Override
    public List<SellsyTranslationLanguageResult> queryTranslationLanguages() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetTranslationLanguages).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyTranslationLanguageResult());
    }

    /**
     * 查询地址列表
     */
    @Override
    public List<SellsyAddressResult> queryAddressList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.AccountPrefs_GetAddressList).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyAddressResult());
    }

    /**
     * 查询指定地址
     * 
     * @param enter
     * @return
     */
    @Override
    public SellsyAddressResult queryAddressOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.AccountPrefs_GetAddress).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return JSON.parseObject(sellsyGeneralResult.getResult().toString(), SellsyAddressResult.class);
    }

    /**
     * 税率列表查询
     * 
     * @return
     */
    @Override
    public List<SellsyTaxeResult> queryTaxeList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetTaxes).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyTaxeResult());
    }

    /**
     * 查询指定税率
     * 
     * @return
     */
    @Override
    public SellsyTaxeResult queryTaxeOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetTaxe).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return JSON.parseObject(sellsyGeneralResult.getResult().toString(), SellsyTaxeResult.class);
    }

    /**
     * 获取物流运营商列表
     */
    @Override
    public List<SellsyShippingResult> queryShippingList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetShippingList).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyShippingResult());
    }

    /**
     * 查询指定物流运营商
     * 
     * @return
     * @param enter
     */
    @Override
    public SellsyShippingResult queryShippingOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetShipping).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return JSON.parseObject(sellsyGeneralResult.getResult().toString(), SellsyShippingResult.class);
    }

    /**
     * 获取计量单位列表
     */
    @Override
    public List<SellsyUnitResult> queryUnitList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetUnits).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyUnitResult());
    }

    /**
     * 查询指定单位
     */
    @Override
    public SellsyUnitResult queryUnitOne() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetUnit).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return JSON.parseObject(sellsyGeneralResult.getResult().toString(), SellsyUnitResult.class);
    }
}
