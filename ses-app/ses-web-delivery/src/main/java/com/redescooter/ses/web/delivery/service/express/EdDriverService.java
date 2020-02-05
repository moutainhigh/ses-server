package com.redescooter.ses.web.delivery.service.express;


import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyResult;

public interface EdDriverService {

    PageResult<DeliveryHistroyResult> expressOrderHistroy(DeliveryHistroyEnter enter);

}
