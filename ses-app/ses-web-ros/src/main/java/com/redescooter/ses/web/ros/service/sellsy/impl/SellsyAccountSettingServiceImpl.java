package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.web.ros.constant.SellsyConstant;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyAccountSettingService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyQueryPayMediumListResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
                .method(SellsyMethodConstant.AccountPrefs_GetCurrencies).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyCurrencyResult());
    }

    /**
     * 查询货币单位
     * 
     * @return
     */
    @Override
    public SellsyCurrencyResult queryCurrencyOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.AccountPrefs_GetCurrency).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        // 默认只有一种货币单位

        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyCurrencyResult());
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
                .method(SellsyMethodConstant.Accountdatas_GetDocLayouts).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonChildFormatting(sellsyGeneralResult, new SellsyLayoutResult());
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
                .method(SellsyMethodConstant.Accountdatas_GetPayDates).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        List<SellsyPayDateResult> sellsyPayDateResults =
            sellsyService.jsonChildFormatting(sellsyGeneralResult, new SellsyPayDateResult());

        return sellsyPayDateResults;
    }

    /**
     * 单据税据 类型 含税或者不含税
     */
    @Override
    public List<SellsyRateCategoryResult> queryRateCategoryList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetRateCategories).params(SellsyConstant.NO_PARAMETER)
                .build();

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

        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyRateCategoryResult());
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
                .method(SellsyMethodConstant.Accountdatas_GetTranslationLanguages).params(SellsyConstant.NO_PARAMETER)
                .build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyTranslationLanguageResult());
    }

    /**
     * 查询地址列表
     */
    @Override
    public List<SellsyAccountAddressResult> queryAddressList() {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.AccountPrefs_GetAddressList).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyAccountAddressResult());
    }

    /**
     * 查询指定地址
     *
     * @param enter
     * @return
     */
    @Override
    public SellsyAccountAddressResult queryAddressOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.AccountPrefs_GetAddress).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyAccountAddressResult());
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
                .method(SellsyMethodConstant.Accountdatas_GetTaxes).params(new ArrayList<>()).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        List<SellsyTaxeResult> sellsyTaxeResults = sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyTaxeResult());

        return sellsyTaxeResults;
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
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyTaxeResult());
    }

    /**
     * 获取物流运营商列表
     */
    @Override
    public List<SellsyShippingResult> queryShippingList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetShippingList).params(SellsyConstant.NO_PARAMETER).build();

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
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyShippingResult());
    }

    /**
     * 获取计量单位列表
     */
    @Override
    public List<SellsyUnitResult> queryUnitList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetUnits).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyUnitResult());
    }

    /**
     * 查询指定单位
     */
    @Override
    public SellsyUnitResult queryUnitOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Accountdatas_GetUnit).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyUnitResult());
    }

    /**
     * 查询打包方式列表
     */
    @Override
    public List<SellsyPackagingResult> queryPackagingList() {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Accountdatas_GetPackagingList).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyPackagingResult());
    }

    /**
     *打包方式详情
     * @return
     */
    @Override
    public SellsyPackagingResult queryPackagingOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Accountdatas_GetPackaging).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyPackagingResult());
    }

    /**
     * 查询公司信息
     * @return
     */
    @Override
    public SellsyCorpInfoResult queryCorpInfos() {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.AccountPrefs_GetCorpInfos).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsontoJavaObj(sellsyGeneralResult, new SellsyCorpInfoResult());
    }

    @Override
    public List<SellsyQueryPayMediumListResult> queryPayMediums() {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Accountdatas_GetPayMediums).params(SellsyConstant.NO_PARAMETER).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyQueryPayMediumListResult());
    }
}
