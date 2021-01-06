package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import com.redescooter.ses.web.website.vo.parts.PartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.ProductPartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.ProductsResult;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 9:38 上午
 * @Description 官网车辆选购服务接口
 **/
public interface ScooterPurchaseService {

    /**
     * 车辆价格列表
     *
     * @param enter
     * @return
     */
    List<ModelPriceResult> modelPriceList(GeneralEnter enter);

    /**
     * 产品详情
     *
     * @param enter
     * @return
     */
    List<ProductsResult> getProductDetails(IdEnter enter);

    /**
     * 配件列表
     *
     * @param enter
     * @return
     */
    List<PartsDetailsResult> getPartsList(StringEnter enter);

    /**
     * 车辆参数配置
     *
     * @param enter
     * @return
     */
    List<ProductPartsDetailsResult> getScooterConfigList(GeneralEnter enter);
}
