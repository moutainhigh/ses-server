package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.AddPartsPriceEnter;
import com.redescooter.ses.web.website.vo.product.PartsPriceDetailsResult;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:26 上午
 * @Description 配件价格服务
 **/
public interface PartsPriceService {
    /**
     * 创建配件价格
     *
     * @param enter
     * @return
     */
    Boolean addPartsPrice(AddPartsPriceEnter enter);

    /**
     * 获取配件价格详情
     *
     * @param enter
     */
    PartsPriceDetailsResult getPartsPriceDetails(IdEnter enter);


    /**
     * 获取配件价格列表
     *
     * @param enter
     */
    List<PartsPriceDetailsResult> getPartsPriceList(GeneralEnter enter);
}
