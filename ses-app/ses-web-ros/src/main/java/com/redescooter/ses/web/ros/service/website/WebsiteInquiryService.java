package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.website.AccessoryResult;
import com.redescooter.ses.web.ros.vo.website.SaveSaleOrderEnter;
import com.redescooter.ses.web.ros.vo.website.ProductColorResult;
import com.redescooter.ses.web.ros.vo.website.ProductResult;

import java.util.List;

/**
 * @ClassName:WebsiteInquiryService
 * @description: WebsiteInquiryService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 15:55
 */
public interface WebsiteInquiryService {
    /**
     * 车辆型号
     *
     * @param enter
     * @return
     */
    List<ProductResult> scooterList(GeneralEnter enter);

    /**
     * 车辆颜色
     *
     * @param enter
     * @return
     */
    List<ProductColorResult> scooterColors(GeneralEnter enter);

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
    GeneralResult saveOrderForm(SaveSaleOrderEnter enter);
}
