package com.redescooter.ses.web.ros.dao.website;

import com.redescooter.ses.web.ros.vo.website.AccessoryResult;
import com.redescooter.ses.web.ros.vo.website.OrderFormsEnter;
import com.redescooter.ses.web.ros.vo.website.OrderFormsResult;
import com.redescooter.ses.web.ros.vo.website.ProductResult;
import com.redescooter.ses.web.ros.vo.website.ScootersEnter;

import java.util.List;

/**
 * @ClassName:WebsiteInquiryServiceMapper
 * @description: WebsiteInquiryServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/13 11:57
 */
public interface WebsiteInquiryServiceMapper {

    /**
     * 电池配件
     *
     * @param type
     * @return
     */
    List<AccessoryResult> accessoryList(String type);

    /**
     * 查询车辆列表
     *
     * @param enter
     * @return
     */
    List<ProductResult> scooters(ScootersEnter enter);

    /**
     * 产品信息
     *
     * @param productId
     * @return
     */
    ProductResult queryProductById(Long productId);

    /**
     * 预订单列表
     *
     * @param enter
     * @return
     */
    List<OrderFormsResult> orderForms(OrderFormsEnter enter);
}
