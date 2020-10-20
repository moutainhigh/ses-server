package com.redescooter.ses.web.ros.service.deliveryopion;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionEditEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveEnter;
import com.redescooter.ses.web.ros.vo.deliveryopion.DeliveryOptionSaveResult;

import java.util.List;

public interface DeliveryOpionServer {

    /**
     * 保存取货配置
     *
     * @param enter
     * @return
     */
    GeneralResult save(DeliveryOptionSaveEnter enter);


    /**
     * 查询取货配置列表
     *
     * @param enter
     * @return
     */
    List<DeliveryOptionSaveResult> list(GeneralEnter enter);

    /**
     * 编辑取货配置
     *
     * @param enter
     * @param id
     * @return
     */
    GeneralResult edit(DeliveryOptionEditEnter enter, Long id);

    /**
     * 获取取货配置详情
     *
     * @param enter
     * @param id
     * @return
     */
    DeliveryOptionSaveResult details(GeneralEnter enter, Long id);
}
