package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.document.SellsyQueryPayMediumListResult;

import java.util.List;

public interface SellsyAccountSettingService {

    /**
     * 查询账户的货币单位
     * 
     * @return
     */
    public List<SellsyCurrencyResult> queryCurrencyList();

    /**
     * 查询货币单位
     * 
     * @return
     */
    public SellsyCurrencyResult queryCurrencyOne(SellsyIdEnter enter);

    /**
     * 布局列表
     * 
     * @return
     */
    public List<SellsyLayoutResult> queryDocLayoutList();

    /**
     * 获取支付日期
     * 
     * @return
     */
    public List<SellsyPayDateResult> queryPayDateList();

    /**
     * 单据税据 类型 含税或者不含税
     */
    public List<SellsyRateCategoryResult> queryRateCategoryList();

    /**
     * 查询指定 税率
     * 
     * @param enter
     */
    public SellsyRateCategoryResult queryateCategoryOne(SellsyIdEnter enter);

    /**
     * 查询翻译语言
     * 
     * @return
     */
    public List<SellsyTranslationLanguageResult> queryTranslationLanguages();

    /**
     * 查询地址列表
     */
    public List<SellsyAccountAddressResult> queryAddressList();

    /**
     * 查询指定地址
     *
     * @param enter
     * @return
     */
    public SellsyAccountAddressResult queryAddressOne(SellsyIdEnter enter);

    /**
     * 税率列表查询
     * 
     * @return
     */
    public List<SellsyTaxeResult> queryTaxeList();

    /**
     * 查询指定税率
     * 
     * @return
     */
    public SellsyTaxeResult queryTaxeOne(SellsyIdEnter enter);

    /**
     * 获取物流运营商列表
     */
    public List<SellsyShippingResult> queryShippingList();

    /**
     * 查询指定物流运营商
     * 
     * @return
     */
    public SellsyShippingResult queryShippingOne(SellsyIdEnter enter);

    /**
     * 获取计量单位列表
     */
    public List<SellsyUnitResult> queryUnitList();

    /**
     * 查询指定单位
     */
    public SellsyUnitResult queryUnitOne(SellsyIdEnter enter);

    /**
     * 查询打包方式列表
     */
    public List<SellsyPackagingResult> queryPackagingList();

    /**
     *打包方式详情
     * @return
     */
    public SellsyPackagingResult queryPackagingOne(SellsyIdEnter enter);

    /**
     * 查询公司信息
     * @return
     */
    public SellsyCorpInfoResult queryCorpInfos();

    /**
     * 查询支付类型
     */
    public List<SellsyQueryPayMediumListResult> queryPayMediums();
}
