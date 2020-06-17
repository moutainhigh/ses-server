package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.CheckEmailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.website.*;

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
     * 车辆列表（颜色） 暂存
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
     * 修改 预订单
     *
     * @param enter
     * @return
     */
    SaveOrderFormResult editOrderForm(SaveSaleOrderEnter enter);

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
    List<OrderFormsResult> orderForms(OrderFormsEnter enter);

    /**
     * 订单详情
     *
     * @param enter
     * @return
     */
    OrderFormInfoResult orderFormInfo(IdEnter enter);

    /**
     * 尾款支付
     *
     * @param enter
     * @return
     */
    GeneralResult payLastParagraph(IdEnter enter);

    /**
     * email 校验
     *
     * @param enter
     * @return
     */
    BooleanResult checkMail(CheckEmailEnter enter);

    /**
     * 客户信息
     *
     * @param enter
     * @return
     */
    CustomerInfoResult customerInfo(GeneralEnter enter);
    /**
     * 存储邮箱
     *
     * @param enter
     */
    GeneralResult email(StorageEamilEnter enter);

}
