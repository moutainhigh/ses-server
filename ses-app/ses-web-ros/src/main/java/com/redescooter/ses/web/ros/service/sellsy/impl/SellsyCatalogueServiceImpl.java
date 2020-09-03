package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyCatalogueService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyCatalogueListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyQueryCatalogueOneEnter;
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
     * 获取产品列表
     */
    @Override
    public List<SellsyCatalogueResult> queryCatalogueList(SellsyCatalogueListEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Catalogue_GetList).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return sellsyService.jsonArrayFormattingByPage(sellsyGeneralResult, new SellsyCatalogueResult());
    }

    /**
     * 获取指定货币信息
     *
     * @param enter
     */
    @Override
    public SellsyCatalogueResult queryCatalogueOne(SellsyQueryCatalogueOneEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Catalogue_GetOne).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        // 默认只有一种货币单位

        return (SellsyCatalogueResult)sellsyService.jsontoJavaObj(sellsyGeneralResult, SellsyCatalogueResult.class);
    }
}
