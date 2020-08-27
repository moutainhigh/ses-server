package com.redescooter.ses.web.ros.service.sellsy;

import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.catalogue.SellsyCatalogueResult;

import java.util.List;

public interface SellsyCatalogueService {
    /**
     * 获取产品列表
     */
    public List<SellsyCatalogueResult> queryCatalogueList();

    /**
     * 获取指定产品信息
     */
    public SellsyCatalogueResult queryCatalogueOne(SellsyIdEnter enter);
}
