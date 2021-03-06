package com.redescooter.ses.web.website.dao;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.parts.PartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<ModelPriceResult> modelAndPriceList(GeneralEnter enter);

    /**
     * 根据产品型号获取产品详情
     *
     * @param enter
     * @return
     */
    List<ProductsResult> getProductDetailByModel(IdEnter enter);

    /**
     * 配件列表
     *
     * @param enter
     * @return
     */
    List<PartsDetailsResult> getPartsList(IdEnter enter);

    /**
     * 获取车辆配置列表
     *
     * @param enter
     * @return
     */
    List<ProductPartsDetailsResult> getScooterConfigList(IdEnter enter);

    /**
     * 官网车型价格列表
     */
    List<ScooterPriceListResult> getScooterPriceList();

    /**
     * 获取电池配件信息
     * @param enter
     * @return
     */
    PartsBatteryDetailsResult getPartsDetails(@Param("enter") GeneralEnter enter);
}
