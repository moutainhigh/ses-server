package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.website.AccessoryResult;
import com.redescooter.ses.web.ros.vo.website.OrderFormResult;
import com.redescooter.ses.web.ros.vo.website.OrderFormsEnter;
import com.redescooter.ses.web.ros.vo.website.ProductModelResult;
import com.redescooter.ses.web.ros.vo.website.SaveOrderFormResult;
import com.redescooter.ses.web.ros.vo.website.SaveSaleOrderEnter;
import com.redescooter.ses.web.ros.vo.website.ProductResult;
import com.redescooter.ses.web.ros.vo.website.ScootersEnter;

import java.util.List;

/**
 * @ClassName:WebsiteInquiryService
 * @description: WebsiteInquiryService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 15:55
 */
public interface WebsiteOrderFormService {

    /**
     * 车辆型号
     *
     * @param enter
     * @return
     */
    List<ProductModelResult> productModels(GeneralEnter enter);


    /**
     * 车辆型号
     *
     * @param enter
     * @return
     */
    List<ProductResult> scooters(ScootersEnter enter);

    /**
     * 配件电池类型
     *
     * @param enter
     * @return
     */
    List<AccessoryResult> accessoryBatteryList(GeneralEnter enter);

    /**
     * 后备箱
     *
     * @param enter
     * @return
     */
    List<AccessoryResult> accessoryTopCaseList(GeneralEnter enter);

    /**
     * 保存 询价单
     *
     * @param enter
     * @return
     */
    SaveOrderFormResult saveOrderForm(SaveSaleOrderEnter enter);

    /**
     * 定金支付
     *
     * @param enter
     * @return
     */
    GeneralResult payDeposit(IdEnter enter);

    /**
     * 预订单列表
     *
     * @param enter
     * @return
     */
    List<OrderFormResult> orderFormList(OrderFormsEnter enter);

    /**
     * 订单详情
     *
     * @param enter
     * @return
     */
    OrderFormResult orderForm(IdEnter enter);

    /**
     * 尾款支付
     *
     * @param enter
     * @return
     */
    GeneralResult payLastParagraph(GeneralEnter enter);
}
