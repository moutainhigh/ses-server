package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.*;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyIdResut;
import com.redescooter.ses.web.ros.vo.sellsy.result.catalogue.SellsyCatalogueResult;

import java.util.List;

public interface SellsyCatalogueService {
    /**
     * 获取产品列表
     */
    public List<SellsyCatalogueResult> queryCatalogueList(SellsyCatalogueListEnter enter);

    /**
     * 获取指定产品信息
     */
    public SellsyCatalogueResult queryCatalogueOne(SellsyQueryCatalogueOneEnter enter);

    /**
     * 获取产品分类列表
     */
    public void quryCatalogueCategoryList();

    /**
     * 获取产品分类列表详情
     */
    public void  quryCatalogueCategoryOne();

    /**
     * 创建产品
     * @param enter
     * @return
     */
    public SellsyIdResut createCatalogue(SellsyCreateCatalogueEnter enter);

    /**
     * 更新产品
     * @param enter
     * @return
     */
    public SellsyIdResut updateCatalogue(SellsyUpdateCatalogueEnter enter);

    /**
     * 删除 产品
     * @param enter
     */
    public void deleteCatalogue(SellsyDeleteCatalogueEnter enter);
}
