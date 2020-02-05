package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.delivery.dao.EdDriverServiceMapper;
import com.redescooter.ses.web.delivery.service.express.EdDriverService;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyEnter;
import com.redescooter.ses.web.delivery.vo.DeliveryHistroyResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class EdDriverServiceImpl implements EdDriverService {

    @Autowired
    private EdDriverServiceMapper edDriverServiceMapper;

    @Override
    public PageResult<DeliveryHistroyResult> expressOrderHistroy(DeliveryHistroyEnter enter) {
        int count = edDriverServiceMapper.expressOrderHistroy(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<DeliveryHistroyResult> deliveryHistroyList = edDriverServiceMapper.expressOrderHistroyList(enter);
        return PageResult.create(enter, count, deliveryHistroyList);    }
}
