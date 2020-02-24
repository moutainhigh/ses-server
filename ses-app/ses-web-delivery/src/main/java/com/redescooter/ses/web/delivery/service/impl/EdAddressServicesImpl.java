package com.redescooter.ses.web.delivery.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.service.EdAddressServices;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderService;
import com.redescooter.ses.web.delivery.vo.edorder.GetAddressOfLonLatEnter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName EdAddressServicesImpl
 * @Author Jerry
 * @date 2020/02/24 11:37
 * @Description:
 */
@Service
public class EdAddressServicesImpl implements EdAddressServices {

    @Autowired
    private CorExpressOrderService expressOrderService;

    @Override
    public GeneralResult getAddressOfLonLat(GetAddressOfLonLatEnter enter) {

        CorExpressOrder update = new CorExpressOrder();
        update.setId(enter.getId());
        if (StringUtils.isNoneBlank(enter.getRecipientLatitude(), enter.getRecipientLongitude())) {
            update.setRecipientLatitude(new BigDecimal(enter.getRecipientLatitude()));
            update.setRecipientLongitude(new BigDecimal(enter.getRecipientLongitude()));
            update.setRecipientGeohash(MapUtil.geoHash(enter.getRecipientLongitude(), enter.getRecipientLatitude()));
        }
        if (StringUtils.isNoneBlank(enter.getSenderLatitude(), enter.getSenderLongitude())) {
            update.setSenderLatitude(new BigDecimal(enter.getSenderLatitude()));
            update.setSenderLongitude(new BigDecimal(enter.getSenderLongitude()));
            update.setSenderGeohash(MapUtil.geoHash(enter.getSenderLongitude(), enter.getSenderLatitude()));
        }
        update.setUpdatedBy(enter.getUserId());
        update.setUpdatedTime(new Date());
        if (StringUtils.isAllEmpty(enter.getRecipientLatitude(), enter.getRecipientLongitude(), enter.getSenderLatitude(), enter.getSenderLongitude())) {
            return new GeneralResult(enter.getRequestId());
        }
        expressOrderService.updateById(update);

        return new GeneralResult(enter.getRequestId());
    }
}
