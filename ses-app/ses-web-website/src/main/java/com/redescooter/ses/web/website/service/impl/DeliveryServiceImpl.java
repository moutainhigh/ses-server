package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.website.enums.DeliveryMethodEnums;
import com.redescooter.ses.web.website.service.DeliveryService;
import com.redescooter.ses.web.website.vo.delivery.DeliveryModeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 8:00 下午
 * @Description 配送方式服务实现类
 **/
@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {
    /**
     * 配送方式列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeliveryModeResult> deliveryTypelist(GeneralEnter enter) {

        List<DeliveryModeResult> list = new ArrayList<>();
        DeliveryModeResult mode = null;

        for (DeliveryMethodEnums delivery : DeliveryMethodEnums.values()) {
            mode = new DeliveryModeResult();
            mode.setCode(delivery.getCode());
            mode.setValue(delivery.getValue());
            mode.setCost(delivery.getCost());
            list.add(mode);
        }
        return list;
    }
}
