package com.redescooter.ses.web.website.constant;

import com.redescooter.ses.api.common.constant.CacheConstants;

/**
 * @Author jerry
 * @Date 2021/1/25 1:03 下午
 * @Description 缓存key常量
 **/
public interface SiteCacheConstants extends CacheConstants {

    /**
     * 车型缓存
     */
    String SCOOTER_MODEL_DETAILS = "scooter_model_details";

    /**
     * 车辆产品缓存
     */
    String SCOOTER_PRODUCT_DETAILS = "scooter_product_details";

    /**
     * 车辆配件缓存
     */
    String PARTS_DETAILS = "parts_details";
}
