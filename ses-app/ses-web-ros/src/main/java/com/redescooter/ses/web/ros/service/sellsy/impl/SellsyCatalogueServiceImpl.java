package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyCatalogueService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.catalogue.SellsyCatalogueResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SellsyCatalogueServiceImpl implements SellsyCatalogueService {

    @Autowired
    private SellsyService sellsyService;

    /**
     * 获取目录列表
     */
    @Override
    public List<SellsyCatalogueResult> queryCatalogueList() {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.AccountPrefs_GetCurrencies).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyCatalogueResult());
    }

    /**
     * 获取指定目录信息
     * 
     * @param enter
     */
    @Override
    public SellsyCatalogueResult queryCatalogueOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.AccountPrefs_GetCurrency).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        // 默认只有一种货币单位

        return JSON.parseObject(sellsyGeneralResult.getResult().toString(), SellsyCatalogueResult.class);
    }
}
