package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.delivery.vo.TenantInforResult;
import com.redescooter.ses.web.delivery.vo.UpdateCustomerInfoEnter;
import com.redescooter.ses.web.delivery.vo.UpdateTenantConfigEnter;

/**
 * @ClassName:TenantService
 * @description: TenantService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/09 14:35
 */
public interface TenantSettingService {
    /**
     * 店铺详细信息
     *
     * @param enter
     * @return
     */
    TenantInforResult tenantInfor(GeneralEnter enter);

    /**
     * 更新店铺信息
     *
     * @param enter
     * @return
     */
    GeneralResult updateTenantConfig(UpdateTenantConfigEnter enter);

    /**
     * 更新客户信息
     *
     * @param enter
     * @return
     */
    GeneralResult updateCustomerInfo(UpdateCustomerInfoEnter enter);

    /**
     * 客户信息
     *
     * @param enter
     * @return
     */
    BaseCustomerResult customerInfor(GeneralEnter enter);

    /**
     * 关闭引导页
     *
     * @param enter
     * @return
     */
    GeneralResult closePageBootTip(GeneralEnter enter);

    /**
     * 开启所有引导页
     *
     * @param enter
     * @return
     */
    GeneralResult openPageBootTipAll(GeneralEnter enter);
}
