package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyCatalogueService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResut;
import com.redescooter.ses.web.ros.vo.sellsy.result.catalogue.SellsyCatalogueResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
     * 获取指定产品信息
     *
     * @param enter
     */
    @Override
    public SellsyCatalogueResult queryCatalogueOne(SellsyQueryCatalogueOneEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                        .method(SellsyMethodConstant.Catalogue_GetOne).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return (SellsyCatalogueResult)sellsyService.jsontoJavaObj(sellsyGeneralResult, SellsyCatalogueResult.class);
    }

    /**
     * 获取产品分类列表
     */
    @Override
    public void quryCatalogueCategoryList() {

    }

    /**
     * 获取产品分类列表详情
     */
    @Override
    public void quryCatalogueCategoryOne() {

    }

    /**
     * 创建产品
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SellsyIdResut createCatalogue(SellsyCreateCatalogueEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.ADD.getValue())
                        .method(SellsyMethodConstant.Catalogue_Create).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return (SellsyIdResut)sellsyService.jsontoJavaObj(sellsyGeneralResult, SellsyIdResut.class);
    }

    /**
     * 更新产品
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SellsyIdResut updateCatalogue(SellsyUpdateCatalogueEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.UPDATE.getValue())
                        .method(SellsyMethodConstant.Catalogue_Update).params(enter).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);
        return (SellsyIdResut)sellsyService.jsontoJavaObj(sellsyGeneralResult, SellsyIdResut.class);
    }

    /**
     * 删除 产品
     * @param enter
     */
    @Transactional
    @Override
    public void deleteCatalogue(SellsyDeleteCatalogueEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
                SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.DELETE.getValue())
                        .method(SellsyMethodConstant.Catalogue_Delete).params(enter).build();
        sellsyService.sellsyExecution(sellsyExecutionEnter);
    }
}
