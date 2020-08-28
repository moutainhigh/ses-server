package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyCatalogueListEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue.SellsyQueryCatalogueOneEnter;
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
}
