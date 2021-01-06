package com.redescooter.ses.web.website.dao;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import com.redescooter.ses.web.website.vo.product.ProductsResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 9:41 上午
 * @Description 官网车辆选购服务DAO
 **/

@Mapper
public interface ScooterPurchaseMapper {

    /**
     * 获取车型及价格
     *
     * @param enter
     * @return
     */
    List<ModelPriceResult> modelPriceList(GeneralEnter enter);

    /**
     * 根据产品型号获取产品详情
     *
     * @param enter
     * @return
     */
    List<ProductsResult> getProductDetails(IdEnter enter);
}
